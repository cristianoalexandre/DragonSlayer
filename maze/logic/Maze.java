package maze.logic;

import java.util.Random;
import java.util.Stack;

public class Maze
{

    private char[][] board =
    {
        {
            'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'
        },
        {
            'X', 'H', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X'
        },
        {
            'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'
        },
        {
            'X', 'D', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'
        },
        {
            'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'
        },
        {
            'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S'
        },
        {
            'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'
        },
        {
            'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X'
        },
        {
            'X', 'E', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X'
        },
        {
            'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X'
        }
    };
    private int n_col;
    private int n_lin;
    private int n_dragon;
    private char empty = ' ';
    private char not_visited = 'N';
    private char wall = 'X';
    private Hero hero;
    private Persona sword;
    private Dragon dragon;
    private Exit exit;

    public Maze()
    {
        Persona.id_counter = 0;

        n_col = 10;
        n_lin = 10;

        hero = new Hero(1, 1, 'H', 'A');
        sword = new Persona(8, 1, 'E');
        exit = new Exit(5, 9, 'S');

        n_dragon = 2;

        dragon = new Dragon(3, 1, 'D');
    }

    private boolean outOfBounds(int lin, int col)
    {
        return (lin >= n_lin || col >= n_col || col < 0 || lin < 0);
    }

    public void show()
    {
        for (int i = 0; i < n_lin; i++)
        {
            for (int k = 0; k < n_col; k++)
            {
                System.out.print("[" + board[i][k] + "]");
            }

            System.out.println("");
        }

        System.out.println("hero: [" + hero.getLine() + "," + hero.getColumn() + "]");
        System.out.println("dragon: [" + dragon.getLine() + "," + dragon.getColumn() + "]");
        System.out.println("sword: [" + sword.getLine() + "," + sword.getColumn() + "]");
        System.out.println("exit: [" + exit.getLine() + "," + exit.getColumn() + "]");

    }

    public void moveHero(char direction) // moves the hero AND checks if the sword was collected -> showing the exit! AND checks eating time...
    {

        if (direction == 'd')
        {
            hero.goRight();
        }
        else if (direction == 'c')
        {
            hero.goUp();
        }
        else if (direction == 'b')
        {
            hero.goDown();
        }
        else if (direction == 'e')
        {
            hero.goLeft();
        }

        if (outOfBounds(hero.getLine(), hero.getColumn()) == true
                || board[hero.getLine()][hero.getColumn()] == wall
                || (board[hero.getLine()][hero.getColumn()] == exit.getSymbol() && exit.isOpen() == false))
        {
            hero.returnToLastPostion();
        }
        else if (board[hero.getLine()][hero.getColumn()] == sword.getSymbol())
        {
            hero.collectSword();
            exit.open();
        }
        else if (board[hero.getLine()][hero.getColumn()] == exit.getSymbol())
        {
            exit.die();
        }

        board[hero.getLine()][hero.getColumn()] = hero.getSymbol();

        if ((hero.getLine() != hero.getLastLine()) || (hero.getColumn() != hero.getLastColumn()))
        {
            board[hero.getLastLine()][hero.getLastColumn()] = empty;
        }

        heroVsDragon();

    }

    private void heroVsDragon()
    {
        if (hero.isAdjacentTo(dragon))
        {
            if (hero.isArmed())
            {
                dragon.die();
                board[dragon.getLine()][dragon.getColumn()] = ' ';
            }
            else
            {
                hero.die();
            }
        }
    }

    public void moveDragons() // moves the dragon IF NOT dead
    {
        if (dragon.isDead() || hero.isDead())
        {
            return;
        }
        else
        {
            Random randomGenerator = new Random();

            int dragon_move;
            boolean validPlay = false;

            while (validPlay == false)
            {
                dragon_move = (randomGenerator.nextInt(5));

                switch (dragon_move)
                {
                    case 0:
                        break;
                    case 1:
                        dragon.goDown();
                        break;
                    case 2:
                        dragon.goLeft();
                        break;
                    case 3:
                        dragon.goRight();
                        break;
                    case 4:
                        dragon.goUp();
                        break;
                }

                validPlay = true;

                if (outOfBounds(dragon.getLine(), dragon.getColumn()) == true
                        || board[dragon.getLine()][dragon.getColumn()] == wall
                        || board[dragon.getLine()][dragon.getColumn()] == sword.getSymbol()
                        || board[dragon.getLine()][dragon.getColumn()] == exit.getSymbol())
                {
                    dragon.returnToLastPostion();
                    validPlay = false;
                }
            }

            board[dragon.getLine()][dragon.getColumn()] = dragon.getSymbol();

            if (((dragon.getLastLine() != dragon.getLine()) || (dragon.getLastColumn() != dragon.getColumn())) && (dragon.getLastColumn() > 0 && dragon.getLastLine() > 0))
            {
                board[dragon.getLastLine()][dragon.getLastColumn()] = empty;
            }

            heroVsDragon();
        }
    }

    public int winner()
    {
        if (exit.isDead()) // winner
        {
            return 0;
        }
        else if (hero.isDead()) // loser
        {
            return -1;
        }
        else // continue the game
        {
            return 1;


        }
    }

    public class MazeGenerator
    {

        Stack<Integer[]> cell_locations;
        int TotalCells = n_lin * n_col;
        int[] current_cell = new int[2];

        public MazeGenerator(int lin, int col)
        {
        }
    }
}