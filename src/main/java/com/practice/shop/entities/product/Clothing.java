package com.practice.shop.entities.product;

public class Clothing extends Product {

    private String size;
    private String material;

    public Clothing(int id, String name, float price, String size, String material) {
        super(id, name, price);
        this.size = size;
        this.material = material;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
