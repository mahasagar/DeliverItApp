package com.mattricks.deliverit.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.mattricks.deliverit.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahasagar on 10/1/17.
 */
public class SampleLoader extends AsyncTaskLoader<List<Product>> implements LoaderManager.LoaderCallbacks<Cursor> {
    private List<Product> mData;
    Context cont;
    CursorLoader cursorLoader;

    public SampleLoader(Context ctx) {
        super(ctx);
        this.cont = ctx;
    }

    @Override
    public List<Product> loadInBackground() {
        // This method is called on a background thread and should generate a
        // new set of data to be delivered back to the client.
        List<Product> data = new ArrayList<>();

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        cursorLoader = new CursorLoader(cont, Uri.parse(DataProvider.CONTENT_URI.toString()), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        StringBuilder res = new StringBuilder();
        while (!cursor.isAfterLast()) {
            res.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }

}