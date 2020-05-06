package ru.kdp.http;


import java.util.HashMap;
import java.util.LinkedHashMap;

public class HTTPResponse {
    private LinkedHashMap< String, String> headers;
    private HashMap<Integer, String> responses;
    private int status = 200;

    public HTTPResponse() {
    setupResponses();
	}

    protected void SetResponseCode(int code){
        this.status = code;
    }

    public int  GetResponseCode(){
        return status;
    }


    protected String GetHeaders(){
    StringBuilder Response = new StringBuilder();
    Response.append(getResponseCodeMessage(status));

        for (HashMap.Entry<String, String> header : headers.entrySet()) {
             Response.append(header.getKey()+": "+header.getValue()+"\n");
        }
        Response.append("\n");
        return Response.toString();
    }

     protected void SetHeaders(String key, String value){

        headers.put(key,value);
    }

    protected void ClearHeaders(){

        headers.clear();
    }

    private  void setupHeaders() {
        headers = new LinkedHashMap< String, String> ();
    }

    private  void setupResponses() {
        responses = new HashMap<Integer, String>();
        responses.put(100, "Continue");
        responses.put(101, "Switching Protocols");

        responses.put(200, "OK");
        responses.put(201, "Created");
        responses.put(202, "Accepted");
        responses.put(203, "Non-Authoritative Information");
        responses.put(204, "No Content");
        responses.put(205, "Reset Content");
        responses.put(206, "Partial Content");

        responses.put(300, "Multiple Choices");
        responses.put(301, "Moved Permanently");
        responses.put(302, "Found");
        responses.put(303, "See Other");
        responses.put(304, "Not Modified");
        responses.put(305, "Use Proxy");
        responses.put(307, "Temporary Redirect");

        responses.put(400, "Bad Request");
        responses.put(401, "Unauthorized");
        responses.put(402, "Payment Required");
        responses.put(403, "Forbidden");
        responses.put(404, "Not Found");
        responses.put(405, "Method Not Allowed");
        responses.put(406, "Not Acceptable");
        responses.put(407, "Proxy Authentication Required");
        responses.put(408, "Request Timeout");
        responses.put(409, "Conflict");
        responses.put(410, "Gone");
        responses.put(411, "Length Required");
        responses.put(412, "Precondition Failed");
        responses.put(413, "Request Entity Too Large");
        responses.put(414, "Request-URI Too Long");
        responses.put(415, "Unsupported Media Type");
        responses.put(416, "Request Range Not Satisfiable");
        responses.put(417, "Expectation Failed");
        responses.put(418, "I'm a teapot");
        responses.put(420, "Enhance Your Calm");

        responses.put(500, "Internal Server Error");
        responses.put(501, "Not implemented");
        responses.put(502, "Bad Gateway");
        responses.put(503, "Service Unavaliable");
        responses.put(504, "Gateway Timeout");
        responses.put(505, "HTTP Version Not Supported");
    }

    public String getResponseCodeMessage(int code) {

	String mess;
        if (responses.containsKey(code)) {
            mess = responses.get(code);
        } else mess = Integer.toString(code);

        return "HTTP/1.1 "+code+" "+mess+"\n";
    }

}
