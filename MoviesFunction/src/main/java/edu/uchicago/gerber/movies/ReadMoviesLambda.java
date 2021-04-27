package edu.uchicago.gerber.movies;


import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadMoviesLambda {

   // private final ObjectMapper objectMapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent handleRequest (APIGatewayProxyRequestEvent requestEvent) throws JsonProcessingException {

        //simulate reading form db
        Movie movie = new Movie("8676787787d-7384ef-347884787ebac", "American Beauty", 1999 );
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(movie.toString());

    }
}