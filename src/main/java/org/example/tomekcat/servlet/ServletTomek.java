package org.example.tomekcat.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tomekcat.httpcontrollerhandler.HttpControllerHandler;
import org.example.tomekcat.models.HttpServletRequest;
import org.example.tomekcat.models.HttpServletResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public final class ServletTomek {

    private static ServletTomek INSTANCE;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpControllerHandler httpControllerHandler = HttpControllerHandler.getINSTANCE();
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

                    //convert bytes to request string
                    String request = HttpServletRequestWrapper.converterBytesToStringRequest(reader).toString();

                    //create HTTPServletRequest model
                    HttpServletRequest requestServlet = HttpServletRequestWrapper.createHttpServletRequest(request.lines().toList());

                    Object result = httpControllerHandler.invokeMethodFromRestController(requestServlet);

//                    byte[] resultInBytes = objectMapper.writeValueAsBytes(result);
                    String body = objectMapper.writeValueAsString(result);

                    String response = HttpServletResponse.getResponse(body);

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
