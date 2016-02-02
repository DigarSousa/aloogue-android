package alugueis.alugueis.util;

import android.util.Pair;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import service.ConstantsService;

public class ServerConnection {
    private String url;
    private HttpURLConnection connection;
    private Boolean doIn;
    private Boolean doOut;
    private String method;

    public ServerConnection(String url) {
        this.url = url;
    }

    public ServerConnection(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public ServerConnection(String url, Boolean doOut, Boolean doIn) {
        this.url = url;
        this.doOut = doOut;
        this.doIn = doIn;
    }

    public ServerConnection(String url, String method, Boolean doOut, Boolean doIn) {
        this.url = url;
        this.method = method;
        this.doOut = doOut;
        this.doIn = doIn;
    }

    private HttpURLConnection getConnection() throws IOException {
        if (connection != null) {
            return connection;
        }

        connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setDoInput(doIn == null ? true : doIn);
        connection.setDoOutput(doOut == null ? true : doOut);
        connection.setRequestProperty(ConstantsService.CONTENT, ConstantsService.JSON);
        connection.setRequestMethod(method == null ? ConstantsService.GET : method);
        return connection;
    }

    public InputStreamReader getReader() throws IOException {
        return new InputStreamReader(getConnection().getInputStream());
    }

    public OutputStreamWriter getWriter() throws IOException {
        return new OutputStreamWriter(getConnection().getOutputStream());
    }

    public String getResponse() throws IOException {
        String line;
        BufferedReader reader;
        reader = new BufferedReader(getReader());
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


}
