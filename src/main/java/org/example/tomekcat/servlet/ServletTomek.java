package org.example.tomekcat.servlet;

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
                    String request = HttpServletRequestWrapper.converterBytesToStringRequest(reader).toString();


                    System.out.println(request);
                    List<String> listSplittedLinesOfRequest = request.lines().toList();

                    System.out.println(listSplittedLinesOfRequest);

                    String response = "HTTP/1.1 200 OK" + System.lineSeparator() +
                            "Content-Type: text/html" + System.lineSeparator() +
                            "Set-Cookie: username=johndoe; Max-Age=3600" + System.lineSeparator() +
                            "Access-Control-Allow-Origin: *"+System.lineSeparator()+
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




}
