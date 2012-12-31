package at.rbg.registry.publish.plugin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.wsdl.Definition;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.extensions.utils.CommonUtil;

/**
 * unzip and check for the contents: wsdl, xsd
 * 
 * if any of these are found, the registry paths are calculated for the metadata
 * entry (web service) and the wsdl artifact.
 * 
 * xsds are not needed to be handled here during publish.
 * 
 */
public class UnzipUtil {
	private static String tempFilePrefix = "wsdl";
	private static String archiveExtension = "zip";
	private static String wsdlExtension = "wsdl";
	private static String xsdExtension = "xsd";

	public static List<InfoContainer> unzip(File f) throws RegistryException {
		List<InfoContainer> retval = new ArrayList<InfoContainer>();

		try {
			InputStream in = new FileInputStream(f);
			Stack<File> fileList = new Stack<File>();
			List<String> uriList = new LinkedList<String>();

			int wsdlPathDepth = Integer.MAX_VALUE;
			int xsdPathDepth = Integer.MAX_VALUE;
			File tempFile = File.createTempFile(tempFilePrefix,
					archiveExtension);
			File tempDir = new File(tempFile.getAbsolutePath().substring(
					0,
					tempFile.getAbsolutePath().length()
							- archiveExtension.length()));
			try {
				BufferedOutputStream out = new BufferedOutputStream(
						new FileOutputStream(tempFile));
				try {
					byte[] contentChunk = new byte[1024];
					int byteCount;
					while ((byteCount = in.read(contentChunk)) != -1) {
						out.write(contentChunk, 0, byteCount);
					}
					out.flush();
				} finally {
					out.close();
				}
				ZipEntry entry;

				makeDir(tempDir);
				ZipInputStream zs;
				List<String> wsdlUriList = new LinkedList<String>();
				List<String> xsdUriList = new LinkedList<String>();
				zs = new ZipInputStream(new FileInputStream(tempFile));
				try {
					entry = zs.getNextEntry();
					while (entry != null) {
						String entryName = entry.getName();
						FileOutputStream os;
						File file = new File(tempFile.getAbsolutePath()
								.substring(
										0,
										tempFile.getAbsolutePath().length()
												- archiveExtension.length())
								+ File.separator + entryName);
						if (entry.isDirectory()) {
							if (!file.exists()) {
								makeDirs(file);
								fileList.push(file);
							}
							entry = zs.getNextEntry();
							continue;
						}
						File parentFile = file.getParentFile();
						if (!parentFile.exists()) {
							makeDirs(parentFile);
						}
						os = new FileOutputStream(file);
						try {
							fileList.push(file);
							byte[] contentChunk = new byte[1024];
							int byteCount;
							while ((byteCount = zs.read(contentChunk)) != -1) {
								os.write(contentChunk, 0, byteCount);
							}
						} finally {
							os.close();
						}
						zs.closeEntry();
						entry = zs.getNextEntry();
						if (entryName != null
								&& entryName.toLowerCase().endsWith(
										wsdlExtension)) {
							String uri = tempFile.toURI().toString();
							uri = uri.substring(0, uri.length()
									- archiveExtension.length())
									+ "/" + entryName;
							if (uri.startsWith("file:")) {
								uri = uri.substring(5);
							}
							while (uri.startsWith("/")) {
								uri = uri.substring(1);
							}
							uri = "file:///" + uri;
							if (uri.endsWith("/")) {
								uri = uri.substring(0, uri.length() - 1);
							}
							int uriPathDepth = uri.split("/").length;
							if (uriPathDepth < wsdlPathDepth) {
								wsdlPathDepth = uriPathDepth;
								wsdlUriList = new LinkedList<String>();
							}
							if (wsdlPathDepth == uriPathDepth) {
								wsdlUriList.add(uri);
							}
						} else if (entryName != null
								&& entryName.toLowerCase().endsWith(
										xsdExtension)) {
							String uri = tempFile.toURI().toString();
							uri = uri.substring(0, uri.length()
									- archiveExtension.length())
									+ "/" + entryName;
							if (uri.startsWith("file:")) {
								uri = uri.substring(5);
							}
							while (uri.startsWith("/")) {
								uri = uri.substring(1);
							}
							uri = "file:///" + uri;
							if (uri.endsWith("/")) {
								uri = uri.substring(0, uri.length() - 1);
							}
							int uriPathDepth = uri.split("/").length;
							if (uriPathDepth < xsdPathDepth) {
								xsdPathDepth = uriPathDepth;
								xsdUriList = new LinkedList<String>();
							}
							if (xsdPathDepth == uriPathDepth) {
								xsdUriList.add(uri);
							}
						} else if (entryName != null) {
							String uri = tempFile.toURI().toString();
							uri = uri.substring(0, uri.length()
									- archiveExtension.length())
									+ "/" + entryName;
							if (uri.startsWith("file:")) {
								uri = uri.substring(5);
							}
							while (uri.startsWith("/")) {
								uri = uri.substring(1);
							}
							uri = "file:///" + uri;
							if (uri.endsWith("/")) {
								uri = uri.substring(0, uri.length() - 1);
							}
							uriList.add(uri);
						}
					}
				} finally {
					zs.close();
				}
				if (wsdlUriList.isEmpty() && xsdUriList.isEmpty()) {
					throw new RegistryException(
							"No WSDLs or Schemas found in the given WSDL archive");
				}
				if (wsdlPathDepth < Integer.MAX_VALUE) {
					for (String uri : wsdlUriList) {
						InfoContainer ic = parseWsdl(uri);
						retval.add(ic);
					}
				}
				if (xsdPathDepth < Integer.MAX_VALUE) {
					for (String uri : xsdUriList) {
						parseXsd(uri);
					}
				}
			} finally {
				in.close();
			}
			try {
				delete(tempFile);
				while (!fileList.isEmpty()) {
					delete(fileList.pop());
				}
				delete(tempDir);
			} catch (IOException e) {
				throw new RegistryException(
						"Unable to cleanup temporary files", e);
			}
		} catch (IOException e) {
			throw new RegistryException(
					"Error occurred while unpacking Governance Archive", e);
		}
		return retval;
	}

	public static void delete(File file) throws IOException {
		if (file != null && file.exists() && !file.delete()) {
		}
	}

	private static void makeDir(File file) throws IOException {
		if (file != null && !file.exists() && !file.mkdir()) {
		}
	}

	private static void makeDirs(File file) throws IOException {
		if (file != null && !file.exists() && !file.mkdirs()) {
		}
	}

	@SuppressWarnings("rawtypes")
	private static InfoContainer parseWsdl(String filename) {
		Definition wsdlDefinition = null;

		WSDLReader wsdlReader;
		try {
			InfoContainer ic = new InfoContainer();
			wsdlReader = WSDLFactory.newInstance().newWSDLReader();
			wsdlDefinition = wsdlReader.readWSDL(filename);
			String tns = wsdlDefinition.getTargetNamespace();
			String tempNamespace = CommonUtil
					.derivePathFragmentFromNamespace(tns);
			int idx = filename.lastIndexOf("/");
			String artifactPath = tempNamespace + filename.substring(idx + 1);
			ic.setWsdlPath(artifactPath);
			Map services = wsdlDefinition.getServices();
			Iterator it = services.values().iterator();
			while (it.hasNext()) {
				Service service = (Service) it.next();
				String serviceName = service.getQName().getLocalPart();
				String resPath = tempNamespace + serviceName;
				ic.setMetadataPath(resPath);
				return ic;
			}
		} catch (WSDLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	private static String parseXsd(String uri) {
		return null;

	}
}
