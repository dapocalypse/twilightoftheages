package Popsim;

public class Value
{
    private ValueSet set;
    private int scaleValue;
    
    public Value(ValueSet set, int scaleValue)
    {
        this.set = set;
        this.scaleValue = scaleValue;
    }
    
    private void clampScale()
    {
        if(scaleValue > 10) scaleValue = 10;
        if(scaleValue < 0) scaleValue = 0;
    }
    
    public int getPosValue() { return scaleValue; }
    private int getNegValue() { return 10 - scaleValue; }
    
    public void modifyValue(int ammount)
    {
        scaleValue += ammount;
        clampScale();
    }
}
