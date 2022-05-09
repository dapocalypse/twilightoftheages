package Popsim;

import java.util.*;

public class PopGroup
{
    private String name;
    private ArrayList<Value> values;
    private int radicalization;
    
    public PopGroup(String name, ArrayList<ValueSet> valueSet, int[] values, int radicalization)
    {
        this.name = name;
        this.values = new ArrayList<Value>();
        // How pro-establishment a group is.
        this.radicalization = radicalization;
        for(int i = 0; i < valueSet.size(); i++)
        {
            this.values.add(new Value(valueSet.get(i), values[i]));
        }
    }
    
    public int getCompatability(Faction faction)
    {
        int returnValue = 0;
        ArrayList<Value> valuesTwo = faction.getValues();
        
        for(int i = 0; i < values.size(); i++)
        {
            returnValue += (values.get(i).getPosValue()-valuesTwo.get(i).getPosValue())*(values.get(i).getPosValue()-valuesTwo.get(i).getPosValue())/valuesTwo.get(i).getPosValue();
        }
        
        if(returnValue == 0) return 1;
        return returnValue;
    }
    
    public void modifyValue(int index, int ammount)
    {
        values.get(index).modifyValue(ammount);
    }
    
    public int getValue(int index)
    {
        return values.get(index).getPosValue();
    }
}
