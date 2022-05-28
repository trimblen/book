package com.bookmapsolution;

public class BookRecord {
    private int 	bPrice;
    private int 	bSize;

    public BookRecord (int price, int size) {
        this.bPrice = price;
        this.bSize = size;
    };

    public int getPrice() {
        return bPrice;
    };

    public int getBSize() {
        return bSize;
    };

    public void setbPrice(int bPrice) {
        this.bPrice = bPrice;
    };

    public void setbSize(int bSize) {
        this.bSize = bSize;
    };
}
