package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 10;
    private int totalBricks = 20;

    private Timer timer;
    private int delay = 5;

    private int playerX;

    private int ballPosX = 320;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    private MapGenerator map;

    public GamePlay (){
        map = new MapGenerator(5,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        timer = new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g){
        // background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1,1,700,600);

        //drawing map
        map.draw((Graphics2D)g);

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD, 25));
        g.drawString("Score: " + score, 570, 30);

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX,ballPosY,20,20);

        //you WON
        if (totalBricks <=0){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("YOU WON !!! Score: "+score, 90, 300);

            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press ENTER to Restart ", 230, 350);
        }

        //game over
        if (ballPosY > 570){
            play = false;
            ballXDir = 0;
            ballYDir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("Game Over, Scores: "+score, 90, 300);

            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("Press ENTER to Restart ", 230, 350);
        }

        if (score==50){
            g.setColor(Color.magenta);
            g.setFont(new Font("serif",Font.BOLD, 20));
            g.drawString("GOOD JOB PLAYER!!!", 10, 20);
        }

        g.dispose();
    }

    public void moveRight(){
        play = true;
        playerX = playerX+20;
    }
    public void moveLeft(){
        play = true;
        playerX = playerX-20;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play){
            // intersection with pedal
            if (new Rectangle(ballPosX,ballPosY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYDir = - ballYDir;
            }
           A: for (int i =0; i<map.map.length; i++){
                for (int j=0; j<map.map[0].length; j++){
                    if (map.map[i][j]>0){
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWith = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY,brickWith, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20,20);
                        Rectangle brickRect =  rect;

                        if (ballRect.intersects(brickRect)){

                            if (map.map[i][j]==3){
                                map.setBrickValue(2,i,j);
                                score+=2;
                            }
                            if (map.map[i][j]==2){
                                map.setBrickValue(1,i,j);
                                score+=2;
                            }
                            else{
                                map.setBrickValue(0,i,j);
                                totalBricks --;
                                score+=10;
                            }

                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width){
                                ballXDir = -ballXDir;
                            }
                            else {
                                ballYDir = -ballYDir;
                            }
                            break A;
                        }
                    }
                }
            }

            //ball movement
            ballPosX += ballXDir;
            ballPosY += ballYDir;
            if (ballPosX < 0){
                ballXDir = -ballXDir;
            }
            if (ballPosY < 0){
                ballYDir = -ballYDir;
            }
            if (ballPosX > 670){
                ballXDir = -ballXDir;
            }

        }
        repaint();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()== KeyEvent.VK_RIGHT){
            if (playerX>=600){
                playerX=600;
            }
            else {
                moveRight();
            }
        }
        if (e.getKeyCode()== KeyEvent.VK_LEFT){
            if (playerX <= 0){
                playerX = 0;
            }
            else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play =true;
                ballPosY = 350;
                ballPosX = 120;
                ballXDir = -1;
                ballYDir = -2;
                playerX = 310;
                score =0;
                totalBricks = 50;
                map = new MapGenerator(5,10);
                repaint();
            }
        }
    }


    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
