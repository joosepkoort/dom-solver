package com.dom.solver.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler
        implements ResponseErrorHandler {

    public static final String SERVER_DOWN_MESSAGE = "server down.";

    public static final String GAME_OVER_MESSAGE = "game over.";

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {
        return (
                httpResponse.getStatusCode().is4xxClientError()
                        || httpResponse.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        if (httpResponse.getStatusCode().is5xxServerError()) {
            // handle SERVER_ERROR
            throw new GameOverException(SERVER_DOWN_MESSAGE);
        } else if (httpResponse.getStatusCode().is4xxClientError() && httpResponse.getStatusCode() == HttpStatus.GONE) {
            // handle CLIENT_ERROR
            throw new GameOverException(GAME_OVER_MESSAGE);
        }
    }
}