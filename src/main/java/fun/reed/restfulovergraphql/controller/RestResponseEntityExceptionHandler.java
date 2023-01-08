package fun.reed.restfulovergraphql.controller;

import fun.reed.restfulovergraphql.ratelimit.OverLimitException;
import manifold.graphql.rt.api.request.GqlRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.UnknownHostException;

/**
 *  RestResponseEntityExceptionHandler
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @Value("${graphql-server.endpoint}")
    private String GRAPHQL_ENDPOINT;

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
        final String error = "{\"Error\": \"Incorrect continent or country code, please visit https://www.iban.com/country-codes for correct code.\"}";
        LOG.info(error, ex);
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({UnknownHostException.class})
    public ResponseEntity<Object> handleNetworkingException(Exception ex, WebRequest request) {
        final String error = "{\"Error\": \"Unable to connect to " + GRAPHQL_ENDPOINT + ", please try again later.\"}";
        LOG.error(error, ex);
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({GqlRequestException.class})
    public ResponseEntity<Object> handleGraphQLException(Exception ex, WebRequest request) {
        final String error = "{\"Error\": \"" + GRAPHQL_ENDPOINT + " returns error\"}";
        LOG.error(error, ex);
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({OverLimitException.class})
    public ResponseEntity<Object> handleOverLimitException(Exception ex, WebRequest request) {
        final String error = "{\"Error\": \"" + ex.getMessage() + "\"}";
        LOG.info(error, ex);
        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.TOO_MANY_REQUESTS);
    }

}
