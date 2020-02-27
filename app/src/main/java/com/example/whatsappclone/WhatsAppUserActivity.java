package com.example.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class WhatsAppUserActivity extends AppCompatActivity {

    private ArrayList<String> listUser;
    private ArrayAdapter adapter;
    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_user);

        listView = findViewById(R.id.listView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        listUser= new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listUser);

        fetchListUsername();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchListUsername();
            }
        });

    }

    private void fetchListUsername() {
        listUser.clear();
        try {
            final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

            parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {

                    if(e == null){

                        showToast("Query friend successfully", FancyToast.SUCCESS);

                        if(objects.size()>0){
                            Log.i("called","call");


                            for(ParseUser user:objects){

                                listUser.add(user.getUsername());

                            }
                            listView.setAdapter(adapter);

                        }



                    }else{

                        showToast(e.getMessage(),FancyToast.ERROR);

                    }
                    swipeRefreshLayout.setRefreshing(false);


                }
            });


        }catch (Exception e){

            e.printStackTrace();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Create menu options menu
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOutItem:

                final String userName = ParseUser.getCurrentUser().getUsername();
                ParseUser.logOutInBackground(new LogOutCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){

                            showToast(userName+" logout successfully",FancyToast.INFO);
                            transitionToLoginActivity();


                        }else{

                            showToast(e.getMessage(),FancyToast.ERROR);

                        }
                    }
                });

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void transitionToLoginActivity() {

        startActivity(new Intent(this,LoginActivity.class));
        finish();

    }


    private void showToast(String message  , int type) {

        FancyToast.makeText(this,message,FancyToast.LENGTH_SHORT,type,false).show();

    }
}
