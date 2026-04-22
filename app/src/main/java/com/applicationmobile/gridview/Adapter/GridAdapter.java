package com.applicationmobile.gridview.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.applicationmobile.gridview.R;

public class GridAdapter extends BaseAdapter{

    private static final String TAG = "GridAdapter";
    public Integer[] thumbnail = {
            R.drawable.contact,
            R.drawable.email,
            R.drawable.gallery,
            R.drawable.home,
            R.drawable.logout,
            R.drawable.news,
            R.drawable.password,
            R.drawable.username,
            R.drawable.website
    };
    private Context mContext;

    public GridAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return thumbnail.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8, 8, 8 , 8);
        imageView.setImageResource(thumbnail[position]);
        return imageView;
    }
}
