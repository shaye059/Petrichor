package ca.weihu.petrichor;

/**
 * Created by divya on 11/30/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;


public class MyDeleteAdapter extends ArrayAdapter<String> {
    // This is constructor for MyAdapter : You can edit its second parameter a/c to your requirement
    // I have used Array List of string
    public MyDeleteAdapter(Context context, ArrayList<String> records) {
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
        list_Txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // It will change textview text :

                list_Txt.setText("Successfully Deleted Friend");

            }
        });
        return convertView;
    }
}
