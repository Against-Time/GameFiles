package runnergame.againsttime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.Random;

public class Game extends AppCompatActivity
{
    Canvas canvas;
    GameCanvas gameCanvas;

    // Display Stuff
    Display display;
    Point size;
    int ScreenH;
    int ScreenW;

    // Game Objects
    int PlayerW;
    int PlayerH;
    Point PlayerPosition;

    Point EnemyPosition;
    int EnemyW;

    // For Ball Movement
    boolean EnemyMovingUp;
    boolean EnemyMovingDown;

    // For racket movement

    // Stats
    long lastFrameTime;
    int fps;
    int score;
    int lives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameCanvas = new GameCanvas(this);
        setContentView(gameCanvas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Setup the sound


        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        ScreenH = size.x;
        ScreenW = size.y;

        // The game objects
        PlayerPosition = new Point();
        PlayerPosition.x = ScreenH -50;
        PlayerPosition.y = ScreenW/2;
        PlayerW = 100;
        PlayerH = 70;

        EnemyW = ScreenH /35;
        EnemyPosition = new Point();
        EnemyPosition.x = ScreenH /2;
        EnemyPosition.y = 1 + EnemyW;

        lives = 3;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //soundPool.play(sample3, 1.0f, 1.0f, 0, 0, 1.0f);
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        while (true) {
            gameCanvas.pause();
            break;
        }

        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameCanvas.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameCanvas.resume();
    }

    private class GameCanvas extends SurfaceView implements Runnable
    {
        Thread ourThread = null;
        SurfaceHolder ourHolder;
        volatile boolean PlayerAlive;
        Paint paint;

        public GameCanvas(Context context) {
            super(context);

            ourHolder = getHolder();
            paint = new Paint();
            EnemyMovingDown = true;
        }

        @Override
        public void run() {
            while (PlayerAlive) {
                updateCourt();
                drawCourt();
                controlFPS();
            }
        }

        private void controlFPS() {
            long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
            long timeToSleep = 15 - timeThisFrame;

            if (timeThisFrame > 0) {
                fps = (int) (1000 / timeThisFrame);
            }

            if (timeToSleep > 0) {
                try {
                    ourThread.sleep(timeToSleep);
                } catch (InterruptedException e) {
                }
            }

            lastFrameTime = System.currentTimeMillis();
        }

        private void drawCourt() {
            if (ourHolder.getSurface().isValid()) {
                canvas = ourHolder.lockCanvas();
                //Paint paint = new Paint();
                canvas.drawColor(Color.BLUE);

                // Draw the background
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(45);
                canvas.drawText("Score: " + score + "Lives: " + lives + " fps:" + fps, 20, 40, paint);

                //Draw the squash racket
                canvas.drawRect(PlayerPosition.x -(PlayerW / 2), PlayerPosition.y - (PlayerH / 2), PlayerPosition.x + (PlayerW / 2), PlayerPosition.y + PlayerH, paint);

                //Draw the ball
                canvas.drawRect(EnemyPosition.x, EnemyPosition.y, EnemyPosition.x + EnemyW, EnemyPosition.y + EnemyW, paint) ;
                ourHolder.unlockCanvasAndPost(canvas);
            }
        }

        private void updateCourt() {
           if ( EnemyPosition.y > ScreenW - EnemyW)
            {

            }

            // we hit the top of the screen
            if ( EnemyPosition.y <= 0) {
                EnemyMovingDown = false;
                EnemyMovingUp = true;
                EnemyPosition.y = 1;

            }

            // Set new coordinates
            if (EnemyMovingDown) {
                EnemyPosition.y += 10;
            }

            if (EnemyMovingUp) {
                EnemyPosition.y -= 10;
            }

            // Has ball hit racket?


        }


        public void pause() {
            PlayerAlive = false;
            try {
                ourThread.join();
            } catch (InterruptedException e) {

            }
        }

        public void resume () {
            PlayerAlive = true;
            ourThread = new Thread(this);
            ourThread.start();
        }
    }
}
