package project.soundrecorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;





public class record_screen extends AppCompatActivity {

    private ImageButton recButton;
    private ImageButton returnButton;
    private SoundRecorder recorder = null;
    private Thread timeOutThread = null;
    private TextView textView;
    private boolean recording;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_screen);

        this.recButton = findViewById(R.id.recButton);
        this.returnButton = findViewById(R.id.backButton);
        this.textView = findViewById(R.id.recordName);

        Intent intent = getIntent();
        filename = intent.getStringExtra(MainActivity.MESSAGE_TRANSIT);
        textView.setText(filename);

        recorder = new SoundRecorder(filename + ".wav", getBaseContext());
        recording = false;
    }

    public void switchToStartScreen(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void startRecording(View v){
        recorder.startRecording();
        recButton.setActivated(false);
        returnButton.setActivated(false);
        textView.setText("ENREGISTREMENT");
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        textView.setText("ENREGISTREMENT TERMINE");
        recorder.stopRecording();
        recButton.setActivated(true);
        returnButton.setActivated(true);
    }

}
