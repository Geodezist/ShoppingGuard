package com.bpg.shoppingguard;

import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by pbakhmach on 22.07.2016.
 */
public class Product {

    private ProductElement productElementEntity;
    private TableRow tableRowEntity;
    private TextView textViewCostEntity;
    private TextView textViewCountEntity;
    private TextView textViewValueEntity;
    private Button buttonDeleteProductEntity;
    private int productIndex;

    public Product(ProductElement productElement, TableRow tableRowProductElement, TextView textViewCost, TextView textViewCount, TextView textViewValue, Button buttonDeleteProduct) {
        productElementEntity = productElement;
        tableRowEntity = tableRowProductElement;
        textViewCostEntity = textViewCost;
        textViewCountEntity = textViewCount;
        textViewValueEntity = textViewValue;
        buttonDeleteProductEntity = buttonDeleteProduct;
    }

    public ProductElement getProductElementEntity() {
        return productElementEntity;
    }

    public TableRow getTableRowEntity() {
        return tableRowEntity;
    }

    public TextView getTextViewCostEntity() {
        return textViewCostEntity;
    }

    public TextView getTextViewCountEntity() {
        return textViewCountEntity;
    }

    public TextView getTextViewValueEntity() {
        return textViewValueEntity;
    }

    public Button getButtonDeleteProductEntity() {
        return buttonDeleteProductEntity;
    }

    public void setButtonDeleteProductEntityID(int productIndex) {
        buttonDeleteProductEntity.setId(productIndex);
    }

    public int getProductIndex() {
        return productIndex;
    }

    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
        this.buttonDeleteProductEntity.setId(productIndex);
    }
}
