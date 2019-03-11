package com.example.triangle3soft.exploreprogramming;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPage extends AppCompatActivity implements View.OnClickListener {

    private TextView helloname;
    private Bundle bundle;
    private Button javabtn,cplusbtn,pythonebtn,basicbth;
    public Toolbar toolbar;
    private String username,title;
    private int id,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manu_page);


        Bundle getbundle = getIntent().getExtras();
        title = getbundle.getString("title");
        username = getbundle.getString("username");
        id = getbundle.getInt("id");
        status = getbundle.getInt("status");
        setuptoolber(title);


        javabtn = (Button) findViewById(R.id.java);
        cplusbtn = (Button) findViewById(R.id.cplusplus);
        pythonebtn = (Button) findViewById(R.id.Pythone);
        basicbth = (Button) findViewById(R.id.basic);

        helloname = (TextView) findViewById(R.id.helloname);

        javabtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Bundle sentbundle = new Bundle();
        Intent intent = new Intent(getApplicationContext(),ModuleSample.class);

        if(view.getId()==javabtn.getId()){

            sentbundle.putInt("Course",1);
            sentbundle.putString("title","Java");

        }
        else if(view.getId()==cplusbtn.getId()){

            sentbundle.putInt("Course",2);
            sentbundle.putString("title","C++");

        }
        else if(view.getId()==pythonebtn.getId()){
            ;
            sentbundle.putInt("Course",3);
            sentbundle.putString("title","Python");

        }

        sentbundle.putString("username",username);
        sentbundle.putInt("id",id);
        sentbundle.putInt("status",status);
        intent.putExtras(sentbundle);
        startActivity(intent);

    }

    private void setuptoolber(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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







