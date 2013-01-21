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
    private char empty = ' ';
    private char not_visited = 'N';
    private char wall = 'X';
    private Hero hero;
    private Persona sword;
    private Dragon[] dragons = new Dragon[2];
    private Exit exit;

    public Maze()
    {
        Persona.id_counter = 0;

        n_col = 10;
        n_lin = 10;

        hero = new Hero(1, 1, 'H', 'A');
        sword = new Persona(8, 1, 'E');
        exit = new Exit(5, 9, 'S');

        dragons[0] = new Dragon(3, 1, 'D');
        dragons[1] = new Dragon(8, 4, 'D');
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
        for (int i = 0; i < dragons.length; i++)
        {
            System.out.println("dragon: [" + dragons[i].getLine() + "," + dragons[i].getColumn() + "]");
        }
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
            sword.die();
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
        for (int i = 0; i < dragons.length; i++)
        {

            if (hero.isAdjacentTo(dragons[i]))
            {
                if (hero.isArmed())
                {
                    dragons[i].die();
                    board[dragons[i].getLine()][dragons[i].getColumn()] = ' ';
                }
                else
                {
                    hero.die();
                }
            }
        }
    }

    public void moveDragons() // moves the dragon IF NOT dead
    {
        for (int i = 0; i < dragons.length; i++)
        {
            if (dragons[i].isDead() || hero.isDead())
            {
                /* do nothing */
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
                            dragons[i].goDown();
                            break;
                        case 2:
                            dragons[i].goLeft();
                            break;
                        case 3:
                            dragons[i].goRight();
                            break;
                        case 4:
                            dragons[i].goUp();
                            break;
                    }
                    System.out.println(dragon_move);

                    validPlay = true;

                    if (outOfBounds(dragons[i].getLine(), dragons[i].getColumn()) == true
                            || board[dragons[i].getLine()][dragons[i].getColumn()] == wall
                            || board[dragons[i].getLine()][dragons[i].getColumn()] == exit.getSymbol()
                            || (board[dragons[i].getLine()][dragons[i].getColumn()] == dragons[i].getSymbol() && dragon_move > 0))
                    {
                        dragons[i].returnToLastPostion();
                        validPlay = false;
                    }
                    else if (board[dragons[i].getLine()][dragons[i].getColumn()] == sword.getSymbol())
                    {
                        dragons[i].guardSword();
                    }
                    else if (dragon_move > 0 && sword.isDead() == false)
                    {
                        dragons[i].leaveSword();
                        board[sword.getLine()][sword.getColumn()] = sword.getSymbol();
                    }
                }

                board[dragons[i].getLine()][dragons[i].getColumn()] = dragons[i].getSymbol();

                if (((dragons[i].getLastLine() != dragons[i].getLine()) || (dragons[i].getLastColumn() != dragons[i].getColumn()))
                        && (dragons[i].getLastColumn() >= 0 && dragons[i].getLastLine() >= 0)
                        && (dragons[i].getLastLine() != sword.getLine() || dragons[i].getLastColumn() != sword.getColumn()))
                {
                    board[dragons[i].getLastLine()][dragons[i].getLastColumn()] = empty;
                }

                heroVsDragon();
            }
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