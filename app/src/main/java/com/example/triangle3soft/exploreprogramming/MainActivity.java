package com.example.triangle3soft.exploreprogramming;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triangle3soft.exploreprogramming.helperClass.Database;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // private Database mydb;
    private Button profileenterbuttonid;
    private EditText signin, signpassword;
    private TextView createprofilebutton;
    public Toolbar toolbar;
    Database mydb;
    public static int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inside your activity (if you did not enable transitions in your theme)
        // For AppCompat getWindow must be called before calling super.OnCreate
        // Must be called before setContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create instance of the Database
        mydb = new Database(this);

        color = getResources().getColor(R.color.primaryLightColor);

        // Set the content and question table first time installation
        Setup_Table();


        setuptoolber("Explore Programming");

        createprofilebutton = findViewById(R.id.createprofilebuttonid);
        profileenterbuttonid = (Button) findViewById(R.id.profileenterbuttonid);
        signin = (EditText) findViewById(R.id.signin);
        signpassword = (EditText) findViewById(R.id.signinpassword);

        createprofilebutton.setOnClickListener(this);
        profileenterbuttonid.setOnClickListener(this);
    }

    private void Setup_Table() {

        Cursor imo = mydb.tableExists("content");
        if(imo.getCount()==0){
            boolean flag =  mydb.storedata();
        }
        imo.close();
        imo = mydb.tableExists("question");
        if(imo.getCount()==0){
            mydb.store_question();
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.createprofilebuttonid) {
            Intent intent = new Intent(getApplicationContext(),Registration.class);
            intent.putExtra("title","Registration");
            startActivity(intent);

        } else {

            String username = signin.getText().toString();
            String password = signpassword.getText().toString();

            if (username.equals("") || password.equals("")) {
                Toast.makeText(getApplicationContext(), "Username or Password incomplete", Toast.LENGTH_SHORT).show();
            } else {
                Cursor res;
                res = mydb.validate_username(username, password);

                if (res.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Username isn't exists,Please create a profile", Toast.LENGTH_SHORT).show();
                } else {
                    int id=0;
                    int status = 0;
                    StringBuffer sss = new StringBuffer();
                    while (res.moveToNext()) {
                        sss.append(res.getString(1));
                        id = res.getInt(0);
                        status = res.getInt(3);

                    }

                    Intent intent = new Intent(getApplicationContext(),MenuPage.class);
                    username = sss.toString();



                    Bundle sentbundle = new Bundle();
                    sentbundle.putString("username",username);
                    sentbundle.putString("title","Main Menu");
                    sentbundle.putInt("id",id);
                    sentbundle.putInt("status",status);
                    intent.putExtras(sentbundle);
                    startActivity(intent);

                }
            }
        }
    }

    private void setuptoolber(String titile) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitle(titile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(20f);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";
        switch (item.getItemId()) {

            case R.id.search:
                msg = "Search";
                break;
            case R.id.Settings:
                msg = "Settings";
                break;
            case R.id.Edit:
                msg = "Edit";
                break;
            case R.id.About:
                msg = "About";
                break;

        }

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}






