package android.example.gpsapp;

import android.example.gpsapp.service.ITranslateSevice;
import android.example.gpsapp.service.MymemoryService;
import android.example.gpsapp.service.RequestElements;
import android.example.gpsapp.service.YandexService;
import android.os.AsyncTask;

public class TranslateService extends AsyncTask<RequestElements, Integer, String> {
    @Override
    protected String doInBackground(RequestElements... requestElementsArr) {
        RequestElements requestElements = requestElementsArr[0];
        ITranslateSevice service = null;
        switch (requestElements.getServiceProvider()) {
            case YANDEX:
                service = new YandexService();
                break;
            case Mymemory:
                service = new MymemoryService();
                break;
            default:
                service = new YandexService();
        }
        return service.translateText(requestElements);
    }
}
