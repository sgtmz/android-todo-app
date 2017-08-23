package com.example.mz.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainToDo extends AppCompatActivity {

    // global class variables
    private EditText etEditText;
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_do);
        // getting the references to the main activity objects
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems = (ListView) findViewById(R.id.lvItems);
        // adding the items to the ArrayList and Adapter using a function
        populateTodoItems();
        // adding the items to the listview
        lvItems.setAdapter(itemsAdapter);
        // handling the deletion of an element when LONG clicked on the listview
        overrideLvItemsLongClickListener();
        // handling the edit of an element with SHORT cliked on the listview
        overrideLvItemsClickListener();
        // checking if the activity is getting created after an item edit
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // calling the function that updates the ListViewItems
            updateLvItems(extras.getString("item_edited"), extras.getInt("item_position"));
        }
    }

    private void overrideLvItemsLongClickListener(){
        /*
         * this function contains the code necessary to override the OnItemLongClickListener
         * event for the ListView Items
         */
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                // removing the element
                items.remove(position);
                // refreshing the adapter
                itemsAdapter.notifyDataSetChanged();
                // saving the new list of data
                saveToDoList();
                return true;
            }
        });
    }

    private void overrideLvItemsClickListener(){
        /*
         * this function contains the code necessary to override the OnItemClickListener
         * event for the ListView Items
         * This also starts the Activity EditItemActivity using intent
         */
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // retrieving the element to edit
                String itemToEdit = items.get(position);
                // instantiating a new intent to open the EditItemActivity
                Intent intent = new Intent(getBaseContext(), EditItemActivity.class);
                // passing the item to edit using extras
                intent.putExtra("item_to_edit", itemToEdit);
                intent.putExtra("item_position", position);
                startActivity(intent);
            }
        });
    }

    public void btAddOnClick(View view) {
        /*
         * this function adds a new item to the list
         */
        //retrieving the user text
        String userText = etEditText.getText().toString();
        // adding the text only if the user entered something
        // so I don't create empty items
        if (userText.compareTo("")!=0) {
            itemsAdapter.add(userText);
            // let's clean the etEditText content
            etEditText.setText("");
            // let's call the save function to allow persistent data
            saveToDoList();
        }
    }

    private void populateTodoItems(){
        /*
         * This function is used to load the items in the arraylist items
         */
        // reading the file content
        File file = new File(getFilesDir(), "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            // woops, something went wrong...
            // TODO: add a decent exception handling here
        }
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

    }

    private void saveToDoList(){
        /*
         * this function saves the current content of the ListView lvItems
         */
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, items);
        } catch (IOException e){
            // woops, something went wrong...
            // TODO: add a decent exception handling here
        }
    }

    private void updateLvItems(String itemEdited, int pos){
        /*
         * this function updates the List View lvItems with the item that was edited
         */
        // replacing the item at position pos with the edited one
        items.set(pos, itemEdited);
        // refreshing the adapter
        itemsAdapter.notifyDataSetChanged();
        // saving the new list of data
        saveToDoList();
    }
}
