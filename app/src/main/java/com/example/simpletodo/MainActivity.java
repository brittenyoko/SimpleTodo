package com.example.simpletodo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        lvitems = (ListView)findViewById(R.id.lvitems);
        lvitems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etnewitem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        writeItems();
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(), "Item Added to List!", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener()  {
        Log.i("MainActivity", "Setting up Listener on list view");

        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("Main Activity", "item removed from list");
                Toast.makeText(getApplicationContext(), "Item removed from List!", Toast.LENGTH_SHORT).show();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
           Log.e("MainActivity", "Error reading file", e);
           items = new ArrayList<>();
        }
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file", e);
        }

    }
}
