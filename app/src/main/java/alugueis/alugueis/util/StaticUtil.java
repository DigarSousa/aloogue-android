package alugueis.alugueis.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Spinner;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import alugueis.alugueis.model.Place;

public class StaticUtil {
    public static final String LOGGED_USER = "loggedUser";
    public static final String PRODUCT_LIST = "productList";
    public static final String PLACE = "place";
    public static final String PLACES_AROUND = "placesAround";

    public static Object readObject(Context context, String key) throws IOException, ClassNotFoundException {
        if (Arrays.asList(context.fileList()).contains(key)) {
            FileInputStream fis = context.openFileInput(key);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return ois.readObject();
        }
        return null;
    }

    public static void setOject(Context context, String key, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(key, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static void remove(Context context, String key) throws IOException {
        context.deleteFile(key);
    }

    public static void selectSpinnerValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

}
