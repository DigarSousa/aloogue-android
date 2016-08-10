package alugueis.alugueis.dialogs;

import alugueis.alugueis.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class PermissionsDialog extends DialogFragment {

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setMessage(R.string.closeApplicationMessenge)

                .setNegativeButton(R.string.positive_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PermissionsDialog.this.dismiss();
                        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                    }
                })

                .setPositiveButton(R.string.negative_msg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });

        return alertDialog.create();
    }
}
