package alugueis.alugueis.util;

import android.content.Context;
import alugueis.alugueis.model.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Pedreduardo on 08/12/2015.
 */
public class UserUtil {

    public static Boolean validateLogin(Context context, String email, String password) {
        //TODO: WebService de validação de usuário
        User logged = new User();

        try{
            logged = (User)getLogged(context);
        }catch(Exception ex){}

        if (logged.getEmail().equals(email) && logged.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public static Object getLogged(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput("loggedUser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object loggedUser = ois.readObject();
        return loggedUser;
    }

    public static void login(Context context, Object loggedUser) throws IOException{
        FileOutputStream fos = context.openFileOutput("loggedUser", Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(loggedUser);
        oos.close();
        fos.close();
    }


    public static void logout(Context context) throws IOException{
        context.deleteFile("loggedUser");
    }
}
