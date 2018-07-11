package com.sszg.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText rmbField;
    private EditText usdField;
    private int user;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rmbField = findViewById(R.id.rmbInput);
        rmbField.setTag(null);
        usdField = findViewById(R.id.usdInput);
        usdField.setTag(null);

        rmbField.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                usdField.setTag("ERE");
                if (rmbField.getTag() == null) {
                    // is only executed if the EditText was directly changed by the user
                    updateField(editable, "Y", "$", (1 / 6.63), usdField);
                }
                usdField.setTag(null);
            }
        });

        usdField.addTextChangedListener(new CustomTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                super.afterTextChanged(editable);
                rmbField.setTag("WE");
                if (usdField.getTag() == null) {
                    // is only executed if the EditText was directly changed by the user
                    updateField(editable, "$", "Y", (6.63), rmbField);
                }
                rmbField.setTag(null);
            }
        });

    }

    public void updateField(Editable editable, String currency, String otherC, double multiplier, EditText inputField) {
        String input = editable.toString();
        input = input.replace(currency, "");
        input = input.trim();
        double money;
        try {
            money = Double.parseDouble(input);
            if (money < 0) {
                money = 0;
            }
        } catch (Exception ex) {
            money = 0;
        }
        money = money * multiplier;
        DecimalFormat df = new DecimalFormat("#.00");
        input = otherC + " " + df.format(money);
        inputField.setText(input);
    }

}
