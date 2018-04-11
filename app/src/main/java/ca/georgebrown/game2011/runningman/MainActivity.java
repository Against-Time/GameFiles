package ca.georgebrown.game2011.runningman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();

        Button quit = (Button) findViewById(R.id.Exit_Button);
        QuitButtonListener quitListener = new QuitButtonListener();
        quit.setOnClickListener(quitListener);

        Button play = (Button) findViewById(R.id.Start_Button);
        PlayButtonListener playListener = new PlayButtonListener();
        play.setOnClickListener(playListener);

        Button options = (Button) findViewById(R.id.Options_Button);
        OptionsButtonListener optionsListener = new OptionsButtonListener();
        options.setOnClickListener(optionsListener);
    }

    private void startPlay() {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
    }


    private class QuitButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class PlayButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            startPlay();
        }
    }

    private class OptionsButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

        }
    }
}

