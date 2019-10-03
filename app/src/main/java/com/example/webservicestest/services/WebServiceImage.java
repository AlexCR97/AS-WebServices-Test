package com.example.webservicestest.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.toolbox.ImageRequest;

public abstract class WebServiceImage extends WebService<Bitmap> {

    protected WebServiceImage(Context context, RequestAcceptedListener<Bitmap> requestAcceptedListener, RequestRejectedListener requestRejectedListener) {
        super(context, requestAcceptedListener, requestRejectedListener);
    }

    @Override
    protected Request defineRequest(String url, Object... params) {
        ImageRequest imageRequest = new ImageRequest(
                url,
                defineResponseListener(),
                0, 0,
                ImageView.ScaleType.CENTER,
                null,
                defineErrorListener()
        );
        imageRequest.setRetryPolicy(defaultRetryPolicy);

        return imageRequest;
    }
}
