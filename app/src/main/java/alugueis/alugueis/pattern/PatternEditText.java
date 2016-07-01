package alugueis.alugueis.pattern;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class PatternEditText extends EditText {
    private PatternView patternView;

    public PatternEditText(Context context) {
        super(context);
        init(null);
    }

    public PatternEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PatternEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public PatternEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        patternView = new PatternView(this);
        patternView.resolveAttributes(attrs);
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (patternView != null) {
            patternView.setText();
        }
        super.setText(text, type);
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return patternView.onSaveInstanceState(superState);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        BaseSavedState savedState = (BaseSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        patternView.onRestoreInstanceState(state);
    }

    public String getRawText() {
        return patternView.getRawText();
    }

    public String getSpecialChar() {
        return patternView.getSpecialChar();
    }

    public void setSpecialChar(String specialChar) {
        patternView.setSpecialChar(specialChar);
    }

    public String getPattern() {
        return patternView.getPattern();
    }

    public void setPattern(String pattern) {
        patternView.setPattern(pattern);
    }
}
