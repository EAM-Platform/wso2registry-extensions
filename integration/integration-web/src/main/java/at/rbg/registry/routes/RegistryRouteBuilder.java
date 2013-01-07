package at.rbg.registry.routes;
import java.io.InputStream;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.language.ConstantExpression;
import org.apache.camel.spring.Main;

public class RegistryRouteBuilder extends RouteBuilder {

	/**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) {
        try {
			Main.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void configure() throws Exception {
		// unknown exceptions
		errorHandler(deadLetterChannel("{{otherErrors}}"));
	
		from("cxf:bean:dependencyQueryEndpoint?dataFormat=POJO").setExchangePattern(ExchangePattern.InOut)
			.to("dependencyQueryRequest")
			.to("direct:queryRegistry")
			.to("dependencyQueryResponse")
			;
		
		from("cxf:bean:registryServiceEndpoint?dataFormat=POJO").setExchangePattern(ExchangePattern.InOut)
		.to("registryRequest")
		;
		
		from("{{eamp.importHostprogramme.service}}").
			setHeader("EXCELFILETYPE", new ConstantExpression("hostexcel")).
			to("direct:excel");	

		from("{{eamp.importInfrastructure.service}}").
		setHeader("EXCELFILETYPE", new ConstantExpression("infrastructureexcel")).
		to("direct:excel");	

		from("direct:excel").
		doTry().
				convertBodyTo(InputStream.class). 
				to("excelProcessor").
				to("registryWritingProcessor").
			doCatch(Exception.class)
				// and catch all other exceptions
				// they are handled by default (ie handled = true)
				.to("failureResponseProcessor")
     	.end();
		
		from("direct:queryRegistry").id("queryRegistryRoute").setExchangePattern(ExchangePattern.InOut).
		doTry().
				to("registryReadingProcessor").
			doCatch(Exception.class)
				// and catch all other exceptions
				// they are handled by default (ie handled = true)
				.to("failureResponseProcessor")
     	.end();

		// prefetch for cache warmup: setting is same as cache
		from("timer:prefetch?period=3600000&delay=1000").
			to("preFetchProcessor").
			to("registryReadingProcessor")
			;
		
		
		// file based only for testing
		from("file:{{import.hostExcelStageDir}}?noop=true").autoStartup(false).
			setHeader("EXCELFILETYPE", new ConstantExpression("hostexcel")).
			to("direct:excel");
		
		from("file:{{import.excelStageDir}}?noop=true").autoStartup(false).
			to("direct:excel");
		
		from("file:{{import.wsdlStageDir}}?noop=true").autoStartup(false).
		doTry().
				convertBodyTo(InputStream.class). 
				to("wsdlProcessor").
			doCatch(Exception.class)
				// and catch all other exceptions
				// they are handled by default (ie handled = true)
				.to("failureResponseProcessor")
     	.end();

		// we just use a trigger
		from("file:{{import.wsdlStageDir}}?noop=true").autoStartup(false).
		doTry().
				to("renameHelper").
			doCatch(Exception.class)
				// and catch all other exceptions
				// they are handled by default (ie handled = true)
				.to("failureResponseProcessor")
     	.end();

 	}
}
