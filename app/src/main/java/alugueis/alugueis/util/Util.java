package alugueis.alugueis.util;

import alugueis.alugueis.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Pedreduardo on 15/10/2015.
 */
public class Util {

    //For CPF and CNPJ validation
    //------
    private static int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    //------

    public static boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting());
    }

    public static boolean isOnlineWithToast(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() == null || !cm.getActiveNetworkInfo().isConnectedOrConnecting()) {
            String text = c.getResources().getString(R.string.verifyConnection);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();
            return false;
        }
        return true;
    }

    public static void populeStatesSpinner(Context c, Spinner stateSpinner) {

        String[] arrayState = new String[]{
                "AC", "AL", "AP", "AM", "BA",
                "CE", "DF", "ES", "GO", "MA",
                "MT", "MS", "MG", "PA", "PB",
                "PR", "PE", "PI", "RJ", "RN",
                "RS", "RO", "RR", "SC", "SP",
                "SE", "TO"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c.getApplicationContext(), R.layout.component_spinner_item_light, arrayState);
        stateSpinner.setAdapter(adapter);
    }

    //For CPF and CNPJ validation
    private static int calculateDigit(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14)) return false;

        Integer digito1 = calculateDigit(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calculateDigit(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11)) return false;

        Integer digito1 = calculateDigit(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calculateDigit(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if (email.matches(regex)) {
            return true;
        }
        return false;
    }

    public static void populeHoursSpinner(Context c, Spinner businessInitialHourSpinner) {
        String[] arrayHours = new String[]{
                "01", "02", "03", "04", "05",
                "06", "07", "08", "09", "10",
                "11", "12", "13", "14", "15",
                "16", "17", "18", "19", "20",
                "21", "22", "23", "24"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c.getApplicationContext(),
                android.R.layout.simple_spinner_item,
                arrayHours);
        businessInitialHourSpinner.setAdapter(adapter);
    }

}
