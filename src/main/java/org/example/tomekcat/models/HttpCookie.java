package org.example.tomekcat.models;

import java.util.Map;

public class HttpCookie {

    private Map<String,String> cookie;

    public Map<String, String> getCookie() {
        return cookie;
    }

    public void setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }
}
