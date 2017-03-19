package ru.merkulyevsasha.rollnumberexample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private RollNumber mRollNumber;

    private Timer mTimer;
    private TimerTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final View root = findViewById(R.id.root);

        mRollNumber = (RollNumber)findViewById(R.id.roll_number);

        Button incr = (Button)findViewById(R.id.increment);
        incr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRollNumber.inc();
            }
        });
        Button decr = (Button)findViewById(R.id.decrement);
        decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRollNumber.dec();
            }
        });

        final EditText number = (EditText)findViewById(R.id.edit_number);
        Button post = (Button)findViewById(R.id.button_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberText = number.getText().toString();
                if (numberText.isEmpty()){
                    Snackbar.make(root, R.string.number_empty_validation_message, Snackbar.LENGTH_LONG).show();
                    return;
                }
            }
        });

        mTimer = new Timer();
        mTask = getNewTimerTask();

        final Button start = (Button)findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = start.getText().toString();
                if (title.equals(getString(R.string.start_button_title))){
                    start.setText(R.string.stop_button_title);
                    try {
                        mTimer.schedule(mTask, 0, 1000);
                    } catch(Exception e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                } else {
                    start.setText(R.string.start_button_title);
                    mTimer.cancel();
                    mTask  = getNewTimerTask();
                    mTimer = new Timer();
                }
            }
        });

    }

    private TimerTask getNewTimerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRollNumber.inc();
                    }
                });
            }
        };
    }

}
