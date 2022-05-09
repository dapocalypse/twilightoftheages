import java.awt.*;
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import Popsim.*;

public class Orion extends Country
{
    private String[] blurbMessage;

    private int stageNumber;

    private String chosenAlignment;
    
    private static ArrayList<Integer> campaignPromises2500;
    private int prfCSeats;
    private int prfUSeats;
    private int prfRSeats;
    private int prfHSeats;
    
    /*
     * PRF-C Variables
     */
    private int prfCARFFavors;
    private int prfCRRFFavors;
    
    private int coalitionTypePRFC; // 0 for Big Tent, 1 is StateCon focus, 2 is SocCon focused
    private int prfCAccessType;
    private int prfCPointsAvaliable;

    public Orion(String name, String flagPath, String[] blurbMessage)
    {
        this.name = name;
        this.blurbMessage = blurbMessage;
        try
        {
            flag = ImageIO.read(new File(flagPath)).getScaledInstance(160, 90, Image.SCALE_SMOOTH);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        chosenAlignment = null;
        stageNumber = -1;
        
        prfCARFFavors = 0;
        prfCRRFFavors = 0;
        
        campaignPromises2500 = new ArrayList<Integer>();
        coalitionTypePRFC = -1;
        prfCAccessType = -1;
        prfCPointsAvaliable = 5;
    }

    public String[] getBlurbMessage() { return blurbMessage; }

    public void gameScreen(Graphics g, Font basedFont)
    {
        if(stageNumber >= -1 && stageNumber <= 6)
            firstElection(g, basedFont);
        else if(stageNumber == 7)
        {
            if(chosenAlignment.equals("Conservative")) prfCCleanup2500(g, basedFont);
            else
            {
                g.setFont(basedFont.deriveFont(15.0f));
                g.setColor(Color.RED);
                g.drawString("Congrats on completing this portion of Twilight of the Ages, unfortunately we do not have post-election content for factions other than the Conservative Faction. Check out the github page for future updates.", 10, g.getFontMetrics().getHeight() + 10);
            }
        }
        else if(stageNumber == 8)
        {
            g.setFont(basedFont.deriveFont(15.0f));
            g.setColor(Color.RED);
            g.drawString("And so the Revolutionary Order approaches dusk. Thank you for playing, check the github page for future updates.", 10, g.getFontMetrics().getHeight() + 10);
        }
    }

    public void processInput(int x, int y)
    {
        if(stageNumber >= -1 && stageNumber <= 6)
            firstElectionInputProcessing(x, y);
        else if(stageNumber == 7)
        {
            if(chosenAlignment.equals("Conservative")) prfCCleanupInput2500(x, y);
        }
    }
    
    /*
     * Faction Cleanup 2500
     */
    public void prfCCleanup2500(Graphics g, Font basedFont)
    {
        g.setColor(Color.RED);
        g.setFont(basedFont.deriveFont(30.0f));
        
        BufferedImage progressImage = null;
        try
        {
            progressImage = ImageIO.read(new File("eyesontheparty.png"));
        } catch(Exception e) {}
        
        if(coalitionTypePRFC == 1)
        {
            g.drawRect(100, 200, 500, 250);
            g.drawRect(650, 200, 500, 250);
            g.drawRect(350, 500, 500, 250);
            g.drawRect(100, 500, 200, 100);
            g.drawString("Points Avaliable: " + prfCPointsAvaliable, 10, 900 - g.getFontMetrics().getHeight());
            g.drawString("Buy Points", 110, 500 + g.getFontMetrics().getHeight());
            g.drawString("Our Friends in the Party", 110, 200 + g.getFontMetrics().getHeight());
            g.drawString("Our Friends Abroad", 660, 200 + g.getFontMetrics().getHeight());
            g.drawString("Our Friends in the Syndicates", 360, 500 + g.getFontMetrics().getHeight());
            g.drawImage(progressImage, 900, 500, null);
            g.setColor(Color.GREEN);
        }
        g.drawRect(10, 10, 400, 110);
        g.drawString("Stability Prevails", 20, g.getFontMetrics().getHeight() + 10);
        g.setColor(Color.RED);
        if(coalitionTypePRFC == 0)
        {
            g.drawRect(650, 200, 500, 250);
            g.drawRect(350, 500, 500, 250);
            g.drawRect(100, 500, 200, 100);
            g.drawString("Points Avaliable: " + prfCPointsAvaliable, 10, 900 - g.getFontMetrics().getHeight());
            g.drawString("Buy Points", 110, 500 + g.getFontMetrics().getHeight());
            g.drawString("Our Friends Abroad", 660, 200 + g.getFontMetrics().getHeight());
            g.drawString("Our Friends in the Syndicates", 360, 500 + g.getFontMetrics().getHeight());
            g.drawImage(progressImage, 900, 500, null);
            g.setColor(Color.GREEN);
        }
        g.drawRect(420, 10, 400, 110);
        g.drawString("One Movement", 430, g.getFontMetrics().getHeight() + 10);
        g.drawString("Many Voices", 430, g.getFontMetrics().getHeight()*2 + 10);
        g.setColor(Color.RED);
        if(coalitionTypePRFC == 2)
            g.setColor(Color.GREEN);
        g.drawRect(830, 10, 400, 110);
        g.drawString("We must preserve", 840, g.getFontMetrics().getHeight() + 10);
        g.drawString("revolutionary values", 840, g.getFontMetrics().getHeight() * 2+ 10);
        g.setColor(Color.RED);
        
        g.drawLine(0, 150, 100000, 150);
        
        g.drawString("PRF-C Popularity:" + String.format("%.2g%n", PopsimManager.getFactionPopularities()[0] * 100) + "%", 10, 900);
        
        if(prfCAccessType != -1)
        {
            String group = "";
            String info = "";
            
            g.setColor(new Color(50, 0, 0));
            g.fillRect(100, 100, 1200, 800);
            g.setColor(Color.RED);
            g.drawRect(100, 100, 1200, 800);
            g.drawRect(110, 110, 50, 50);
            
            if(prfCAccessType == 0)
            {
                group = "Our Friends in the Party";
                info = "Certain aspects of our movement is poison, we need to promote calm and stability.";
            }
            if(prfCAccessType == 1)
            {
                group = "Our Friends Abroad";
                info = "The Front is not exclusive to Orion, we must secure support with the Avantan, and Rak Fronts.";
            }
            if(prfCAccessType == 2)
            {
                group = "Our Friends in the Syndicates";
                info = "The Syndicates are the backbone of the modern Orion Miracle, their support is invaluable";
            }
            
            g.drawString(group, 200, 200 + g.getFontMetrics().getHeight());
            g.setFont(basedFont.deriveFont(20.0f));
            g.drawString(info, 110, 250 + g.getFontMetrics().getHeight());
            
            
            if(prfCAccessType == 0)
            {
                g.drawRect(200, 300, 1000, 50);
                g.fillRect(200, 300, PopsimManager.getPopGroupDistribution(0) * 1000 / (PopsimManager.getPopGroupDistribution(4) + PopsimManager.getPopGroupDistribution(0)), 50);
                g.setColor(new Color(50, 0, 0));
                g.drawString("Health of the Movement", 210, 310 + g.getFontMetrics().getHeight());
                g.setColor(Color.RED);
                g.drawRect(200, 400, 500, 50);
                g.drawRect(200, 460, 500, 50);
                g.drawRect(200, 520, 500, 50);
                g.drawString("Distance Ourselves", 210, 410 + g.getFontMetrics().getHeight());
                g.drawString("Advocate for Stability", 210, 470 + g.getFontMetrics().getHeight());
                g.drawString("Pander to Them", 210, 530 + g.getFontMetrics().getHeight());
            }
            if(prfCAccessType == 1)
            {
                g.drawRect(200, 300, 1000, 50);
                g.fillRect(200, 300, PopsimManager.getFactionValue(0, 2) * 100, 50);
                g.setColor(new Color(50, 0, 0));
                g.drawString("Our Support Abroad", 210, 310 + g.getFontMetrics().getHeight());
                g.setColor(Color.RED);
                g.drawRect(200, 400, 500, 50);
                g.drawRect(200, 460, 500, 50);
                g.drawString("Build International Support", 210, 410 + g.getFontMetrics().getHeight());
                g.drawString("Advocate for Internationalism", 210, 470 + g.getFontMetrics().getHeight());
                
                g.drawRect(200, 520, 500, 50);
                g.drawRect(725, 520, 500, 50);
                g.drawString("Buy Favors from the Avantan RF", 210, 530 + g.getFontMetrics().getHeight());
                g.drawString("Buy Favors from the Rak RF", 735, 530 + g.getFontMetrics().getHeight());
                g.drawString("ARF Favors: " + prfCARFFavors, 210, 630 + g.getFontMetrics().getHeight());
                g.drawString("RRF Favors: " + prfCRRFFavors, 735, 630 + g.getFontMetrics().getHeight());
            }
            if(prfCAccessType == 2)
            {
                g.drawRect(200, 300, 700, 50);
                int barValue = (10 - PopsimManager.getFactionValue(0, 5)) * 100;
                g.fillRect(200, 300, barValue, 50);
                g.drawString("Control of the Syndicates", 210, 310 + g.getFontMetrics().getHeight());
                g.drawRect(200, 400, 500, 50);
                g.drawRect(200, 460, 500, 50);
                if(PopsimManager.getFactionValue(0, 5) > 4)
                    g.setColor(new Color(50, 0, 0));
                g.drawRect(200, 520, 500, 50);
                g.setColor(Color.RED);
                g.drawString("Reign them In", 210, 410 + g.getFontMetrics().getHeight());
                g.drawString("Advocate for cooperation.", 210, 470 + g.getFontMetrics().getHeight());
                if(PopsimManager.getFactionValue(0, 5) > 4)
                    g.setColor(new Color(50, 0, 0));
                g.drawString("Pry off Autonomists and Radicals", 210, 530 + g.getFontMetrics().getHeight());
            }
        }
    }
    
    public void prfCCleanupInput2500(int x, int y)
    {
        if(y <= 120 && y >= 10 && coalitionTypePRFC == -1 && prfCAccessType == -1)
        {
            if(x <= 410 && x >= 10)
            {
                coalitionTypePRFC = 1;
                PopsimManager.modifyPopGroupDistribution(0, 3);
                PopsimManager.modifyPopGroupDistribution(4, -3);
                PopsimManager.addFactionValueModifier(0, 4, -2);
            }
            if(x <= 820 && x >= 420)
            {
                coalitionTypePRFC = 0;
                PopsimManager.modifyModifierTable(0, 0, -2);
                PopsimManager.modifyModifierTable(1, 0, 2);
                PopsimManager.modifyModifierTable(2, 0, 2);
                PopsimManager.modifyModifierTable(3, 0, 2);
                PopsimManager.modifyModifierTable(4, 0, -2);
            }
            if(x <= 1230 && x >= 830)
            {
                coalitionTypePRFC = 2;
                PopsimManager.modifyPopGroupDistribution(0, -3);
                PopsimManager.modifyPopGroupDistribution(4, 3);
            }
        }
        else if(prfCAccessType != -1)
        {
            if(x >= 110 && x <= 160 && y >= 110 && y <= 160) prfCAccessType = -1;
            if(prfCAccessType == 0 && coalitionTypePRFC != 0)
            {
                if(x >= 200 && x <= 700 && prfCPointsAvaliable != 0)
                {
                    if(y >= 400 && y <= 450 && (PopsimManager.getPopGroupDistribution(4) != 0 || PopsimManager.getPopGroupDistribution(4) != 1))
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.modifyPopGroupDistribution(0, 2);
                        PopsimManager.modifyPopGroupDistribution(4, -2);
                    }
                    if(y >= 460 && y <= 510)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.modifyModifierTable(0, 0, 3);
                    }
                    if(y >= 520 && y <= 570)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.modifyModifierTable(4, 0, 2);
                    }
                }
            }
            if(prfCAccessType == 1)
            {
                if(x >= 200 && x <= 700 && prfCPointsAvaliable != 0)
                {
                    if(y >= 400 && y <= 450)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.addFactionValueModifier(0, 2, 2);
                    }
                    if(y >= 460 && y <= 510)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.addPopGroupValueModifier(0, 2, 2);
                    }
                    if(y >= 520 && y <= 570)
                    {
                        prfCPointsAvaliable--;
                        prfCARFFavors++;
                    }
                }
                if(x >= 725 && x <= 1225)
                {
                    if(y >= 520 && y <= 570)
                    {
                        prfCPointsAvaliable--;
                        prfCRRFFavors++;
                    }
                }
            }
            if(prfCAccessType == 2)
            {
                if(x >= 200 && x <= 700 && prfCPointsAvaliable != 0)
                {
                    if(y >= 400 && y <= 450 && PopsimManager.getFactionValue(0, 5) != 3 && PopsimManager.getFactionValue(0, 5) != 4)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.addFactionValueModifier(0, 5, -2);
                    }
                    if(y >= 460 && y <= 510  && PopsimManager.getPopGroupValue(0, 5) != 3 && PopsimManager.getPopGroupValue(0, 5) != 4)
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.addPopGroupValueModifier(0, 5, -2);
                    }
                    if(y >= 520 && y <= 570 && !(PopsimManager.getFactionValue(0, 5) > 4))
                    {
                        prfCPointsAvaliable--;
                        PopsimManager.modifyModifierTable(1, 1, -2);
                        PopsimManager.modifyModifierTable(3, 3, -2);
                    }
                }
            }
        }
        else if (coalitionTypePRFC != -1)
        {
            if(x >= 900 && x <= 1150 && y >= 500 && y <= 750) stageNumber++;
            if(x >= 100 && x <= 300 && y >= 500 && y <= 600)
            {
                prfCPointsAvaliable += 3;
                PopsimManager.modifyModifierTable(0, 0, -1);
                PopsimManager.modifyModifierTable(1, 0, -1);
                PopsimManager.modifyModifierTable(2, 0, -1);
                PopsimManager.modifyModifierTable(3, 0, -1);
                PopsimManager.modifyModifierTable(4, 0, -1);
                PopsimManager.modifyPopGroupDistribution(4, 1);
                if(PopsimManager.getPopGroupDistribution(0) != 0)
                    PopsimManager.modifyPopGroupDistribution(0, -1);
                PopsimManager.addFactionValueModifier(0, 2, -1);
                PopsimManager.addPopGroupValueModifier(0, 2, -1);
                PopsimManager.addFactionValueModifier(0, 5, 1);
                PopsimManager.addPopGroupValueModifier(0, 5, 1);
            }
            if(x >= 100 && x <= 600 && y >= 200 && y <= 450 && coalitionTypePRFC != 0)
                prfCAccessType = 0;
            if(x >= 650 && x <= 1150 && y >= 200 && y <= 450)
                prfCAccessType = 1;
            if(x >= 350 && x <= 850 && y >= 500 && y <= 750)
                prfCAccessType = 2;
        }
    }

    /*
     * Below is the handling of the 2500 election.
     */

    public void firstElection(Graphics g, Font basedFont)
    {
        switch(stageNumber)
        {
            case -1:
            chooseAlignment(g, basedFont);
            break;
            case 0:
            alignmentSelected(g, basedFont);
            break;
        }

        if(stageNumber >= 1 && stageNumber <= 5)
        {
            g.setColor(Color.RED);
            g.setFont(basedFont.deriveFont(20.0f));

            g.drawRect(100, 100, 1200, 300);
            g.drawRect(100, 410, 1200, 50);
            g.drawRect(100, 470, 1200, 50);
            g.drawRect(100, 530, 1200, 50);
            g.drawRect(100, 590, 1200, 50);

            Question2500 question = new Question2500();
            question.draw(g, basedFont);
        }
        
        if(stageNumber == 6)
        {
            g.setColor(Color.RED);
            g.setFont(basedFont.deriveFont(20.0f));
            
            int prfCSeats = 0;
            int prfUSeats = 0;
            int prfRSeats = 0;
            int prfHSeats = 0;
            
            if(PopsimManager.getFactionPopularities()[0] == 1.0)
                prfCSeats = 5;
            else if(PopsimManager.getFactionPopularities()[0] >= 0.8)
                prfCSeats = 4;
            else if(PopsimManager.getFactionPopularities()[0] >= 0.6)
                prfCSeats = 3;
            else if(PopsimManager.getFactionPopularities()[0] >= 0.4)
                prfCSeats = 2;
            else if(PopsimManager.getFactionPopularities()[0] >= 0.2)
                prfCSeats = 1;
                
            if(PopsimManager.getFactionPopularities()[1] == 1.0)
                prfUSeats = 5;
            else if(PopsimManager.getFactionPopularities()[1] >= 0.8)
                prfUSeats = 4;
            else if(PopsimManager.getFactionPopularities()[1] >= 0.6)
                prfUSeats = 3;
            else if(PopsimManager.getFactionPopularities()[1] >= 0.4)
                prfUSeats = 2;
            else if(PopsimManager.getFactionPopularities()[1] >= 0.2)
                prfUSeats = 1;
                
            if(PopsimManager.getFactionPopularities()[2] == 1.0)
                prfRSeats = 5;
            else if(PopsimManager.getFactionPopularities()[2] >= 0.8)
                prfRSeats = 4;
            else if(PopsimManager.getFactionPopularities()[2] >= 0.6)
                prfRSeats = 3;
            else if(PopsimManager.getFactionPopularities()[2] >= 0.4)
                prfRSeats = 2;
            else if(PopsimManager.getFactionPopularities()[2] >= 0.2)
                prfRSeats = 1;
                
            if(PopsimManager.getFactionPopularities()[3] == 1.0)
                prfHSeats = 5;
            else if(PopsimManager.getFactionPopularities()[3] >= 0.8)
                prfHSeats = 4;
            else if(PopsimManager.getFactionPopularities()[3] >= 0.6)
                prfHSeats = 3;
            else if(PopsimManager.getFactionPopularities()[3] >= 0.4)
                prfHSeats = 2;
            else if(PopsimManager.getFactionPopularities()[3] >= 0.2)
                prfHSeats = 1;
                
            if(prfCSeats + prfUSeats + prfRSeats + prfHSeats == 1)
            {
                prfCSeats++;
                prfUSeats++;
                prfRSeats++;
                prfHSeats++;
            }
            
            if(prfCSeats + prfUSeats + prfRSeats + prfHSeats == 2)
            {
                prfCSeats++;
                prfUSeats++;
                prfRSeats++;
                prfHSeats++;
                if(PopsimManager.getFactionPopularities()[0] < PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[0] < PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[0] < PopsimManager.getFactionPopularities()[3]) 
                   prfCSeats--;
                else if(PopsimManager.getFactionPopularities()[1] < PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[1] < PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[1] < PopsimManager.getFactionPopularities()[3]) 
                   prfUSeats--;
                else if(PopsimManager.getFactionPopularities()[2] < PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[2] < PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[2] < PopsimManager.getFactionPopularities()[3]) 
                   prfRSeats--;
                else prfHSeats--;
            }
            
            if(prfCSeats + prfUSeats + prfRSeats + prfHSeats == 3)
            {
                int toExclude = -1;
                if(PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[3])
                {
                   prfCSeats++;
                   toExclude = 0;
                }
                else if(PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[3]) 
                {
                   prfUSeats++;
                   toExclude = 1;
                }
                else if(PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[3]) 
                {
                   prfRSeats++;
                   toExclude = 2;
                }
                else
                {
                   prfHSeats++;
                   toExclude = 3;
                }
                
                if((PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[1] || toExclude == 1) &&
                   (PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[2] || toExclude == 2) &&
                   (PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[3] || toExclude == 3) &&
                    toExclude != 0) 
                   prfCSeats++;
                else if((PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[0] || toExclude == 0) &&
                   (PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[2] || toExclude == 2) &&
                   (PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[3] || toExclude == 3) &&
                    toExclude != 1) 
                   prfUSeats++;
                else if((PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[0] || toExclude == 0) &&
                   (PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[2] || toExclude == 1) &&
                   (PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[3] || toExclude == 3) &&
                    toExclude != 2) 
                   prfRSeats++;
                else prfHSeats++;
            }
            
            if(prfCSeats + prfUSeats + prfRSeats + prfHSeats == 4)
            {
                if(PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[0] > PopsimManager.getFactionPopularities()[3])
                   prfCSeats++;
                else if(PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[2] &&
                   PopsimManager.getFactionPopularities()[1] > PopsimManager.getFactionPopularities()[3]) 
                   prfUSeats++;
                else if(PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[1] &&
                   PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[0] &&
                   PopsimManager.getFactionPopularities()[2] > PopsimManager.getFactionPopularities()[3]) 
                   prfRSeats++;
                else
                   prfHSeats++;
            }
            
            String[] electionResults = {"Final Election Results:",
                    "PRF-C Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[0] * 100) + "%, winning " + prfCSeats + " seats.",
                    "PRF-U Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[1] * 100) + "%, winning " + prfUSeats + " seats.",
                    "PRF-R Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[2] * 100) + "%, winning " + prfRSeats + " seats.",
                    "PRF-H Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[3] * 100) + "%, winning " + prfHSeats + " seats."};
            
            for(int i = 0; i < electionResults.length; i++)
            {
                g.drawString(electionResults[i], 100, 410 + g.getFontMetrics().getHeight() * (1 + i));
            }
            
            g.drawRect(10, 200, 600, 50);
            
            g.drawString("Congrats voters, you have made Orion proud.", 20, g.getFontMetrics().getHeight() + 200);
        }
    }

    public void firstElectionInputProcessing(int x, int y)
    {
        /*
         * Don't read if you want fair gameplay, for rough draft of the final project it isn't that
         * revealing so look away.
         */

        if(stageNumber == -1)
            if(chosenAlignment == null && y >= 100 && y <= 400)
            {
                if(x >= 100 && x <= 320) chosenAlignment = "Conservative";
                if(x >= 330 && x <= 330+220) chosenAlignment = "Ultrafederalist";
                if(x >= 560 && x <= 560+220) chosenAlignment = "Reformist";
                if(x >= 790 && x <= 790+220) chosenAlignment = "Hardliner";
                if(chosenAlignment != null) stageNumber++;
            }
        if(stageNumber == 0)
            if(y >= 800 && y <= 900 && x >= 200 && x <= 500)
            {
                stageNumber++;
            }
        if(stageNumber >= 1 && stageNumber <= 5)
        {
            Question2500 question = new Question2500();
            question.processInput(x, y);
        }
        if(stageNumber == 6)
            if(y >= 200 && y <= 260 && x >= 10 && x <= 610)
            {
                stageNumber++;
            }
    }

    public void chooseAlignment(Graphics g, Font basedFont)
    {
        if(chosenAlignment == null)
        {
            g.setColor(Color.RED);
            g.drawRect(100, 100, 220, 300);
            g.drawRect(330, 100, 220, 300);
            g.drawRect(560, 100, 220, 300);
            g.drawRect(790, 100, 220, 300);

            g.setFont(basedFont.deriveFont(68.0f));
            g.drawString("PRF-C", 110, 155);
            g.drawString("PRF-U", 335, 155);
            g.drawString("PRF-R", 570, 155);
            g.drawString("PRF-H", 800, 155);
            g.drawString("Choose an Alignment:", 100, 90);

            Image dordevic = null;
            Image vega = null;
            Image jackie = null;
            Image jean = null;
            try
            {
                dordevic = ImageIO.read(new File("dordevic.jpg")).getScaledInstance(218, 248, Image.SCALE_SMOOTH);
                vega = ImageIO.read(new File("vega.jpg")).getScaledInstance(218, 248, Image.SCALE_SMOOTH);
                jackie = ImageIO.read(new File("jackie.jpg")).getScaledInstance(218, 248, Image.SCALE_SMOOTH);
                jean = ImageIO.read(new File("jean.jpg")).getScaledInstance(218, 248, Image.SCALE_SMOOTH);
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            g.drawImage(dordevic, 101, 151, null);
            g.drawImage(jean, 331, 151, null);
            g.drawImage(jackie, 561, 151, null);
            g.drawImage(vega, 791, 151, null);

            g.drawRect(100, 410, 1100, 300);

            String[] prfElectionMessage = {"The First Party Committee is one of the most well respected institutions within the Common",
                    "\nand United League of Orion. Composed of five people elected by all of the party, is",
                    "\nmembership can be one of the most hotly debated questions throughout the Orion belt and",
                    "\nabroad, as it is the senior most body within the People's Revolutionary Front.",
                    "\nThe Committee itself is chosen by a general vote, with the 5 topmost gross vote earners",
                    "\nreceiving a spot on this prestigious committee. For this election, there are four 'blocs'",
                    "\nwhich are considered to be in the running, the Conservatives, Ultrafederalists, Reformists,",
                    "\nand Hardliners.",
                    "\nPRF-C Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[0] * 100) + "%",
                    "\nPRF-U Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[1] * 100) + "%",
                    "\nPRF-R Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[2] * 100) + "%",
                    "\nPRF-H Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[3] * 100) + "%"};

            g.setFont(basedFont.deriveFont(20.0f));

            for(int i = 0; i < prfElectionMessage.length; i++)
            {
                g.drawString(prfElectionMessage[i], 100, 410 + g.getFontMetrics().getHeight() * (1 + i));
            }
        }
    }

    private void alignmentSelected(Graphics g, Font basedFont)
    {
        g.setColor(Color.RED);
        g.drawRect(100, 100, 1000, 600);

        String[] alignmentBlurbString = {};
        switch(chosenAlignment)
        {
            case "Conservative":
            String[] conBlurbString = {"The Conservative faction within the People's Revolutionary Front is, rather",
                    "counter-intuitively, new. It is the construction born from the viscious war of the party",
                    "with itself, and of the dense pressure for calm and stable government during the war.",
                    "The movement broadly concerns itself with the preservation of the current political order,",
                    "Syndicate Capitalism, and society. The Revolution is a cultural statement, one which should",
                    "be preserved.",
                    "There are two main groups within the Conservative faction, although just as many Social",
                    "and State Conservatives identify with other factions, so too do many liberals and radicals",
                    "identify with the Conservative Faction. State Conservatives believe in the preservation of",
                    "stability and prosperity, while Social Conservatives believe in the rigid protection of",
                    "societal structure.",
                    "Throughout the campaign season, the post-war identity of the Conservative Faction is expected",
                    "to be resolved."};
            alignmentBlurbString = conBlurbString;
            break;
            case "Ultrafederalist":
            String[] ultraFedBlurbString = {"The Ultrafederalist faction within the PRF is one that prioritizes states rights",
                    "and market freedom. While they vary from state to state in regards to individual",
                    "policy regarding specific issues, they are largely united in their question for",
                    "state autonomy, free markets free of Syndicate monopolies, and the pursuit of personal",
                    "liberty. It is important to note that on the fringes of this faction lie the Communalists",
                    "who take inspiration from the Utopian Socialists of the old world. Believing in the",
                    "pursuit of peace, communal autonomy, and common ownership.",
                    "Throughout the campaign, questions as to the extent of the rollback of federal interference,",
                    "the ultimate fate of the syndicates, and whether or not the more undemocratic nature of",
                    "Orion's society should be left to the states or wholesale abolished will be addressed."};
            alignmentBlurbString = ultraFedBlurbString;
            break;
            case "Reformist":
            String[] reformistBlurbString = {"The Reformist Faction within the People's Revolutionary Front believe firmly in the",
                    "liberalization of the social, economic, and even political spheres. Largely made",
                    "up of liberals, even though some state conservatives are marginally aligned with",
                    "the faction, the Reformist faction believes themselves to be steering the State",
                    "closer in line to the vision of the revolution, one that neccessitates democracy",
                    "equity, and justice. Some seek to bring these reforms to sweeping heights, seeing",
                    "many of the very institutions of modern Orion as Counter-Revolution. Others seek",
                    "moderate reforms.",
                    "Throughout the campaign the reformist faction will have to face questions as to the",
                    "extent of reforms, whether or not the Revolution will continue to have a material",
                    "place within Orion's state, and the direction in which to move away from Syndicate",
                    "capitalism."};
            alignmentBlurbString = reformistBlurbString;
            break;
            case "Hardliner":
            String[] hardlinerBlurbString = {"The Hardline faction of the front is said by its detractors to be a bickering pack",
                    "of hyenas. To its supporters, and those who recognized its strengths, the hardline",
                    "faction is a set of blunt instruments and precise tools. A diverse group of moderates",
                    "who believe in the dogmatic interpretation of Revolutionary creed, and the deliberate",
                    "proliferation of Revolutionary ideology abroad, and radicals who believe that the State",
                    "of Orion is not doing enough to prevent dissent, and ensure the Unity of the Revolution.",
                    "There are two fringe groups, among them anarchists who believe that the concept of a",
                    "state at all is one which is deeply centralist in nature, and National Revolutionaries",
                    "who believe that Orion itself IS the revolution, these groups are all bound by the idea",
                    "that the founders in the Greatest Revolution of 2500 simply did not go far enough to",
                    "enact the true ideals of the Revolution."};
            alignmentBlurbString = hardlinerBlurbString;
            break;
        }

        g.setFont(basedFont.deriveFont(18.0f));

        for(int i = 0; i < alignmentBlurbString.length; i++)
        {
            g.drawString(alignmentBlurbString[i], 110, 110 + g.getFontMetrics().getHeight() * (1 + i));
        }

        g.drawRect(200, 800, 300, 100);
    }

    private class Question2500
    {
        private String[] questionText;
        private String[] answerOne;
        private String[] answerTwo;
        private String[] answerThree;
        private String[] answerFour;

        private int[] oneModifiers;
        private double oneAltChance;
        private int[] oneAltModifier;

        private int[] twoModifiers;
        private double twoAltChance;
        private int[] twoAltModifier;

        private int[] threeModifiers;
        private double threeAltChance;
        private int[] threeAltModifier;

        private int[] fourModifiers;
        private double fourAltChance;
        private int[] fourAltModifier;

        private int factionToAffect;

        // Load from file
        public Question2500()
        {
            try
            {
                int loadingPhase = 0;

                ArrayList<String> questionText = new ArrayList<String>();
                ArrayList<String> answerOne = new ArrayList<String>();
                ArrayList<String> answerTwo = new ArrayList<String>();
                ArrayList<String> answerThree = new ArrayList<String>();
                ArrayList<String> answerFour = new ArrayList<String>();

                File questionFile = new File("2500Election\\" + chosenAlignment + "\\" + stageNumber + ".txt");
                Scanner scan = new Scanner(questionFile);

                while(scan.hasNextLine())
                {
                    String data = scan.nextLine();
                    
                    if(loadingPhase == 16)
                    {
                        this.fourAltModifier = new int[data.split(",").length];
                        for(int i = 0; i < fourAltModifier.length; i++)
                            fourAltModifier[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 15)
                    {
                        this.fourAltChance = Double.parseDouble(data);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 14)
                    {
                        this.fourModifiers = new int[data.split(",").length];
                        for(int i = 0; i < fourModifiers.length; i++)
                            fourModifiers[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 13)
                    {
                        this.threeAltModifier = new int[data.split(",").length];
                        for(int i = 0; i <  threeAltModifier.length; i++)
                             threeAltModifier[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 12)
                    {
                        this.threeAltChance = Double.parseDouble(data);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 11)
                    {
                        this.threeModifiers = new int[data.split(",").length];
                        for(int i = 0; i <  threeModifiers.length; i++)
                             threeModifiers[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 10)
                    {
                        this.twoAltModifier = new int[data.split(",").length];
                        for(int i = 0; i < twoAltModifier.length; i++)
                            twoAltModifier[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 9)
                    {
                        this.twoAltChance = Double.parseDouble(data);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 8)
                    {
                        this.twoModifiers = new int[data.split(",").length];
                        for(int i = 0; i < twoModifiers.length; i++)
                            twoModifiers[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 7)
                    {
                        this.oneAltModifier = new int[data.split(",").length];
                        for(int i = 0; i < oneAltModifier.length; i++)
                            oneAltModifier[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 6)
                    {
                        this.oneAltChance = Double.parseDouble(data);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 5)
                    {
                        this.oneModifiers = new int[data.split(",").length];
                        for(int i = 0; i < oneModifiers.length; i++)
                            oneModifiers[i] = Integer.parseInt(data.split(",")[i]);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 4 && !data.equals("~")) 
                        answerFour.add(data);
                    else if(loadingPhase == 4)
                    {
                        this.answerFour = new String[answerFour.size()];
                        for(int i = 0; i < answerFour.size(); i++)
                            this.answerFour[i] = answerFour.get(i);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 3 && !data.equals("~")) 
                        answerThree.add(data);
                    else if(loadingPhase == 3)
                    {
                        this.answerThree = new String[answerThree.size()];
                        for(int i = 0; i < answerThree.size(); i++)
                            this.answerThree[i] = answerThree.get(i);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 2 && !data.equals("~")) 
                        answerTwo.add(data);
                    else if(loadingPhase == 2)
                    {
                        this.answerTwo = new String[answerTwo.size()];
                        for(int i = 0; i < answerTwo.size(); i++)
                            this.answerTwo[i] = answerTwo.get(i);
                        loadingPhase++;
                    }
                    
                    if(loadingPhase == 1 && !data.equals("~")) 
                        answerOne.add(data);
                    else if(loadingPhase == 1)
                    {
                        this.answerOne = new String[answerOne.size()];
                        for(int i = 0; i < answerOne.size(); i++)
                            this.answerOne[i] = answerOne.get(i);
                        loadingPhase++;
                    }

                    if(loadingPhase == 0 && !data.equals("~")) 
                        questionText.add(data);
                    else if(loadingPhase == 0)
                    {
                        this.questionText = new String[questionText.size()];
                        for(int i = 0; i < questionText.size(); i++)
                            this.questionText[i] = questionText.get(i);
                        loadingPhase++;
                    }
                }
                
                switch(chosenAlignment)
                {
                    case "Conservative":
                        this.factionToAffect = 0;
                        break;
                        
                    case "Ultrafederalist":
                        this.factionToAffect = 1;
                        break;
                        
                    case "Reformist":
                        this.factionToAffect = 2;
                        break;
                        
                    case "Hardliner":
                        this.factionToAffect = 3;
                        break;
                }
                
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        public Question2500(String[] questionText, String[] answerOne, String[] answerTwo, String[] answerThree, String[] answerFour,
        int[] oneModifiers, double oneAltChance, int[] oneAltModifier, int[] twoModifiers, double twoAltChance, int[] twoAltModifier,
        int[] threeModifiers, double threeAltChance, int[] threeAltModifier, int[] fourModifiers, double fourAltChance, int[] fourAltModifier,
        int factionToAffect)
        {
            this.questionText = questionText;
            this.answerOne = answerOne;
            this.answerTwo = answerTwo;
            this.answerThree = answerThree;
            this.answerFour = answerFour;
            this.oneModifiers = oneModifiers;
            this.oneAltChance = oneAltChance;
            this.oneAltModifier = oneAltModifier;
            this.twoModifiers = twoModifiers;
            this.twoAltChance = twoAltChance;
            this.twoAltModifier = twoAltModifier;
            this.threeModifiers = threeModifiers;
            this.threeAltChance = threeAltChance;
            this.threeAltModifier = threeAltModifier;
            this.fourModifiers = fourModifiers;
            this.fourAltChance = fourAltChance;
            this.fourAltModifier = fourAltModifier;
            this.factionToAffect = factionToAffect;
        }

        public void draw(Graphics g, Font basedFont)
        {
            for(int i = 0; i < questionText.length; i++)
            {
                g.drawString(questionText[i], 110, 110 + g.getFontMetrics().getHeight() * (1 + i));
            }
            g.drawString("PRF-C Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[0] * 100) + "%", 110, 110 + g.getFontMetrics().getHeight() * (questionText.length + 1));
            g.drawString("PRF-U Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[1] * 100) + "%", 110, 110 + g.getFontMetrics().getHeight() * (questionText.length + 2));
            g.drawString("PRF-R Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[2] * 100) + "%", 110, 110 + g.getFontMetrics().getHeight() * (questionText.length + 3));
            g.drawString("PRF-H Popularity: " + String.format("%.2g%n", PopsimManager.getFactionPopularities()[3] * 100) + "%", 110, 110 + g.getFontMetrics().getHeight() * (questionText.length + 4));

            for(int i = 0; i < answerOne.length; i++)
            {
                g.drawString(answerOne[i], 110, 420 + g.getFontMetrics().getHeight() * (i) + 10);
            }

            for(int i = 0; i < answerTwo.length; i++)
            {
                g.drawString(answerTwo[i], 110, 480 + g.getFontMetrics().getHeight() * (i) + 10);
            }

            for(int i = 0; i < answerThree.length; i++)
            {
                g.drawString(answerThree[i], 110, 540 + g.getFontMetrics().getHeight() * (i) + 10);
            }

            for(int i = 0; i < answerFour.length; i++)
            {
                g.drawString(answerFour[i], 110, 600 + g.getFontMetrics().getHeight() * (i) + 10);
            }
        }

        public void processInput(int x, int y)
        {
            if(!(x >= 100 && x <= 1300))
                return;

            if(y >= 410 && y <= 460)
            {
                if(Math.random() < oneAltChance)
                    for(int i = 0; i < oneModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, oneAltModifier[i]);
                    }
                else
                    for(int i = 0; i < oneModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, oneModifiers[i]);
                    }
                campaignPromises2500.add(1);
                stageNumber++;
            }

            if(y >= 470 && y <= 520)
            {
                if(Math.random() < twoAltChance)
                    for(int i = 0; i < twoModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, twoAltModifier[i]);
                    }
                else
                    for(int i = 0; i < twoModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, twoModifiers[i]);
                    }
                campaignPromises2500.add(2);
                stageNumber++;
            }

            if(y >= 530 && y <= 580)
            {
                if(Math.random() < twoAltChance)
                    for(int i = 0; i < threeModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, threeAltModifier[i]);
                    }
                else
                    for(int i = 0; i < threeModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, threeModifiers[i]);
                    }
                campaignPromises2500.add(3);
                stageNumber++;
            }

            if(y >= 590 && y <= 640)
            {
                if(Math.random() < twoAltChance)
                    for(int i = 0; i < threeModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, threeAltModifier[i]);
                    }
                else
                    for(int i = 0; i < threeModifiers.length; i++)
                    {
                        PopsimManager.modifyModifierTable(i, factionToAffect, threeModifiers[i]);
                    }
                campaignPromises2500.add(4);
                stageNumber++;
            }
        }
    }
}
