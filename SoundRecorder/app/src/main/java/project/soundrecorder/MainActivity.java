package project.soundrecorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

    public final static String MESSAGE_TRANSIT = "soundrecorder.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    public void switchToRecordScreen(View view){
        Intent intent = new Intent(this, record_screen.class);
        Button clickedButton = findViewById(view.getId());
        String msg = clickedButton.getEditableText().toString();
        intent.putExtra(MESSAGE_TRANSIT, msg);
        startActivity(intent);
    }
}
