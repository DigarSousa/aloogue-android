package service.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import service.ConstantsService;

public class ConnectionManeger {
    private String url;
    private HttpURLConnection connection;
    private String method;

    public ConnectionManeger(String url) {
        this.url = url;
    }

    public ConnectionManeger(String url, String method) {
        this.url = url;
        this.method = method;
    }

    private HttpURLConnection getConnection() throws IOException {
        if (connection != null) {
            return connection;
        }

        connection = (HttpURLConnection) new URL(url).openConnection();
        if (method != null) {
            connection.setDoOutput(Boolean.TRUE);
            connection.setRequestProperty(ConstantsService.CONTENT, ConstantsService.JSON);
            connection.setRequestMethod(method);
        }
        return connection;
    }

    public void connect() throws IOException {
        getConnection();
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
