package br.com.scheiner.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.google.common.net.MediaType;

import br.com.scheiner.camel.dto.InvestmentRequest;
import br.com.scheiner.camel.dto.InvestmentResponse;

@Component
public class RestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		// restConfiguration().host("0.0.0.0").port(9090).bindingMode(RestBindingMode.auto);

		restConfiguration().component("netty-http").bindingMode(RestBindingMode.json)
				// and output using pretty print
				.dataFormatProperty("prettyPrint", "true")
				// setup context path and port number that netty will use
				.contextPath("/").port(9090)
				// add swagger api-doc out of the box
				.apiContextPath("/api-doc").apiProperty("api.title", "User API").apiProperty("api.version", "1.2.3")
				// and enable CORS
				.apiProperty("cors", "true");

		rest("/user")
		.description("User API")
		.post()
		.type(InvestmentRequest.class)
		.route()
		.routeId("post-user")
		.log("User received: ${body}")
		.wireTap("direct:sendFindData") // assincono
		.to("direct:response")
		.end();
		
		from("direct:response") // response message
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				  Object object = exchange.getIn().getBody();
				  System.out.println(object);
				  exchange.getIn().setHeader("Content-Type", MediaType.JSON_UTF_8);
				  exchange.getIn().setBody(new InvestmentResponse("Testando"));	
			}	
			
		})
		.log("TESTE")
        .end();
		
		
		
		from("direct:sendFindData") 
		.marshal().json(JsonLibrary.Jackson)
		
        .to("http://localhost:8080/api/v1/accounts/123/investment?bridgeEndpoint=true") 
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .setBody(simple("${body}"))
        .unmarshal(new JacksonDataFormat(InvestmentResponse.class))
        .errorHandler(defaultErrorHandler().maximumRedeliveries(2).redeliveryDelay(5000).logRetryAttempted(true)
        .logExhausted(true))
        .end();
		

	}
	//.choice().when().simple("header.HTTP_RESPONSE_CODE >= 500").errorHandler(defaultErrorHandler().maximumRedeliveries(3).redeliveryDelay(5000).logRetryAttempted(true).logExhausted(true)).end();

}