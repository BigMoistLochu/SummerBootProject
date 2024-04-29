package org.example.tomekcat.models;

public class HttpServletRequest {


    private String method;

    private String endpoint;
    private String body;

    public HttpServletRequest(String method, String endpoint, String body) {
        this.method = method;
        this.endpoint = endpoint;
        this.body = body;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
