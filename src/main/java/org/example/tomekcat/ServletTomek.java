package org.example.tomekcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public final class ServletTomek {

    private static ServletTomek INSTANCE;
    public synchronized static ServletTomek startServer() throws IOException {
        if(INSTANCE==null)
        {
            INSTANCE = new ServletTomek();
        }
        return INSTANCE;
    }
    private ServletTomek(){
        try {
            ServerSocket serverSocket = createServerSocket(8080);
            if(serverSocket.isBound()){
                System.out.println("Servlet Tomek start default on port 8080");
                Thread thread = new Thread(createRunnableTaskForHandlingRequest(serverSocket));
                thread.start();
            }

        }
        catch(IOException e) {
            System.out.println("Blad przy tworzeniu servletu");
        }
    }

    private ServerSocket createServerSocket(int port) throws IOException {
        if(port < 0 || port > 65535) throw new IllegalArgumentException();
        return new ServerSocket(port);
    }

    private Runnable createRunnableTaskForHandlingRequest(ServerSocket serverSocket)
    {
        return ()->{
            while(true)
            {
                try {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    //reader - read all bytes package from request
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String request = converterBytesToStringRequest(reader).toString();
                    List<String> listSplittedLinesOfRequest = request.lines().toList();

                    System.out.println(listSplittedLinesOfRequest);

                    String response = "HTTP/1.1 200 OK" + System.lineSeparator() +
                            "Content-Type: text/html" + System.lineSeparator() +
                            "Set-Cookie: username=johndoe; Max-Age=3600" + System.lineSeparator() +
                            System.lineSeparator() +//empty line for separate body from header
                            "<!DOCTYPE html><html><head><link rel=\"stylesheet\" href=\"mystyle.css\"></head><body><h1>Hello, World!</h1></body></html>";

                    //respone:
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(response.getBytes());
                    outputStream.flush();



                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    private StringBuilder converterBytesToStringRequest(BufferedReader reader) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        int _byte;
        int CR = 0x0D; // 13 (carriage return)
        int LF = 0x0A; // 10 (new line)
        while ((_byte = reader.read()) >= 0) {
            if(_byte == CR){
                _byte = reader.read();

                if(_byte == LF){
                    _byte = reader.read();
                    if(_byte == CR) break;//tutaj bedzie body requesta

                    //tutaj pierwsza litera i nowa linia dla header
                    stringBuilder.append(System.lineSeparator()).append((char) _byte);
                }
            }else{
                //tutaj znajd
                stringBuilder.append((char) _byte);
            }
        }

        return stringBuilder;
    }

    private String createHttpServletModelResponse(List<String> listOfSplitedRequestLines)
    {
        int indexOf = listOfSplitedRequestLines.get(0).indexOf(" /");

        String substring = listOfSplitedRequestLines.get(0).substring(0,indexOf);


        return substring;

    }



}
