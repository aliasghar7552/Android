package com.example.aliasghar.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button save;
    EditText etname, etemail, etphone;

    public static final String MYSHAREDPREFERENCES = "mySharedPreferences";
    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";

    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        save = (Button)findViewById(R.id.button2);
        etname = (EditText)findViewById(R.id.editText);
        etemail = (EditText)findViewById(R.id.editText2);
        etphone = (EditText)findViewById(R.id.editText3);

        sharedPreferences = getSharedPreferences(MYSHAREDPREFERENCES, Context.MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etname.getText().toString();
                String email = etname.getText().toString();
                String phone = etname.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(NAME, name);
                editor.putString(EMAIL, email);
                editor.putString(PHONE, phone);

                Toast.makeText(MainActivity.this, "Submit", Toast.LENGTH_LONG).show();
            }
        });
    }
}
