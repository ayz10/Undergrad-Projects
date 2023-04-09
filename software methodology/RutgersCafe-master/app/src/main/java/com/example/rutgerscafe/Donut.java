package com.example.rutgerscafe;

/**
 *This class represents a donut and is a MenuItem
 * @author Vineel Reddy
 * @author Alexander Zhao
 */
public class Donut extends MenuItem{
    private String type;
    private String flavor;
    private int donutID;

    public Donut(String itemName, double price, String type, String flavor, int donutID) {
        super(itemName, price);
        this.type = type;
        this.flavor = flavor;
        this.donutID = donutID;
    }

    public Donut(int donutID){
        this.donutID = donutID;
    }

    public Donut(String type){
        this.type = type;
    }

    /**
     * Detemines the item price
     * @return
     */
    @Override
    public double itemPrice(){
        double price = 0;
        switch (this.type){
            case "Yeast":
                price = 1.59;
                break;
            case "Cake":
                price = 1.79;
                break;
            case "Hole":
                price = 0.30;
                break;
        }
        return price;
    }

    /**
     * returns the type of donut
     * @return
     */
    public String getType(){
        return this.type;
    }

    /**
     * returns the flavor of the donut
     * @return
     */
    public String getFlavor(){
        return this.flavor;
    }

    /**
     * determines if another menuitem is equal
     * @param menuItem
     * @return
     */
    @Override
    public boolean equals(MenuItem menuItem){
//        Donut donut = (Donut) menuItem;
//        if(this.donutID == donut.getDonutID()){
//            return true;
//        }
//        return false;

        if(menuItem instanceof Donut){
            return this.donutID == ((Donut) menuItem).donutID;
        }
        else{
            return false;
        }
    }

    /**
     * returns the donutID
     * @return
     */
    public int getDonutID(){
        return this.donutID;
    }

    /**
     * returns a string containing the data contained within
     * @return
     */
    @Override
    public String toString(){
        String order = "Donut, Type: ";
        switch(this.type){
            case "Yeast":
                order = order + "Yeast | ";
                break;
            case "Cake":
                order = order + "Cake | ";
                break;
            case "Hole":
                order = order + "Hole | ";
                break;
        }
        switch(this.flavor){
            case "Strawberry":
                order = order + "Flavor: Strawberry";
                break;
            case "Chocolate":
                order = order + "Flavor: Chocolate";
                break;
            case "Glazed":
                order = order + "Flavor: Glazed";
                break;
            case "Pumpkin":
                order = order + "Flavor: Pumpkin";
                break;
            case "Boston Creme":
                order = order + "Flavor: Boston Creme";
                break;
            case "Birthday Cake":
                order = order + "Flavor: Birthday Cake";
                break;
            case "Blueberry":
                order = order + "Flavor: Blueberry";
                break;
            case "Vanilla":
                order = order + "Flavor: Vanilla";
                break;
            case "Jelly":
                order = order + "Flavor: Jelly";
                break;
        }
        return order;
    }
}
