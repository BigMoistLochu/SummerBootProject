package org.example.tomekcat.requestlayer;

import org.example.tomekcat.models.HttpCookie;

import java.util.List;
import java.util.Map;

public class HttpServletTomekRequest {

    //tutaj rozbijamy requesta z bajtow na Metode,Headery

    //Method(GET,POST,PUT,DELETE...)
    //Header-Name (mapa np: key: Content-Type: json/xd
    //Cookie(oddzielna klasa na Cookie

    private String method;
    private Map<String,String> headers;

    private List<HttpCookie> httpCookies;

    private String boody;




}
