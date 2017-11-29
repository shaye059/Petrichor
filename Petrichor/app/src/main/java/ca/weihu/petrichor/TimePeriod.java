package ca.weihu.petrichor;

/**
 * Class TimePeriodCollection
 *
 * @author Galacticos
 * @version 1.0 Tuesday, November 28, 2017
 */
public class TimePeriod {

    private TimePeriodLabel label;
    private int identifier;
    private TimePeriod parent;

    private enum TimePeriodLabel {
        DAY, WEEK, MONTH, YEAR
    }

    public void setType(TimePeriodLabel x) {
        label = x;
    }

    public void setIdentifier(int x) {
        identifier = x;
    }

}
