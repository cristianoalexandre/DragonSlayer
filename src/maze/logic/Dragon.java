package maze.logic;

public class Dragon extends Persona
{

    private boolean asleep = false;
    private boolean guarding_sword = false;
    private char guarding_sword_symbol = 'F';
    private char asleep_symbol = 'Z';

    public Dragon(int lin, int col, char symbol)
    {
        super(lin, col, symbol);
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

    public void guardSword()
    {
        guarding_sword = true;
    }

    public void leaveSword()
    {
        guarding_sword = false;
    }

    public char getSymbol()
    {
        if (guarding_sword)
        {
            return guarding_sword_symbol;
        }
        else if (asleep)
        {
            return asleep_symbol;
        }
        else
        {
            return symbol;
        }
    }
}