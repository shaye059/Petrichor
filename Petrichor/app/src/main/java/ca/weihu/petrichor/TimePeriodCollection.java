package ca.weihu.petrichor;

/**
 * Created by Tri-An on November 28, 2017.
 */

public class TimePeriodCollection implements HighlightCollection {

    TimePeriod[] timePeriods;

    @Override
    public Highlight[] returnHighlight() {
        return new Highlight[0];
    }
}
