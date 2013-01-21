package maze.cli;

import java.util.Scanner;
import maze.logic.Maze;

public class Game
{    
    public static void main(String[] args)
    {        
        Scanner in = new Scanner(System.in);
        String input;

        Maze maze = new Maze();

        while (maze.winner() == 1)
        {
            maze.show();
            System.out.print("Direccao a jogar (c(ima)|b(aixo)|d(ireita)|e(squerda): ");
            input = in.nextLine();
            System.out.println("");
            if (input.equals("c") || input.equals("cima"))
            {
                    maze.moveHero('c');
            }
            else if (input.equals("b") || input.equals("baixo"))
            {
                    maze.moveHero('b');
            }
            else if (input.equals("d") || input.equals("direita"))
            {
                    maze.moveHero('d');
            }
            else if (input.equals("e") || input.equals("esquerda"))
            {
                    maze.moveHero('e');
            }
            
            maze.moveDragons();
        }
        
        maze.show();
    }
}