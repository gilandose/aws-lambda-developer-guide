package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

// Handler value: example.HandlerApiGateway
public class HandlerApiGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  @Override
  public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
  {
    var yeet = gson.fromJson(event.getBody(),Yeet.class);
    var headers = new HashMap<String,String>();
    headers.put("Content-Type", "application/json");
    headers.put("X-Custom-Header", "application/json");
    var logger = context.getLogger();
    try {
      String output = String.format("{ \"message\": \"hello world yeet from %s\" }",yeet.getYeet());
      Util.logEnvironment(event, context, gson);
      return new APIGatewayProxyResponseEvent()
              .withBody(output)
              .withHeaders(headers)
              .withStatusCode(200)
              .withIsBase64Encoded(false);
    } catch (Exception e) {
      return new APIGatewayProxyResponseEvent()
              .withBody("{}")
              .withHeaders(headers)
              .withStatusCode(500)
              .withIsBase64Encoded(false);
    }
  }
}