package firas.jolha.gpsapp.utils;

@Deprecated
public final class TranslateWebService {
//    private enum AvailableService {TRANSLATE, DETECT}
//
//    private static final String YANDEX_API_KEY = "trnsl.1.1.20200103T174915Z.bf0d375d35660b5c.5c230b73e56af2aac30cd21282774483a0ee0ba5";
//    private static final String GOOGLE_API_KEY = "";
//    private static final String SERVICE_HOST_URI = "https://translate.yandex.net";
//    private static final String SERVICE_SETTINGS_URI = "/api/v1.5/tr.json";
//    private static final String SERVICE_URI = SERVICE_HOST_URI + SERVICE_SETTINGS_URI;
//
//    private static final String TRANSLATE_URI = SERVICE_URI + "/translate";
//    private static final String DETECT_URI = SERVICE_URI + "/detect";
//
//    private static final String ENCODING_CHARSET = "UTF-8";
//    private static final String HTTP_REQUEST_METHOD = "POST";
//    private static final String ACCEPT_REQUEST_PROPERTY = "application/json";
//    private static final String CONTENT_TYPE_REQUEST_PROPERTY = "application/x-www-form-urlencoded";
//
//    private static final String TRANSLATE_RESPONSE_MESSAGE_HEADER = "text";
//    private static final String DETECT_RESPONSE_MESSAGE_HEADER = "lang";
//
//
//    private static String constructServiceUri(String param1, String plainText, AvailableService service) {
//        String encoded_text = "";
//        try {
//            encoded_text = URLEncoder.encode(plainText, ENCODING_CHARSET);
//        } catch (UnsupportedEncodingException e) {
//            encoded_text = plainText;
//        }
//        if (service == AvailableService.TRANSLATE) {
//            return TRANSLATE_URI + "?lang=" + param1 + "&key=" + YANDEX_API_KEY + "&text=" + encoded_text;
//        } else {
//            return DETECT_URI + "?hint=" + param1 + "&key=" + YANDEX_API_KEY + "&text=" + encoded_text;
//        }
//    }
//
//    public static String translateText(String fromToLangs, String text) {
//        String translateUri = constructServiceUri(fromToLangs, text, AvailableService.TRANSLATE);
//        HttpURLConnection connection = connectToWebService(translateUri);
//        String output = getResponseMessage(connection);
//        try {
//            JSONObject jb = new JSONObject(output);
//            JSONArray array = new JSONArray(jb.getString(TRANSLATE_RESPONSE_MESSAGE_HEADER));
//            output = "";
//            for (int i = 0; i < array.length(); i++) {
//                output += array.getString(i);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
//
//    public static String detectLanguage(String hint, String text) {
//        String detectUri = constructServiceUri(hint, text, AvailableService.DETECT);
//        HttpURLConnection connection = connectToWebService(detectUri);
//        String output = getResponseMessage(connection);
//        try {
//            JSONObject jb = new JSONObject(output);
//            JSONArray array = new JSONArray(jb.getString(DETECT_RESPONSE_MESSAGE_HEADER));
//            output = "";
//            for (int i = 0; i < array.length(); i++) {
//                output += array.getString(i);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return output;
//    }
//
//
//    private static String getResponseMessage(HttpURLConnection connection) {
//        String output = "";
//        if (connection != null) {
//            try (BufferedReader br = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), ENCODING_CHARSET))) {
//                StringBuilder response = new StringBuilder();
//                String responseLine;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                output += response.toString();
//            } catch (IOException e) {
//                //TODO: handle exception
//            }
//        }
//        return output;
//    }
//
//    private static HttpURLConnection connectToWebService(String uri) {
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(uri);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod(HTTP_REQUEST_METHOD);
//            connection.setRequestProperty("Content-Type", CONTENT_TYPE_REQUEST_PROPERTY);
//            connection.setRequestProperty("Accept", ACCEPT_REQUEST_PROPERTY);
//            connection.connect();
//
//
//        } catch (MalformedURLException e) {
//            //TODO: handle exception
//        } catch (IOException ex) {
//            //TODO: handle exception
//        }
//        return connection;
//
//    }


}
