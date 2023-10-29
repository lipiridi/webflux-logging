package com.gtihub.lipiridi.webflux;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.RequestPath;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpLog {

    private URI uri;
    private RequestPath path;
    private HttpMethod method;
    private HttpStatusCode statusCode;
    private MultiValueMap<String, String> queryParams;
    private Map<String, List<String>> formData;
    private HttpHeaders requestHeaders;
    private String requestBody;
    private HttpHeaders responseHeaders;
    private String responseBody;

    public HttpLog() {
    }

    public HttpLog(URI uri, RequestPath path, HttpMethod method, HttpStatusCode statusCode, MultiValueMap<String, String> queryParams, Map<String, List<String>> formData, HttpHeaders requestHeaders, String requestBody, HttpHeaders responseHeaders, String responseBody) {
        this.uri = uri;
        this.path = path;
        this.method = method;
        this.queryParams = queryParams;
        this.formData = formData;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.statusCode = statusCode;
    }

    @JsonGetter("path")
    public String getJsonPath() {
        return path == null ? null : path.toString();
    }

    @JsonGetter("method")
    public String getJsonMethod() {
        return method == null ? null : method.toString();
    }

    @JsonGetter("statusCode")
    public Integer getJsonStatusCode() {
        return statusCode == null ? null : statusCode.value();
    }

    @JsonGetter("requestBody")
    public Object getJsonRequestBody() {
        return getProcessedBody(requestBody);
    }

    @JsonGetter("responseBody")
    public Object getJsonResponseBody() {
        return getProcessedBody(responseBody);
    }

    @Nullable
    private Object getProcessedBody(final String body) {
        if (!StringUtils.hasLength(body)) {
            return null;
        }

        JsonNode jsonNode = JsonUtils.getContainerJsonNode(body);
        return jsonNode == null ? body : jsonNode;
    }

    public URI getUri() {
        return this.uri;
    }

    public RequestPath getPath() {
        return this.path;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public MultiValueMap<String, String> getQueryParams() {
        return this.queryParams;
    }

    public Map<String, List<String>> getFormData() {
        return this.formData;
    }

    public HttpHeaders getRequestHeaders() {
        return this.requestHeaders;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public HttpHeaders getResponseHeaders() {
        return this.responseHeaders;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public HttpStatusCode getStatusCode() {
        return this.statusCode;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public void setPath(RequestPath path) {
        this.path = path;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setQueryParams(MultiValueMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public void setFormData(Map<String, List<String>> formData) {
        this.formData = formData;
    }

    public void setRequestHeaders(HttpHeaders requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void setResponseHeaders(HttpHeaders responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HttpLog httpLog = (HttpLog) o;

        if (!Objects.equals(uri, httpLog.uri)) return false;
        if (!Objects.equals(method, httpLog.method)) return false;
        return Objects.equals(statusCode, httpLog.statusCode);
    }

    @Override
    public int hashCode() {
        int result = uri != null ? uri.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (statusCode != null ? statusCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HttpLog{" +
                "uri=" + uri +
                ", method=" + method +
                ", statusCode=" + statusCode +
                '}';
    }
}
