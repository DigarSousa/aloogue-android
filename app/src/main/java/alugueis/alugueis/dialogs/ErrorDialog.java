package alugueis.alugueis.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import alugueis.alugueis.R;

public class ErrorDialog {
    private String errorMsg;
    private AlertDialog alertDialog;

    public ErrorDialog(Context context) {
        this(context, null);
    }

    public ErrorDialog(Context context, String title) {

        alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setIcon(R.drawable.ic_error)
                .create();
    }

    public ErrorDialog setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    public ErrorDialog setOnDimissListener(DialogInterface.OnDismissListener onDimissListener) {
        alertDialog.setOnDismissListener(onDimissListener);
        return this;
    }

    public ErrorDialog setIcon(int iconId){
        alertDialog.setIcon(iconId);
        return this;
    }

    public void show() {
        alertDialog.show();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
}
