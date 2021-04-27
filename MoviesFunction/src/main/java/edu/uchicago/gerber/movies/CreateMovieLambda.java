package edu.uchicago.gerber.movies;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

public class CreateMovieLambda {

   private final ObjectMapper objectMapper = new ObjectMapper();

   private final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();
   private final DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

    public APIGatewayProxyResponseEvent handleRequest (APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {

      Table moviesTable = dynamoDB.getTable(System.getenv("MOVIES_TABLE"));

      Movie movie = objectMapper.readValue(requestEvent.getBody(), Movie.class);
      Item item = new Item()
              .withPrimaryKey("id", movie.getId())
              .withString("title", movie.getTitle())
              .withInt("year", movie.getYear());

       moviesTable.putItem(item);


        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(movie.toString());

    }
}
