package ca.weihu.petrichor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelpHowToUse extends AppCompatActivityMod {

    private StringBuilder text = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_how_to_use);

        //Reader that extracts texts from "instructions" text file in assets
        //Code borrowed from Naruto Uzumaki, found on Stack Overflow in the following thread
        // https://stackoverflow.com/questions/33779607/reading-a-txt-file-and-outputing-as-a-textview-in-android
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("instructions")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
                text.append('\n');
                text.append('\n');
            }
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(),"Error reading file!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }

            TextView output= (TextView) findViewById(R.id.instructionView);
            output.setText((CharSequence) text);
            output.setMovementMethod(new ScrollingMovementMethod());

        }


    }

    //OnClick method for Back Button
    public void onBtnBack(View view) {

        Intent in = new Intent(getApplicationContext(), NavBar.class);
        startActivity(in);
    }
}
