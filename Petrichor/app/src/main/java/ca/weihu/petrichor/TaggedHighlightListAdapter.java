package ca.weihu.petrichor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by spenc on 2017-12-06.
 */

public class TaggedHighlightListAdapter extends ArrayAdapter<TaggedHighlight>{

    /*==============================================================================================
        V A R I A B L E S
    ==============================================================================================*/

        protected enum hListPurpose {
            DAY, WEEK, MONTH, YEAR, SCROLLBACK, TODAYINPAST, RANDOMDAY
        }

        private Activity context;
        private List<TaggedHighlight> taggedHighlights;
        private String highlightID;

        ca.weihu.petrichor.HighlightListAdapter.hListPurpose use;

    /*==============================================================================================
        C O N S T R U C T O R
    ==============================================================================================*/

        public TaggedHighlightListAdapter(Activity context, List<TaggedHighlight> taggedHighlights, String thisEmail, String currentUser) {
            super(context, R.layout.layout_highlight_list_item, taggedHighlights);
            this.context = context;
            this.taggedHighlights = taggedHighlights;
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

            final TaggedHighlight taggedHighlight = taggedHighlights.get(position);

                if (taggedHighlight.getId() != null) {
                    highlightID = taggedHighlight.getId();

                    textViewDate.setText(Time.convertToMonthAbr(highlightID) + " "
                            + Time.convertToDayOfWeekAbr(highlightID) + " "
                            + Time.convertToDay(highlightID) + ", "
                            + Time.convertToYear(highlightID) + " (week "
                            + Time.convertToWeek(highlightID) + ")");
                } else {
                    textViewDate.setText("No Highlights to display.");
                }

// when have time implement only show day of the week once for the first highlight only, etc.

            textViewHighlight.setText(taggedHighlight.getDescription());

            share.setVisibility(View.GONE);


            return listViewSingleHighlight;
        }
    }


