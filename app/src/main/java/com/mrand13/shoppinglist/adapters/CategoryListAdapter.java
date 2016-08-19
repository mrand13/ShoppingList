package com.mrand13.shoppinglist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mrand13.shoppinglist.R;
import com.mrand13.shoppinglist.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View v){super(v);}
    }
    //View holder for the shopping list adapter, each card is a category
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView header, subtotal;
        public ListView items_list;

        //Hack for hashmap manipulation
        public String category;

        public ViewHolder(View itemView, List<Item> items){
            super(itemView);

            header = (TextView)itemView.findViewById(R.id.shopping_list_category_label);
            subtotal = (TextView)itemView.findViewById(R.id.shopping_list_category_subtotal);
            items_list = (ListView)itemView.findViewById(R.id.shopping_list_category_item_list);

            //Init the list
            ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(itemView.getContext(),items);
            items_list.setAdapter(shoppingListAdapter);
            this.category = items.get(0).getCategory();
        }
    }

    //Class members
    private Map<String,List<Item>> mShoppingList;
    private Iterator mIterator;
    private Context mContext;

    public CategoryListAdapter(Context context, Map<String,List<Item>> shoppingList){
        this.mContext = context;
        this.mShoppingList = new HashMap<>();
        this.mShoppingList.putAll(shoppingList);
        this.mIterator = mShoppingList.entrySet().iterator();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.shopping_list_category_card, parent, false);

        if(mIterator.hasNext()){
            Map.Entry pair = (Map.Entry)mIterator.next();
            mIterator.remove();
            CategoryListAdapter.ViewHolder vh = new CategoryListAdapter.ViewHolder(view,(List<Item>)pair.getValue());
            return vh;
        }
        else{
            RecyclerView.ViewHolder vh = new CategoryListAdapter.EmptyViewHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position){
        if(viewHolder instanceof CategoryListAdapter.ViewHolder) {
            //Item.Category category = mCategories.get(position);
            CategoryListAdapter.ViewHolder vh = (CategoryListAdapter.ViewHolder)viewHolder;
            vh.header.setText(vh.category);
        }
    }

    @Override
    public int getItemCount(){
        return mShoppingList.size();
    }
}
