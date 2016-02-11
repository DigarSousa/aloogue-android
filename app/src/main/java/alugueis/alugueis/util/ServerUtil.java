package alugueis.alugueis.util;

import android.util.Pair;

import com.orm.dsl.NotNull;

public class ServerUtil {
    public static String buildUrl(@NotNull String url, Pair... pairs) {
        StringBuilder urlBuilder = new StringBuilder();
        String params = "";
        urlBuilder.append(url);
        urlBuilder.append("?");

        for (Pair pair : pairs) {
            if (!params.isEmpty()) {
                params += "&";
            }
            params += pair.first + "=" + pair.second;
        }
        return urlBuilder.append(params).toString();
    }
}
