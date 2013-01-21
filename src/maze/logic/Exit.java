package maze.logic;

public class Exit extends Persona
{
    private boolean open_status = false;
    
    public Exit(int lin, int col, char symbol)
    {
        super(lin, col, symbol);
    }
    
    public boolean isOpen()
    {
        return open_status;
    }
    
    public void open()
    {
        open_status = true;
    }
}