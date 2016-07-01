package alugueis.alugueis.pattern;

import alugueis.alugueis.R;
import alugueis.alugueis.pattern.utils.PatternUtils;
import android.content.res.TypedArray;
import android.os.Parcelable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class PatternView {
    private String specialChar;
    private String pattern;

    private String mRawText = "";

    private EditText editText;

    public PatternView(EditText et) {
        this.editText = et;
    }

    public void resolveAttributes(AttributeSet attributes) {
        if (attributes == null) return;
        TypedArray a = editText.getContext().obtainStyledAttributes(attributes, R.styleable.PatternEditText);
        pattern = a.getString(R.styleable.PatternEditText_pattern);
        specialChar = a.getString(R.styleable.PatternEditText_specialChar);
        if (specialChar == null) {
            specialChar = "#";
        }

        boolean showHint = a.getBoolean(R.styleable.PatternEditText_showHint, false);
        a.recycle();

        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pattern.length())});

        if (showHint) {
            editText.setHint(pattern);
        }

        final TextWatcher textWatcher = new TextWatcher() {
            private boolean mForcing = false;
            private StringBuilder sb;

            private boolean isDeleting = false;

            private int differenceCount = 0;
            public int toBeSetCursorPosition = 0;
            public int mBeforeTextLength = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBeforeTextLength = s.length();
                if (!mForcing) {
                    sb = new StringBuilder();
                    differenceCount = PatternUtils.getDifferenceCount(s.toString().substring(0, editText.getSelectionStart()), pattern, specialChar.charAt(0));
                    sb.append(mRawText);
                    if (after == 0) {
                        isDeleting = true;
                        try {
                            sb.delete(editText.getSelectionEnd() - count - differenceCount, editText.getSelectionEnd() - differenceCount);
                        } catch (IndexOutOfBoundsException e) {
                            //Do nothing. User tried to delete unremovable char(s) of pattern.
                        }
                    } else {
                        isDeleting = false;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mForcing) {
                    if (!isDeleting) {
                        try {
                            int from = editText.getSelectionEnd() - count - differenceCount;
                            if (from < 0) {
                                from = 0;
                            }
                            sb.insert(from, s.subSequence(start, start + count));
                        } catch (StringIndexOutOfBoundsException e) {
                            Log.e("PatternedEditText: ", e.toString());
                            //getSelectionEnd() returns 0 after screen rotation.
                            //Added to handle filling EditText after rotation.
                            //onRestoreInstanceState() is responsible for setting text.
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mForcing) {
                    mForcing = true;
                    mRawText = sb.toString();
                    String convertedText;
                    if (PatternUtils.isTextAppliesPattern(mRawText, pattern, specialChar.charAt(0))) {
                        convertedText = mRawText;
                        mRawText = PatternUtils.convertPatternedTextToText(mRawText, pattern, specialChar.charAt(0));
                    } else {
                        convertedText = PatternUtils.convertTextToPatternedText(mRawText, pattern, specialChar.charAt(0));
                    }
                    toBeSetCursorPosition = editText.getSelectionStart() + convertedText.length() - s.length();
                    if (mBeforeTextLength == 0) {
                        toBeSetCursorPosition = convertedText.length();
                    }
                    s.clear();
                    s.append(convertedText);
                    try {
                        if (isDeleting) {
                            if (toBeSetCursorPosition < convertedText.length()) {
                                ++toBeSetCursorPosition;
                            }
                        } else if (toBeSetCursorPosition != convertedText.length()) {
                            --toBeSetCursorPosition;
                        }
                        editText.setSelection(toBeSetCursorPosition);
                    } catch (IndexOutOfBoundsException e) {
                        Log.e("PatternedEditText: ", e.toString());
                    }
                    mForcing = false;
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    public Parcelable onSaveInstanceState(Parcelable superState) {
        return new PetSavedState(superState, mRawText);
    }

    public void onRestoreInstanceState(Parcelable state) {
        PetSavedState savedState = (PetSavedState) state;
        mRawText = savedState.getRealText();
        editText.setText(mRawText);
    }

    public void setText() {
        this.mRawText = "";
    }

    public String getRawText() {
        return mRawText;
    }

    public String getSpecialChar() {
        return specialChar;
    }

    public void setSpecialChar(String specialChar) {
        this.specialChar = specialChar;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
