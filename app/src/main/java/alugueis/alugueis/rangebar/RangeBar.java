package alugueis.alugueis.rangebar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
        barPaint.setStyle(Paint.Style.FILL);
        barPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), barPaint);
    }
}
