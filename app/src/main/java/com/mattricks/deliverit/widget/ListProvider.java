package com.mattricks.deliverit.widget;

import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mattricks.deliverit.R;
import com.mattricks.deliverit.adapters.ListItem;
import com.mattricks.deliverit.utilities.DataProvider;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private ArrayList<ListItem> listItemList = new ArrayList<>();
    private Context context = null;
    Cursor cursor;

    public ListProvider(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        populateListItem();
    }

    private void populateListItem() {
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ListItem listItem = new ListItem();
            int name_row = cursor.getColumnIndex("name");
            int distributorName_row = cursor.getColumnIndex(DataProvider.distributorName);
            int distributorPrice_row = cursor.getColumnIndex(DataProvider.distributorPrice);
            listItem.setProductName(cursor.getString(name_row));
            listItem.setDistributorName(cursor.getString(distributorName_row));
            listItem.setDistributorPrice(cursor.getString(distributorPrice_row));
            listItemList.add(listItem);
        }
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_row);
        ListItem listItem = listItemList.get(position);

        remoteView.setTextViewText(R.id.tvWidgetProductName, listItem.getProductName());
        remoteView.setTextViewText(R.id.tvWidgetDistributorName, listItem.getDistributorName());
        remoteView.setTextViewText(R.id.tvWidgetDistributorPrice, listItem.getDistributorPrice());

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {

    }

}
