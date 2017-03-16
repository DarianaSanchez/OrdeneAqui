package com.example.asuspc.ordeneaqui.adapters;

/**
 * Created by Asus PC on 14/3/17.
 */
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asuspc.ordeneaqui.R;
import com.example.asuspc.ordeneaqui.models.Item;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class ItemCartAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Item> items;

    public ItemCartAdapter(Activity activity, List<Item> items) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.items = items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = inflater.inflate(R.layout.carrito_item, null);

        Item producto = (Item) getItem(position);

        TextView txView;
        ImageView imgView;

        //Llamar los componentes y hacerles un SET Text

        txView = (TextView) vi.findViewById(R.id.name_product);
        txView.setText(producto.getName());

        txView = (TextView) vi.findViewById(R.id.descripcion);
        txView.setText(producto.getDescription());

        txView = (TextView) vi.findViewById(R.id.cantidad_producto);
        txView.setText("" + producto.getQuantity());

        txView = (TextView) vi.findViewById(R.id.precio_product);
        txView.setText("RD$ " + producto.getPrice());

        imgView = (ImageView) vi.findViewById(R.id.image_product);

        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(10)
                .oval(false)
                .build();


        Picasso
                .with(activity)
                .load(producto.getThumbnail())
                .resize(50, 50)
                .centerCrop()
                .transform(transformation)
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_manage)
                .into(imgView);

        return vi;
    }
}