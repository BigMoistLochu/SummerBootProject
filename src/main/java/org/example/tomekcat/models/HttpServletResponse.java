package org.example.tomekcat.models;

public class HttpServletResponse {



    public static String getResponse(String body){

        return "HTTP/1.1 200 OK" + System.lineSeparator() +
                "content-type: application/json; charset=utf-8" + System.lineSeparator() +
                "Set-Cookie: username=johndoe; Max-Age=3600" + System.lineSeparator() +
                "Access-Control-Allow-Origin: *"+System.lineSeparator()+
                "Access-Control-Allow-Methods: POST"+System.lineSeparator()+
                "Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type"+System.lineSeparator()+
                System.lineSeparator() +//empty line for separate body from header
                body;
    }
}
