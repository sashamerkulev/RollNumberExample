package ru.merkulyevsasha.rollnumberexample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button mStartIncrement;
    private Button mStartDecrement;
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
                cancelTimer();
                mRollNumber.inc();
            }
        });

        Button decr = (Button)findViewById(R.id.decrement);
        decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
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
                cancelTimer();
                mRollNumber.setNumber(Integer.parseInt(numberText));
            }
        });

        mTimer = new Timer();

        mStartIncrement = (Button)findViewById(R.id.button_start_inc);
        mStartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mStartIncrement.getText().toString();
                if (title.equals(getString(R.string.start_button_title))){
                    mStartIncrement.setText(R.string.stop_button_title);
                    try {
                        cancelTimer();
                        mTask = getNewIncTimerTask();
                        mTimer.schedule(mTask, 0, 1000);
                    } catch(Exception e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                } else {
                    cancelTimer();
                }
            }
        });

        mStartDecrement = (Button)findViewById(R.id.button_start_dec);
        mStartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mStartDecrement.getText().toString();
                if (title.equals(getString(R.string.start_button_title))){
                    mStartDecrement.setText(R.string.stop_button_title);
                    try {
                        cancelTimer();
                        mTask = getNewDecTimerTask();
                        mTimer.schedule(mTask, 0, 1000);
                    } catch(Exception e){
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }
                } else {
                    cancelTimer();
                }
            }
        });

        Button random = (Button)findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTimer();
                Random rand = new Random();
                int n = rand.nextInt(999999);
                mRollNumber.setNumber(n);
            }
        });


    }

    private TimerTask getNewIncTimerTask(){
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

    private TimerTask getNewDecTimerTask(){
        return new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRollNumber.dec();
                    }
                });
            }
        };
    }

    private void cancelTimer(){
        mStartIncrement.setText(R.string.start_button_title);
        mStartDecrement.setText(R.string.start_button_title);
        mTimer.cancel();
        mTask  = null;
        mTimer = new Timer();
    }

}
