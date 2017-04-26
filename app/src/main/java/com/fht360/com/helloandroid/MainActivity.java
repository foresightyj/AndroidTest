package com.fht360.com.helloandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button mIncrementCounterButton, mGetPostsButton;
    private TextView mCounterTextView, mPostsTextView;
    private int counter = 0;
    private final String TAG = "Main";

    private RequestQueue queue;
    private final String root = "http://jsonplaceholder.typicode.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        queue  = Volley.newRequestQueue(this);

        mIncrementCounterButton = (Button)findViewById(R.id.increment_counter_button);
        mCounterTextView = (TextView)findViewById(R.id.counter_text_view);

        mGetPostsButton = (Button)findViewById(R.id.get_posts_button);
        mPostsTextView = (TextView)findViewById(R.id.posts_text_view);

        Log.i(TAG, "MainActivity Created");

        mIncrementCounterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.i(TAG, "Increment button is clicked");
                Toast toast = Toast.makeText(MainActivity.this, R.string.increment_toast, Toast.LENGTH_SHORT);
                counter++;
                mCounterTextView.setText(String.valueOf(counter));
                toast.show();
            }
        });

        mGetPostsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Get Posts Button is clicked");
                JsonArrayRequest request = new JsonArrayRequest
                        (Request.Method.GET, root + "/posts/", null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.i(TAG, "# of posts is " + response.length());
                                mPostsTextView.setText(response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.getMessage());
                            }
                        });
                queue.add(request);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
