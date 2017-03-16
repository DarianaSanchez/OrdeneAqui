package com.example.asuspc.ordeneaqui.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asuspc.ordeneaqui.R;
import com.example.asuspc.ordeneaqui.models.Oferta;

import java.util.List;

/**
 * Created by Asus PC on 13/3/17.
 */

public class OfertasAdapter extends RecyclerView.Adapter<OfertasAdapter.MyViewHolder> {
    private Context mContext;
    private List<Oferta> ofertaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, restaurant;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            restaurant = (TextView) view.findViewById(R.id.restaurant);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public OfertasAdapter(Context mContext, List<Oferta> ofertaListList) {
        this.mContext = mContext;
        this.ofertaList = ofertaListList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.oferta_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Oferta oferta = ofertaList.get(position);
        holder.title.setText(oferta.getOfertaTitle());
        holder.restaurant.setText(oferta.getRestauranteName());

        // loading album cover using Glide library
        Glide.with(mContext).load(oferta.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_see_more:
                    Toast.makeText(mContext, "Ver más", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_add_favorite:
                    Toast.makeText(mContext, "Agregar a Favoritos", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return ofertaList.size();
    }
}
