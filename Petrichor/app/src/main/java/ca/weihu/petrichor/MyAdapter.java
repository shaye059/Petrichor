package ca.weihu.petrichor;

/**
 * Created by divya on 11/30/2017.
 */

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ca.weihu.petrichor.R;

import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<String> {
    // This is constructor for MyAdapter : You can edit its second parameter a/c to your requirement
    // I have used Array List of string
    public MyAdapter(Context context, ArrayList<String> records) {
        super(context, 0, records);
    }

    @Override
    // This is important method : You can write your own code in this function
    // You can set your textview/ button methods :
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_view, parent, false);
        }

        final Button list_Txt = (Button) convertView.findViewById(R.id.List_txt);

        list_Txt.setText(item);

        return convertView;
    }
}
