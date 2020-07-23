package com.jonbott.knownspies.ModelLayer.Network;

import android.util.Log;

import com.jonbott.knownspies.Helpers.Threading;

import java.io.IOException;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by martin on 7/22/20.
 */

public class NetworkLayerImpl implements NetworkLayer {

    private static final String TAG = "NetworkLayer";

    //region Network Methods

    @Override
    public void loadJson(Consumer<String> finished) {
        Log.d(TAG, "loading json from web");

        Threading.async(() -> makeRequest(), finished, null);
    }

    private String makeRequest() {
        String result = "";
        try {
            result = run("http://localhost:8080/");

            //fake server delay
            Thread.sleep(2000);

        } catch (Exception e) {
            Log.d(TAG, "makeWebCall: Failed!");
            e.printStackTrace();
        }

        return result;
    }

    private String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //endregion

}
