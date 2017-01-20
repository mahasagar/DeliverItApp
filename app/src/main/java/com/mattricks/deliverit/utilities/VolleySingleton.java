package com.mattricks.deliverit.utilities;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton mVolleysingi = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton() {
        this.mRequestQueue = Volley.newRequestQueue(MyApplication.getInstanceContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> imgCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 1024 / 8));

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                // TODO Auto-generated method stub
                imgCache.put(url, bitmap);
            }

            @Override
            public Bitmap getBitmap(String url) {
                // TODO Auto-generated method stub
                return imgCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance() {

        if (mVolleysingi == null) {
            mVolleysingi = new VolleySingleton();
        }
        return mVolleysingi;
    }

    public RequestQueue getREquestQueue() {

        return mRequestQueue;

    }

    public ImageLoader getImageLoader() {
        return mImageLoader;

    }
}
