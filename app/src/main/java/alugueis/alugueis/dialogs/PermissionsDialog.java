package alugueis.alugueis.dialogs;

import alugueis.alugueis.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class PermissionsDialog extends DialogFragment {
    private PermissionDialogListener permissionDialogListener;

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(R.string.closeApplicationMessenge)

                .setNegativeButton(R.string.positive_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionsDialog.this.dismiss();
                        permissionDialogListener.onPositiveClick(PermissionsDialog.this);
                    }
                })

                .setPositiveButton(R.string.negative_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        permissionDialogListener.onNegativeClick(PermissionsDialog.this);
                    }
                });

        return alertDialog.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            permissionDialogListener = (PermissionDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    public interface PermissionDialogListener {
        void onPositiveClick(PermissionsDialog dialogFragment);

        void onNegativeClick(PermissionsDialog dialogFragment);
    }
}
