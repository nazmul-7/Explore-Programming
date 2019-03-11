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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triangle3soft.exploreprogramming.helperClass.Database;

public class Content extends AppCompatActivity {

    private int course_id, chapterid, module_id;
    private Database mydb;
    private Button nextbutton;
    private TextView contenttext;
    public Toolbar toolbar;
    private String title;
    private  String username;
    private int id,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);


        Bundle getbundle = getIntent().getExtras();
        course_id = getbundle.getInt("Course");
        module_id = getbundle.getInt("module");
        chapterid = getbundle.getInt("chapter");
        title     = getbundle.getString("title");
        username  = getbundle.getString("username");
        id        = getbundle.getInt("id");
        status        = getbundle.getInt("status");

        setuptoolber(title);

        mydb = new Database(this);

        contenttext = (TextView) findViewById(R.id.content_id);
        nextbutton = (Button) findViewById(R.id.nextbuttonid);

        String data;
        // Get Content Data from Content Table
        data = fetch_content(course_id,module_id,chapterid);


        contenttext.setText(data);

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizintent = new Intent(getApplicationContext(), Quiz.class);

                Bundle sentbundle = new Bundle();
                sentbundle.putInt("Course",course_id);
                sentbundle.putInt("module",module_id);
                sentbundle.putInt("chapter",chapterid);
                sentbundle.putString("username",username);
                sentbundle.putString("title",title);
                sentbundle.putInt("id",id);
                sentbundle.putInt("status",status);
                quizintent.putExtras(sentbundle);

                startActivity(quizintent);
                finish();
            }
        });

    }

    private String fetch_content(int course_id, int module_id, int chapterid) {

        Cursor res;
        res = mydb.fetch_content(course_id, module_id, chapterid);
        String maincontent;

        if (res.getCount() == 0) {
            maincontent = "Contents are coming, stay tune!!!!";
        } else {

            StringBuffer sss = new StringBuffer();
            while (res.moveToNext()) {
                sss.append(res.getString(0));
            }
            maincontent = sss.toString();
        }
        return maincontent;
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







