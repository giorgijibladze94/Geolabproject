package com.example.geolabedu.autoservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geolabedu.autoservice.DetailsActivity;
import com.example.geolabedu.autoservice.R;
import com.example.geolabedu.autoservice.database.VehicleData;

import java.util.List;

/**
 * Created by GeoLabOwl on 06.08.15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.VersionViewHolder>{

    private List<VehicleData> versionModels;
    private Context context;

    public MyRecyclerAdapter(Context context) {
        this.context = context;
    }

    public MyRecyclerAdapter(Context context, List<VehicleData> versionModels) {
        this.context = context;
        this.versionModels = versionModels;
    }


    //http://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, final int i) {
        VehicleData vehicleData=versionModels.get(i);
        versionViewHolder.date.setText(vehicleData.getCalendar());
        versionViewHolder.image.setImageURI(Uri.parse(vehicleData.getImage2()));

        versionViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Click Click - " + i, Toast.LENGTH_LONG).show();
                Intent intent=new Intent(context,DetailsActivity.class);
                intent.putExtra("data", versionModels.get(i));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return versionModels == null ? 0 : versionModels.size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        ImageView image;
        CardView cardView;


        public VersionViewHolder(View itemView) {
            super(itemView);

            Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/Helvetica-Bold.otf");

            image= (ImageView) itemView.findViewById(R.id.imageView2);
            date = (TextView) itemView.findViewById(R.id.card_calendar);

            date.setTypeface(typeface);

            cardView = (CardView) itemView.findViewById(R.id.cardlist_item);

        }
    }
}
