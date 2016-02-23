package com.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView{

    private Game game;
    private String text = " ";
    private Bitmap bitmapHead, bitmapTill, bitmapBody, bitmapBackGround, bitmapApple, bitmapWatermelon, bitmapPoison;
    private float x, y;

    // в консрукторе загружаем битмапы (из ресурсов) и добавляем метод обратного вызова для Surface
    public GameSurface(Context context){
        super(context);
        // создаем новое игровое поле
        game = new Game();
        // загружаем картинки
        bitmapHead = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.head);
        bitmapTill = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.till);
        bitmapBody = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.body);
        bitmapBackGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.back_ground);
        bitmapApple = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.apple);
        bitmapWatermelon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.watermelon);
        bitmapPoison = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.poison);
    }

    // метод рисует змейку и другие объекты
    public void drawSnake(Canvas canvas){

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int x = width / Game.sizeByX;
        int y = height / Game.sizeByY;

        Bitmap head = Bitmap.createScaledBitmap(bitmapHead, x, y, true);
        Bitmap body = Bitmap.createScaledBitmap(bitmapBody, x, y, true);
        Bitmap till = Bitmap.createScaledBitmap(bitmapTill, x, y, true);
        Bitmap backGround = Bitmap.createScaledBitmap(bitmapBackGround, x, y, true);
        Bitmap apple = Bitmap.createScaledBitmap(bitmapApple, x, y, true);
        Bitmap watermelon = Bitmap.createScaledBitmap(bitmapWatermelon, x, y, true);
        Bitmap poison = Bitmap.createScaledBitmap(bitmapPoison, x, y, true);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(128);

        // рисуем игровое поле с фруктами на нем
        for(int i = 0; i < Game.sizeByX; i++) {
            for(int j = 0; j < Game.sizeByY; j++) {
                canvas.drawBitmap(backGround, x * i, y * j, paint);
                if(game.getField()[i][j] == 2) {
                    canvas.drawBitmap(apple, x * i, y * j, paint);
                }
                if(game.getField()[i][j] == 3) {
                    canvas.drawBitmap(watermelon, x * i, y * j, paint);
                }
                if(game.getField()[i][j] == 4) {
                    canvas.drawBitmap(poison, x * i, y * j, paint);
                }
            }
        }
        paint.setAlpha(0);

        // рисуем змейку
        for(int i = 0; i < game.getSnakeLength(); i++) {
            canvas.drawBitmap(body, game.getSnake().get(i).getPosByX() * x, game
                    .getSnake().get(i).getPosByY() * y, new Paint());
            if(i == 0) {
                canvas.drawBitmap(till, game.getSnake().get(i).getPosByX() * x, game
                        .getSnake().get(i).getPosByY() * y, new Paint());
            }
            if(i == game.getSnakeLength() - 1) {
                canvas.drawBitmap(head, game.getSnake().get(i).getPosByX() * x, game
                        .getSnake().get(i).getPosByY() * y, new Paint());
            }
        }
		
		paint.setColor(Color.WHITE);
		paint.setAlpha(255);
		paint.setTextSize(25);
		canvas.drawText(text, 50, 50, paint);

    }

    public Game getGame() {
        return game;
    }

    public void setText(String text) {
        this.text = text;
    }
}