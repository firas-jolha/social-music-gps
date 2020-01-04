package android.example.gpsapp;

import android.example.gpsapp.utils.TranslateWebService;
import android.os.AsyncTask;

public class TranslateService extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... strings) {
        String text = strings[0];
        String fromToLangs = strings[1];
        return TranslateWebService.translateText(fromToLangs, text);
    }
}
