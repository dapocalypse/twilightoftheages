package Popsim;

import java.util.*;

public class Faction
{
    private String name;
    private ArrayList<Value> values;
    
    public Faction(String name, ArrayList<ValueSet> valueSet, int[] values)
    {
        this.name = name;
        this.values = new ArrayList<Value>();
        
        for(int i = 0; i < valueSet.size(); i++)
        {
            this.values.add(new Value(valueSet.get(i), values[i]));
        }
    }
    
    public ArrayList<Value> getValues()
    {
        return values;
    }
    
    public String getName() { return name; }
    
    public void modifyValue(int index, int ammount)
    {
        values.get(index).modifyValue(ammount);
    }
    
    public int getValue(int index)
    {
        return values.get(index).getPosValue();
    }
}
