import javax.swing.table.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Score extends JDialog implements ActionListener
{
    int[] highScores = new int[10];
    JLabel scoreLabel;
    JButton button;
    public String[] names=new String[10];
    String[][] tableData = {{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""},{"0", ""}};

    public Score(int score, Frame owner)
    {
        super (owner, "High Scores:", true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setBounds (300, 300, 450, 250);

        button = new JButton ("Done");

        String[] columnNames = {"Score", "Name"};
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        JTable scoreTable = new JTable(tableData, columnNames);
        scoreTable.setDefaultRenderer(Object.class, centerRenderer);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(150);

        try {
            Scanner in = new Scanner(new FileReader("HighScores.txt"));
            for (int i=0; i<10; i++)
            {
                int x = in.nextInt();
                String y = in.next();
                highScores[i] = x;
                names[i] = y;
            }
            in.close();

        }
        catch (IOException e) {
            System.out.println("Cannot open file.");
        }

        scoreLabel = new JLabel ();
        JPanel panel = new JPanel ();
        panel.add(scoreLabel);
        panel.add(scoreTable);
        panel.add(button);

        button.addActionListener (this);

        addScore(score, null);

        this.setContentPane (panel);
        this.setVisible (true);
        repaint();
    }

    public void addScore(int newScore, String name)
    {
        scoreLabel.setText("Your Score: "+newScore);
        for (int i=0; i<10; i++)
        {
            if (newScore > highScores[i])
            {
                for (int j=9; j>i; j--)
                {
                    highScores[j] = highScores[j-1];
                    names[j] = names[j-1];
                }                
                highScores[i] = newScore;
                names[i] = name;

                if (name == null)
                {
                    String inputValue = JOptionPane.showInputDialog ("Enter your name:");
                    names[i] = inputValue;
                }                
                break;
            }
        }
        try {
            PrintWriter out = new PrintWriter("HighScores.txt");
            for (int i=0; i<10; i++)
            {
                int x = highScores[i];
                out.println(x);
                String y = names[i];
                out.println(y);
            }
            out.close();
        }
        catch (IOException e) {
            System.out.println("Cannot open file.");
        }
        for (int i=0; i<10; i++)
        {            
            tableData[i][0]=Integer.toString(highScores[i]);
            tableData[i][1]=names[i];
            repaint();
        }
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource () == button)
        {
            this.dispose(); 
        }        
    }
}