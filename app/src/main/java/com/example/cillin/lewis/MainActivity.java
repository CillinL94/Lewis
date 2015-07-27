package com.example.cillin.lewis;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Timestamp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends Activity implements OnItemSelectedListener
{
    private Button btnAddNewCategory;
    private TextView txtCategory;
    public Spinner spinnerFood;
    public TextView timeInput;

    // array list for spinner adapter
    private ArrayList<Location> categoriesList;
    ProgressDialog pDialog;

    // API urls
    // Url to create new category
    private String URL_NEW_CATEGORY = "http://192.168.1.4/food_api/new_category.php";
    // Url to get all categories
    private String URL_CATEGORIES = "http://192.168.1.4/food_api/get_categories.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddNewCategory = (Button) findViewById(R.id.btnAddNewCategory);
        spinnerFood = (Spinner) findViewById(R.id.spinFood);
        timeInput = (TextView) findViewById(R.id.textView2);

        categoriesList = new ArrayList<Location>();

        spinnerFood.setSelection(0, false);

        // spinner item select listener
        spinnerFood.setOnItemSelectedListener(this);

        // Add new category click event
        btnAddNewCategory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (spinnerFood.getSelectedItem().toString().length() > 0) {

                    /*java.util.Date dt = new java.util.Date();

                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    String currentTime = sdf.format(dt);//current date and time here, '2015-07-17 18:38:46'

                    Intent intent1 = new Intent(MainActivity.this, Map.class);
                    intent1.putExtra("DATE_KEY", currentTime);
                    startActivity(intent1);*/

                    //intent1.putExtra("SPINNER_KEY", newCategory);
                    // Call Async task to create new category
                    new AddNewCategory().execute();
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);

                    //setSpinnerSelectionWithoutCallingListener(spinnerFood, null);

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter category name", Toast.LENGTH_SHORT)
                            .show();
                }

            }
        });

        new GetCategories().execute();
    }

    /**
     * Adding spinner data
     * */
    public void populateSpinner() {
        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
        }


        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerFood.setAdapter(spinnerAdapter);

    }

    /**
     * Async task to get all food categories
     * */
    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching locations..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler jsonParser = new ServiceHandler();
            String json = jsonParser.makeServiceCall(URL_CATEGORIES, ServiceHandler.GET);

            Log.e("Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    if (jsonObj != null) {
                        JSONArray green_line = jsonObj
                                .getJSONArray("green_line");

                        for (int i = 0; i < green_line.length(); i++) {
                            JSONObject catObj = (JSONObject) green_line.get(i);
                            Location cat = new Location(catObj.getInt("id"),


                                    catObj.getString("name"));
                            categoriesList.add(cat);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
        }
    }

    /**
     * Async task to create a new food category
     * */
    private class AddNewCategory extends AsyncTask<String, Void, Void> {

        boolean isNewCategoryCreated = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Updating location..");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... args) {

            String newCategory = spinnerFood.getSelectedItem().toString();

            // Preparing post params
            List<NameValuePair> paramsa = new ArrayList<NameValuePair>();
            paramsa.add(new BasicNameValuePair("name", newCategory));
            //params.add(new BasicNameValuePair("dtime", currentTime));

            ServiceHandler serviceClient = new ServiceHandler();

            String json = serviceClient.makeServiceCall(URL_NEW_CATEGORY,
                    ServiceHandler.POST, paramsa);

            Log.d("Create Response: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject jsonObj = new JSONObject(json);
                    boolean error = jsonObj.getBoolean("error");
                    // checking for error node in json
                    if (!error) {
                        // new category created successfully
                        isNewCategoryCreated = true;
                    } else {
                        Log.e("Create Category Error: ", "> " + jsonObj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("JSON Data", "Didn't receive any data from server!");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            populateSpinner();
            if (isNewCategoryCreated) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // fetching all categories
                        new GetCategories().execute();
                    }
                });
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {

        Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }
}
