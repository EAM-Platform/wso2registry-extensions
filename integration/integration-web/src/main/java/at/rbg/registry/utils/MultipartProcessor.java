package at.rbg.registry.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this was extracted from Jettys MultiPartFilter.
 * It is used for file uploads via servlet.
 * 
 * @author ubba
 *
 */
public class MultipartProcessor {
	public static int CR = '\015';
	public static int LF = '\012';
	private static final Logger log = LoggerFactory.getLogger(MultipartProcessor.class);

	public static InputStream parseInputStream(InputStream in, String content_type) throws UnsupportedEncodingException {
		BufferedInputStream ins = new BufferedInputStream(in);
		@SuppressWarnings("unused")
		String filename = null;
		
		String boundary = "--"
				+ value(content_type.substring(content_type
						.indexOf("boundary=")));
		byte[] byteBoundary = (boundary + "--").getBytes("ISO-8859-1");

		try {
			// Get first boundary
			byte[] bytes = MultipartProcessor.readLine(ins);
			String line = bytes == null ? null : new String(bytes, "UTF-8");
			if (line == null || !line.equals(boundary)) {
				throw new IOException("Missing initial multi part boundary");
			}

			// Read each part
			boolean lastPart = false;
			String content_disposition = null;
			while (!lastPart) {
				while (true) {
					bytes = MultipartProcessor.readLine(ins);
					// If blank line, end of part headers
					if (bytes == null || bytes.length == 0)
						break;
					line = new String(bytes, "UTF-8");

					// place part header key and value in map
					int c = line.indexOf(':', 0);
					if (c > 0) {
						String key = line.substring(0, c).trim()
								.toLowerCase();
						String value = line.substring(c + 1, line.length())
								.trim();
						if (key.equals("content-disposition"))
							content_disposition = value;
					}
				}
				// Extract content-disposition
				boolean form_data = false;
				if (content_disposition == null) {
					throw new IOException("Missing content-disposition");
				}

				StringTokenizer tok = new StringTokenizer(
						content_disposition, ";");
				String name = null;
				while (tok.hasMoreTokens()) {
					String t = tok.nextToken().trim();
					String tl = t.toLowerCase();
					if (t.startsWith("form-data"))
						form_data = true;
					else if (tl.startsWith("name="))
						name = value(t);
					else if (tl.startsWith("filename="))
						filename = value(t);
				}

				// Check disposition
				if (!form_data) {
					continue;
				}
				if (name == null || name.length() == 0) {
					continue;
				}

				OutputStream out = null;
				try {
					out = new ByteArrayOutputStream();

					int state = -2;
					int c;
					boolean cr = false;
					boolean lf = false;

					// loop for all lines`
					while (true) {
						int b = 0;
						while ((c = (state != -2) ? state : ins.read()) != -1) {
							state = -2;
							// look for CR and/or LF
							if (c == 13 || c == 10) {
								if (c == 13)
									state = ins.read();
								break;
							}
							// look for boundary
							if (b >= 0 && b < byteBoundary.length
									&& c == byteBoundary[b])
								b++;
							else {
								// this is not a boundary
								if (cr)
									out.write(13);
								if (lf)
									out.write(10);
								cr = lf = false;
								if (b > 0)
									out.write(byteBoundary, 0, b);
								b = -1;
								out.write(c);
							}
						}
						// check partial boundary
						if ((b > 0 && b < byteBoundary.length - 2)
								|| (b == byteBoundary.length - 1)) {
							if (cr)
								out.write(13);
							if (lf)
								out.write(10);
							cr = lf = false;
							out.write(byteBoundary, 0, b);
							b = -1;
						}
						// boundary match
						if (b > 0 || c == -1) {
							if (b == byteBoundary.length)
								lastPart = true;
							if (state == 10)
								state = -2;
							break;
						}
						// handle CR LF
						if (cr)
							out.write(13);
						if (lf)
							out.write(10);
						cr = (c == 13);
						lf = (c == 10 || state == 10);
						if (state == 10)
							state = -2;
					}
				} finally {
					out.close();
				}

				bytes = ((ByteArrayOutputStream) out).toByteArray();
			}
			return new ByteArrayInputStream(bytes);
			
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			// be happy
		}
		return null;
	}
	
	private static String value(String nameEqualsValue) {
		String value = nameEqualsValue.substring(
				nameEqualsValue.indexOf('=') + 1).trim();
		int i = value.indexOf(';');
		if (i > 0)
			value = value.substring(0, i);
		if (value.startsWith("\"")) {
			value = value.substring(1, value.indexOf('"', 1));
		} else {
			i = value.indexOf(' ');
			if (i > 0)
				value = value.substring(0, i);
		}
		return value;
	}

	private static byte[] readLine(InputStream in) throws IOException {
		byte[] buf = new byte[256];

		int i = 0;
		int loops = 0;
		int ch = 0;

		while (true) {
			ch = in.read();
			if (ch < 0)
				break;
			loops++;

			// skip a leading LF's
			if (loops == 1 && ch == LF)
				continue;

			if (ch == CR || ch == LF)
				break;

			if (i >= buf.length) {
				byte[] old_buf = buf;
				buf = new byte[old_buf.length + 256];
				System.arraycopy(old_buf, 0, buf, 0, old_buf.length);
			}
			buf[i++] = (byte) ch;
		}

		if (ch == -1 && i == 0)
			return null;

		// skip a trailing LF if it exists
		if (ch == CR && in.available() >= 1 && in.markSupported()) {
			in.mark(1);
			ch = in.read();
			if (ch != LF)
				in.reset();
		}

		byte[] old_buf = buf;
		buf = new byte[i];
		System.arraycopy(old_buf, 0, buf, 0, i);

		return buf;
	}

}
