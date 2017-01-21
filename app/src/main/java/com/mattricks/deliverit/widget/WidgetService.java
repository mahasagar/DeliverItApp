package com.mattricks.deliverit.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViewsService;

import com.mattricks.deliverit.utilities.DataProvider;

public class WidgetService extends RemoteViewsService implements LoaderManager.LoaderCallbacks<Cursor>, Loader.OnLoadCompleteListener<Cursor> {
    private static final int LOADER_ID_NETWORK = 1;
    Cursor cursor;
    /*
         * So pretty simple just defining the Adapter of the listview
         * here Adapter is ListProvider
         * */
    CursorLoader mCursorLoader;

    @Override
    public void onCreate() {

        String[] s = new String[0];
        mCursorLoader = new CursorLoader(this.getApplicationContext(), DataProvider.CONTENT_URI, s, "", s, "");
        mCursorLoader.registerListener(LOADER_ID_NETWORK, this);
        mCursorLoader.startLoading();
        cursor = mCursorLoader.loadInBackground();

    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
        // Bind data to UI, etc
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.d("here1 in", intent.toString());
        return (new ListProvider(this.getApplicationContext(), cursor));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroy() {

        // Stop the cursor loader
        if (mCursorLoader != null) {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
    }

}
