package com.example.geolabedu.autoservice.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.geolabedu.autoservice.R;

import java.util.ArrayList;

/**
 * Created by GeoLabOwl on 20.09.15.
 */
public class CustomViewPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> arrayList;
    LayoutInflater inflater;

    public CustomViewPagerAdapter(Context context, ArrayList list) {
        this.context = context;
        this.arrayList = list;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView;
        View itemView;

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView=inflater.inflate(R.layout.viewpager,container,false);

        imageView= (ImageView) itemView.findViewById(R.id.imagePager);
        imageView.setImageURI(Uri.parse(arrayList.get(position)));

        container.addView(itemView);

        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);

    }
}
