package ru.merkulyevsasha.rollnumberexample.controls;

/**
 * Created by sasha on 19.03.2017.
 */

abstract class Operation implements OnAnimationEndListener{

    int digit;
    final RollDigit[] rollDigits;

    Operation(int digit, RollDigit[] rollDigits){
        this.digit = digit;
        this.rollDigits = rollDigits;
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

    protected abstract void start();

    static Operation start(OperationEnum operationEnum, int digit, RollDigit[] digits){
        Operation oper = operationEnum == OperationEnum.Decrement
                ? new Decrement(digit-1, digits)
                : new Increment(digit-1, digits);
        oper.start();
        return oper;
    }

}
