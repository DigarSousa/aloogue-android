package alugueis.alugueis.util;

import alugueis.alugueis.model.LoggedUser;

import java.util.List;

/**
 * Created by Pedreduardo on 08/12/2015.
 */
public class UserUtil {

    public static Boolean validateLogin(String email, String password) {
        //TODO: WebService de validação de usuário
        LoggedUser logged=new LoggedUser();

        List<LoggedUser> loggedUsers = LoggedUser.listAll(LoggedUser.class);
        if (loggedUsers.size() > 0) {
            logged = loggedUsers.get(0);
        }

        if (logged.getEmail().equals(email) && logged.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public static LoggedUser getLogged() {

        LoggedUser loggedUser = new LoggedUser();
        List<LoggedUser> loggedUsers = LoggedUser.listAll(LoggedUser.class);
        if (loggedUsers.size() > 0) {
            loggedUser = loggedUsers.get(0);
        }
        return loggedUser;
    }
}
