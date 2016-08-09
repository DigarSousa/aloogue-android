package alugueis.alugueis.dialogs;

import alugueis.alugueis.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class LocationDisabledDialog extends DialogFragment {
    private LocationProvidersResult locationProvidersResult;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(getString(R.string.enableLocationProviders))
                .setPositiveButton(getString(R.string.locationSettings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LocationDisabledDialog.this.dismiss();
                        locationProvidersResult.onPositiveAction();
                    }
                });
        return alertBuilder.create();
    }

    public interface LocationProvidersResult {
        void onPositiveAction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        locationProvidersResult = (LocationProvidersResult) getActivity();
    }
}

