package com.example.cillin.lewis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by cillin on 27/07/2015.
 */
public class Map extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        //This page layout is located in the menu XML file
        //SetContentView links a Java file, to its XML file for the layout
        setContentView(R.layout.map);

        /*Spinner mySpinner = (Spinner)findViewById(R.id.spinFood);
        String text = mySpinner.getSelectedItem().toString();
        EditText e = (EditText) findViewById (R.id.editText1);
        e.setText(text);*/

        String spinnerString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                spinnerString= null;
            } else {
                spinnerString= extras.getString("SPINNER_KEY");
            }
        } else {
            spinnerString= (String) savedInstanceState.getSerializable("SPINNER_KEY");
        }

        TextView a = (TextView)findViewById(R.id.textView);
        a.setText(spinnerString);

        String dateString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                dateString= null;
            } else {
                dateString= extras.getString("DATE_KEY");
            }
        } else {
            dateString= (String) savedInstanceState.getSerializable("DATE_KEY");
        }

        TextView b = (TextView)findViewById(R.id.textView2);
        b.setText(dateString);



        Button mainm = (Button)findViewById(R.id.mainmenu);
        mainm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This button is linked to the map page
                Intent i = new Intent(Map.this, MainMenu.class);

                //Activating the intent
                startActivity(i);
            }
        });
    }
}
