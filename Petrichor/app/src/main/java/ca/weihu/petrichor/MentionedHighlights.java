package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MentionedHighlights extends AppCompatActivity implements HighlightCollection{

    private List<TaggedHighlight> highlights;
    private ListView list;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String userID;
    private DatabaseReference userRef;
    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mentionedhighlights);
        hideSystemUI();

        list=(ListView)findViewById(R.id.listMentionedHighlights);

        mAuth = FirebaseAuth.getInstance().getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();
        context = this;


        userRef = FirebaseDatabase.getInstance().getReference().child("Account").child(userID).child("taggedhighlights");

        highlights = new ArrayList<TaggedHighlight>();


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highlights.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //This is definitely bad practice considering the type stored here doesn't inherit
                    //from Highlight but it doesn't work when I do that so it's going to stay like this
                    TaggedHighlight highlight = postSnapshot.getValue(TaggedHighlight.class);
                    highlights.add(highlight);
                }
                if(highlights.size() == 0){
                    highlights.add(new TaggedHighlight(null, "No tagged highlights to show :(", null));
                }

                final TaggedHighlightListAdapter highlightsAdapter =
                        new TaggedHighlightListAdapter(MentionedHighlights.this, highlights,user.getEmail(), userID);

                list.setAdapter(highlightsAdapter);

                list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TaggedHighlight highlight = highlights.get(i);
                        showUnTagDialgogue(highlight);
                        return true;
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MentionedHighlights.this, "Error retrieving highlights.",
                        Toast.LENGTH_SHORT);
            }

        });


    }

    private void showUnTagDialgogue( final TaggedHighlight highlight){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.untag_highlight_dialogue, null);
        dialogBuilder.setView(dialogView);

        final Button buttonReemove = (Button) dialogView.findViewById(R.id.buttonRemoveTag);
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.buttonCancelTD);


        dialogBuilder.setCustomTitle(inflater.inflate(R.layout.dialogue_title_tag, null));
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonReemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                DatabaseReference databaseHighlights = FirebaseDatabase.getInstance().getReference().
                        child("Account").child(userID).child("taggedhighlights");
                databaseHighlights.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot highlightSnapshot: dataSnapshot.getChildren()){
                            if(highlightSnapshot.getKey().equals(highlight.getId()+highlight.getTaggerID())) {
                                FirebaseDatabase.getInstance().getReference().
                                        child("Account").child(userID).child("taggedhighlights").child(highlightSnapshot.getKey()).setValue(null);
                            }
                        }
                        Toast.makeText(context, "Tag Removed", Toast.LENGTH_LONG).show();
                        context.recreate();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                b.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
                hideSystemUI();
            }
        });
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void onBtnBack(View view) {
        onBackPressed();
    }

}