package android.example.gpsapp.service;

import java.util.HashMap;

public class RequestMessage {

    private String url;
    private HashMap<String,String> urlParams;
    private HashMap<String,String> requestHeaders;

    public RequestMessage(String url, HashMap<String, String> urlParams, HashMap<String, String> requestHeaders) {
        this.url = url;
        this.urlParams = urlParams;
        this.requestHeaders = requestHeaders;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(HashMap<String, String> urlParams) {
        this.urlParams = urlParams;
    }

    public HashMap<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(HashMap<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
}
