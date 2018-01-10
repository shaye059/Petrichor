package ca.weihu.petrichor;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by spenc on 2017-12-06.
 * In theory this should extend Highlight but it doesn't write to the database in that situation for
 * some reason so it'll just be a separate object with it's own adapter.
 */


public class TaggedHighlight {
    // I N S T A N C E  V A R I A B L E S

    private String id;
    String description;
    private String taggerID;


    // C O N S T R U C T O R S

    public TaggedHighlight() {
    }

    public TaggedHighlight(String id,String description, String taggerID){
        this.id = id;
        this.description = description;
        this.taggerID = taggerID;
    }


    // M E T H O D S

    // S E T T E R S

    public void setId(String id) {
        this.id = id;
    }

    public void setTaggerID(String id) {this.taggerID = id;}

    public void setDescription(String description){this.description = description;}




    // G E T T E R S

    public String getId() {
        return id;
    }

    public String getTaggerID(){return taggerID;}

    public String getDescription(){return  description;}
}
