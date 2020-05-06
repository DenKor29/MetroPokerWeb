package ru.kdp.http;

public interface HttpServerListener {
    void onReciveRequest(HTTPConnection htpConnection);
    void onConnectionServer(HttpServer httpServer);
    void onConnectionReady(HttpServer httpServer, HTTPConnection httpConnection);
    void onDisconnectionReady(HttpServer httpServer, HTTPConnection httpConnection);
    void onDisconnection(HttpServer httpServer);
    void onException(HttpServer httpServer, Exception e);
    }
