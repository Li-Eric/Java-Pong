import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Pong extends JFrame implements Runnable, KeyListener
{
    int ball_x = 10;
    int ball_y = 10;
    double ball_dx = 1;
    double ball_dy = 1;
    int paddle1_y = 160;
    int paddle2_y = 160;
    int score = 0;  
    final int WIDTH=800;
    final int HEIGHT=600;
    static boolean up_pressed=false, down_pressed = false;
    static boolean up2_pressed=false, down2_pressed = false;
    private Score highScorePanel;

    public Pong()
    {
    	JOptionPane.showMessageDialog (null, "Welcome to Pong! Player 1 moves with W and S, player 2 moves with UP and DOWN.");
        setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds (200, 200, WIDTH, HEIGHT);
        this.setVisible (true);
        ball_x = this.getWidth()/2;
        ball_y = this.getHeight()/2 ;
        Thread th = new Thread(this);
        th.start();
        this.addKeyListener(this);
        getFocus(this);
    }

    public void getFocus(final JFrame frame)
    {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    frame.requestFocus();
                }
            });
    }

    public void keyPressed(KeyEvent e)
    {
    	
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            up_pressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            down_pressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            up2_pressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            down2_pressed = true;
        }

    }

    public void keyReleased(KeyEvent e)    
    {        
        if (e.getKeyCode() == KeyEvent.VK_W)
        {
            up_pressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S)
        {
            down_pressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            up2_pressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            down2_pressed = false;
        }

    }

    public void keyTyped(KeyEvent e)
    {
    }

    public void run(){
        while (true){     
            ball_x += ball_dx;
            ball_y += ball_dy; 
            if (ball_x+ball_dx>WIDTH-10 || ball_x+ball_dx<0)
            {
                ball_dx = -ball_dx;
            }
            if (ball_y+ball_dy>HEIGHT-20 || ball_y+ball_dy<25)
            {
                ball_dy = -ball_dy;
            }

            if (down_pressed == true)
            {
                paddle1_y = paddle1_y+2;
            }
            if (up_pressed == true)
            {
                paddle1_y = paddle1_y-2;
            }

            if (down2_pressed == true)
            {
                paddle2_y = paddle2_y+2;
            }
            if (up2_pressed == true)
            {
                paddle2_y = paddle2_y-2;
            }

            repaint(); 
            try{
                Thread.sleep(5);
            }
            catch (InterruptedException e)
            {
            }

            if (ball_x == 1 || ball_x == 790)
            {  
                JOptionPane.showMessageDialog (null, "You Lost. Enter your name for the high score list! Restarting soon!");

                new Score(score, this);

                ball_x = this.getWidth()/2;
                ball_y = this.getHeight()/2 ;
                ball_dx = 1;
                ball_dy = 1;
                paddle1_y = 160;
                paddle2_y = 160;
                up_pressed = false;
                down_pressed = false;
                up2_pressed = false;
                down2_pressed = false;
                score = 0;

            }
            if (ball_dx<0 && ball_x < 85 && ball_y > paddle1_y && ball_y < paddle1_y + 110)
            {
                ball_dx = -ball_dx;
                score++;                
            }

            if (ball_dx>0 && ball_x > 715 && ball_y > paddle2_y && ball_y < paddle2_y + 110)
            {
                ball_dx = -ball_dx;
                score++;
            }
        }
    }    

    public void paint(Graphics g)
    {
        Image backbuffer = createImage(getWidth(), getHeight());
        Graphics bg = backbuffer.getGraphics();
        bg.setColor(Color.blue);
        bg.fillOval(ball_x, ball_y, 10, 10);                    

        Graphics gg = backbuffer.getGraphics();
        gg.setColor(Color.red);
        gg.fillRect(75, paddle1_y, 10, 100);     
        gg.fillRect(725, paddle2_y, 10, 100);

        Graphics fg = backbuffer.getGraphics();
        fg.setColor(new Color(0, 255, 0) );
        fg.drawString("" + score, 400, 50);

        g.drawImage(backbuffer,0,0,this);
    }

    public static void main (String[] args)
    {
        new Pong();        
    }    
}
