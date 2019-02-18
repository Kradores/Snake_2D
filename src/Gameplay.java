import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener
{
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;


    private ImageIcon rightMouth, upMouth, downMouth, leftMouth;
    private ImageIcon snakeImage;
    private ImageIcon titleImage;

    private int lengthOfSnake = 10;
    private int moves = 0;


    private Timer timer;
    private int delay = 50;

    private Random random = new Random();
    private int enemyXPos = (random.nextInt(33)+1) * 25;
    private int enemyYPos = (random.nextInt(22)+3) * 25;

    private ImageIcon enemyImage;

    private int score = 0;
    private boolean gameOver = false;




    public Gameplay()
    {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        if(moves == 0)
        {
            snakeXLength[2] = 50;
            snakeXLength[1] = 75;
            snakeXLength[0] = 100;
            snakeYLength[2] = 100;
            snakeYLength[1] = 100;
            snakeYLength[0] = 100;
        }
        // draw title image border
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 851, 55);

        // draw title image
        titleImage = new ImageIcon("graphics/snaketitle.jpg");
        titleImage.paintIcon(this, g, 25, 11);

        //draw border for gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 577);

        // draw background for gameplay
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);

        // draw score
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Score: " + score, 780, 30);

        // draw length of snake
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Length: " + lengthOfSnake, 780, 50);

        rightMouth = new ImageIcon("graphics/rightmouth.png");
        rightMouth.paintIcon(this, g, snakeXLength[0], snakeYLength[0]);

        for(int i = 0; i<lengthOfSnake; i++)
        {
            if(i == 0 && right)
            {
                rightMouth = new ImageIcon("graphics/rightmouth.png");
                rightMouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if(i == 0 && left)
            {
                leftMouth = new ImageIcon("graphics/leftmouth.png");
                leftMouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if(i == 0 && up)
            {
                upMouth = new ImageIcon("graphics/upmouth.png");
                upMouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if(i == 0 && down)
            {
                downMouth = new ImageIcon("graphics/downmouth.png");
                downMouth.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }

            if(i != 0)
            {
                snakeImage = new ImageIcon("graphics/snakeimage.png");
                snakeImage.paintIcon(this, g, snakeXLength[i], snakeYLength[i]);
            }
        }

        enemyImage = new ImageIcon("graphics/enemy.png");
        if((enemyXPos == snakeXLength[0]) && (enemyYPos == snakeYLength[0]))
        {
            score++;
            lengthOfSnake++;
            enemyXPos = (random.nextInt(33)+1) * 25;
            enemyYPos = (random.nextInt(22)+3) * 25;
        }

        enemyImage.paintIcon(this, g, enemyXPos, enemyYPos);

        for(int position = 1; position < lengthOfSnake; position++)
        {
            if(snakeXLength[position] == snakeXLength[0] && snakeYLength[position] == snakeYLength[0])
            {
                right = false;
                left = false;
                up = false;
                down = false;
                gameOver = true;

                g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 50));
                g.drawString("GAME OVER", 300, 300);

                g.setFont(new Font("arial", Font.BOLD, 20));
                g.drawString("Press SPACE to Restart", 350, 340);
            }
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

        timer.start();

        if(right)
        {
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeYLength[position+1] = snakeYLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeXLength[position] = snakeXLength[position] + 25;
                } else
                {
                    snakeXLength[position] = snakeXLength[position-1];
                }
                if(snakeXLength[position] > 850)
                {
                    snakeXLength[position] = 25;
                }
            }
            repaint();
        }

        if(left)
        {
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeYLength[position+1] = snakeYLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeXLength[position] = snakeXLength[position] - 25;
                } else
                {
                    snakeXLength[position] = snakeXLength[position-1];
                }
                if(snakeXLength[position] < 25)
                {
                    snakeXLength[position] = 850;
                }
            }
            repaint();
        }

        if(up)
        {
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeXLength[position+1] = snakeXLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeYLength[position] = snakeYLength[position] - 25;
                } else
                {
                    snakeYLength[position] = snakeYLength[position-1];
                }
                if(snakeYLength[position] < 75)
                {
                    snakeYLength[position] = 625;
                }
            }
            repaint();
        }

        if(down)
        {
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeXLength[position+1] = snakeXLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeYLength[position] = snakeYLength[position] + 25;
                } else
                {
                    snakeYLength[position] = snakeYLength[position-1];
                }
                if(snakeYLength[position] > 625)
                {
                    snakeYLength[position] = 75;
                }
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            moves = 0;
            score = 0;
            lengthOfSnake = 3;
            gameOver = false;
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT && !gameOver)
        {
            moves++;
            right = true;
            if(left)
            {
                right = false;
                return;
            }
            up = false;
            down = false;
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeYLength[position+1] = snakeYLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeXLength[position] = snakeXLength[position] + 25;
                } else
                {
                    snakeXLength[position] = snakeXLength[position-1];
                }
                if(snakeXLength[position] > 850)
                {
                    snakeXLength[position] = 25;
                }
            }
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT && !gameOver)
        {
            moves++;
            left = true;
            if(right)
            {
                left = false;
                return;
            }
            up = false;
            down = false;
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeYLength[position+1] = snakeYLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeXLength[position] = snakeXLength[position] - 25;
                } else
                {
                    snakeXLength[position] = snakeXLength[position-1];
                }
                if(snakeXLength[position] < 25)
                {
                    snakeXLength[position] = 850;
                }
            }
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_UP && !gameOver)
        {
            moves++;
            up = true;
            if(down)
            {
                up = false;
                return;
            }
            left = false;
            right = false;
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeXLength[position+1] = snakeXLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeYLength[position] = snakeYLength[position] - 25;
                } else
                {
                    snakeYLength[position] = snakeYLength[position-1];
                }
                if(snakeYLength[position] < 75)
                {
                    snakeYLength[position] = 625;
                }
            }
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN && !gameOver)
        {
            moves++;
            down = true;
            if(up)
            {
                down = false;
                return;
            }
            left = false;
            right = false;
            for(int position = lengthOfSnake-1; position>=0; position--)
            {
                snakeXLength[position+1] = snakeXLength[position];
            }
            for(int position = lengthOfSnake; position>=0; position--)
            {
                if(position==0)
                {
                    snakeYLength[position] = snakeYLength[position] + 25;
                } else
                {
                    snakeYLength[position] = snakeYLength[position-1];
                }
                if(snakeYLength[position] > 625)
                {
                    snakeYLength[position] = 75;
                }
            }
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
