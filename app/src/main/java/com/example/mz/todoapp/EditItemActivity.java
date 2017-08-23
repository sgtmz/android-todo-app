package com.example.mz.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    //class variables
    private EditText etItemToEdit;
    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etItemToEdit = (EditText) findViewById(R.id.etItemToEdit);
        // getting the item to edit string value from extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // populating the etItemToEdit with the current value
            etItemToEdit.setText(extras.getString("item_to_edit"));
            // saving the item position
            itemPosition = extras.getInt("item_position");
        }
    }

    public void btnCancelOnClick(View view) {
        /*
         * this function ignores the changes and shows back the MainToDo activity
         */
        Intent intent = new Intent(getBaseContext(), MainToDo.class);
        startActivity(intent);
    }

    public void btnSaveOnClick(View view) {
        /*
         * this function saves the changes to the edited item and shows back the MainToDo activity
         */
        Intent intent = new Intent(getBaseContext(), MainToDo.class);
        // passing the edited item and position back to the main activity
        intent.putExtra("item_edited", etItemToEdit.getText().toString());
        intent.putExtra("item_position", itemPosition);
        startActivity(intent);
    }
}

