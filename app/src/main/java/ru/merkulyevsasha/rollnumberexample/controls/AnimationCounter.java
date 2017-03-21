package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

abstract class AnimationCounter  implements OnAnimationEndListener{
    final int digit;
    private int animationTimes;
    final RollDigit[] rollDigits;

    AnimationCounter(int digit, int times, RollDigit[] rollDigits){
        this.animationTimes = times;
        this.digit = digit;
        this.rollDigits = rollDigits;
    }

    protected abstract void start();

    @Override
    public void onAnimationEnd(boolean overflow) {
        animationTimes--;
        if (animationTimes > 0){
            start();
        }
    }

    static AnimationCounter start(OperationEnum operationEnum, int digit, int times, RollDigit[] rollDigits){
        AnimationCounter counter = operationEnum == OperationEnum.Decrement
                ? new AnimationDecCounter(digit, times, rollDigits)
                : new AnimationIncCounter(digit, times, rollDigits);
        counter.start();
        return counter;
    }

}
