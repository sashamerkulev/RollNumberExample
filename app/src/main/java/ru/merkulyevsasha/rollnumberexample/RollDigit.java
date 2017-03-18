package ru.merkulyevsasha.rollnumberexample;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sasha on 17.03.2017.
 */

public class RollDigit extends LinearLayout{

    private static int DURATION = 500;
    private static float TEXTSIZE = 3/5F;
    private static int MARGIN = 8;

    private int digit;
    private int textSize;

    private TextView[] views;

    private TextView hiddenDigit;

    private int w;
    private int h;

    private Paint normalTextPaint;
    private Rect normalTextBounds;


    public RollDigit(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        normalTextPaint = new Paint();
        normalTextBounds = new Rect();

        views = new TextView[3];

        views[0] = createTextView(context, View.VISIBLE);
        views[1] = createTextView(context, View.VISIBLE);
        views[2] = createTextView(context, View.VISIBLE);

        hiddenDigit = createTextView(context, View.GONE);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RollDigit,
                0, 0);

        try {
            digit = attributes.getInteger(R.styleable.RollDigit_digit, 1);
            textSize = attributes.getInteger(R.styleable.RollDigit_textSize, 24);

        } finally {
            attributes.recycle();
        }

        setDigit(digit);
        setTextSize(context, attrs);

        addView(views[0]);
        addView(views[1]);
        addView(views[2]);
        addView(hiddenDigit);

//        setBackgroundColor(Color.BLACK);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);

    }

    private TextView createTextView(Context context, int visibility){
        TextView r= new TextView(context);
        r.setVisibility(visibility);
        r.setTextColor(Color.WHITE);
        r.setGravity(Gravity.CENTER);
        return r;
    }

    public void setTextSize(Context context, AttributeSet attrs){

        normalTextPaint.setStyle(Paint.Style.FILL);
        normalTextPaint.setColor(Color.WHITE);
        normalTextPaint.setStrokeWidth(2);
        normalTextPaint.setTextSize(textSize* getContext().getResources().getDisplayMetrics().density);
        normalTextPaint.getTextBounds("8", 0, 1, normalTextBounds);

        ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
        mDrawable.getPaint().setShader(new RadialGradient(normalTextBounds.width()/2,
                normalTextBounds.height()/5+normalTextBounds.height(), normalTextBounds.height(),
                Color.parseColor("#606060"), Color.parseColor("#000000"), Shader.TileMode.CLAMP));
        setBackground(mDrawable );

        w = (int)(normalTextBounds.width()* 1.5)+getPaddingLeft() + getPaddingRight();
        h = normalTextBounds.height()*5 + getPaddingBottom() + getPaddingTop();

        LinearLayout.LayoutParams params = new LayoutParams(context, attrs);
        params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        params.width = w;
        params.height = h;
        setLayoutParams(params);


        hiddenDigit.setTextSize(textSize*TEXTSIZE);

        views[0].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize*TEXTSIZE);

        views[1].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        views[2].setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize*TEXTSIZE);
    }

    public void setDigit(int digit){
        this.digit = digit;
        views[0].setText(String.valueOf(prevNumber(digit)));
        views[1].setText(String.valueOf(digit));
        views[2].setText(String.valueOf(nextNumber(digit)));
    }

    private int nextNumber(int number){
        int next = number + 1;
        if (next > 9) {
            next = 0;
        }
        return next;
    }

    private int prevNumber(int number){
        int prev = number - 1;
        if (prev < 0) {
            prev = 9;
        }
        return prev;
    }

    public void dec(final OnAnimationEndListener callback) {

        hiddenDigit.setText(String.valueOf(prevNumber(prevNumber(digit))));
        hiddenDigit.setVisibility(View.VISIBLE);

        final float prevYStart = views[0].getY();

        final float numberYStart = views[1].getY();

        final float nextYStart = views[2].getY();

        ObjectAnimator yPrevPrevAnimator = ObjectAnimator
                .ofFloat(hiddenDigit, "y", -normalTextBounds.height(), prevYStart)
                .setDuration(DURATION);

        ObjectAnimator yPrevAnimator = ObjectAnimator
                .ofFloat(views[0], "y", prevYStart, numberYStart)
                .setDuration(DURATION);
        ObjectAnimator sizePrevAnimator = ObjectAnimator
                .ofFloat(views[0], "textSize", textSize*TEXTSIZE, textSize)
                .setDuration(DURATION);


        ObjectAnimator yNumberAnimator = ObjectAnimator
                .ofFloat(views[1], "y", numberYStart, nextYStart)
                .setDuration(DURATION);
        ObjectAnimator sizeNumberAnimator = ObjectAnimator
                .ofFloat(views[1], "textSize", textSize, textSize*TEXTSIZE)
                .setDuration(DURATION);

        ObjectAnimator yNextAnimator = ObjectAnimator
                .ofFloat(views[2], "y", nextYStart, nextYStart+normalTextBounds.height()*2)
                .setDuration(DURATION);



        AnimatorSet mAnimator = new AnimatorSet();
        mAnimator.play(yNumberAnimator).with(sizeNumberAnimator)
                .with(yNextAnimator).with(yPrevAnimator).with(sizePrevAnimator).with(yPrevPrevAnimator);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boolean overflow = false;
                digit--;
                if (digit < 0){
                    digit = 9;
                    overflow = true;
                }

                TextView tmp = views[2];
                views[2] = views[1];
                views[1] = views[0];
                views[0] = hiddenDigit;

                hiddenDigit = tmp;
                //tmp.setVisibility(View.GONE);
                callback.onAnimationEnd(overflow);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        mAnimator.start();
    }

    public void inc(final OnAnimationEndListener callback) {

        hiddenDigit.setText(String.valueOf(nextNumber(nextNumber(digit))));
        hiddenDigit.setVisibility(View.VISIBLE);

        final float prevYStart = views[0].getY();

        final float numberYStart = views[1].getY();

        final float nextYStart = views[2].getY();

        ObjectAnimator yPrevAnimator = ObjectAnimator
                .ofFloat(views[0], "y", prevYStart, -normalTextBounds.height())
                .setDuration(DURATION);

        ObjectAnimator yNumberAnimator = ObjectAnimator
                .ofFloat(views[1], "y", numberYStart, prevYStart)
                .setDuration(DURATION);
        ObjectAnimator sizeNumberAnimator = ObjectAnimator
                .ofFloat(views[1], "textSize", textSize, textSize*TEXTSIZE)
                .setDuration(DURATION);

        ObjectAnimator yNextAnimator = ObjectAnimator
                .ofFloat(views[2], "y", nextYStart, numberYStart)
                .setDuration(DURATION);
        ObjectAnimator sizeNextAnimator = ObjectAnimator
                .ofFloat(views[2], "textSize", textSize*TEXTSIZE, textSize)
                .setDuration(DURATION);

        ObjectAnimator yNextNextAnimator = ObjectAnimator
                .ofFloat(hiddenDigit, "y", nextYStart+normalTextBounds.height(), nextYStart)
                .setDuration(DURATION);


        AnimatorSet mAnimator = new AnimatorSet();
        mAnimator.play(yNumberAnimator).with(sizeNumberAnimator)
                .with(yNextAnimator).with(sizeNextAnimator).with(yPrevAnimator).with(yNextNextAnimator);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boolean overflow = false;
                digit++;
                if (digit > 9){
                    digit = 0;
                    overflow = true;
                }

                TextView tmp = views[0];

                views[0] = views[1];
                views[1] = views[2];
                views[2] = hiddenDigit;

                hiddenDigit = tmp;
                //tmp.setVisibility(View.GONE);
                callback.onAnimationEnd(overflow);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mAnimator.start();

    }

    public int getDigit(){
        return digit;
    }

}
