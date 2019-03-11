package com.example.triangle3soft.exploreprogramming;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.triangle3soft.exploreprogramming.helperClass.Database;

public class Quiz extends AppCompatActivity {

    private int course_id,chapterid,module_id;
    private Database mydb;
    private TextView question;
    private RadioGroup answer;
    private RadioButton ans1,ans2,ans3,defult;
    public Toolbar toolbar;
    private String title , username;
    private int id,status;
    private FloatingActionButton button;


    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle getbundle = getIntent().getExtras();
        course_id = getbundle.getInt("Course");
        module_id = getbundle.getInt("module");
        chapterid = getbundle.getInt("chapter");
        title     = getbundle.getString("title");
        username  = getbundle.getString("username");
        id        = getbundle.getInt("id");
        status        = getbundle.getInt("status");

        double random = Math.ceil(Math.random()*2);
        final int serial = (int) random;
        setuptoolber(title);

        mydb = new Database(this);

        answer = (RadioGroup) findViewById(R.id.answer);
        ans1 = (RadioButton) findViewById(R.id.ans1);
        ans2 = (RadioButton) findViewById(R.id.ans2);
        ans3 = (RadioButton) findViewById(R.id.ans3);
        button = (FloatingActionButton) findViewById(R.id.nextquestionl);
        question = (TextView) findViewById(R.id.questionid);


        // Get Question from Question Table
        data = fetch_question(course_id ,module_id,chapterid,serial);

        question.setText(data[0]);
        ans1.setText(data[1]);
        ans2.setText(data[2]);
        ans3.setText(data[3]);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(answer.getCheckedRadioButtonId() == ans1.getId() ||answer.getCheckedRadioButtonId() == ans2.getId() || answer.getCheckedRadioButtonId() == ans3.getId() ){

                    defult = (RadioButton) findViewById(answer.getCheckedRadioButtonId());
                    String answer = defult.getText().toString();
                    if (answer.equals(data[4])){
                        Toast.makeText(getApplicationContext(),"Right Answer !!!",Toast.LENGTH_LONG).show();



                        mydb.updateScore(id,course_id,module_id,chapterid);
                        if(status == 0 ){
                            int get = mydb.queryscore(id,course_id,module_id);
                            if (get >= 7 ){
                                mydb.updatestatus(id,course_id,module_id+1);
                                Toast.makeText(getApplicationContext(),"New Module unlock !!!",Toast.LENGTH_LONG).show();
                            }
                        }

                        Intent backintenet = new Intent(getApplicationContext(),ModuleSample.class);

                        Bundle sentbundle = new Bundle();
                        sentbundle.putInt("Course",course_id);
                        sentbundle.putInt("module",module_id);
                        sentbundle.putInt("chapter",chapterid);
                        sentbundle.putString("username",username);
                        sentbundle.putString("title",title);
                        sentbundle.putInt("id",id);
                        sentbundle.putInt("status",status);
                        backintenet.putExtras(sentbundle);

                        startActivity(backintenet);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Wrong  Answer !!!!",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Select a Answer",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String[] fetch_question(int course_id, int module_id, int chapterid, int serial) {

        Cursor res;

        res = mydb.fetch_question(course_id,module_id,chapterid,serial);
        String [] data = new String[5];

        if(res.getCount() > 0){


            while (res.moveToNext()) {
                data[0] = res.getString(0);
                data[1] = res.getString(1);
                data[2] = res.getString(2);
                data[3] = res.getString(3);
                data[4] = res.getString(4);
            }
        }

        return data;
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








