package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

class AnimationDecCounter extends AnimationCounter{

    AnimationDecCounter(int digit, int counter, RollDigit[] rollDigits){
        super(digit, counter, rollDigits);
    }

    @Override
    public void start(){
        rollDigits[digit].dec(this);
    }

}
