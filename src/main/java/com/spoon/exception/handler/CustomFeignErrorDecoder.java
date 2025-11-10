 
package com.spoon.exception.handler;

/**
 *
 * @author User
 */
import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        if (status == HttpStatus.NOT_FOUND) {
            return new RecipeNotFoundException("The requested recipe was not found on the external API.");
        }
        
        if (status.is5xxServerError()) {
             return new ExternalApiException("External service error occurred: " + response.status());
        }
        
         return defaultErrorDecoder.decode(methodKey, response);
    }
}