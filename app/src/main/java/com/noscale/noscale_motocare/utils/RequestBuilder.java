package com.noscale.noscale_motocare.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.noscale.noscale_motocare.utils.listeners.ResponseListener;
import java.util.Map;

/**
 * Created by kurniawanrizzki on 17/01/18.
 */

public class RequestBuilder {

    private static RequestBuilder instance;
    private RequestQueue queue;
    private ImageLoader loader;
    private ResponseListener jsonRespListener;

    public static RequestBuilder getInstance (Context context) {

        if (null == instance) {
            instance = new RequestBuilder(context);
        }

        return instance;
    }

    public RequestBuilder (Context context) {
        queue = Volley.newRequestQueue(context);
        jsonRespListener = new ResponseListener(context);
    }

    /**
     * Build request based on parameter that was given;
     * @param url please to have a note if the url has been providen by backend firstly;
     * @param method {@link com.android.volley.Request.Method#POST} ,{@link com.android.volley.Request.Method#GET}
     * @param params parameter that used for each method;
     */
    public void build (String url, int method, final Object params) {

        StringRequest request = new StringRequest(method, url, jsonRespListener, jsonRespListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return (Map<String, String>) params;
            }
        };

        queue.add(request);

    }

    public ImageLoader loadImage () {

        loader = new ImageLoader(queue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });

        return loader;

    }

}
