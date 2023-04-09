package com.example.project4;

import java.util.ArrayList;
/**
 *This class represents a coffee and is a MenuItem
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class Coffee extends MenuItem implements Customizable{
    private int size; // 0 = short, 1 = tall, 2 = grande, 3 = venti.
    private String[] addIns = new String[5];
    private int coffeeID;
    public Coffee(String itemName, double price, int size, String[] addIns, int ID) {
        super(itemName, price);
        for(int i = 0; i < addIns.length; i++){
            this.addIns[i] = addIns[i];
        }
        this.size = size;
        coffeeID = ID;
    }
//comment
    public Coffee(int size, String[] addIns){
        this.size = size;
        this.addIns = addIns;
    }
    public Coffee(int ID){
        super();
        this.coffeeID = ID;
    }

    /***
     * Determines the price of the coffee
     * @return
     */
    @Override
    public double itemPrice(){
        double price = 0;
        switch(this.size){
            case 0:
                price = 1.69;
                break;
            case 1:
                price = 2.09;
                break;
            case 2:
                price = 2.49;
                break;
            case 3:
                price = 2.89;
                break;
        }
        double priceAddIn = 0.30;
        int numAddIns = numElementsInArray(this.addIns);
        price = price + (priceAddIn * numAddIns);
        return price;
    }

    @Override
    public boolean add(Object obj) {
        if(obj instanceof String){
            for(int i = 0; i < this.addIns.length; i++){
                if(this.addIns[i]==null){
                    this.addIns[i] = obj.toString();
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * removes an addin from the coffee
     * @param obj
     * @return
     */
    @Override
    public boolean remove(Object obj) {
        if(obj instanceof String){
            for(int i = 0; i < this.addIns.length; i++){
                if(this.addIns[i].equals(obj.toString())){
                    this.addIns[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * returns all of the addins
     * @return
     */
    public String[] getAddIns(){
        return this.addIns;
    }

    /***
     * returns the size of the addins
     * @return
     */
    public String getSize(){
        switch(this.size){
            case 0:
                return "Short";
            case 1:
                return "Tall";
            case 2:
                return "Grande";
            case 3:
                return "Venti";
            default:
                return "INVALID SIZE";
        }
    }

    /***
     * Outputs a string of the data contained
     * @return
     */
    @Override
    public String toString(){
        String order = "Coffee, size: ";
        switch (this.size){
            case 0:
                order = order + "Short |";
                break;
            case 1:
                order = order + "Tall |";
                break;
            case 2:
                order = order + "Grande |";
                break;
            case 3:
                order = order + "Venti |";
                break;
        }

        if(isEmpty()){
            return order;
        }
        order = order + " ADD-INS: ";
        for(int i = 0; i<this.addIns.length;i++){
            if(this.addIns[i]!=null){
                order = order + "/" + this.addIns[i];
            }
        }
        return super.toString() + order;
    }

    /***
     * returns the coffeeID
     * @return
     */
    public int getCoffeeID() {
        return coffeeID;
    }

    /**
     * Determines if another menuitem is equal to this one
     * @param menuItem
     * @return
     */
    @Override
    public boolean equals(MenuItem menuItem){
//        Coffee coffee = (Coffee) menuItem;
//        if(this.itemName.equals(coffee.getItemName())){
//            if(this.size==coffee.size){
//                for(int i =0; i < coffee.getAddIns().length;i++){
//                    if(!coffee.getAddIns()[i].equals(this.getAddIns()[i]))
//                        return false;
//                }
//            }
//
//        }
//        return true;
        if(menuItem instanceof Coffee){
            return this.coffeeID == ((Coffee) menuItem).coffeeID;
        }
        else{
            return false;
        }
    }

    /**
     * checks if the coffe has no addins
     * @return
     */
    private boolean isEmpty(){
        for(int i = 0; i<this.addIns.length;i++){
            if(this.addIns[i]==null) return false;
        }
        return true;
    }

    /**
     * returns the number of addins
     * @param array
     * @return
     */
    private int numElementsInArray(String[] array){
        int counter = 0;
        for(int i = 0; i<array.length;i++){
            if(array[i] == null) break;
            counter++;
        }
        return counter;
    }
}
