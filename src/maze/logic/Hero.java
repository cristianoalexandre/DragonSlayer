package maze.logic;

public class Hero extends Persona
{
    private char armed_symbol = 'A';
    private boolean armed = false;
    
    public Hero(int lin, int col, char symbol, char armed_symbol)
    {
        super(lin, col, symbol);
        this.armed_symbol = armed_symbol;
    }
    
    public boolean isArmed()
    {
        return armed;
    }
    
    public void collectSword()
    {
        armed = true;
        this.setSymbol('A');
    }
    
    public char getArmedSymbol()
    {
        return armed_symbol;
    }
    
    @Override
    public void print()
    {
        if (!armed)
            System.out.print(symbol);
        else
            System.out.print(armed_symbol);
    }
    
}