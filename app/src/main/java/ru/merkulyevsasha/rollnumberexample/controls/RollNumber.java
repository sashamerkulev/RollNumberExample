package ru.merkulyevsasha.rollnumberexample.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import ru.merkulyevsasha.rollnumberexample.R;

/**
 * Created by sasha on 17.03.2017.
 */

public class RollNumber extends LinearLayout {

    private int digits;
    private int number;

    private RollDigit[] rollDigits;

    public RollNumber(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.RollNumber, 0, 0);
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
            digit.setDigit(Integer.parseInt(numberString.substring(i, i + 1)));
            rollDigits[i] = digit;
            addView(digit);
        }

        setBackgroundColor(Color.parseColor("#00132B"));
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

    }

    public void onStart(Context context){
        clearAnimation();
        for (RollDigit rollDigit : rollDigits) {
            rollDigit.onStart(context);
        }
    }

    private String getNumberString(int number) {
        String numberString = String.valueOf(number);
        if (digits <= numberString.length()){
            return numberString;
        }
        int zeros = digits - numberString.length();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < zeros; i++){
            sb.append("0");
        }
        sb.append(numberString);
        return sb.toString();
    }

    public void inc() {
        if (isStable()) {
            number++;
            if (number > getMaxNumber()){
                number = 0;
            }
            Operation.start(OperationEnum.Increment, digits, rollDigits);
        }
    }

    public void dec() {
        if (isStable()) {
            number--;
            if (number < 0){
                number = getMaxNumber();
            }
            Operation.start(OperationEnum.Decrement, digits, rollDigits);
        }
    }

    public int getNumber(){
        return number;
    }

    private int getMaxNumber(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i <digits; i++){
            sb.append("9");
        }
        return Integer.parseInt(sb.toString());
    }

    private String getStringDigits(){
        StringBuilder sb = new StringBuilder();
        for (RollDigit rollDigit : rollDigits) {
            sb.append(rollDigit.getDigit());
        }
        return sb.toString();
    }

    private boolean isStable(){
        String numberString = getNumberString(number);
        String digitsString = getStringDigits();
        return numberString.equals(digitsString);
    }

    public void setNumber(int newNumber) {
        if (!isStable())
            return;

        String newNumberString = getNumberString(newNumber);
        String numberString = getNumberString(number);
        this.number = newNumber;

        for(int i=0; i < newNumberString.length(); i++){
            int newDigit = Integer.parseInt(newNumberString.substring(i, i+1));
            int oldDigit = Integer.parseInt(numberString.substring(i, i+1));

            if (newDigit == oldDigit)
                continue;

            if (newDigit > oldDigit) {
                AnimationCounter.start(OperationEnum.Increment, i, newDigit - oldDigit, rollDigits);
            }

            if (newDigit < oldDigit){
                AnimationCounter.start(OperationEnum.Decrement, i, oldDigit - newDigit, rollDigits );
            }
        }
    }

}
