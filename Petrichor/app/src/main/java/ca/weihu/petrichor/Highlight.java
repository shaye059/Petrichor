package ca.weihu.petrichor;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Tri-An on December 02, 2017.
 */

public class Highlight {

    // I N S T A N C E  V A R I A B L E S

    private String id;
    private String description;

    private DatabaseReference dbRefHighlight;


    // C O N S T R U C T O R S

    public Highlight() {
    }

    public Highlight(String id, String description){

        this.id = id;
        this.description = description;
    }


    // M E T H O D S

    // S E T T E R S

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // G E T T E R S

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public DatabaseReference getDbRefHighlight() {
        return dbRefHighlight;
    }
}
