package com.bpg.shoppingguard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    ViewGroup.LayoutParams lpView;
    TableLayout tableLayout;
    TextView textViewSumOfProducts;
    TextView textViewLimitValue;
    EditText editTextCost;
    EditText editTextCount;

    ProductsArray allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();


        lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);

        textViewSumOfProducts = (TextView) findViewById(R.id.textViewSumOfProducts);
        textViewLimitValue = (TextView) findViewById(R.id.textViewLimitValue);
        textViewLimitValue.setText(intent.getStringExtra(StartUpActivity.EXTRA_MESSAGE));

        editTextCost = (EditText) findViewById(R.id.editTextCost);
        editTextCount = (EditText) findViewById(R.id.editTextCount);

        editTextCount.setOnFocusChangeListener(this);

        allProducts = new ProductsArray();
    }


    @Override
    public void onClick(View v) {

        tableLayout.removeView(allProducts.getProduct(v.getId()).getTableRowEntity());
        allProducts.deleteProductCost(v.getId());
        textViewSumOfProducts.setText(allProducts.getSumValueOfProductsString());
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            String curCost = editTextCost.getText().toString();
            String curCount = editTextCount.getText().toString();
            ProductElement prodElement;
            Product prod;

            if (curCost.isEmpty()) {
                return;
            }

            if (!curCount.isEmpty()) {
                prodElement = new ProductElement(new BigDecimal(curCost), new BigDecimal(curCount));
            } else {
                prodElement = new ProductElement(new BigDecimal(curCost));
            }

            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TableRow.LayoutParams layoutParamsForCell = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);

            TextView textViewCost = new TextView(this);
            textViewCost.setText(prodElement.getCostString());
            textViewCost.setLayoutParams(layoutParamsForCell);

            TextView textViewCount = new TextView(this);
            textViewCount.setText(prodElement.getCountString());
            textViewCount.setLayoutParams(layoutParamsForCell);

            TextView textViewValue = new TextView(this);
            textViewValue.setText(prodElement.getValueString());
            textViewValue.setLayoutParams(layoutParamsForCell);

            Button buttonDeleteProduct = new Button(this, null, android.R.attr.borderlessButtonStyle);
            buttonDeleteProduct.setText("X");
            buttonDeleteProduct.setMinHeight(0);
            buttonDeleteProduct.setMinWidth(0);
            buttonDeleteProduct.setLayoutParams(layoutParamsForCell);
            buttonDeleteProduct.setOnClickListener(this);

            tableRow.addView(textViewCost);
            tableRow.addView(textViewCount);
            tableRow.addView(textViewValue);
            tableRow.addView(buttonDeleteProduct);

            prod = new Product(prodElement, tableRow, textViewCost,textViewCount, textViewValue, buttonDeleteProduct);

            tableLayout.addView(tableRow, lpView);
            allProducts.addProduct(prod);
            textViewSumOfProducts.setText(allProducts.getSumValueOfProductsString());

            //((Button) v).setText(  );
            buttonDeleteProduct.setText((String.valueOf(prod.getProductIndex())));

            editTextCost.setText("");
            editTextCount.setText("");
/*
            EditText editTextDynamicCost = new EditText(this);
            editTextDynamicCost.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editTextDynamicCost.setText(prod.getCostString());
            editTextDynamicCost.setLayoutParams(layoutParamsForCell);

            EditText editTextDynamicCount = new EditText(this);
            editTextDynamicCount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editTextDynamicCount.setText(prod.getCountString());
            editTextDynamicCount.setLayoutParams(layoutParamsForCell);
            editTextDynamicCount.setOnFocusChangeListener(this);
*/


        }
    }
}
