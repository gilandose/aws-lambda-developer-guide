package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
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
    var client = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder()
            .GET()
            .version(HttpClient.Version.HTTP_2)
            .uri(URI.create("https://checkip.amazonaws.com"))
            .timeout(Duration.ofSeconds(15))
            .build();
    var logger = context.getLogger();

    try {
      var fresponse = client.send(request, HttpResponse.BodyHandlers.ofString());
      String output = String.format("{ \"message\": \"hello world yeet from %s\", \"location\": \"%s\" }",yeet.getYeet(), fresponse.body());
      Util.logEnvironment(event, context, gson);
      return new APIGatewayProxyResponseEvent()
              .withBody(output)
              .withHeaders(headers)
              .withStatusCode(fresponse.statusCode())
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