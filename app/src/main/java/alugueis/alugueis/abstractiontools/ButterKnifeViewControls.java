package alugueis.alugueis.abstractiontools;

import android.support.annotation.NonNull;
import android.view.View;

import butterknife.ButterKnife;

public class ButterKnifeViewControls {

   public static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override
        public void set(@NonNull View view, Boolean value, int index) {
            view.setFocusable(value);
            view.setFocusableInTouchMode(value);
            view.setEnabled(value);
        }
    };
}
