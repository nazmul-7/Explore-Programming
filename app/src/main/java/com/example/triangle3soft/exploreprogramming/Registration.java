package com.example.triangle3soft.exploreprogramming;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triangle3soft.exploreprogramming.helperClass.Database;

public class Registration extends AppCompatActivity {

    private Button Createdprofilebutton;
    private RadioGroup rgroup;
    private RadioButton rbutton1, rbutton, rbutton2;
    private TextView skilltext;
    private EditText usernametext, passwordtext, cpasswordtext;
    public Toolbar toolbar;
    Database mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // inside your activity (if you did not enable transitions in your theme)
        // For AppCompat getWindow must be called before calling super.OnCreate
        // Must be called before setContentView
       // getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Intent getintent= getIntent();
        String title = getintent.getStringExtra("title");
        setuptoolber(title);

        mydb = new Database(this);

        Createdprofilebutton = (Button) findViewById(R.id.createdprofilebutton);
        rgroup = (RadioGroup) findViewById(R.id.Skills);
        rbutton1 = (RadioButton) findViewById(R.id.bg);
        rbutton2 = (RadioButton) findViewById(R.id.it);


        skilltext = (TextView) findViewById(R.id.RadioGrouptext);

        usernametext = (EditText) findViewById(R.id.usernameenter);
        passwordtext = (EditText) findViewById(R.id.passenter);
        cpasswordtext = (EditText) findViewById(R.id.cpassenter);


        add_data();


    }

    public void add_data() {
        Createdprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    int i = rgroup.getCheckedRadioButtonId();
                    rbutton = (RadioButton) findViewById(i);
                    String username = usernametext.getText().toString();
                    String password = passwordtext.getText().toString();
                    String cpassword = cpasswordtext.getText().toString();

                    if (password.equals(cpassword)) {
                        boolean isinserted = mydb.insert_data(username, password, rbutton.getText().toString());

                        if (isinserted == true) {
                            Toast.makeText(getApplicationContext(), "Registration Successfull\n     Please login", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Data not Inserted", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password Doesn't match", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Invalid data -try again", Toast.LENGTH_SHORT).show();


                }

            }
        });
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
        Toast.makeText(getApplicationContext(), msg + " is Clicked", Toast.LENGTH_SHORT).show();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }


        return super.onOptionsItemSelected(item);
    }

}






