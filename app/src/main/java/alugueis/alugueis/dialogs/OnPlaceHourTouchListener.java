package alugueis.alugueis.dialogs;

import alugueis.alugueis.abstractiontools.KeyTools;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OnPlaceHourTouchListener implements View.OnFocusChangeListener, View.OnTouchListener {
    private EditText editText;
    private Context context;
    private TimePickerDialog timePickerDialog;

    public OnPlaceHourTouchListener(Context context, EditText editText) {
        this.editText = editText;
        this.context = context;
        setUpPicker();
    }

    private void setUpPicker() {
        editText.setInputType(InputType.TYPE_NULL);

        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, i);
                c.set(Calendar.MINUTE, i1);

                editText.setText(new SimpleDateFormat("HH:mm").format(c.getTime()));
                KeyTools.hideInputMethod(context, editText);
            }
        }, 0, 0, true);

        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                KeyTools.hideInputMethod(context, editText);
            }
        });

        timePickerDialog.setCanceledOnTouchOutside(true);
    }

    private void showTimePicker() {
        if (!timePickerDialog.isShowing()) {
            timePickerDialog.show();
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            KeyTools.hideInputMethod(context, editText);
            showTimePicker();
        }
        ;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            showTimePicker();
            editText.requestFocus();
            return true;
        }
        return false;
    }
}
