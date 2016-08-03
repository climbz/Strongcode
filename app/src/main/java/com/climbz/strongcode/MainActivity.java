package com.climbz.strongcode;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.gridview);

//        List<ItemObject> allOldItems = getAllItemObject();
//        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, allOldItems);
        gridview.setAdapter(new MyAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();

                // this 'mActivity' parameter is Activity object, you can send the current activity.
                if (position==1) {
                    Intent i = new Intent(view.getContext(), AutoWarmupTimer.class);
                    view.getContext().startActivity(i);
                }else if (position==3){
                    Intent i = new Intent(view.getContext(), MobilityActivity.class);
                    view.getContext().startActivity(i);
                }
            }
        });
    }


    private class MyAdapter extends BaseAdapter {
        private List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);

            //items.add(new Item("Image 1", R.drawable.chess));
            items.add(new Item("Flexibility", R.drawable.bridge));
            items.add(new Item("Bodyline Routine", R.drawable.nakedplank));
            items.add(new Item("Conditioning", R.drawable.onearmhandstand));
            items.add(new Item("Mobility", R.drawable.flap));
            items.add(new Item("Handstand", R.drawable.tuckhand));
            items.add(new Item("Planche", R.drawable.planche));
            items.add(new Item("ET", R.drawable.et));
            items.add(new Item("Swim", R.drawable.ski536));
            items.add(new Item("Cardio", R.drawable.haze));
            //items.add(new Item("Above Water", R.drawable.ski)); //TODO: Fix large image failure (shows up white)
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;
            TextView name;

            if (v == null) {
                v = inflater.inflate(R.layout.image_with_text, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView) v.getTag(R.id.picture);
            name = (TextView) v.getTag(R.id.text);

            Item item = (Item) getItem(i);

            picture.setImageResource(item.drawableId);
            name.setText(item.name);

            return v;
        }

        private class Item {
            final String name;
            final int drawableId;

            Item(String name, int drawableId) {
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}