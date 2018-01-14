package com.example.aliasghar.sqlite;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchContent extends AppCompatActivity {
    Button btn_search;

    TextView id, name, email, phone;

    EditText searchText;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);

        helper = new DatabaseHelper(this);

        btn_search = (Button) findViewById(R.id.button3);

        searchText = (EditText) findViewById(R.id.editText4);

        id = (TextView) findViewById(R.id.textView2);
        name = (TextView) findViewById(R.id.textView3);
        email = (TextView) findViewById(R.id.textView4);
        phone = (TextView) findViewById(R.id.textView5);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = helper.searchData(searchText.getText().toString());

                    while (cursor.moveToNext()) {
                        id.setText(cursor.getString(0));
                        name.setText(cursor.getString(1));
                        email.setText(cursor.getString(2));
                        phone.setText(cursor.getString(3));
                    }

            }
        });
    }
}
