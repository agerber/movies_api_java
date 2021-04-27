package edu.uchicago.gerber.movies;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateMovieLambda {

   private final ObjectMapper objectMapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent handleRequest (APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {

        Movie movie = objectMapper.readValue(requestEvent.getBody(), Movie.class);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(movie.toString());

    }
}
