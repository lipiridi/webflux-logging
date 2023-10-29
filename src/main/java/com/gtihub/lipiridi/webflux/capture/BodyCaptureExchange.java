package com.gtihub.lipiridi.webflux.capture;

import jakarta.annotation.Nonnull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


public class BodyCaptureExchange extends ServerWebExchangeDecorator {

    private final BodyCaptureRequest bodyCaptureRequest;
    private final BodyCaptureResponse bodyCaptureResponse;
    private final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

    public BodyCaptureExchange(ServerWebExchange exchange) {
        super(exchange);
        this.bodyCaptureRequest = new BodyCaptureRequest(exchange.getRequest());
        this.bodyCaptureResponse = new BodyCaptureResponse(exchange.getResponse());
    }

    @Nonnull
    @Override
    public BodyCaptureRequest getRequest() {
        return bodyCaptureRequest;
    }

    @Nonnull
    @Override
    public BodyCaptureResponse getResponse() {
        return bodyCaptureResponse;
    }

    @Nonnull
    @Override
    public Mono<MultiValueMap<String, String>> getFormData() {
        return super.getFormData().doOnNext(this::capture);
    }

    public Map<String, List<String>> getFullFormData() {
        return formData;
    }

    private void capture(MultiValueMap<String, String> multiValueMap) {
        formData.putAll(multiValueMap);
    }

}
