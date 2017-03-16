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
import com.example.asuspc.ordeneaqui.models.Restaurante;

import java.util.List;

/**
 * Created by Asus PC on 13/3/17.
 */

public class RestaurantesAdapter  extends RecyclerView.Adapter<RestaurantesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Restaurante> restauranteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurant, location;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            restaurant = (TextView) view.findViewById(R.id.restaurant);
            location = (TextView) view.findViewById(R.id.location);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    public RestaurantesAdapter(Context mContext, List<Restaurante> restauranteList) {
        this.mContext = mContext;
        this.restauranteList = restauranteList;
    }

    @Override
    public RestaurantesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurante_card, parent, false);

        return new RestaurantesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RestaurantesAdapter.MyViewHolder holder, int position) {
        Restaurante restaurante = restauranteList.get(position);
        holder.restaurant.setText(restaurante.getName());
        holder.location.setText(restaurante.getLocation());

        // loading album cover using Glide library
        Glide.with(mContext).load(restaurante.getThumbnail()).into(holder.thumbnail);

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
        popup.setOnMenuItemClickListener(new RestaurantesAdapter.MyMenuItemClickListener());
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
        return restauranteList.size();
    }
}
