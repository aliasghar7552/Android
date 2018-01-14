package com.example.aliasghar.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteContent extends AppCompatActivity {

    EditText deleteText;
    Button deleteButton;
    DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_content);

        helper = new DatabaseHelper(this);

        deleteText = (EditText) findViewById(R.id.delete_text);
        deleteButton = (Button) findViewById(R.id.btn_delete);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = helper.deleteData(deleteText.getText().toString());
                if (result == true){
                    Toast.makeText(getBaseContext(), "Item deleted with ID = " + deleteText.getText().toString(), Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "item not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
