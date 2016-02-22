
package com.snake;

import java.util.ArrayList;

/*
     0 - пустая ячейка
     1 - ячейку занимает тело змеи
     2 - ячейку занимает яблоко ( + 10 очков, + 2 к росту )
     3 - ячейку занимает арбуз ( + 20 очков, + 3 к росту )
     4 - ячейку занимает мухомор
*/

public class Game{

    public static final int sizeByX = 18;
    public static final int sizeByY = 30;

    // направление движения
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;

    // переменная хранит набранные очки
    private int score = 0;
    // признак - растет ли змейка
    private int isGrowing = 0;
    // массив представляет собой игровое поле
    private int[][] field = new int[sizeByX][sizeByY];
    // тело змейки
    private ArrayList<Position> snake = new ArrayList<Position>();
    // переменная содержит текущее направление
    private int curDirection = Game.EAST;

    Game(){
        for(int x = 0; x < sizeByX; x++){
            for(int y = 0; y < sizeByY; y++){
                field[x][y] = 0;
            }
        }

        snake.add(new Position(2, 2));
        field[2][2] = 1;
        snake.add(new Position(2, 3));
        field[2][3] = 1;
        snake.add(new Position(2, 4));
        field[2][4] = 1;

        addFruit();
        addPoison();
    }

    // добавить фрукт на игровое поле
    private void addFruit(){

        boolean isTrue = true;

        while(isTrue){
            int x = (int)(Math.random() * sizeByX);
            int y = (int)(Math.random() * sizeByY);
            if(field[x][y] == 0){
                isTrue = false;
                field[x][y] = (2 + (int)(Math.random() * ((3 - 2) + 1)));
            }
        }

    }

    // добавляем мухомор
    private void addPoison(){

        int i = 5;
        while(i > 0){
            boolean isTrue = true;
            while(isTrue){
                int x = (int)(Math.random() * sizeByX);
                int y = (int)(Math.random() * sizeByY);
                if(field[x][y] == 0){
                    isTrue = false;
                    field[x][y] = 4;
                }
            }
            i--;
        }

    }

    // выполнить шаг в заданном направлении
    public boolean nextMove(){

        switch(curDirection){
            case NORTH:{
                int nextX = snake.get(snake.size() - 1).getPosByX();
                int nextY = snake.get(snake.size() - 1).getPosByY() + 1;
                if(checkBorder(nextX, nextY)){
                    return checkNextPosition(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case EAST:{
                int nextX = snake.get(snake.size() - 1).getPosByX() + 1;
                int nextY = snake.get(snake.size() - 1).getPosByY();
                if(checkBorder(nextX, nextY)){
                    return checkNextPosition(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case SOUTH:{
                int nextX = snake.get(snake.size() - 1).getPosByX();
                int nextY = snake.get(snake.size() - 1).getPosByY() - 1;
                if(checkBorder(nextX, nextY)){
                    return checkNextPosition(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case WEST:{
                int nextX = snake.get(snake.size() - 1).getPosByX() - 1;
                int nextY = snake.get(snake.size() - 1).getPosByY();
                if(checkBorder(nextX, nextY)){
                    return checkNextPosition(nextX, nextY);
                }
                else{
                    return false;
                }
            }
        }

        return false;
    }

    // проверить, не упирается ли змейка в границы игрового поля
    private boolean checkBorder(int x, int y){
        if(x >= 0 && x < sizeByX && y >= 0 && y < sizeByY){
            return true;
        }
        return false;
    }

    private boolean checkNextPosition(int x, int y){
        switch (field[x][y]) {
            case 0:
                if(isGrowing > 0){
                    isGrowing--;
                }
                else{
                    field[snake.get(0).getPosByX()][snake.get(0).getPosByY()] = 0;
                    snake.remove(0);
                }
                snake.add(new Position(x, y));
                field[x][y] = 1;
                return true;
            case 1:
                return false;
            case 2:
                isGrowing += 2;
                score += 10;

                field[x][y] = 0;
                snake.add(new Position(x, y));
                field[x][y] = 1;

                addFruit();
                return true;
            case 3:
                isGrowing += 3;
                score += 20;

                field[x][y] = 0;
                snake.add(new Position(x, y));
                field[x][y] = 1;

                addFruit();
                return true;
            case 4:
                return false;
            default:
                return false;
        }
    }

    public int getScore() {
        return score;
    }

    public int[][] getField() {
        return field;
    }

    public ArrayList<Position> getSnake() {
        return snake;
    }

    public int getSnakeLength() {
        return snake.size();
    }

    public int getCurDirection() {
        return curDirection;
    }

    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }

    public void clearScore(){
        this.score = 0;
    }

}
