package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

class Decrement extends Operation {

    Decrement(int digit, RollDigit[] rollDigits){
        super(digit, rollDigits);
    }

    @Override
    public void start(){
        rollDigits[digit].dec(this);
    }

}

