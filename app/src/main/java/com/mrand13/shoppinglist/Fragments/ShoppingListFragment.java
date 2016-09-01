package com.mrand13.shoppinglist.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mrand13.shoppinglist.R;
import com.mrand13.shoppinglist.adapters.CategoryListAdapter;
import com.mrand13.shoppinglist.models.Item;
import com.mrand13.shoppinglist.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment
        implements CategoryListAdapter.CategoryChangeListener, CategoryListAdapter.ShoppingListListener{
    public static final String TAG = "ShoppingListFragment";

    //Class members
    private View mRootView;
    private RecyclerView mRecycler;
    private CategoryListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView mCartTotalPrice, mCartTotalItems;


    public ShoppingListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        mRootView = inflater.inflate(R.layout.shopping_list_layout,container,false);

        //init recyclerview
        mRecycler = (RecyclerView)mRootView.findViewById(R.id.shopping_list_recycler);
        mRecycler.setHasFixedSize(true);

        //init cartview
        mCartTotalPrice = (TextView)mRootView.findViewById(R.id.shopping_list_cart_total);
        mCartTotalItems = (TextView)mRootView.findViewById(R.id.shopping_list_cart_quantity);

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);

        /*
        Map<String,List<Item>> testMap = new HashMap<>();
        List<Item> sub = new ArrayList<>();
        sub.add(new Item("Steak", 1.00, 1, Item.Category.MEAT));
        sub.add(new Item("Ribs", 3.00, 1, Item.Category.MEAT));
        sub.add(new Item("Chops", 2.00, 1, Item.Category.MEAT));
        testMap.put(Item.Category.MEAT, sub);
        sub.clear();
        sub.add(new Item("Apples", 1.00, 3, Item.Category.FRUIT));
        sub.add(new Item("Banana", 3.00, 1, Item.Category.FRUIT));
        testMap.put(Item.Category.FRUIT, sub);
        */

        List<ShoppingList> shoppingLists = new ArrayList<>();
        List<Item> sub = new ArrayList<>();
        sub.add(new Item("Steak", 1.00, 1, Item.Category.MEAT));
        sub.add(new Item("Ribs", 3.00, 1, Item.Category.MEAT));
        sub.add(new Item("Chops", 2.00, 1, Item.Category.MEAT));
        shoppingLists.add(new ShoppingList(sub));
        sub.clear();
        sub.add(new Item("Apples", 1.00, 3, Item.Category.FRUIT));
        sub.add(new Item("Banana", 3.00, 1, Item.Category.FRUIT));
        shoppingLists.add(new ShoppingList(sub));
        shoppingLists.add(new ShoppingList());

        mAdapter = new CategoryListAdapter(mRootView.getContext(),shoppingLists, this, this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    //Callback Listeners
    public void onCartUpdate(double totalPrice, int totalItems){
        mCartTotalPrice.setText("Cart Total: " + String.format("%.2f", totalPrice));
        mCartTotalItems.setText("Cart Items: " + totalItems);
    }

    public void onShoppingListItemClick(int position, Item item){
        Log.d("ShoppingFragment", "Positon: " + position + " AdapterCnt: " + mAdapter.getItemCount());
        ((CategoryListAdapter.ViewHolderCart)mRecycler.findViewHolderForAdapterPosition(mAdapter.getItemCount()-1)).addItem(item);
    }

    public void onShoppingCartItemClick(int position, Item item){
        //Loop through all the mAdapter view holders, up until the last element. Last element is always the Cart
        for(int i = 0; i<mAdapter.getItemCount()-1; i++){
            //Cast the viewholder to the ListViewholder
            CategoryListAdapter.ViewHolderList vh =((CategoryListAdapter.ViewHolderList) mRecycler.findViewHolderForAdapterPosition(i));
            if(vh != null) {
                //Compare categories and add item
                if (vh.category.equals(item.getCategory())) {
                    vh.addItem(item);
                    break;
                }
            }
            else Log.d("ShoppingFragment", "Null ViewHolder at position: " + i);
        }
    }
}
