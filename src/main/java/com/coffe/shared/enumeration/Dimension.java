package com.coffe.shared.enumeration;

public enum Dimension {
    SMALL(5, 1), MEDIUM(10, 2), LARGE(15, 3);
    private int additionalPrice;
    private int additionalQuantity;

    Dimension(int price, int quantity) {
        this.additionalPrice = price;
        this.additionalQuantity = quantity;
    }

    public int getAdditionalPrice() {
        return additionalPrice;
    }

    public void setAdditionalPrice(int additionalPrice) {
        this.additionalPrice = additionalPrice;
    }

    public int getAdditionalQuantity() {
        return additionalQuantity;
    }

    public void setAdditionalQuantity(int additionalQuantity) {
        this.additionalQuantity = additionalQuantity;
    }
}
