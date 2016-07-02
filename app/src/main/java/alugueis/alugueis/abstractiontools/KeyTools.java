package alugueis.alugueis.abstractiontools;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyTools {

    private static InputMethodManager inputMethodManager;

    /**
     * Show input method and put the focus on view that was passed by param
     *
     * @param view
     */
    public static void showInputMethod(Context context, View view) {
        view.requestFocus();
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Are you reading this? Really? ¬¬
     */
    public static void hideInputMethod(Context context, View view) {
        inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Controls the visibility of input  method, if visible is true, then the input method is showed, else,
     * then input method is hidden
     *
     * @param context
     * @param view
     * @param visible
     */
    public static void visibleInputMethod(Context context, View view, Boolean visible) {
        if (visible) {
            showInputMethod(context, view);
        } else {
            hideInputMethod(context, view);
        }
    }

}
