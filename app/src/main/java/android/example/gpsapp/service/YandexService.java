package android.example.gpsapp.service;

import android.example.gpsapp.utils.HttpHeader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;

public class YandexService extends AbstractTranslateService {

    private static final char DELIMITER = '-';

    @Override
    public RequestMessage getRequestMessage(RequestElements requestElements) {
        String url = requestElements.getServiceProvider().YANDEX.getUrl();
        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("key", ServiceProvider.YANDEX.getApiKey());
        urlParams.put("lang", requestElements.getFromLang().getShort_lang() + DELIMITER + requestElements.getToLang().getShort_lang());
        urlParams.put("text", requestElements.getText());
        HashMap<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put(HttpHeader.ACCEPT.getHeaderKey(), HttpHeader.ACCEPT.getHeaderDefaultValue());
        requestHeaders.put(HttpHeader.CONTENT_TYPE.getHeaderKey(), HttpHeader.CONTENT_TYPE.getHeaderDefaultValue());

        return new RequestMessage(url, urlParams, requestHeaders);
    }

    @Override
    public ResponseMessage getResponseMessage(HttpURLConnection connection) {
        String output = "";
        if (connection != null) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), HttpHeader.ENCODING_CHARSET.getHeaderDefaultValue()))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                output += response.toString();
            } catch (IOException e) {
                //TODO: handle exception
            }
        }
        HashMap<String, String> responseHeaders = new HashMap<>();

        return new ResponseMessage(output, responseHeaders);
    }

    @Override
    public String processResponseMessage(ResponseMessage responseMessage) {
        String response = responseMessage.getMessageBody();
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response);
            response = jsonObject.get("text").toString();

//            JSONObject jb = new JSONObject(response);
//            JSONArray array = new JSONArray(jb.getString(TRANSLATE_RESPONSE_MESSAGE_HEADER));
//            response = "";
//            for (int i = 0; i < array.length(); i++) {
//                output += array.getString(i);
//            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return response;
    }
}
