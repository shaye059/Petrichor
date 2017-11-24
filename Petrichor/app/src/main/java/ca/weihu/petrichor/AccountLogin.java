package ca.weihu.petrichor;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.RelativeLayout;

import java.util.List;

public class AccountLogin extends AppCompatActivity {

    private RelativeLayout relLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        hideSystemUI();


        // code to hide keyboard when relative layout is touched

        relLayout = (RelativeLayout) findViewById(R.id.relLay);

        relLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){

                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                hideSystemUI();

                return true;
            }
        });
    }

    /**
     * Overriding onBackPressed() to display a warning before exiting the app
     */
    @Override
    public void onBackPressed() {

        // check if activity_account_login is last in the stack and if so then show warning
        // https://stackoverflow.com/questions/5975811/how-to-check-if-an-activity-is-the-last-one-in-the-activity-stack-for-an-applica

        ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

        if(taskList.get(0).numActivities == 1 &&
                taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Closing App");
            dialogBuilder.setMessage("Do you really want to exit?");

            dialogBuilder.setNegativeButton("No", null);

            dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                //@Override
                public void onClick(DialogInterface dialog, int n) {
                    AccountLogin.super.onBackPressed();
                }
            });

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();

        } else {
            AccountLogin.super.onBackPressed();
        }
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }

    public void onLogin(View view) {
        Intent in = new Intent(getApplicationContext(), Home.class);
        startActivity(in);
    }
    public void onAccountCreate(View view) {
        Intent in = new Intent(getApplicationContext(), AccountCreate.class);
        startActivity(in);
    }


    // P R E V E N T I N G  E X I T I N G  F U L L S C R E E N

    // checks if window has focus
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        // When the window gains focus, hide the system UI.
        if (hasFocus) {
            hideSystemUI();

        // When the window loses focus (e.g. the action overflow is shown),
        // cancel any pending hide action.
        } else {
            hideSystemUI();
        }
    }

    // hides status bar and navbar
    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}