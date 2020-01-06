package android.example.gpsapp.service;

public enum ServiceProvider {
    YANDEX("", ""),
    Mymemory("", "");

    enum Category {
        TRANSLATE, DETECT;
    }

    private String url;
    private String apiKey;

    ServiceProvider(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }

    public String getUrl() {
        return url;
    }

    public String getApiKey() {
        return apiKey;
    }
}
