package com.climbz.strongcode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GymnasticStaticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymnastic_statics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //my code below
        final ListView listview = (ListView) findViewById(R.id.listview);

        List<Exercise> exercises = null;
        try {
            XMLExerciseParserHandler parser = new XMLExerciseParserHandler();
            exercises = parser.parse(getAssets().open("statics.xml"));
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


                Toast.makeText(GymnasticStaticsActivity.this, item, Toast.LENGTH_SHORT).show();

            }

        });
    }

}
