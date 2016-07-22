package com.bpg.shoppingguard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by pbakhmach on 21.07.2016.
 */
public class ProductsArray {

    private ArrayList<Product> arrayListOfProducts = new ArrayList<>();

    public ProductsArray() {
    }

    /*
    public ProductsArray(Product prod) {
        arrayListOfProducts.add(prod);
        prod.setProductIndex(arrayListOfProducts.size()-1);
    }
    */

    public void addProduct(Product prod) {
        arrayListOfProducts.add(prod);
        prod.setProductIndex(arrayListOfProducts.size() - 1);

    }

    private BigDecimal getSumValueOfProducts() {
        BigDecimal sumValueOfProducts = new BigDecimal(0);

        for (int i = 0; i < arrayListOfProducts.size(); i++) {
            sumValueOfProducts = sumValueOfProducts.add(arrayListOfProducts.get(i).getProductElementEntity().getValue());
        }
        return sumValueOfProducts.setScale(ProductElement.ROUND_DIGITS, BigDecimal.ROUND_HALF_EVEN);
    }

    public String getSumValueOfProductsString() {
        return String.format(Locale.getDefault(), "%.2f", this.getSumValueOfProducts());
    }

    public Product getProduct(int productIndex) {
        return arrayListOfProducts.get(productIndex);
    }

    public void deleteProductCost(int productCostIndex) {
        arrayListOfProducts.remove(productCostIndex);
        for (int i = 0; i < arrayListOfProducts.size(); i++) {
            arrayListOfProducts.get(i).setProductIndex(i);
        }
    }

}
