package com.example.rutgerscafe;


import java.text.DecimalFormat;

/**
 * This class holds menuitems and other information relevant for an order and implements Customizable
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class Order implements Customizable{
    private MenuItem[] items;
    private int numItems;
    private int[] quantity;
    private int orderID;
    private double totalCost;

    public Order(MenuItem[] items, int numItems, int[] quantity, int orderID){
        this.numItems = numItems;
        this.items = items;
        for(int i = 0; i < items.length; i++){
            this.items[i] = null;
        }
        this.quantity = quantity;
        for(int i = 0; i < quantity.length; i++){
            quantity[i] = 0;
        }
        this.orderID = orderID;
    }

    public Order(int orderID){
        this.orderID = orderID;
    }

    public Order(){
        this.items = new MenuItem[4];
        this.numItems = 0;
        this.quantity = new int[4];
    }

    /**
     * Adds an object to this order
     * @param obj
     * @return
     */
    @Override
    public boolean add(Object obj){
        MenuItem orderItem = (MenuItem) obj;
        int index = find(orderItem);
        if(this.items.length == numItems){
            grow();
        }
        if(index== -1){
            for(int i = 0; i < this.items.length; i++){
                if(this.items[i]==null){
                    this.items[i] = orderItem;
                    this.quantity[i] = 1;
                    numItems++;
                    return true;
                }
            }
            return false;
        }
        else{
            quantity[index]++;
            numItems++;
            return true;
        }
    }

    /**
     * removes an object from this order if it exists
     * @param obj
     * @return
     */
    @Override
    public boolean remove(Object obj){
        MenuItem orderItem = (MenuItem) obj;
        int index = find(orderItem);
        if(index== -1){
            return false;
        }
        else{
            this.quantity[index]--;
            if(this.quantity[index]<1){
                items[index] = null;
            }
            this.numItems--;
            if(!isEmpty()){
                MenuItem[] newArray = new MenuItem[this.items.length];
                int pointer = 0;
                for(int i = 0; i < this.items.length;i++){
                    if(this.items[i]!=null){
                        newArray[pointer] = this.items[i];
                    }
                }
                this.items = newArray;
            }

            return true;
        }
    }

    /**
     * sets the total cost equal to some input
     * @param amount
     */
    public void setTotalCost(double amount){
        this.totalCost = amount;
    }

    /**
     * returns the total cost of this order
     * @return
     */
    public double getTotalCost(){
        return this.totalCost;
    }

    /**
     * returns a string containing the information stored in this object
     * @return
     */
    public String printOrder(){
        String order = "OrderID:" + this.orderID;
        for(int i = 0; i<this.items.length;i++){
            if(this.items[i]==null) {
                continue;
            }
            double price = this.items[i].itemPrice() * this.quantity[i];
            DecimalFormat df = new DecimalFormat("###.##");
            order = order + this.items[i].toString() + " | Quantity: " + this.quantity[i] + " ....... $"+df.format(price)+"\n";
        }
        return order;
    }

    /**
     * removes the data from this order and increments the order counter in main
     */
    public void finishOrder(){
        for(int i = 0; i < this.items.length; i++){
            this.items[i] = null;
        }
        for(int i = 0; i < this.quantity.length; i++){
            this.quantity[i] = 0;
        }
        MainActivity.setOrderId(MainActivity.getOrderID()+1);
    }

    /**
     * returns the orderID of this order
     * @return
     */
    public int getOrderID(){
        return this.orderID;
    }

    /**
     * returns the items stored
     * @return
     */
    public MenuItem[] getItems(){
        return this.items;
    }

    /**
     * returns the quantity array of items stored
     * @return
     */
    public int[] getQuantity(){
        return this.quantity;
    }


    /**
     * checks if another order is equal to this one
     * @param order
     * @return
     */
    public boolean compareOrders(Order order){
        if(this.orderID==order.getOrderID()){
            return true;
        }
        return false;
    }

    /**
     * increases the size of the array of items
     */
    private void grow() {
        MenuItem [] newArray = new MenuItem[this.items.length+4];
        for(int i = 0; i < this.items.length; i++){
            newArray[i] = this.items[i];
        }
        this.items = newArray;

        int [] newArray2 = new int[this.quantity.length+4];
        for(int i = 0; i < this.quantity.length; i++){
            newArray2[i] = this.quantity[i];
        }
        this.quantity = newArray2;
    }

    /**
     * finds a menuitem stored in this object and returns its location if it exists
     * @param orderItem
     * @return
     */
    private int find(MenuItem orderItem){
        if(this.items==null){
            return -1;
        }
        for(int i = 0; i < this.items.length; i++){
            if(this.items[i]==null){
                continue;
            }
            if(this.items[i].equals(orderItem)){
                return i;
            }
        }
        return -1;
    }

    /**
     * returns true if items array is empty
     * @param
     * @return
     */
    private boolean isEmpty(){
        if(this.numItems == 0){
            return true;
        }
        return false;
    }

    /**
     * set orderID
     * @param
     * @return
     */
    public void setOrderID(int orderID){
        this.orderID = orderID;
    }
}

