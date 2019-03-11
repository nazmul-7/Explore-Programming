package com.example.triangle3soft.exploreprogramming.helperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.example.triangle3soft.exploreprogramming.MainActivity;
import com.example.triangle3soft.exploreprogramming.R;

/**
 * Created by Nazmul.7 on 11/24/2017.
 */

public class BaseAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerlist;
    private HashMap<String,List<String>> childlist;
    private Integer[] score,statuslist;


    public BaseAdapter(Context context, List<String> headerlist, HashMap<String, List<String>> childlist, Integer[] score,Integer[] statuslist)  {
        this.context = context;
        this.headerlist = headerlist;
        this.childlist = childlist;
        this.score = score;
        this.statuslist = statuslist;
    }

    @Override
    public int getGroupCount() {
        return headerlist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return childlist.get(headerlist.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return headerlist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return childlist.get(headerlist.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String s = (String) getGroup(i);
        if(view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.groupview,null);
        }


        TextView text1 = view.findViewById(R.id.grouptext);
        ImageView img1 = (ImageView) view.findViewById(R.id.img1);
        ImageView img2 = (ImageView) view.findViewById(R.id.img2);
        ImageView img3 = (ImageView) view.findViewById(R.id.img3);


        if (score[i] >= 2) {
            img1.setImageResource(R.drawable.star2);
        }
        if (score[i] >= 5) {
            img3.setImageResource(R.drawable.star2);
        }
        if (score[i] >= 7) {
            img2.setImageResource(R.drawable.star2);
        }

           if(statuslist[i]==0)
            {view.setClickable(true);
                     view.setBackgroundColor(MainActivity.color);
                                               }

        text1.setText(s);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        String s = (String) getChild(i,i1);
        if(view==null){

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.childview,null);
        }


        TextView text1 = view.findViewById(R.id.childtext);


        text1.setText(s);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
