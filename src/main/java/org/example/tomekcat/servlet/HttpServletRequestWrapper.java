package org.example.tomekcat.servlet;

import org.example.tomekcat.models.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServletRequestWrapper {

    /**
     * Wyjasnienie jak dziala:
     * Kazdy bajt jest zczytywany z BufferedReader i analizowany osobno
     * Jesli bajt jest rowny CR to potem sprawdzany jest LF to znaczy ze zostal dokonany przeskos na nowa linie
     * Jesli dokonaly sie dwa przeskoki na raz to znaczy ze miedzy headerami jest pusta linia i reszta to bedzie body
     * Jesli bajt nie jest rowny CR to wrzucany jest jako char do StringBuildera
     *
     * Example:
     *
     * "
     * POST
     *
     * "
     * Ten przyklad to ciag bajtow: 120(P),117(O),123(S),124(T),13(carriage return),10(new line)
     * a w stringbuilderze bedzie to wygladalo tak : "POST\r\n"
     *W przypadku gdy request posiada body to wykonywana jest jeszcze jedna funkcja ktora bedzie zbierac
     * tyle bajtow ile rozmiar Content-Length
     * @param reader pakiet bajtow wyslanych przez przegladarke(klienta)
     * @return zwraca przekonwertowanego stringa z bajtow
     */
    public static StringBuilder converterBytesToStringRequest(BufferedReader reader) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        int _byte;
        int CR = 0x0D; // 13 (carriage return)
        int LF = 0x0A; // 10 (new line)

        while ((_byte = reader.read()) >= 0) {

            if(_byte == CR){
                _byte = reader.read();

                if(_byte == LF){
                    _byte = reader.read();

                    //second check checks if it is not an empty line
                    if(_byte == CR) {
                        _byte = reader.read();
                        if(_byte == LF)
                        {
                            stringBuilder.append(System.lineSeparator()).append(System.lineSeparator());

                            int lengthOfBody = getLengthOfContentType(stringBuilder);
                            return converterBytesToStringBodyRequest(lengthOfBody,reader,stringBuilder);
                        }
                    }


                    stringBuilder.append(System.lineSeparator())
                            .append((char) _byte);
                }

            }
            else{
                stringBuilder.append((char) _byte);
            }
        }

        return stringBuilder;
    }

    private static int getLengthOfContentType(StringBuilder stringBuilder)
    {
        Pattern pattern = Pattern.compile("Content-Length: (\\d+)");
        Matcher matcher = pattern.matcher(stringBuilder);
        if(!matcher.find()) return 0;

        return Integer.parseInt(matcher.group(1));
    }

    private static StringBuilder converterBytesToStringBodyRequest(int content_length_from_request,BufferedReader reader,StringBuilder stringBuilder) throws IOException {
        for (int i = 0; i < content_length_from_request; i++) {
            int _byte = reader.read();

            if(_byte >= 0) {
                stringBuilder.append((char) _byte);
            }

        }

        return stringBuilder;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////BELOW METHOD FOR HttpRequest

    //HttpServletRequest
    public static HttpServletRequest createHttpServletRequest(List<String> httpRequestLines){
        String method = getMethodFromRequest(httpRequestLines.getFirst());
        String endpoint = getEndpointFromRequest(httpRequestLines.getFirst());
        String body = getBodyFromRequest(httpRequestLines.getLast());
        return new HttpServletRequest(method,endpoint,body);
    }

    private static String getEndpointFromRequest(String httpRequestLine)
    {
        return httpRequestLine.substring(httpRequestLine.indexOf("/"),httpRequestLine.indexOf(" HTTP"));
    }

    private static String getMethodFromRequest(String httpRequestLine)
    {
        return httpRequestLine.substring(0,httpRequestLine.indexOf(" /"));
    }

    private static String getBodyFromRequest(String httpRequestLine)
    {
        return httpRequestLine;
    }


}
