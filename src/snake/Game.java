
package snake;

public class Game {
    
    // устанавливаем размер поля
    private static int sizeByX = 18;
    private static int sizeByY = 30;
    
    // направления движения
    private static final int DIR_NORTH = 1;
    private static final int DIR_EAST = 2;
    private static final int DIR_SOUTH = 3;
    private static final int DIR_WEST = 4;
    
    // количество набранных очков
    private int score = 0;
    
    // матрица игового поля
    private int[][] field;
    
    // змея - массив двумерных координат
    private Snake snake;
    
    // текущее направление движения
    private int curDirection = Game.DIR_EAST;
    
    // добавить фрукт
    private void AddFruit(){
        
    }
    
    // сделать ход
    private boolean nextMove(){
        return true;
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
