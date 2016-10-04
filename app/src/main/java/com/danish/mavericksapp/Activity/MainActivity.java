package com.danish.mavericksapp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.danish.mavericksapp.Adapter.UsersAdapter;
import com.danish.mavericksapp.Dialog.AddUserDialogFragment;
import com.danish.mavericksapp.Model.User;
import com.danish.mavericksapp.R;
import com.danish.mavericksapp.Utility.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddUserDialogFragment.OnCompleteListener{

    public static final String TAG = "MainActivity";
    private static final String ADD_USER_DIALOG = "AddUserDialogFragment";
    ArrayList<User> userArrayList;




    private String jsonArray = "{\n" +
            "  \"ResponseCode\": \"200\",\n" +
            "  \"Users\": [\n" +
            "  {\n" +
            "    \"FirstName\": \"andrew\",\n" +
            "    \"LastName\": \"carnegie\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"FirstName\": \"Henry\",\n" +
            "    \"LastName\": \"Ford\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"FirstName\": \"Oprah\",\n" +
            "    \"LastName\": \"Winfrey\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"FirstName\": \"Bill\",\n" +
            "    \"LastName\": \"Gates\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"FirstName\": \"larry\",\n" +
            "    \"LastName\": \"Page\"\n" +
            "  }\n" +
            "  ]\n" +
            "  \n" +
            "}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonArray);
            userArrayList = new ArrayList<>();
            if (jsonObject.getInt(Constants.KeyValues.RESPONSE_CODE) == Constants.SUCCESS_RESPONSE_CODE) {

                JSONArray usersArray = jsonObject.getJSONArray(Constants.KeyValues.USERS);// new JSONArray(jsonObject.getJSONArray(Constants.KeyValues.USERS));
                if (usersArray.length() > 0) {
                    for (int i = 0; i < usersArray.length(); i++) {
                        User user = new User(usersArray.getJSONObject(i));
                        userArrayList.add(user);
                    }
                    setUpRecyclerView();
                } else {
                    Toast.makeText(this, "No Users Found!", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while parsing", Toast.LENGTH_SHORT).show();
        }


        Log.i(TAG, " JsonResponse: " + jsonArray);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddUserDialogFragment addUserDialogFragment = new AddUserDialogFragment();
                addUserDialogFragment.show(getSupportFragmentManager(), ADD_USER_DIALOG);
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

    private void setUpRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        UsersAdapter mUsersAdapter =
                new UsersAdapter(userArrayList, MainActivity.this, getSupportFragmentManager());
        mRecyclerView.setAdapter(mUsersAdapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(MainActivity.this);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManagerVertical);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onComplete(int result, User user) {
        if (result == Activity.RESULT_OK) {
            userArrayList.add(user);
            setUpRecyclerView();
        }

    }
}
