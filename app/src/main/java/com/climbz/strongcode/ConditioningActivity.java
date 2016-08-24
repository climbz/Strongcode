package com.climbz.strongcode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConditioningActivity extends AppCompatActivity {

    //TODO: clean and reorganize expandablelistview related code
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditioning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.explistview);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        // preparing list data

        List<Exercise> exercises = null;
        try {
            XMLExerciseParserHandler parser = new XMLExerciseParserHandler();
            exercises = parser.parse(getAssets().open("conditioning.xml"));
            for (int i = 0; i < exercises.size(); ++i) {
                listDataHeader.add(exercises.get(i).getTitle());
                listDataChild.put(listDataHeader.get(i), exercises.get(i).getProgression());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        /*final ExpandableListView listview = (ExpandableListView) findViewById(R.id.explistview);

        List<Exercise> exercises = null;
        try {
            XMLExerciseParserHandler parser = new XMLExerciseParserHandler();
            exercises = parser.parse(getAssets().open("conditioning.xml"));
            final ArrayList<String> titleList = new ArrayList<String>();
            for (int i = 0; i < exercises.size(); ++i) {
                titleList.add(exercises.get(i).getTitle());
            }
            final SimpleArrayAdapter adapter = new SimpleArrayAdapter(this,titleList);
            listview.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

            }

        });*/
    }


    //TODO:clean this up as in delete it and do it another way
    private void prepareListDataTemporary() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
    }

}
