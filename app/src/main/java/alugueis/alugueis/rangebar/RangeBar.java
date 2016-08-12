package alugueis.alugueis.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import alugueis.alugueis.R;


public class RangeBar extends View {
    private Paint barPaint;

    public RangeBar(Context context) {
        super(context);
        init();
    }

    public RangeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RangeBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        barPaint = new Paint();
        barPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(getPaddingLeft() + getLeft(), 10, getPaddingRight() - getRight(),10, barPaint);
    }
}
