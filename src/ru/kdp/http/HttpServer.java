package ru.kdp.http;

import ru.kdp.network.ApplicationServer;
import ru.kdp.network.ApplicationServerListener;
import ru.kdp.network.TCPConnection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class HttpServer  implements ApplicationServerListener,HTTPConnectionListener{

    private ApplicationServer app;
    private HttpServerListener event;
    private ArrayList<HTTPConnection> connectionsRequest = new ArrayList<>();


    public HttpServer(HttpServerListener eventListener) {
       this.event = eventListener;
       app = new ApplicationServer(this,"Http Server");

    }

    public void interrupt(){
        app.interrupt();
    }
    public void start(int port) {
        app.start(port);
    }
    public void start(int port,int timeoutacept) {
        app.start(port,timeoutacept);
    }

    private int GetIndexTCPConnection(TCPConnection tcpConnection)
    {
        int index = -1;
        int cnt = connectionsRequest.size();
        for (int i = 0; i < cnt; i++) {
            if (connectionsRequest.get(i).getTcpConnection() == tcpConnection) {
                index = i;
                break;
            };
        }
        return index;
    }
    @Override
    public synchronized void onConnectionServer(ApplicationServer applicationServer) {
        event.onConnectionServer(this);
    }

    @Override
    public synchronized void onConnectionReady(ApplicationServer applicationServer, TCPConnection tcpConnection) {
       HTTPConnection httpConnection = new HTTPConnection(this,tcpConnection);
        connectionsRequest.add(httpConnection);
        event.onConnectionReady(this,httpConnection);
    }

    @Override
    public synchronized void onDisconnectionReady(ApplicationServer applicationServer, TCPConnection tcpConnection) {

        int index = GetIndexTCPConnection(tcpConnection);
        if (index != -1) {
            event.onDisconnectionReady(this,connectionsRequest.get(index));
            connectionsRequest.remove(index);
        }
    }

    @Override
    public synchronized void onDisconnection(ApplicationServer applicationServer) {
     event.onDisconnection(this);

    }

    @Override
    public synchronized void onException(ApplicationServer applicationServer, Exception e) {
    event.onException(this,e);
    }

    @Override
    public synchronized void onMessageString(ApplicationServer applicationServer, TCPConnection tcpConnection,String value) {

        int index = GetIndexTCPConnection(tcpConnection);
        if (index != -1) connectionsRequest.get(index).RequestAppendString(value);

    }

    @Override
    public synchronized void onReciveRequest(HTTPConnection httpConnection) {

        HTTPRequest httpRequest = new HTTPRequest(httpConnection) ;
        SendResponseConnection(httpRequest);
    }

    public synchronized void SendResponseConnection(HTTPRequest httpRequest){

        HTTPConnection httpConnection = httpRequest.getConnection();
        String ResponseTextBody = "";
        byte[] ResponseBody = null;
        int countBody = 0;



        String path = httpRequest.GetPatch() ;
        if (path.equals(HTTPRequest.wwwDir)) path += "index.html";

        File file = new File(path);
        HTTPResponse httpResponse = new HTTPResponse();

	//Бинарный файл
        if (path.endsWith(".ico")|| path.endsWith(".png")) {
            ResponseBody = GetFileResponse(path);
            if (ResponseBody != null) {
                countBody = ResponseBody.length;
            } else
            {
                ResponseTextBody  = "File " + path +" not found!!!\n";
                httpResponse.SetResponseCode(404);
                countBody = GetBytesResponse(ResponseTextBody);
            }
        }
        else    {

	//Текстовый файл
        ResponseTextBody = GetFileTextResponse(path);

        if (ResponseTextBody == null || ResponseTextBody.isEmpty()){

            ResponseTextBody  = "File " + path +" not found!!!\n";
            httpResponse.SetResponseCode(404);
            countBody = GetBytesResponse(ResponseTextBody);

        } else      countBody = GetBytesResponse(ResponseTextBody);
        
        }

        LocalDateTime lastModifed = Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDateTime();

        httpResponse.SetHeaders("Server","HTTP Server");
        httpResponse.SetHeaders("Date",GetLocalTimeHttpServer(LocalDateTime.now()));
        httpResponse.SetHeaders("Cache-Control","no-cache");
        httpResponse.SetHeaders("Content-Type",httpRequest.getMimeType());
        httpResponse.SetHeaders("Content-Length",""+countBody);
        httpResponse.SetHeaders("Last-Modified",GetLocalTimeHttpServer(lastModifed));
        httpResponse.SetHeaders("Connection","close");

        String response = httpResponse.GetHeaders();
        httpConnection.sendString(response);
 
       if (ResponseTextBody.isEmpty()) httpConnection.sendBytes(ResponseBody);
       else httpConnection.sendString(ResponseTextBody);
       
       file = null;
       httpResponse = null;
    }

    private String GetLocalTimeHttpServer(LocalDateTime dateTime){

        String result="";
        try {
            result = dateTime.format(DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss",Locale.US))+" GMT+3";
        } catch (DateTimeParseException e) {
            System.out.println("HTTPServer DateTime Exeption: " + e);
        }
        return result;
    }


    private int GetBytesResponse(String value){
        int count = 0;
        try {
            count = value.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException e) {
            count = 0;
        };
        return count;
    }



    private String GetFileTextResponse(String file) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file), "UTF8"));
        } catch (UnsupportedEncodingException e) {
           event.onException(this,e);
            return null;
        } catch (FileNotFoundException e) {
            event.onException(this,e);
            return null;
        } ;

        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = "\n";

        try {
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) stringBuilder.append(line+ls);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            event.onException(this, e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                event.onException(this, e);

            }
        }

        return null;
    }


    private byte[] GetFileResponse(String filename) {

        File file = new File(filename);
        Path path = file.toPath();

        try {
            return Files.readAllBytes(path);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
