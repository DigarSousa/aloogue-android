package alugueis.alugueis.dialogs;

import alugueis.alugueis.R;
import alugueis.alugueis.util.MapsUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class LocationDisabledDialog extends DialogFragment {
    private Dialog dialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(getString(R.string.enableLocationProviders))
                .setPositiveButton(getString(R.string.locationSettings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MapsUtil.callLocationSettings(getActivity());
                    }
                });
        return alertBuilder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
