package firas.jolha.gpsapp;

import android.example.gpsapp.service.AbstractTranslateService;
import android.example.gpsapp.service.ITranslateSevice;
import android.example.gpsapp.service.RequestElements;
import android.os.AsyncTask;

public class TranslateService extends AsyncTask<RequestElements, Integer, String> {
    private RequestElements temp = null;
    private String translatedText;

    @Override
    protected String doInBackground(final RequestElements... requestElementsArr) {
        temp = requestElementsArr[0];
        RequestElements requestElements = temp;
        ITranslateSevice service = AbstractTranslateService.getTranslateService(requestElements.getServiceProvider());
        translatedText = service.translateText(requestElements);
        return translatedText;
    }
}
