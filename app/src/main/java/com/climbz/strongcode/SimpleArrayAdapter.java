package com.climbz.strongcode;


    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.TextView;

    public class SimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public SimpleArrayAdapter(Context context, String[] values) {
            super(context, -1, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item_with_icon, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            String s = values[position];
            if (s.startsWith("End")) {
                imageView.setImageResource(R.drawable.et);
            }

            return rowView;
        }
    }


