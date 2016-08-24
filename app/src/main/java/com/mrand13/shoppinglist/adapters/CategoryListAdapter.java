package com.mrand13.shoppinglist.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mrand13.shoppinglist.R;
import com.mrand13.shoppinglist.models.Item;
import com.mrand13.shoppinglist.models.ShoppingList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //Callback Listeners
    public interface CategoryChangeListener{
        void onCartUpdate(double totalPrice, int totalItems);
    }
    public interface ShoppingListListener{
        void onShoppingListItemClick(int position, Item item);
        void onShoppingCartItemClick(int position, Item item);
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
        public EmptyViewHolder(View v){super(v);}
    }
    //View holder for the shopping list adapter, each card is a category
    public static class ViewHolderList extends RecyclerView.ViewHolder{

        public TextView header, subtotal;
        public ListView items_list;
        public ShoppingListAdapter shoppingListAdapter;
        public CardView cardView;
        //Hack for hashmap manipulation
        public String category;

        public ViewHolderList(View itemView, ShoppingList shoppingList){
            super(itemView);

            header = (TextView)itemView.findViewById(R.id.shopping_list_category_label);
            subtotal = (TextView)itemView.findViewById(R.id.shopping_list_category_subtotal);
            items_list = (ListView)itemView.findViewById(R.id.shopping_list_category_item_list);
            cardView = (CardView)itemView.findViewById(R.id.shopping_list_category_card);

            //Init the list
            shoppingListAdapter = new ShoppingListAdapter(itemView.getContext(),shoppingList.getItems());
            items_list.setAdapter(shoppingListAdapter);
            items_list.setItemsCanFocus(true);
            this.category = shoppingList.getCategory();
            this.header.setText(this.category);
            notifyUpdateViews();

        }

        public void bind(final ShoppingListListener listener, final int parent_position){
            items_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = (Item) items_list.getItemAtPosition(position);
                    if (item == null)
                        Log.d("CategoryAdapter", "NULL ITEM CLICK");
                    else
                        Log.d("CategoryAdapter", "ItemClick: " + parent_position + " Item name: " + item.getName());
                    shoppingListAdapter.remove(item);
                    listener.onShoppingListItemClick(parent_position, item);
                }
            });
        }

        public void addItem(Item item){
            shoppingListAdapter.add(item);
            notifyUpdateViews();
        }

        private void notifyUpdateViews(){
            this.subtotal.setText("$" + String.format("%.2f",shoppingListAdapter.getTotal()));
        }
    }
    //View holder for the shopping cart adapter, only one card, displayed at the end of the list
    public static class ViewHolderCart extends RecyclerView.ViewHolder{
        public TextView total, quantity;
        public ListView cart_items_list;
        public ShoppingListAdapter shoppingListAdapter;
        public CardView cardView;

        public ViewHolderCart(View itemView, List<Item> items){
            super(itemView);
            cart_items_list = (ListView)itemView.findViewById(R.id.shopping_list_cart_item_list);

            total = (TextView)itemView.findViewById(R.id.shopping_list_cart_total);
            quantity = (TextView)itemView.findViewById(R.id.shopping_list_cart_quantity);
            cardView = (CardView)itemView.findViewById(R.id.shopping_list_cart_card);

            total.setText("CART");
            //Init the list
            shoppingListAdapter = new ShoppingListAdapter(itemView.getContext(),items);
            cart_items_list.setAdapter(shoppingListAdapter);
        }

        public void bind(final ShoppingListListener listener){
            cart_items_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Item item = (Item)cart_items_list.getItemAtPosition(position);
                    if(item == null)
                        Log.d("CategoryAdapter", "NULL ITEM CLICK");
                    shoppingListAdapter.remove(item);
                    listener.onShoppingCartItemClick(position, item);
                }
            });
        }

        public void addItem(Item item){
            shoppingListAdapter.add(item);
        }
    }

    //Class members
    private List<ShoppingList> mShoppingList;
    private List<Item> mCartList;
    private int mShoppingListIterator;
    private Context mContext;
    private CategoryChangeListener mCategoryChangeListener;
    private ShoppingListListener mShoppingListListener;
    private final int ITEM_TYPE_LIST = 0, ITEM_TYPE_CART = 1;

    public CategoryListAdapter(Context context, List<ShoppingList> shoppingList, CategoryChangeListener categoryChangeListener, ShoppingListListener shoppingListListener){
        this.mContext = context;
        this.mShoppingListIterator = 0;
        this.mShoppingList = new ArrayList<>();
        this.mShoppingList.addAll(shoppingList);
        this.mCartList = new ArrayList<>();

        this.mCategoryChangeListener = categoryChangeListener;
        this.mShoppingListListener = shoppingListListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if(viewType == ITEM_TYPE_LIST) {
            View view = inflater.inflate(R.layout.shopping_list_category_card, parent, false);
            if (mShoppingListIterator < mShoppingList.size()) {
                CategoryListAdapter.ViewHolderList vh = new CategoryListAdapter.ViewHolderList(view, mShoppingList.get(mShoppingListIterator));
                mShoppingListIterator++;
                return vh;
            } else {
                RecyclerView.ViewHolder vh = new CategoryListAdapter.EmptyViewHolder(view);
                return vh;
            }
        }
        else if(viewType == ITEM_TYPE_CART){
            Log.d("CategoryAdapter","Created Cart");
            View view = inflater.inflate(R.layout.shopping_list_cart,parent,false);
            CategoryListAdapter.ViewHolderCart vh = new CategoryListAdapter.ViewHolderCart(view,mCartList);
            mShoppingListIterator++;
            return vh;
        }
        else{
            View view = inflater.inflate(R.layout.shopping_list_category_card,parent,false);
            RecyclerView.ViewHolder vh = new CategoryListAdapter.EmptyViewHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder,int position){
        if(viewHolder instanceof CategoryListAdapter.ViewHolderList) {
            //Item.Category category = mCategories.get(position);
            CategoryListAdapter.ViewHolderList vh = (CategoryListAdapter.ViewHolderList)viewHolder;
            vh.bind(mShoppingListListener, position);
        }
        else if(viewHolder instanceof CategoryListAdapter.ViewHolderCart){
            CategoryListAdapter.ViewHolderCart vh = (CategoryListAdapter.ViewHolderCart)viewHolder;
            vh.bind(mShoppingListListener);
        }
    }

    @Override
    public int getItemCount(){
        return mShoppingList.size();
    }

    @Override
    public int getItemViewType(int position){
        //May need a counter which is incremented in onCreateViewHolder
        //Log.d("CategoryAdapter", "Position: " + position + " Shop Size: " + mShoppingList.size());
        if(position == mShoppingList.size()-1)
            return ITEM_TYPE_CART;
        else return ITEM_TYPE_LIST;
    }

    public void sendToCart(){

    }
}
