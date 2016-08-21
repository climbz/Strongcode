package com.climbz.strongcode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConditioningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditioning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.listview);

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

        });
    }

}
