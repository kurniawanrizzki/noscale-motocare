package com.noscale.noscale_motocare.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.GarageController;
import com.noscale.noscale_motocare.models.Garage;
import com.noscale.noscale_motocare.utils.Global;
import com.noscale.noscale_motocare.utils.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurniawanrrizki on 18/12/17.
 */

public class GarageFrameAdapter extends RecyclerView.Adapter<GarageFrameAdapter.GarageViewHolder> {

    private List<Garage> garageList;
    private GarageController controller;

    public GarageFrameAdapter (GarageController controller) {
        this.controller = controller;
        garageList = new ArrayList<>();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public GarageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_garage_layout, parent, false);
        GarageViewHolder viewHolder = new GarageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GarageViewHolder holder, int position) {

        Garage item = garageList.get(position);
        holder.garageName.setText(item.getName());
        holder.garageAddress.setText(item.getAddress());
        holder.garageDescription.setText(item.getDescription());

        if (item.getPhoto().equals(Global.DEFAULT_STRING_VALUE)) {
            holder.garagePhoto.setImageResource(R.drawable.no_image);
        } else {
            controller.loadImage(item.getPhoto(),holder.garagePhoto);
        }

    }

    @Override
    public int getItemCount() {
        return garageList.size();
    }

    public List<Garage> getGarageList () {
        return garageList;
    }

    public void setGarageList (List<Garage> garageList) {
        this.garageList = garageList;
    }

    public class GarageViewHolder extends RecyclerView.ViewHolder {

        private CardView garageWrapper;
        private TextView garageName;
        private TextView garageAddress;
        private TextView garageDescription;
        private NetworkImageView garagePhoto;

        public GarageViewHolder(View itemView) {
            super(itemView);

            garageWrapper = (CardView) itemView.findViewById(R.id.garage_wrapper);
            garageName = (TextView) itemView.findViewById(R.id.garage_name);
            garageAddress = (TextView) itemView.findViewById(R.id.garage_address);
            garageDescription = (TextView) itemView.findViewById(R.id.garage_description);
            garagePhoto = (NetworkImageView) itemView.findViewById(R.id.garage_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Garage item = garageList.get(position);
                    controller.onItemRequestService(item.getId());
                }
            });

        }
    }

}
