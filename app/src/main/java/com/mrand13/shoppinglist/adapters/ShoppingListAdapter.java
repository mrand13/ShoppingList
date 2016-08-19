package com.mrand13.shoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mrand13.shoppinglist.R;
import com.mrand13.shoppinglist.models.Item;

import java.util.List;


public class ShoppingListAdapter extends ArrayAdapter<Item> {
    public ShoppingListAdapter(Context context, List<Item> items){
        super(context,0,items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_list_item,parent,false);
        }
        //Initialize the views
        TextView label = (TextView)convertView.findViewById(R.id.shopping_list_item_label);
        TextView price = (TextView)convertView.findViewById(R.id.shopping_list_item_price);
        TextView quantity = (TextView)convertView.findViewById(R.id.shopping_list_item_quantity);

        label.setText(item.getName());
        price.setText(item.getPriceToString());
        quantity.setText("" + item.getQuantity());
        return convertView;
    }
}
