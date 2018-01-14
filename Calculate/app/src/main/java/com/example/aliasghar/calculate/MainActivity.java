package com.example.aliasghar.calculate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText ed1, ed2, ed3;
    Button b1;
    TextView t1;
    Double result, amount, per;
    char op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        t1 = (TextView) findViewById(R.id.textView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed1.getText().toString().equals("") && ed2.getText().toString().equals(""))
                    Toast.makeText(getBaseContext(), "Enter amount and percentage", Toast.LENGTH_SHORT).show();

                else if (ed1.getText().toString().equals(""))
                    Toast.makeText(getBaseContext(), "Enter amount", Toast.LENGTH_SHORT).show();

                else  if (ed2.getText().toString().equals(""))
                    Toast.makeText(getBaseContext(), "Enter percentage", Toast.LENGTH_SHORT).show();

                else {
                    amount = Double.parseDouble(ed1.getText().toString());
                    per = Double.parseDouble(ed2.getText().toString());
                    result = (amount * per);
                    t1.setText("Result is: " + result);
                }

            }
        });


    }
}
