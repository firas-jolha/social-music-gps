package firas.jolha.gpsapp;

import android.content.pm.PackageManager;
import android.example.gpsapp.database.MyFirebaseDatabase;
import android.example.gpsapp.service.RequestElements;
import android.example.gpsapp.service.ServiceProvider;
import android.example.gpsapp.utils.Lang;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class MyTranslateActivity extends AppCompatActivity {

    // choose service
    private ServiceProvider serviceProvider = ServiceProvider.YANDEX;
    private static final long DELAY = 1000; //milliseconds

    // Views
    private Spinner fromLangSpinner = null;
    private Spinner toLangSpinner = null;
    private EditText translateText = null;
    private EditText outputText = null;
    private FloatingActionButton exchangeLangButton = null;
    private Switch translateServiceSwitch = null;

    // current activity
    private MyTranslateActivity current = this;

    private void init() {
        fromLangSpinner = findViewById(R.id.fromLangSpinner);
        toLangSpinner = findViewById(R.id.toLangSpinner);
        translateText = findViewById(R.id.translateText);
        outputText = findViewById(R.id.outputText);
        exchangeLangButton = findViewById(R.id.exchangeLanguageButton);
        translateServiceSwitch = findViewById(R.id.translateServiceSwitch);
    }

    private void initLangSpinner(Spinner langSpinner) {
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, Lang.getLangsArrayList());
        langSpinner.setAdapter(arrayAdapter1);
    }

    private AdapterView.OnItemSelectedListener getFromLangSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                translateText.setText(translateText.getText() + "");
                translateText.setSelection(translateText.getText().length());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private AdapterView.OnItemSelectedListener getToLangSpinnerItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("spinner", String.format("position is %d, id %d ", position, id));
                if (id == Lang.ARABIC.getId()) {
                    translateText.setTextDirection(View.TEXT_DIRECTION_RTL);
                    translateText.setGravity(Gravity.START);
                    translateText.setHint("اكتب للترجمة....");
                } else {
                    translateText.setTextDirection(View.TEXT_DIRECTION_LTR);
                    translateText.setGravity(Gravity.START);
                    translateText.setHint(R.string.translate_text_hint);
                }
                translateText.setText(translateText.getText() + "");
                translateText.setSelection(translateText.getText().length());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            private Timer timer = new Timer();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.d("Text Changed", String.format("s = %s, start = %d, count = %d, after = %d", s, start, count, after));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("Text Changed", String.format("s = %s, start = %d, count = %d", s, start, count));
            }

            @Override
            public void afterTextChanged(Editable s) {

                final String text = s.toString();
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       {
                                           String[] perms = {"android.permission.INTERNET"};
                                           if (checkSelfPermission("android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) {
                                               ActivityCompat.requestPermissions(current, perms, 1);
                                           }
                                           String output = "";
                                           try {
                                               if (text == null || text.length() <= 0) return;

                                               Lang fromLang = Lang.values()[fromLangSpinner.getSelectedItemPosition()];
                                               Lang toLang = Lang.values()[toLangSpinner.getSelectedItemPosition()];
                                               if (fromLang == toLang) {
                                                   output = text;
                                               } else {

                                                   ServiceProvider serviceProvider = current.serviceProvider;
                                                   RequestElements requestElements = new RequestElements(text, fromLang, toLang, serviceProvider);

                                                   output = new TranslateService().execute(requestElements).get();
                                               }
                                           } catch (ExecutionException e) {
                                               e.printStackTrace();
                                           } catch (InterruptedException e) {
                                               e.printStackTrace();
                                           }
                                           outputText.setText(output);
                                       }
                                   }
                               }, DELAY
                );
            }
        };
    }

    private View.OnClickListener getExchangeLangaugeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = fromLangSpinner.getSelectedItemPosition();
                fromLangSpinner.setSelection(toLangSpinner.getSelectedItemPosition());
                toLangSpinner.setSelection(temp);
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener getTranslateServiceOnCheckedChangeListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    serviceProvider = ServiceProvider.YANDEX;
                } else {
                    serviceProvider = ServiceProvider.MYMEMORY;
                }
                translateText.setText(translateText.getText() + "");
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_translate);

        init();

        initLangSpinner(fromLangSpinner);
        initLangSpinner(toLangSpinner);
        toLangSpinner.setOnItemSelectedListener(getFromLangSpinnerItemSelectedListener());
        fromLangSpinner.setOnItemSelectedListener(getToLangSpinnerItemSelectedListener());

        translateText.addTextChangedListener(getTextWatcher());


        exchangeLangButton.setOnClickListener(getExchangeLangaugeOnClickListener());

        translateServiceSwitch.setOnCheckedChangeListener(getTranslateServiceOnCheckedChangeListener());
        translateServiceSwitch.setChecked(true);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "Hello Firebase Database");
        data.put("username", "Firas");
        data.put("password", "a;ldmqwmkdqwd1cke192ce1");
        FirebaseDatabase database = new MyFirebaseDatabase().writeToDB(data);
        Log.v("database", database.getApp().getName());


    } // OnCreate()

    {
    }


}
