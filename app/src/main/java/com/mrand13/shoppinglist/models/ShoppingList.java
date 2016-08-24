package com.mrand13.shoppinglist.models;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
    private List<Item> mItems;
    private String mCategory;

    public ShoppingList(){
        this.mItems = new ArrayList<>();
    }

    public ShoppingList(final List<Item> items){
        this.mItems = new ArrayList<>();
        this.mItems.addAll(items);
        this.mCategory = mItems.get(0).getCategory();
    }

    public void addItem(Item item){
        this.mItems.add(item);
    }

    public void addItems(final List<Item> items){
        for(Item i: items)
            this.mItems.add(i);
    }

    public int size(){
        return this.mItems.size();
    }

    public void clear(){
        this.mItems.clear();
    }

    public List<Item> getItems(){
        return this.mItems;
    }

    public double getTotal(){
        double total = 0.0;
        for(Item i: mItems)
            total += i.getPrice();
        return total;
    }

    public String getCategory(){
        return this.mCategory;
    }
}