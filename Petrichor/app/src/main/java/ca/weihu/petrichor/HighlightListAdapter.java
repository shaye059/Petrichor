package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Tri-An on December 03, 2017.
 */

public class HighlightListAdapter extends ArrayAdapter<Highlight> {

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

    protected enum hListPurpose {
        DAY, WEEK, MONTH, YEAR, SCROLLBACK, TODAYINPAST, RANDOMDAY
    }

    private Activity context;
    private List<Highlight> highlights;
    private String thisEmail;
    private String currentUser;

    hListPurpose use;

    /*==============================================================================================
        C O N S T R U C T O R
    ==============================================================================================*/

    public HighlightListAdapter(Activity context, List<Highlight> highlights, hListPurpose use, String thisEmail, String currentUser) {
        super(context, R.layout.layout_highlight_list_item, highlights);
        this.context = context;
        this.highlights = highlights;
        this.use = use;
        this.thisEmail = thisEmail;
        this.currentUser = currentUser;
    }

    /*==============================================================================================
        M E T H O D S
    ==============================================================================================*/

    /**
     *
     * @param position gets view of EACH highlight corresponding to position in query
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        //View listViewSingleHighlight = inflater.inflate(R.layout.layout_highlight_list_item, parent, false)
        View listViewSingleHighlight = inflater.inflate(R.layout.layout_highlight_item,
                null, true);

        TextView textViewDate = (TextView) listViewSingleHighlight
                .findViewById(R.id.textViewDate);
        TextView textViewHighlight = (TextView) listViewSingleHighlight
                .findViewById(R.id.textViewHighlight);

        View share = listViewSingleHighlight.findViewById(R.id.tagFriendsButton);

        final Highlight highlight = highlights.get(position);

        if (use == hListPurpose.WEEK) {
            //textViewDate.setWidth(150);
            textViewDate.setText( Time.convertToDayOfWeekFullName(highlight.getId()) + " "
                    + Time.convertToDay(highlight.getId()) );

        } else if (use == hListPurpose.MONTH) {
            //textViewDate.setWidth(250);
            textViewDate.setText( Time.convertToDayOfWeekFullName(highlight.getId()) + " "
                    + Time.convertToDay(highlight.getId()) + " (week "
                    + Time.convertToWeek(highlight.getId()) + ")" );

        } else if (use == hListPurpose.YEAR) {
            //textViewDate.setWidth(250);
            textViewDate.setText( Time.convertToMonthFullName(highlight.getId()) + ", "
                    + Time.convertToDayOfWeekFullName(highlight.getId()) + " "
                    + Time.convertToDay(highlight.getId()) + " (week "
                    + Time.convertToWeek(highlight.getId()) + ")" );
        } else if (use == hListPurpose.SCROLLBACK
                || use == hListPurpose.TODAYINPAST
                || use == hListPurpose.RANDOMDAY) {

            //textViewDate.setWidth(250);

            if (highlight.getId() != null) {
                textViewDate.setText(Time.convertToMonthAbr(highlight.getId()) + " "
                        + Time.convertToDayOfWeekAbr(highlight.getId()) + " "
                        + Time.convertToDay(highlight.getId()) + ", "
                        + Time.convertToYear(highlight.getId()) + " (week "
                        + Time.convertToWeek(highlight.getId()) + ")");
            } else {
                textViewDate.setText("No Highlights to display.");
            }
        }

// when have time implement only show day of the week once for the first highlight only, etc.

        textViewHighlight.setText(highlight.getDescription());

        if(!currentUser.equals(thisEmail)){
            share.setVisibility(View.GONE);
        }else {
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, TagFriend.class);
                    in.putExtra("highlightID", highlight.getId());
                    in.putExtra("description", highlight.getDescription());
                    context.startActivity(in);
                }
            });
        }

        return listViewSingleHighlight;
    }
}
