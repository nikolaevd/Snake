
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

    // размеры игрового поля
    public static final int SIZE_BY_X = 18;
    public static final int SIZE_BY_Y = 30;

    // направление движения
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
/*
    0 - первый запуск
    1 - запуск при переходе на следующий уровень
    2 - запуск после проигрыша
*/
    public static int MODE = 0;
    public static int SCORE = 0;
    public static int LEVEL = 0;

    // признак - растет ли змейка
    private int isGrowing = 0;
    // массив представляет собой игровое поле
    private int[][] field = new int[SIZE_BY_X][SIZE_BY_Y];
    // тело змейки
    private ArrayList<Position> snake = new ArrayList<Position>();
    // переменная содержит текущее направление
    private int curDirection = Game.EAST;

    Game(){
        for(int x = 0; x < SIZE_BY_X; x++){
            for(int y = 0; y < SIZE_BY_Y; y++){
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
            int x = (int)(Math.random() * SIZE_BY_X);
            int y = (int)(Math.random() * SIZE_BY_Y);
            if(field[x][y] == 0){
                isTrue = false;
                field[x][y] = (2 + (int)(Math.random() * ((3 - 2) + 1)));
            }
        }

    }

    // добавляем мухомор
    private void addPoison(){

        int i = 9;
        while(i > 0){
            boolean isTrue = true;
            while(isTrue){
                int x = (int)(Math.random() * SIZE_BY_X);
                int y = (int)(Math.random() * SIZE_BY_Y);
                if(field[x][y] == 0){
                    isTrue = false;
                    field[x][y] = 4;
                }
            }
            i--;
        }

    }

    // сделать шаг в заданном направлении
    public boolean nextMove(){

        switch(curDirection){
            case NORTH:{
                int nextX = snake.get(snake.size() - 1).getPosByX();
                int nextY = snake.get(snake.size() - 1).getPosByY() + 1;
                if(checkBorder(nextX, nextY)){
                    return makeMove(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case EAST:{
                int nextX = snake.get(snake.size() - 1).getPosByX() + 1;
                int nextY = snake.get(snake.size() - 1).getPosByY();
                if(checkBorder(nextX, nextY)){
                    return makeMove(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case SOUTH:{
                int nextX = snake.get(snake.size() - 1).getPosByX();
                int nextY = snake.get(snake.size() - 1).getPosByY() - 1;
                if(checkBorder(nextX, nextY)){
                    return makeMove(nextX, nextY);
                }
                else{
                    return false;
                }
            }
            case WEST:{
                int nextX = snake.get(snake.size() - 1).getPosByX() - 1;
                int nextY = snake.get(snake.size() - 1).getPosByY();
                if(checkBorder(nextX, nextY)){
                    return makeMove(nextX, nextY);
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
        if(x >= 0 && x < SIZE_BY_X && y >= 0 && y < SIZE_BY_Y){
            return true;
        }
        return false;
    }

    // делаем ход
    private boolean makeMove(int x, int y){
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
                SCORE += 10;

                field[x][y] = 0;
                snake.add(new Position(x, y));
                field[x][y] = 1;

                addFruit();
                return true;
            case 3:
                isGrowing += 1;
                SCORE += 20;

                field[x][y] = 0;
                snake.add(new Position(x, y));
                field[x][y] = 1;

                addFruit();
                return true;
            case 4:
                if(snake.size() > 3 && SCORE >= 10){
                    field[snake.get(0).getPosByX()][snake.get(0).getPosByY()] = 0;
                    snake.remove(0);
                    SCORE -= 10;
                    field[x][y] = 0;
                }
                else{
                    return false;
                }

                return true;
            default:
                return false;
        }
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

    public void setCurDirection(int curDirection) {
        this.curDirection = curDirection;
    }

}
