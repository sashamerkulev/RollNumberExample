package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

abstract class AnimationCounter  implements OnAnimationEndListener{
    int digit;
    int animationCounter;
    RollDigit[] rollDigits;

    AnimationCounter(int digit, int counter, RollDigit[] rollDigits){
        this.animationCounter = counter;
        this.digit = digit;
        this.rollDigits = rollDigits;
    }

    public abstract void start();

    @Override
    public void onAnimationEnd(boolean overflow) {
        animationCounter--;
        if (animationCounter > 0){
            start();
        }
    }

    static AnimationCounter startOperation(OperationEnum operationEnum, int digit, int times, RollDigit[] rollDigits){
        AnimationCounter counter = operationEnum == OperationEnum.Decrement
                ? new AnimationDecCounter(digit, times, rollDigits)
                : new AnimationIncCounter(digit, times, rollDigits);
        counter.start();
        return counter;
    }

}
