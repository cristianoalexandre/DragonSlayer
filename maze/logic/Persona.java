package maze.logic;

public class Persona
{
    /*
     * atributes
     */
    protected static int id_counter;

    protected int id;
    protected char symbol;
    protected int[] old_position = new int[2];
    protected int[] current_position = new int[2];
    protected boolean dead = false;

    /*
     * private methods
     */
    protected void saveLastPosition()
    {
        if (current_position[0] >= 0)
        {
            old_position[0] = current_position[0];
            old_position[1] = current_position[1];
        }
    }
    
    public Persona(int lin, int col, char symbol)
    {
        id = id_counter;
        id_counter++;
        
        current_position[0] = lin;
        current_position[1] = col;

        this.symbol = symbol;
    }
    
    public int getID()
    {
        return id;
    }
    
    public boolean isAdjacentTo(Persona persona)
    {
        return (((getLine() == persona.getLine()) && (getColumn() == persona.getColumn() + 1))
             || ((getLine() == persona.getLine()) && (getColumn() == persona.getColumn() - 1))
             || ((getColumn() == persona.getColumn()) && (getLine() == persona.getLine() + 1))
             || ((getColumn() == persona.getColumn()) && (getLine() == persona.getLine() - 1)));
    }

    public char getSymbol()
    {
        return symbol;
    }

    public int getLine()
    {
        return current_position[0];
    }

    public int getColumn()
    {
        return current_position[1];
    }

    public int getLastLine()
    {
        return old_position[0];
    }

    public int getLastColumn()
    {
        return old_position[1];
    }
    
    public void setCoord(int lin, int col)
    {
        saveLastPosition();
        current_position[0] = lin;
        current_position[1] = col;
    }

    public void die()
    {
        dead = true;
    }
    
    public boolean isDead()
    {
        return dead;
    }

    public void goLeft()
    {
        saveLastPosition();
        current_position[1]--;
    }

    public void goRight()
    {
        saveLastPosition();
        current_position[1]++;
    }

    public void goUp()
    {
        saveLastPosition();
        current_position[0]--;
    }

    public void goDown()
    {
        saveLastPosition();
        current_position[0]++;
    }

    public void print()
    {
        System.out.print(symbol);
    }

    public void returnToLastPostion()
    {
            current_position[0] = old_position [0];
            current_position[1] = old_position [1];
    }

    public void setSymbol(char new_symbol)
    {
        symbol = new_symbol;
    }
}
