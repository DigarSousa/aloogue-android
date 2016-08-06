package alugueis.alugueis.dialogs;

import alugueis.alugueis.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

public class PermissionsDenied extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setMessage(R.string.location_settings);
        alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int wish) {
                Intent i = new Intent();
                i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getActivity().getPackageName()));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                getActivity().startActivity(i);

            }
        });
        return alertBuilder.create();
    }
}
