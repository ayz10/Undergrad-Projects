package com.example.rutgerscafe;


/**
 * This class stores orders and has methods to manage that list of orders
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class StoreOrders implements Customizable{
    private Order[] allOrders;
    private int numOrders;

    public StoreOrders(Order[] allOrders, int numOrders){
        this.allOrders = allOrders;
        this.numOrders = numOrders;
    }

    /**
     * adds an object into the stored list of orders
     * @param obj
     * @return
     */
    @Override
    public boolean add(Object obj) {
        Order order = (Order) obj;
        if(this.allOrders.length==numOrders){
            grow();
        }
        for(int i=0;i<this.allOrders.length;i++){
            if(this.allOrders[i]!=null){
                continue;
            }
            else if(this.allOrders[i]==null){
                this.allOrders[i]=order;
                numOrders++;
                MainActivity.setOrderId(MainActivity.getOrderID()+1);
                return true;
            }
        }
        return false;
    }

    /**
     * removes an object from the stored list of orders
     * @param obj
     * @return
     */
    @Override
    public boolean remove(Object obj) {
        Order order = (Order) obj;
        int index = find(order);
        if(index==-1){
            return false;
        }
        else{
            this.numOrders--;
            this.allOrders[index]=null;

            Order[] temp = new Order[numOrders];
            int pointer = 0;
            for(int i = 0; i < this.allOrders.length; i++){
                if(this.allOrders[i] != null){
                    temp[pointer] = this.allOrders[i];
                    pointer++;
                }
            }
            this.allOrders = temp;
            return true;
        }
    }


    /**
     * converts the information stored within into a string and returns it
     * @return
     */
    public String storeOrderToString(){
        String storeOrders = "";
        for(int i=0; i<this.allOrders.length;i++){
            if(allOrders[i] != null){
                storeOrders = storeOrders + this.allOrders[i].printOrder() + "\n";
            }
        }
        return storeOrders;
    }

    /**
     * returns the list of the orders stored
     * @return
     */
    public Order[] getAllOrders(){
        return this.allOrders;
    }

    /**
     * increases the size of the list of orders
     */
    private void grow() {
        Order [] newArray = new Order[this.allOrders.length+4];
        for(int i = 0; i < this.allOrders.length; i++){
            newArray[i] = this.allOrders[i];
        }
        this.allOrders = newArray;
    }

    /**
     * searches for an order and returns the index of it if it exists
     * @param order
     * @return
     */
    private int find(Order order){
        for(int i = 0; i < this.allOrders.length; i++){
            if(this.allOrders[i]!= null && this.allOrders[i].compareOrders(order)){
                return i;
            }
        }
        return -1;
    }

    /**
     * returns true if allOrders array is empty
     * @param
     * @return
     */
    public boolean isEmpty(){
        return numOrders == 0;
    }
}

