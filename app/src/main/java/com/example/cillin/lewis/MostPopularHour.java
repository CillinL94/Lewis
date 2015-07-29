package com.example.cillin.lewis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

/**
 * Created by cillin on 27/07/2015.
 */
public class MostPopularHour extends ListActivity
{
    // Progress Dialog
    private ProgressDialog pDialog2;

    // Creating JSON Parser object
    JSONParser jParser2 = new JSONParser();

    ArrayList<HashMap<String, String>> popularHour;

    // url to get all products list
    private static String url_popular_list = "http://192.168.1.4/food_api/most_popular_hour.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_LEWIS_LOCATIONS = "lewis_locations";
    private static final String TAG_NAME = "Cnt";
    private static final String TAG_DTIME = "Hr";


    // products JSONArray
    JSONArray lewis_locations2 = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popularhour);

        // Hashmap for ListView
        popularHour = new ArrayList<HashMap<String, String>>();

        // Loading products in Background Thread
        new LoadHours().execute();

        // Get listview
        ListView lv = getListView();

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoadHours extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog2 = new ProgressDialog(MostPopularHour.this);
            pDialog2.setMessage("Loading hours. Please wait...");
            pDialog2.setIndeterminate(false);
            pDialog2.setCancelable(false);
            pDialog2.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<NameValuePair> param = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jParser2.makeHttpRequest(url_popular_list, "GET", param);

            // Check your log cat for JSON reponse
            Log.d("All Products: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    lewis_locations2 = json.getJSONArray(TAG_LEWIS_LOCATIONS);

                    // looping through All Products
                    for (int i = 0; i < lewis_locations2.length(); i++) {
                        JSONObject c = lewis_locations2.getJSONObject(i);

                        // Storing each json item in variable
                        String dtime = c.getString(TAG_DTIME);
                        String name = c.getString(TAG_NAME);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_DTIME, dtime);
                        map.put(TAG_NAME, name);


                        // adding HashList to ArrayList
                        popularHour.add(map);
                    }
                } else {
                    // no products found
                    // Launch Add New product Activity
                    Intent i = new Intent(getApplicationContext(),
                            MainActivity.class);
                    // Closing all previous activities
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog2.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter2 = new SimpleAdapter(
                            MostPopularHour.this, popularHour,
                            R.layout.list_item4, new String[] {
                             TAG_DTIME, TAG_NAME},
                            new int[] { R.id.popularhour });
                    // updating listview
                    setListAdapter(adapter2);
                }
            });

        }

    }
}
