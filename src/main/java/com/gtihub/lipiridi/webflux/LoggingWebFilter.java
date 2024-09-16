package com.gtihub.lipiridi.webflux;

import com.gtihub.lipiridi.webflux.capture.BodyCaptureExchange;
import com.gtihub.lipiridi.webflux.capture.BodyCaptureRequest;
import com.gtihub.lipiridi.webflux.capture.BodyCaptureResponse;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;

public class LoggingWebFilter implements WebFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingWebFilter.class);
    private final Collection<String> ignorePatterns;
    private final Collection<HttpLogConsumer> httpLogConsumers;

    public LoggingWebFilter() {
        this(null, null);
    }

    public LoggingWebFilter(Collection<String> ignorePatterns, Collection<HttpLogConsumer> httpLogConsumers) {
        this.ignorePatterns = ignorePatterns == null ? Collections.emptyList() : ignorePatterns;
        this.httpLogConsumers = httpLogConsumers == null ? Collections.emptyList() : httpLogConsumers;
    }

    @Nonnull
    @Override
    public Mono<Void> filter(@Nonnull ServerWebExchange serverWebExchange, @Nonnull WebFilterChain webFilterChain) {
        if (isIgnored(serverWebExchange)) {
            return webFilterChain.filter(serverWebExchange);
        }

        BodyCaptureExchange bodyCaptureExchange = new BodyCaptureExchange(serverWebExchange);
        return webFilterChain.filter(bodyCaptureExchange).doOnEach(voidInstance -> {
            HttpLog httpLog = createLog(bodyCaptureExchange);
            httpLogConsumers.forEach(httpLogConsumer -> httpLogConsumer.accept(httpLog));
            LOG.info(JsonUtils.getJsonString(httpLog));
        });
    }

    private boolean isIgnored(ServerWebExchange serverWebExchange) {
        String path = serverWebExchange.getRequest().getPath().toString();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        return ignorePatterns.stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }

    private HttpLog createLog(final BodyCaptureExchange bodyCaptureExchange) {
        BodyCaptureRequest bodyCaptureRequest = bodyCaptureExchange.getRequest();
        BodyCaptureResponse bodyCaptureResponse = bodyCaptureExchange.getResponse();

        return new HttpLog(
                bodyCaptureRequest.getURI(),
                bodyCaptureRequest.getPath(),
                bodyCaptureRequest.getMethod(),
                bodyCaptureResponse.getStatusCode(), bodyCaptureRequest.getQueryParams(),
                bodyCaptureExchange.getFullFormData(),
                bodyCaptureRequest.getHeaders(),
                bodyCaptureRequest.getFullBody(),
                bodyCaptureResponse.getHeaders(),
                bodyCaptureResponse.getFullBody()
        );
    }

}

