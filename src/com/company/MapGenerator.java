package com.company;

import java.awt.*;

public class MapGenerator {

    int[][] map ;
    int brickWidth;
    int brickHeight;

    public MapGenerator(int rows, int col){
        map = new int[rows][col];
        for (int i=0; i<map.length; i+=1){
            for (int j=0; j<map[0].length;j+=3){
                map[i][j]=1;
            }
            for (int j=1; j<map[0].length;j+=3){
                map[i][j]=2;
            }
            for (int j=2; j<map[0].length;j+=3){
                map[i][j]=3;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/rows;
    }

    void draw(Graphics2D g){
        for (int i=0; i<map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {

                if (map[i][j] == 1){
                    g.setColor(Color.magenta);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    // create borders for bricks
                    g.setStroke(new BasicStroke(5));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
                if (map[i][j] == 2){
                    g.setColor(Color.ORANGE);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    // create borders for bricks
                    g.setStroke(new BasicStroke(5));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
                if (map[i][j] == 3){
                    g.setColor(Color.CYAN);
                    g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);

                    // create borders for bricks
                    g.setStroke(new BasicStroke(5));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
                }
            }
        }
    }
    void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }

}
