package com.snake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {

    Game game;
    Bitmap bitmapHead, bitmapTill, bitmapBody, bitmapBackGround, bitmapFruit;
    float x, y;

    // Установка новых кординат телефона в пространстве
    // для того чтобы правильно нарисовать кружки на фоне
    public void setXY(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Собственно конструктор в котором мы загружаем
    // из ресурсов битмапы и добавляем метод обратного вызова
    // для нашей Surface
    public GameSurface(Context context){
        super(context);
        // вот тут мы создаем новое игровое поле
        game = new Game();
        // а тут загружаем картинки
        bitmapHead = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.head);
        bitmapTill = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.till);
        bitmapBody = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.body);
        bitmapBackGround = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.back_ground);
        bitmapFruit = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.fruit);
    }

    // Рисуем здесь
    public void drawSnake(Canvas canvas){

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int mx = width / Game.sizeByX;
        int my = height / Game.sizeByY;

        // стрейчим битмапы
        Bitmap head = Bitmap.createScaledBitmap(bitmapHead, mx, my, true);
        Bitmap body = Bitmap.createScaledBitmap(bitmapBody, mx, my, true);
        Bitmap till = Bitmap.createScaledBitmap(bitmapTill, mx, my, true);
        Bitmap backGround = Bitmap.createScaledBitmap(bitmapBackGround, mx, my, true);
        Bitmap fruit = Bitmap.createScaledBitmap(bitmapFruit, mx, my, true);

        // создаем на всякий кисточку
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);

        // рисуем кружки
        canvas.drawCircle(width / 2, height / 2, width / 4, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(width / 2 - x * 5, height / 2 + y * 5, width / 10, paint);
        paint.setColor(Color.BLACK);
        paint.setAlpha(128);

        // рисуем игровое поле с фруктами на нем
        for (int i = 0; i < Game.sizeByX; i++) {
            for (int j = 0; j < Game.sizeByY; j++) {
                canvas.drawBitmap(backGround, mx * i, my * j, paint);
                if (game.getField()[i][j] > 1) {
                    canvas.drawBitmap(fruit, mx * i, my * j, paint);
                }
            }
        }
        paint.setAlpha(0);

        // рисуем змею
        for (int i = 0; i < game.getSnakeLength(); i++) {
            canvas.drawBitmap(body, game.getSnake().get(i).getPosByX() * mx, game
                    .getSnake().get(i).getPosByY() * my, new Paint());
            if (i == 0) {
                canvas.drawBitmap(till, game.getSnake().get(i).getPosByX() * mx, game
                        .getSnake().get(i).getPosByY() * my, new Paint());
            }
            if (i == game.getSnakeLength() - 1) {
                canvas.drawBitmap(head, game.getSnake().get(i).getPosByX() * mx, game
                        .getSnake().get(i).getPosByY() * my, new Paint());
            }
        }

    }

}