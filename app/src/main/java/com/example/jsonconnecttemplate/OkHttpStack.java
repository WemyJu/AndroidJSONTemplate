package com.example.jsonconnecttemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by mac on 15/6/27.
 */
public class OkHttpStack extends HurlStack {
    private final OkHttpClient client;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.client = client;
    }

    @Override protected HttpURLConnection createConnection(URL url) throws IOException {
        return client.open(url);
    }
}
