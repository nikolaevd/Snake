
package snake;

import java.util.ArrayList;

/*
     0 - пустая ячейка
    -1 - ячейку занимает тело змеи
    -2 - ячейку занимает фрукт
*/

public class Game {
    
    private static final int sizeByX = 18;
    private static final int sizeByY = 30;
    
    private static final int DIR_NORTH = 1;
    private static final int DIR_EAST = 2;
    private static final int DIR_SOUTH = 3;
    private static final int DIR_WEST = 4;
    
    private int score = 0;
    
    private int[][] field = new int[sizeByX][sizeByY];
    
    private ArrayList<Position> snake = new ArrayList<Position>();
    
    private boolean bGrwoing = false;
    
    private int curDirection = Game.DIR_EAST;
    
    Game(){
        for(int x = 0; x < sizeByX; x++){
            for(int y = 0; y < sizeByY; y++){
                field[x][y] = 0;
            }
        }
        
        snake.add(new Position(2, 2));        
        field[2][2] = -1;
        snake.add(new Position(2, 3));
        field[2][3] = -1;
        snake.add(new Position(2, 4));
        field[2][4] = -1;
        
        AddFruit();
    }
    
    private void AddFruit(){
        
        boolean bFlag = true;
        
        while(bFlag){
            int x = (int)(Math.random() * sizeByX);
            int y = (int)(Math.random() * sizeByY);
            if(field[x][y] == 0){
                bFlag = false;
                field[x][y] = -2;
            }
        }
        
    }
    
    private boolean nextMove(){
        
        switch(curDirection){
            case DIR_NORTH:
                
            case DIR_EAST:
                
            case DIR_SOUTH:
                
            case DIR_WEST:
                              
        }
        
        return false;
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
