package ru.merkulyevsasha.rollnumberexample;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //final RollDigit rollDigit = (RollDigit)findViewById(R.id.roll_digit);
        final RollNumber rollNumber = (RollNumber)findViewById(R.id.roll_number);

        Button incr = (Button)findViewById(R.id.increment);
        incr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rollDigit.inc();
                rollNumber.inc();
            }
        });
        Button decr = (Button)findViewById(R.id.decrement);
        decr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rollDigit.dec();
                rollNumber.dec();
            }
        });
//
//        final TextView prev = (TextView)findViewById(R.id.prev);
//        final TextView number = (TextView)findViewById(R.id.number);
//        final TextView next = (TextView)findViewById(R.id.next);
//
//        Button auto = (Button)findViewById(R.id.autoincrement);
//        auto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final float prevYStart = prev.getY();
//                final float numberYStart = number.getY();
//                final float nextYStart = next.getY();
//
//                ObjectAnimator yNumberAnimator = ObjectAnimator
//                        .ofFloat(number, "y", numberYStart, prevYStart)
//                        .setDuration(2000);
//                //yNumberAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//                //yNumberAnimator.setEvaluator(new FloatEvaluator());
//
//                ObjectAnimator yNextAnimator = ObjectAnimator
//                        .ofFloat(next, "y", nextYStart, numberYStart)
//                        .setDuration(2000);
//                //yNextAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//                //yNextAnimator.setEvaluator(new FloatEvaluator());
//
//                ObjectAnimator yPrevAnimator = ObjectAnimator
//                        .ofFloat(prev, "y", nextYStart+100, nextYStart)
//                        .setDuration(2000);
//                //yPrevAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
////                yPrevAnimator.setEvaluator(new FloatEvaluator());
//
//                AnimatorSet mAnimatorSunset = new AnimatorSet();
//                mAnimatorSunset.play(yNumberAnimator)
//                        .with(yNextAnimator).with(yPrevAnimator);
//                mAnimatorSunset.start();
//            }
//        });

//        LinearLayout root = (LinearLayout)findViewById(R.id.root);
//        TextView r= new TextView(this);
//        r.setVisibility(View.VISIBLE);
//        r.setTextColor(Color.BLACK);
//        r.setText("A");
//        r.setTextSize(TypedValue.COMPLEX_UNIT_SP, 65);
//        root.addView(r);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
