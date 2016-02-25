package service.httputil;

import android.util.Pair;

import com.orm.dsl.NotNull;

import service.ConstantsService;

public class URLBuilder {
    private String url;

    public URLBuilder() {
        this.url = ConstantsService.URL;
    }

    public URLBuilder(@NotNull Class T) {
        this.url = ConstantsService.URL + "/" + T.getSimpleName().toLowerCase();
    }

    public URLBuilder append(@NotNull String path) {
        this.url += path;
        return this;
    }

    public URLBuilder putParams(Pair... params) {
        if (params!=null && params.length > 0) {

            StringBuilder builder = new StringBuilder();
            String getParams = "";
            builder.append(this.url);
            builder.append("?");

            for (Pair pair : params) {
                if (!getParams.isEmpty()) {
                    getParams += "&";
                }
                getParams += pair.first + "=" + pair.second;
            }
            builder.append(getParams);
            this.url = builder.toString();

        }
        return this;
    }

    public String build() {
        return this.url;
    }

}
