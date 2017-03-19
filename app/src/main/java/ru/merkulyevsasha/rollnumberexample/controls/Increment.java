package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

class Increment extends Operation {

    Increment(int digit, RollDigit[] rollDigits){
        super(digit, rollDigits);
    }

    @Override
    public void start(){
        rollDigits[digit].inc(this);
    }

}
