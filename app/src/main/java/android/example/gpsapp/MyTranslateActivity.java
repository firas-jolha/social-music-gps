package android.example.gpsapp;

import android.content.pm.PackageManager;
import android.example.gpsapp.utils.Lang;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyTranslateActivity extends AppCompatActivity {

    private ArrayList<String> langs = new ArrayList<String>();

    {
        for (Lang l : Lang.values()) {
            langs.add(l.getLong_lang());
        }
    }

    private Spinner fromLangSpinner = null;
    private Spinner toLangSpinner = null;
    private EditText translateText = null;
    private EditText outputText = null;
    private FloatingActionButton exchangeLangButton = null;

    private TextWatcher textWatcher = null;
    private String text = "";
    private MyTranslateActivity current = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_translate);

        fromLangSpinner = findViewById(R.id.fromLangSpinner);
        toLangSpinner = findViewById(R.id.toLangSpinner);
        translateText = findViewById(R.id.translateText);
        outputText = findViewById(R.id.outputText);


        {
            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, langs);
            ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, langs);
            fromLangSpinner.setAdapter(arrayAdapter1);
            toLangSpinner.setAdapter(arrayAdapter2);
            toLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    translateText.setText(translateText.getText() + "");
                    translateText.setSelection(translateText.getText().length());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            fromLangSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("spinner", String.format("position is %d, id %d ", position, id));
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
            });
        }

        {
            textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d("Text Changed", String.format("s = %s, start = %d, count = %d, after = %d", s, start, count, after));
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d("Text Changed", String.format("s = %s, start = %d, count = %d", s, start, count));
                }

                @Override
                public void afterTextChanged(Editable s) {
                    text = s.toString();

                    {
                        String[] perms = {"android.permission.INTERNET"};
                        if (checkSelfPermission("android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(current, perms, 1);
                        }
                        String output = "";
                        try {
                            output = new TranslateService().execute(text, fromToLangs()).get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        outputText.setText(output);
                    }

                }
            };
            translateText.addTextChangedListener(textWatcher);
        }

        exchangeLangButton = findViewById(R.id.exchangeLanguageButton);
        exchangeLangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = fromLangSpinner.getSelectedItemPosition();
                fromLangSpinner.setSelection(toLangSpinner.getSelectedItemPosition());
                toLangSpinner.setSelection(temp);
            }
        });

    } // OnCreate()

    private String fromToLangs() {
        String from = "en";
        String to = "en";
        for (Lang l : Lang.values()) {
            if (fromLangSpinner.getSelectedItemId() == l.getId()) {
                from = l.getShort_lang();
            }
            if (toLangSpinner.getSelectedItemId() == l.getId()) {
                to = l.getShort_lang();
            }
        }
        return from + "-" + to;
    } // fromToLangs()

}
