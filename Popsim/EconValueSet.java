package Popsim;

public class EconValueSet extends ValueSet
{
    private String thirdValue;
    
    public EconValueSet(String posName, String negName, String thirdValue)
    {
        super(posName, negName);
        this.thirdValue = thirdValue;
    }
}
