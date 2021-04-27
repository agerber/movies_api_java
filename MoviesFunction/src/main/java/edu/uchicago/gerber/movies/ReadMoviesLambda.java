package edu.uchicago.gerber.movies;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ReadMoviesLambda {

   private final ObjectMapper objectMapper = new ObjectMapper();

    private final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().build();

    public APIGatewayProxyResponseEvent handleRequest (APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {


        ScanRequest scanRequest = new ScanRequest().withTableName(System.getenv("MOVIES_TABLE"));
        ScanResult scanResult = amazonDynamoDB.scan(scanRequest);

       List<Movie> movies = scanResult.getItems().stream()
                //map transform item - movie
                .map(item -> new Movie(
                        item.get("id").getS(),
                        item.get("title").getS(),
                        Integer.parseInt(item.get("year").getN())
                ))
                .collect(Collectors.toList());
                //collect to a List

        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(objectMapper.writeValueAsString(movies));

    }
}