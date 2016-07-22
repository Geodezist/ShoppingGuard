package com.bpg.shoppingguard;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Created by pbakhmach on 22.06.2016.
 */
public class ProductElement {

    private static int productIDSequence = 0;
    public static final int ROUND_DIGITS = 2;

    private int productID;
    private BigDecimal cost;
    private BigDecimal count;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int setProductID) {
        productID = setProductID;
    }

    /*Product cost methods*/
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getCost() {
        return cost.setScale(ROUND_DIGITS, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getCostString() {
        return String.format(Locale.getDefault(), "%.2f", this.getCost());
    }

    /*Product count methods*/
    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getCount() {
        return count.setScale(ROUND_DIGITS, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getCountString() {
        return String.format(Locale.getDefault(), "%.2f", this.getCount());
    }

    /*Product value methods*/
    public BigDecimal getValue() {
        return cost.multiply(count).setScale(ROUND_DIGITS, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getValueString() {
        return String.format(Locale.getDefault(), "%.2f", this.getValue());
    }

    //public ProductCost() {
    //    productID = getProductIDSequence();
    //}

    public ProductElement(BigDecimal cost) {
        //productID = getProductIDSequence();
        setCost(cost);
        setCount(BigDecimal.ONE);
    }

    public ProductElement(BigDecimal cost, BigDecimal count) {
        //productID = getProductIDSequence();
        setCost(cost);
        setCount(count);
    }

    private int getProductIDSequence() {
        int result = productIDSequence;
        productIDSequence = productIDSequence + 1;
        return result;
    }

    @Override
    public String toString() {
        return "ProductCost{" +
                "productID=" + productID +
                ", cost=" + cost +
                ", count=" + count +
                '}';
    }
}
