package com.mattricks.deliverit.utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.mattricks.deliverit.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahasagar on 10/1/17.
 */
public class SampleLoader extends AsyncTaskLoader<List<Product>> {
    private List<Product> mData;

    public SampleLoader(Context ctx) {
        super(ctx);
    }

    @Override
    public List<Product> loadInBackground() {
        // This method is called on a background thread and should generate a
        // new set of data to be delivered back to the client.
        List<Product> data = new ArrayList<Product>();

        // TODO: Perform the query here and add the results to 'data'.

        return data;
    }

    @Override
    public void deliverResult(List<Product> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        List<Product> oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    private void releaseResources(List<Product> data) {
        // should be released here.
    }

    private SampleLoader mObserver;
}