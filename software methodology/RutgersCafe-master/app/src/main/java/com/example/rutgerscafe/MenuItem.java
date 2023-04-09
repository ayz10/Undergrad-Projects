package com.example.rutgerscafe;

/**
 * Represents a menuitem and acts as an abstract class for coffee and donut
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class MenuItem {
    protected String itemName;
    protected double price;

    public MenuItem(String itemName, double price){
        this.itemName = itemName;
        this.price = price;
    }
    public MenuItem(){
        this.itemName = "";
        this.price = 0;
    }

    /**
     * gets the name of the menuitem
     * @return
     */
    public String getItemName(){
        return this.itemName;
    }

    /**
     * checks if another menuitem is equal to this one
     * @param menuItem
     * @return
     */
    public boolean equals(MenuItem menuItem){
        return false;
    }

    /**
     * outputs a string containing the data of this object
     * @return
     */
    @Override
    public String toString(){
        return "";
    }

    /**
     * returns the price of this object
     * @return
     */
    public double itemPrice() {
        return this.price;
    }
}
