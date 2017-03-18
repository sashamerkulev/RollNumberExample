package ru.merkulyevsasha.rollnumberexample;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * Created by sasha on 17.03.2017.
 */

public class RollNumber extends LinearLayout implements OnAnimationEndListener {

    private int digits;
    private int number;

    private RollDigit[] rollDigits;

    private int current_digit;
    private boolean incrementOperation;
    private boolean animationEnd = true;

    public RollNumber(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RollNumber,
                0, 0);

        try {
            digits = attributes.getInteger(R.styleable.RollNumber_digits, 6);
            number = attributes.getInteger(R.styleable.RollNumber_number, 0);

            if (digits <= 0) {
                throw new NumberFormatException();
            }

            if (number <= 0) {
                throw new NumberFormatException();
            }

        } finally {
            attributes.recycle();
        }

        String numberString = getNumberString(number);
        rollDigits = new RollDigit[numberString.length()];
        for (int i = 0; i < numberString.length(); i++) {
            RollDigit digit = new RollDigit(context, attrs);
            ;
            digit.setDigit(Integer.parseInt(numberString.substring(i, i + 1)));
            rollDigits[i] = digit;
            addView(digit);
        }

        setBackgroundColor(Color.DKGRAY);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

    }

    private String getNumberString(int number) {
        String numberString = String.valueOf(number);
        while (numberString.length() < digits) {
            numberString = "0" + numberString;
        }
        return numberString;
    }

    public void inc() {
        if (animationEnd) {
            number++;
            animationEnd = false;
            incrementOperation = true;
            current_digit = digits - 1;
            incrementIf(current_digit);
        }
    }

    public void dec() {
        if (animationEnd) {
            number--;
            animationEnd = false;
            incrementOperation = false;
            current_digit = digits - 1;
            decrementIf(current_digit);
        }
    }

    private void decrementIf(int current) {
        if (current_digit >= 0) {
            decrement(current);
        } else {
            animationEnd = true;
        }
    }

    private void incrementIf(int current) {
        if (current >= 0) {
            increment(current);
        } else {
            animationEnd = true;
        }
    }

    @Override
    public void onAnimationEnd(boolean nextDigit) {
        if (nextDigit) {
            current_digit--;
            if (incrementOperation) {
                incrementIf(current_digit);
            } else {
                decrementIf(current_digit);
            }
        } else {
            animationEnd = true;
        }
    }

    private void increment(int current) {
        rollDigits[current].inc(this);
    }

    private void decrement(int current) {
        rollDigits[current].dec(this);
    }

    public int getNumber(){
        return number;
    }



}
