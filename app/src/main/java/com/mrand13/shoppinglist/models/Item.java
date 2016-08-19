package com.mrand13.shoppinglist.models;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Item {
    //Set up categories
    //----------------------------
    @StringDef({Category.MISC,Category.FRUIT,Category.PRODUCE,Category.DAIRY,Category.MEAT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Category{
        String MISC = "Misc";
        String FRUIT = "Fruit";
        String PRODUCE = "Produce";
        String DAIRY = "Dairy";
        String MEAT = "Meat";
    }
    //----------------------------

    //Class members
    private String mName;
    private double mPrice;
    private int mQuantity;
    @Category
    private String mCategory;

    //Constructors
    //-----------------------------
    public Item(){
        this.mName = "";
        this.mPrice = 0.0;
        this.mQuantity = 0;
        this.mCategory = Category.MISC;
    }

    public Item(String name, double price, int quantity, @Category String category){
        this.mName = name;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mCategory = category;
    }

    public Item(String name, double price){
        this(name,price,1,Category.MISC);
    }

    //Mutators
    //-----------------------------
    public void setName(String name){
        this.mName = name;
    }
    public void setPrice(double price){
        this.mPrice = price;
    }
    public void setQuantity(int quantity){
        this.mQuantity = quantity;
    }
    public void setCategory(@Category String category){
        this.mCategory = category;
    }

    public void addQuantity(){
        this.mQuantity += 1;
    }
    public void addQuantity(int quantity){
        this.mQuantity += quantity;
    }
    public void addPrice(double price){
        this.mPrice += price;
    }

    //Accessors
    //----------------------------
    public String getName(){
        return mName;
    }

    public double getPrice(){
        return mPrice;
    }

    public int getQuantity(){
        return mQuantity;
    }

    @Item.Category
    public String getCategory(){
        return mCategory;
    }

    //To String Helpers
    //----------------------------
    public String getPriceToString(){
        return String.format("%.2f",mPrice);
    }
}
