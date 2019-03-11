package com.example.triangle3soft.exploreprogramming;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.example.triangle3soft.exploreprogramming.helperClass.BaseAdapter;
import com.example.triangle3soft.exploreprogramming.helperClass.Database;

public class ModuleSample extends AppCompatActivity {

    private ExpandableListView expanlist;


    private List<String> headerlist,basic,conditional,data,classes,more;
    private HashMap<String,List<String>> childlist;
    private BaseAdapter baseAdapter;
    private int course_id,module_id,chapterid,id,status;
    private String course,title , username;
    public Integer [] score , statuslist;
    Database db= new Database(this);
    private int lastexpanposition = -1;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_sample);

        Bundle getbundle = getIntent().getExtras();
        title     = getbundle.getString("title");
        username  = getbundle.getString("username");
        id        = getbundle.getInt("id");
        status        = getbundle.getInt("status");
        course_id = getbundle.getInt("Course");
        module_id = getbundle.getInt("module",-1);
        chapterid = getbundle.getInt("chapter",-1);


        setuptoolber(title);


        score = getScore(username);
        statuslist = getstatuslist(username);


        expanlist = (ExpandableListView) findViewById(R.id.expanlist);
        preparedatalist();

        baseAdapter = new BaseAdapter(this,headerlist,childlist,score,statuslist);
        expanlist.setAdapter(baseAdapter);

        expanlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                String ss = headerlist.get(i);
                //Toast.makeText(getApplicationContext(),"Group name : "+ss,Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        expanlist.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                String ss = headerlist.get(i);
                //  Toast.makeText(getApplicationContext(),ss+" Collapse  ",Toast.LENGTH_SHORT).show();



            }
        });

        expanlist.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Intent intent = new Intent(getApplicationContext(), Content.class);

                Bundle sentbundle = new Bundle();
                sentbundle.putInt("Course",course_id);
                sentbundle.putInt("module",i+1);
                sentbundle.putInt("chapter",i1+1);
                sentbundle.putString("username",username);
                sentbundle.putString("title",title);
                sentbundle.putInt("id",id);
                sentbundle.putInt("status",status);
                intent.putExtras(sentbundle);
                startActivity(intent);

                return false;
            }
        });

        expanlist.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if(lastexpanposition !=-1 && lastexpanposition!=i){
                    expanlist.collapseGroup(lastexpanposition);
                }
                lastexpanposition = i;

            }
        });





    }

    private Integer[] getstatuslist(String username) {

        Integer status[] = new Integer[5];

        status[0] = db.getstatus(id,course_id,1);
        status[1] = db.getstatus(id,course_id,2);
        status[2] = db.getstatus(id,course_id,3);
        status[3] = db.getstatus(id,course_id,4);
        status[4] = db.getstatus(id,course_id,4);

        return status;


    }


    private Integer[] getScore(String username) {
        Integer score[] = new Integer[5];

        score[0] = db.queryscore(id,course_id,1);
        score[1] = db.queryscore(id,course_id,2);
        score[2] = db.queryscore(id,course_id,3);
        score[3] = db.queryscore(id,course_id,4);
        score[4] = db.queryscore(id,course_id,4);

        return score;

    }

    protected  void preparedatalist(){

        headerlist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.java)));
        data = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.data)));
        basic = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Basic)));
        conditional = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.Condition)));
        classes = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.classes)));
        more = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.more)));

        childlist = new HashMap<>();
        childlist.put(headerlist.get(0),basic);
        childlist.put(headerlist.get(1),data);
        childlist.put(headerlist.get(2),conditional);
        childlist.put(headerlist.get(3),classes);
        childlist.put(headerlist.get(4),more);



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







