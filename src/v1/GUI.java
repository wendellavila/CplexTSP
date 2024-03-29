package v1;
import ilog.concert.IloIntVar;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*============================================
Traveling salesman problem solved with CPLEX
==============================================
Author: Wendell Joao Castro de Avila
RA: 2017.1.08.013
Date: 31.10.2020
==============================================*/

public class GUI extends JFrame {
    
    private int[][] xy;
    private IloIntVar[][] s;
    private double[][] currentSolution;
    
    public GUI(float[][] xy){
        this.currentSolution = new double[Data.cities][Data.cities];
        for(int i = 0; i < Data.cities; i++){
            for(int j = 0; j < Data.cities; j++){
                this.currentSolution[i][j] = 0.0;
            }
        }
        
        //float to int
        this.xy = new int[xy.length][xy[0].length];
        for(int i = 0; i < this.xy.length; ++i){
            for (int j = 0; j < this.xy[0].length; j++){
                this.xy[i][j] = (int) xy[i][j];
            }
        }
        generateScreen();
    }
    
    public GUI(float[][] xy, double[][] currentSolution){
        this.currentSolution = currentSolution;
        
        //float to int
        this.xy = new int[xy.length][xy[0].length];
        for(int i = 0; i < this.xy.length; ++i){
            for (int j = 0; j < this.xy[0].length; j++){
                this.xy[i][j] = (int) xy[i][j];
            }
        }
        generateScreen();
    }
    
    public void generateScreen(){
        drawCities();
        
        setLocation(800, 0); //initial position of window in the screen
        setLayout(null);  //no layout manager
        setSize(700,700);//frame size 1000 width x 800 height
        setVisible(true); //frame will be visible, by default not visible
        setAlwaysOnTop(true);

        paint(this.getGraphics());
        
        //close window on x icon click
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
    }
    
    public void setCurrentSolution(double[][] currentSolution){
        this.currentSolution = currentSolution;
    }
    
    public void drawCities(){
        JPanel[] panel = new JPanel[Data.cities];
        for(int i = 0; i < panel.length; i++){
            JLabel l = new JLabel(Integer.toString(i));
            l.setForeground(Color.darkGray);
            l.setFont(new Font("Calibri", Font.BOLD, 13));
            panel[i] = new JPanel();
            panel[i].setBounds(xy[i][0] * 6,xy[i][1] * 6,23,23);
            panel[i].setOpaque(false);
            panel[i].add(l);
            add(panel[i]); //adding JPanel into JFrame
        }
    }
    
    public void drawTours(Graphics2D g2){
        //drawing lines between cities
        for(int i = 0; i < Data.cities; i++) {
            for(int j = 0; j < Data.cities; j++) {
                // == 1
                if(currentSolution[i][j] > 0.9){
                    Line2D line = new Line2D.Float(
                    xy[i][0] * 6 + 15, xy[i][1] * 6 + 45,
                    xy[j][0] * 6 + 15, xy[j][1] * 6 + 45);

                    g2.setColor(Color.RED);
                    g2.draw(line);
                }
            }
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        drawTours(g2);
    }
}
