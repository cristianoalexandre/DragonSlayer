package maze.logic;

public class Dragon extends Persona
{
    private boolean asleep = false;
    
    public Dragon(int lin, int col, char symbol)
    {
        super(lin,col,symbol);
    }
    
    public boolean isAsleep()
    {
        return asleep;
    }
    
    public void sleep()
    {
        asleep = true;
    }
    
    public void awake()
    {
        asleep = false;
    }
    
}
