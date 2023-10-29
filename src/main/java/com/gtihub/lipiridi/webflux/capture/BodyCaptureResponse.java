package com.gtihub.lipiridi.webflux.capture;

import jakarta.annotation.Nonnull;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BodyCaptureResponse extends ServerHttpResponseDecorator {

    private final StringBuilder body = new StringBuilder();

    public BodyCaptureResponse(ServerHttpResponse delegate) {
        super(delegate);
    }

    @Nonnull
    @Override
    public Mono<Void> writeWith(@Nonnull Publisher<? extends DataBuffer> body) {
        Flux<DataBuffer> buffer = Flux.from(body);
        return super.writeWith(buffer.doOnNext(this::capture));
    }

    private void capture(DataBuffer buffer) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.readableByteCount());
        buffer.toByteBuffer(byteBuffer);
        this.body.append(StandardCharsets.UTF_8.decode(byteBuffer));
    }

    public String getFullBody() {
        return this.body.toString();
    }

}
