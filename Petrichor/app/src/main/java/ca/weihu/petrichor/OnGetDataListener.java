package ca.weihu.petrichor;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by spenc on 2017-12-04.
 */

public interface OnGetDataListener {
    //this is for callbacks
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
