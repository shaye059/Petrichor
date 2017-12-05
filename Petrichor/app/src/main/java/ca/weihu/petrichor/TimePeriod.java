package ca.weihu.petrichor;

/**
 * Class TimePeriodCollection
 *
 * @author Galacticos
 * @version 1.0 Tuesday, November 28, 2017
 */
public class TimePeriod {

    public enum TimePeriodLabel {
        DAY, WEEK, MONTH, YEAR
    }

    private TimePeriodLabel label;
    private int identifier;
    private TimePeriod parent;


    public TimePeriod(TimePeriodLabel label, int identifier, TimePeriod parent) {
        this.label=label;
        this.identifier=identifier;
        this.parent=parent;
    }

    public void setType(TimePeriodLabel x) {
        label = x;
    }

    public void setIdentifier(int x) {
        identifier = x;
    }

    public void setParent(TimePeriod parent){
        this.parent=parent;
    }

    public TimePeriodLabel getLabel(){
        return label;
    }

    public int getIdentifier(){
        return identifier;
    }

    public TimePeriod getParent(){
        return parent;
    }

}
