package alugueis.alugueis.dialogs;

import android.content.Context;
import android.content.DialogInterface;

import alugueis.alugueis.LoginFragment;
import alugueis.alugueis.R;

public class DialogsUtil {
    private Context context;

    public static void connectionError(Context context){
        connectionError(context,null);
    }

    public static void connectionError(Context context, DialogInterface.OnDismissListener onDismissListener){
        ErrorDialog errorDialog=new ErrorDialog(context, context.getString(R.string.noConnected));
        errorDialog.setErrorMsg(context.getString(R.string.notConnectedMsg));
        errorDialog.setIcon(R.drawable.ic_error);

        if(onDismissListener!=null){
            errorDialog.setOnDimissListener(onDismissListener);
        }

        errorDialog.show();
    }
}
