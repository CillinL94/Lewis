package com.example.cillin.lewis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by cillin on 27/07/2015.
 */
public class MainMenu extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //This page layout is located in the mainmenu XML file
        //SetContentView links a Java file, to its XML file for the layout
        setContentView(R.layout.mainmenu);

        Button themap = (Button)findViewById(R.id.map);
        themap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This button is linked to the map page
                Intent i = new Intent(MainMenu.this, Map.class);

                //Activating the intent
                startActivity(i);
            }
        });

        //Second button on the list
        //Addpub button that has been created in the add XML file
        Button thelocation = (Button)findViewById(R.id.location);
        thelocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //This button is linked to the location page
                Intent i = new Intent(MainMenu.this, MainActivity.class);

                //Activating the intent
                startActivity(i);
            }
        });

        //Second button on the list
        //Addpub button that has been created in the add XML file
        Button stops = (Button)findViewById(R.id.allstops);
        stops.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //This button is linked to the location page
                Intent i = new Intent(MainMenu.this, RecentStops.class);

                //Activating the intent
                startActivity(i);
            }
        });

        //Second button on the list
        //Addpub button that has been created in the add XML file
        Button mostpopular = (Button)findViewById(R.id.popularstops);
        mostpopular.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //This button is linked to the location page
                Intent i = new Intent(MainMenu.this, PopularStops.class);

                //Activating the intent
                startActivity(i);
            }
        });

        //Second button on the list
        //Addpub button that has been created in the add XML file
        Button leastpopular = (Button)findViewById(R.id.unpopularstops);
        leastpopular.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //This button is linked to the location page
                Intent i = new Intent(MainMenu.this, UnpopularStops.class);

                //Activating the intent
                startActivity(i);
            }
        });
    }
}
