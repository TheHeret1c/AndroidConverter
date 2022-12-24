package com.example.converterKhublaryan;

// Khublaryan Edward 303 group

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spFrom, spTo;
    EditText textFrom, textTo;

    ArrayList<ArrayList<Convert>> units = new ArrayList<>();

    ArrayList<RadioButton> rblist = new ArrayList<RadioButton>();

    ArrayAdapter<String> types;

    int checkedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spFrom = findViewById(R.id.spinnerFrom);
        spTo = findViewById(R.id.spinnerTo);
        textFrom = findViewById(R.id.editTextFrom);
        textTo = findViewById(R.id.editTextTo);

        types = new <String> ArrayAdapter(this, android.R.layout.simple_list_item_1);

        for (int i = 0; i < 4; i++){
            units.add(new ArrayList<Convert>());
        }

        // radioButtons
        {
            rblist.add(findViewById(R.id.rbLength));
            rblist.add(findViewById(R.id.rbSquare));
            rblist.add(findViewById(R.id.rbWeight));
            rblist.add(findViewById(R.id.rbVolume));
        }

        // units
        {
            // length
            units.get(0).add(new Convert("mm", 0.001));
            units.get(0).add(new Convert("cm", 0.01));
            units.get(0).add(new Convert("m", 1));
            units.get(0).add(new Convert("km", 1000));

            // Square
            units.get(1).add(new Convert("mm²", 0.000001));
            units.get(1).add(new Convert("cm²", 0.0001));
            units.get(1).add(new Convert("m²", 1));
            units.get(1).add(new Convert("ha²", 10000));

            // weight
            units.get(2).add(new Convert("mg", 0.000001));
            units.get(2).add(new Convert("g", 0.001));
            units.get(2).add(new Convert("kg", 1));
            units.get(2).add(new Convert("mg", 1000));

            // volume
            units.get(3).add(new Convert("mm³", 0.000000001));
            units.get(3).add(new Convert("cm³", 0.000001));
            units.get(3).add(new Convert("m³", 1));
            units.get(3).add(new Convert("km³", 1000000000));
        }
    }


    public void onRadioButtons(View v){
        types.clear();
        for (int i = 0; i < rblist.stream().count(); i++){
            if (rblist.get(i).isChecked()){
                checkedIndex = i;
                for (int j = 0; j < rblist.stream().count(); j++){
                    types.add(units.get(i).get(j).name);
                }
            }
        }
        spFrom.setAdapter(types);
        spTo.setAdapter(types);
    }

    public void Calculate(View v){
        String text = textFrom.getText().toString();
        for (int i = 0; i < 4; i++) {
            if (rblist.get(i).isChecked()) {
                try {
                    double from = Double.parseDouble(textFrom.getText().toString());
                    double result;

                    result = from * units.get(checkedIndex).get(spFrom.getSelectedItemPosition()).coef / units.get(checkedIndex).get(spTo.getSelectedItemPosition()).coef;

                    textTo.setText(String.valueOf(result));
                }
                catch(Exception ex){
                    Toast toast = Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }
}