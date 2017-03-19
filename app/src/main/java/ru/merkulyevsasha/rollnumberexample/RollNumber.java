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

public class RollNumber extends LinearLayout {

    private int digits;
    private int number;

    private RollDigit[] rollDigits;

    //private boolean animationEnd = true;

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
            digit.setDigit(Integer.parseInt(numberString.substring(i, i + 1)));
            rollDigits[i] = digit;
            addView(digit);
        }

        setBackgroundColor(Color.parseColor("#00132B"));
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

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
            startOperation(OperationEnum.Increment, digits);
        }
    }

    public void dec() {
        if (isStable()) {
            number--;
            startOperation(OperationEnum.Decrement, digits);
        }
    }

    public int getNumber(){
        return number;
    }

    private String getStringDigits(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < rollDigits.length; i++){
            sb.append(rollDigits[i].getDigit());
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
                startOperation(OperationEnum.Increment, i, newDigit - oldDigit);
            }

            if (newDigit < oldDigit){
                startOperation(OperationEnum.Decrement, i, oldDigit - newDigit );
            }
        }
    }

    Operation startOperation(OperationEnum operationEnum, int digits){
        Operation oper = operationEnum == OperationEnum.Decrement
                ? new Decrement(digits-1)
                : new Increment(digits-1);
        oper.start();
        return oper;
    }

    AnimationCounter startOperation(OperationEnum operationEnum, int digit, int times){
        AnimationCounter counter = operationEnum == OperationEnum.Decrement
                ? new AnimationDecCounter(digit, times)
                : new AnimationIncCounter(digit, times);
        counter.start();
        return counter;
    }

    private abstract class AnimationCounter  implements OnAnimationEndListener{
        int digit;
        int animationCounter;

        AnimationCounter(int digit, int counter){
            this.animationCounter = counter;
            this.digit = digit;
        }

        public abstract void start();

        @Override
        public void onAnimationEnd(boolean overflow) {
            animationCounter--;
            if (animationCounter > 0){
                start();
            }
        }
    }

    private class AnimationIncCounter extends AnimationCounter{

        AnimationIncCounter(int digit, int counter){
            super(digit, counter);
        }

        @Override
        public void start(){
            rollDigits[digit].inc(this);
        }

    }

    private class AnimationDecCounter extends AnimationCounter{

        AnimationDecCounter(int digit, int counter){
            super(digit, counter);
        }

        @Override
        public void start(){
            rollDigits[digit].dec(this);
        }

    }

    private enum OperationEnum {
        Increment,
        Decrement
    }

    private abstract class Operation implements OnAnimationEndListener{

        int digit;
        Operation(int digit){
            this.digit = digit;
        }

        @Override
        public void onAnimationEnd(boolean overflow) {
            if (overflow){
                digit--;
                if (digit >=0){
                    start();
                }
            }
        }

        public abstract void start();

    }

    private class Increment extends Operation {

        Increment(int digit){
            super(digit);
        }

        @Override
        public void start(){
            rollDigits[digit].inc(this);
        }

    }

    private class Decrement extends Operation {

        Decrement(int digit){
            super(digit);
        }

        @Override
        public void start(){
            rollDigits[digit].dec(this);
        }

    }

}
