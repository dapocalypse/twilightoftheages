package Popsim;

import java.util.*;

public class PopsimManager
{
    private static ArrayList<ValueSet> valueSets;
    private static ArrayList<PopGroup> popGroups;
    private static ArrayList<Faction> factions;
    
    private static double[][] popularityTable;
    private static int[] popGroupRatio;
    private static double[][] modifierTable;
    
    // For testing purposes
    public static void main(String[] args)
    {
        initialise();
    }
    
    // initialise popsim values
    public static void initialise()
    {
        // VALUES
        
        // How opposed to centralism are you?
        ValueSet federalism = new ValueSet("Federalism", "Unitarianism");
        // How open should politics be to the masses? This covers more policy than rhetoric.
        // Whether there is democracy or not basically.
        ValueSet populism = new ValueSet("Populism", "Elitism");
        // Should the Revolution be spread abroad or do we need to secure it at home?
        ValueSet internationalism = new ValueSet("Internationalism", "Isolationism");
        // Hawk or dove? Sword or Pen?
        ValueSet militarism = new ValueSet("Militarism", "Pacifism");
        // How much power should the government have over citizens lives?
        ValueSet libertarianism = new ValueSet("Libertarianism", "Statism");
        
        /*
         * Alright, Capitalism here means deregulation and what not.
         * Third-Positionism here means intense collaboration between private enterprise and the state
         * Socialism here means economic nationalisation, this doesn't really line up with what I actually want to 
         * describe but its the best I have here.
         * the 0-10 scale remains, with values 4-6 representing third positionism. 6-7 would be syndicate capitalism, where companies
         * organize into giant conglomerates which monopolize a single industry and cooperate with eachother. 4-5 is when the syndicates
         * are under the state and companies register with it. 
         * 
         * On the far end of 1 is collectivism.
         * 
         * Labor Unions and workers rights are covered under Progressivism-Traditionalism
         */
        EconValueSet econValueSet = new EconValueSet("Capitalism", "Socialism", "Third-Positionism");
        // Is the revolution based upon purely material influences or is there something deeper, almost spiritual.
        ValueSet materialism = new ValueSet("Materialism", "Esotericism");
        // Self explanatory, progressive also includes things like welfare, and labor rights.
        ValueSet progressivism = new ValueSet("Progressivism", "Traditionalism");
        // Do we like aliens or not. 
        ValueSet multiculturalism = new ValueSet("Multiculturalism", "Xenophobia");
        
        ArrayList<ValueSet> valueArrays = new ArrayList<ValueSet>();
        valueArrays.add(federalism);
        valueArrays.add(populism);
        valueArrays.add(internationalism);
        valueArrays.add(militarism);
        valueArrays.add(libertarianism);
        valueArrays.add(econValueSet);
        valueArrays.add(materialism);
        valueArrays.add(multiculturalism);
        valueArrays.add(progressivism);
        
        valueSets = valueArrays;
        
        // POPGROUPS
        
        // State Conservatives value calm and stable government, and securing the Revolution not through reform or export, but through
        // securing Orion first.
        int[] stateCon = {7, 5, 4, 6, 4, 4, 7, 4, 4};
        PopGroup stateConservative = new PopGroup("State Conservatives", valueSets, stateCon, 25);
        
        // Autonomists represend the ideal Revolutionaries. At least according to them. While their group is more diverse, they unite
        // under the banner of states' rights and duties, and a competitive and free economy.
        int[] auton = {10, 7, 4, 4, 8, 8, 7, 6, 5};
        PopGroup autonomist = new PopGroup("Autonomists", valueSets, auton, 70);
        
        // Revolutionary Liberals are an offshoot of classical revolutionary ideology. To them, the Revolution was not completed in
        // 2500 but requires constant and honest work and reform. Liberalisation of the economy, media, and even politics are some
        // of their primary concerns.
        int[] liberal = {6, 8, 7, 4, 6, 7, 7, 7, 8};
        PopGroup revolutionaryLiberal = new PopGroup("Revolutionary Liberals", valueSets, liberal, 60);
        
        // Revolutionary Radicals bring the Revolution to its logical conclusion. Seeing the Revolution as not fully implemented,
        // they will vote and support those who promise to expand Revolutionary dogma to dictate more of society. They also
        // support foreign interventions, and ensuring the stars themselves become shaped by the will of the Revolution.
        int[] radical = {6, 3, 8, 8, 4, 4, 4, 7, 6};
        PopGroup revolutionaryRadical = new PopGroup("Revolutionary Radicals", valueSets, radical, 70);
        
        // Social Conservatives should not be confused with State Conservatives. They are disinterested with politics and are less
        // mainstream. These Conservatives are primarily concerned about major social changes, disrupting hierarchies, and violating
        // traditions.
        int[] socCon = {6, 3, 3, 7, 7, 6, 3, 3, 3};
        PopGroup socialConservative = new PopGroup("Social Conservatives", valueSets, socCon, 30);
        
        // Communalists (fringe) take much from the socialists of yore. While the communist movement itself is not present in Orion,
        // the communalists are greatly inspired by it. Finding their origins amongst the first colonists, their chief values are the ideals of
        // cooperative community, social government, freedom from overreach and exploitation, and most importantlt, a free society.
        int[] com = {7, 9, 8, 3, 9, 1, 7, 8, 9};
        PopGroup communalist = new PopGroup("Communalists", valueSets, com, 65);
        
        // National Revolutionaries (fringe) are peculiar and extreme radicals. There are no moderate positions. National Revolutionaries first
        // and formost believe that the state of Orion itself is the Revolution, and that she must secure her own existance first and forever.
        // More radical proposals include the nationalization of the syndicates, having companies directly register under the state. 
        // In addition, they believe in the complete fusion of state and party.
        int[] natRev = {3, 3, 3, 10, 2, 4, 2, 2, 2};
        PopGroup nationalRevolutionary = new PopGroup("National Revolutionaries", valueSets, natRev, 85);
        
        popGroups = new ArrayList<PopGroup>();
        popGroups.add(stateConservative);
        popGroups.add(autonomist);
        popGroups.add(revolutionaryLiberal);
        popGroups.add(revolutionaryRadical);
        popGroups.add(socialConservative);
        popGroups.add(communalist);
        popGroups.add(nationalRevolutionary);
        
        // FACTIONS
        
        // Conservative Faction of the People's Revolutionary Front
        int[] prfc = {7, 4, 4, 6, 5, 5, 6, 4, 4};
        Faction prfConservative = new Faction("PRF-Conservative", valueSets, prfc);
        
        // Ultrafederalist Faction of the People's Revolutionary Front
        int[] prfu = {10, 7, 4, 4, 8, 8, 7, 4, 5};
        Faction prfUltrafederalist = new Faction("PRF-Ultrafederalist", valueSets, prfu);
        
        // Reformist Faction of the People's Revolutionary Front
        int[] prfr = {6, 8, 7, 4, 6, 6, 6, 6, 7};
        Faction prfReform = new Faction("PRF-Reformist", valueSets, prfr);
        
        // Hardliner Faction of the People's Revolutionary Front
        int[] prfh = {5, 3, 7, 9, 3, 4, 3, 6, 5};
        Faction prfHardliner = new Faction("PRF-Hardliner", valueSets, prfh);
        
        factions = new ArrayList<Faction>();
        factions.add(prfConservative);
        factions.add(prfUltrafederalist);
        factions.add(prfReform);
        factions.add(prfHardliner);
        
        int[] a = {10, 4, 8, 8, 5, 1, 1};
        popGroupRatio = a;
        
        modifierTable = new double[popGroups.size()][factions.size()];
        
        loadCompatibility();
    }
    
    private static void loadCompatibility()
    {
        int[][] setMatrix = new int[popGroups.size()][factions.size()];
        popularityTable = new double[popGroups.size()][factions.size()];
        
        // For each pop-group's and faction's values we take the number of the pop group and subtract it from the faction number
        // square it, and divide the square by the faction number. We add up all of these for each value.
        
        for(int i = 0; i < popGroups.size(); i++)
            for(int j = 0; j < factions.size(); j++)
                setMatrix[i][j] = popGroups.get(i).getCompatability(factions.get(j));
                
        for(int i = 0; i < popGroups.size(); i++)
            for(int j = 0; j < factions.size(); j++)
            {
                popularityTable[i][j] = popGroupRatio[i]/setMatrix[i][j];
            }
    }
    
    public static double[] getFactionPopularities()
    {
        int total = 0;
        double[] returnArray = new double[factions.size()];
        double[][] finalPopTable = new double[popGroups.size()][factions.size()];
        
        for(int i = 0; i < popGroups.size(); i++)
            for(int j = 0; j < factions.size(); j++)
                finalPopTable[i][j] += popularityTable[i][j];
        
        for(int i = 0; i < popGroups.size(); i++)
            for(int j = 0; j < factions.size(); j++)
                    if(modifierTable[i][j] != 0)
                    {
                        int cap = 0;
                        for(int a = 0; a < factions.size(); a++)
                            if(a != i)
                                cap += popularityTable[i][a];
                        
                        double modifier = modifierTable[i][j];

                        if(cap < modifierTable[i][j])
                            modifier = cap;
                            
                        if(popularityTable[i][j] + modifierTable[i][j] < 0)
                            modifier = popularityTable[i][j] + modifierTable[i][j]; 
                            
                        finalPopTable[i][j] += modifier;
                        
                        for(int a = 0; a < factions.size(); a++)
                            if(a != i)
                                finalPopTable[i][a] = popularityTable[i][a] - modifier * popularityTable[i][a] / cap;
                    }
                    
        for(int i = 0; i < popGroups.size(); i++)
            for(int j = 0; j < factions.size(); j++)
                total += finalPopTable[i][j];
                
        for(int i = 0; i < factions.size(); i++)
        {
            int popularity = 0;
            for(int j = 0; j < popGroups.size(); j++)
                popularity += finalPopTable[j][i];
            returnArray[i] = popularity/(double)total;
        }
                
        return returnArray;
    }
    
    public static void modifyModifierTable(int x, int y, int ammount)
    {
        modifierTable[x][y] += ammount;
    }
    
    public static void modifyPopGroupDistribution(int index, int ammount)
    {
        if(popGroupRatio[index] + ammount < 0)
            return;
            
        popGroupRatio[index] += ammount;
    }
    
    public static int getPopGroupDistribution(int index)
    {
        return popGroupRatio[index];
    }
    
    public static void addFactionValueModifier(int factionIndex, int valueIndex, int ammount)
    {
        factions.get(factionIndex).modifyValue(valueIndex, ammount);
    }
    
    public static int getFactionValue(int factionIndex, int valueIndex)
    {
        return factions.get(factionIndex).getValue(valueIndex);
    }
    
    public static void addPopGroupValueModifier(int factionIndex, int valueIndex, int ammount)
    {
        popGroups.get(factionIndex).modifyValue(valueIndex, ammount);
    }
    
    public static int getPopGroupValue(int factionIndex, int valueIndex)
    {
        return popGroups.get(factionIndex).getValue(valueIndex);
    }
}
