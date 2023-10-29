package com.gtihub.lipiridi.webflux.capture;

import jakarta.annotation.Nonnull;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BodyCaptureRequest extends ServerHttpRequestDecorator {

    private final StringBuilder body = new StringBuilder();

    public BodyCaptureRequest(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Nonnull
    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::capture);
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
