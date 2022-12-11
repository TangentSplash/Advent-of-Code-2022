public class Variable {
    private int value;
    private boolean fixedValue;

    public Variable (int value)
    {
        this.value=value;
        fixedValue=true;
    }

    public Variable ()
    {
        fixedValue=false;
    }

    public int getValue(int currentValue)
    {
        if(fixedValue)
        {
            return value;
        }
        else
        {
            return currentValue;
        }
    }
}
