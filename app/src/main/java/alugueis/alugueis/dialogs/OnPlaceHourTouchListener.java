package alugueis.alugueis.dialogs;

import alugueis.alugueis.abstractiontools.KeyTools;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class OnPlaceHourTouchListener implements View.OnTouchListener {
    private EditText editText;
    private Context context;

    public OnPlaceHourTouchListener(Context context, EditText editText) {
        this.editText = editText;
        this.context = context;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        KeyTools.hideInputMethod(context, view);

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    editText.setText(i + ":" + i1);
                }
            }, 0, 0, true);
            timePickerDialog.show();
            return true;
        }
        return false;
    }

}
