package android.example.gpsapp.utils;

public enum HttpHeader {
    REQUEST_METHOD("Request-Method","POST");

    private String headerKey;
    private String headerDefaultValue;

    HttpHeader(String headerKey, String headerDefaultValue) {
        this.headerKey = headerKey;
        this.headerDefaultValue = headerDefaultValue;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public String getHeaderDefaultValue() {
        return headerDefaultValue;
    }
}
