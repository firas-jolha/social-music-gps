package android.example.gpsapp;

import android.example.gpsapp.service.AbstractTranslateService;
import android.example.gpsapp.service.ITranslateSevice;
import android.example.gpsapp.service.RequestElements;
import android.os.AsyncTask;

public class TranslateService extends AsyncTask<RequestElements, Integer, String> {
    @Override
    protected String doInBackground(RequestElements... requestElementsArr) {

        RequestElements requestElements = requestElementsArr[0];
        ITranslateSevice service = AbstractTranslateService.getTranslateService(requestElements.getServiceProvider());
        String translatedText = service.translateText(requestElements);
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return translatedText;
    }
}
