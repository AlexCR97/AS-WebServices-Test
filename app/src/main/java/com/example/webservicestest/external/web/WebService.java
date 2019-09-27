package com.example.webservicestest.external.web;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;

public abstract class WebService<T> {

    public interface RequestAcceptedListener<T> {
        void onRequestAccepted(T response);
    }

    public interface RequestRejectedListener {
        void onRequestRejected();
    }

    private Context context;
    protected RequestAcceptedListener<T> requestAcceptedListener;
    protected RequestRejectedListener requestRejectedListener;

    protected final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
    );

    protected WebService(Context context, RequestAcceptedListener<T> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        this.context = context;
        this.requestAcceptedListener = requestAcceptedListener;
        this.requestRejectedListener = requestRejectedListener;
    }

    protected abstract String defineUrl(Object... params);

    protected abstract Response.Listener defineResponseListener();

    protected abstract Response.ErrorListener defineErrorListener();

    protected abstract Request defineRequest(String url, Object... params);

    public void sendRequest(Object... params) {
        String url = defineUrl(params);
        url = url.replace(" ", "%20");
        Request request = defineRequest(url, params);
        VolleySingleton.getInstance(context).addToRequestQueue(request);
    }

}
