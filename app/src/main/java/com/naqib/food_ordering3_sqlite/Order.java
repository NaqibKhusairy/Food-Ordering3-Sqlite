package com.naqib.food_ordering3_sqlite;
public class Order {
    int id;
    int burger,fries,chicken, hotdog,cola ;
    double total;
    String joiningDate;
    public Order(int id, int burger,int fries,int chicken, int hotdog,int
            cola,double total, String joiningDate) {
        this.id = id;
        this.burger = burger;
        this.fries = fries;
        this.chicken = chicken;
        this.hotdog = hotdog;
        this.cola = cola;
        this.total = total;
        this.joiningDate = joiningDate;
    }

    public int getBurger() {
        return burger;
    }
    public int getFries() {
        return fries;
    }
    public int getChicken() {
        return chicken;
    }
    public int getHotdog() {
        return hotdog;
    }
    public int getCola() {
        return cola;
    }
    public double getTotal() {
        return total;
    }
    public String getJoiningDate() {
        return joiningDate;
    }
}