import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public abstract class Country
{
    protected String name;
    protected Image flag;
    
    public Image getImage() { return flag; }
    public abstract void gameScreen(Graphics g, Font basedFont);
    public abstract void processInput(int x, int y);
}
