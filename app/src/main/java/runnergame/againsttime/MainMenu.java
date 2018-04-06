//Author(s): Jatin Kumar(101035696); victor skierski(100952026); shariq adtani(101031329); Aljon Ramos(101050991).
package runnergame.againsttime;

import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button playButtonVariable = (Button) findViewById(R.id.B_Play);
        PlayButtonListener playListenerVariable = new PlayButtonListener();
        playButtonVariable.setOnClickListener(playListenerVariable);

        //Quit Button
        Button quit = (Button) findViewById(R.id.B_Quit);
        QuitButtonListener quitListener = new QuitButtonListener();
        quit.setOnClickListener(quitListener);

    }
    private class QuitButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    //Start Play function
    private void startPlay() {
        Intent intentInstance = new Intent(this, Game.class);
        startActivity(intentInstance);
    }
    //Play Listener
    private class PlayButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            startPlay();
        }
    }
}
