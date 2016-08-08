package com.bpg.shoppingguard;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private ViewGroup.LayoutParams lpView;
    private TableLayout tableLayout;
    private TextView textViewSumOfProducts;
    private EditText editTextCost;
    private EditText editTextCount;
    private LinearLayout linearLayoutLimitAndTotal;

    private GestureDetectorCompat mDetector;

    private ProductsArray allProducts;
    private BigDecimal limitValue;
    private static final String WHITE_FLAG = "white";
    private static final String RED_FLAG = "red";
    private String currentBackgroundColor = WHITE_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        lpView = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        linearLayoutLimitAndTotal = (LinearLayout) findViewById(R.id.linearLayoutLimitAndTotal);

        textViewSumOfProducts = (TextView) findViewById(R.id.textViewSumOfProducts);
        TextView textViewLimitValue = (TextView) findViewById(R.id.textViewLimitValue);
        limitValue = new BigDecimal(intent.getStringExtra(StartUpActivity.LIMIT_VALUE)).setScale(ProductElement.ROUND_DIGITS, BigDecimal.ROUND_HALF_EVEN);
        textViewLimitValue.setText(String.format(Locale.getDefault(), "%.2f", limitValue));

        editTextCost = (EditText) findViewById(R.id.editTextCost);
        editTextCount = (EditText) findViewById(R.id.editTextCount);

        editTextCount.setOnFocusChangeListener(this);

        allProducts = new ProductsArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_change_limit_value:
                Toast toastChangeMoneyLimitValue = Toast.makeText(this, R.string.action_change_limit_value, Toast.LENGTH_SHORT);
                toastChangeMoneyLimitValue.show();
                return true;
            case R.id.action_delete_all:
                allProducts.deleteAllProduct(tableLayout);
                ChangeSumOfProducts(textViewSumOfProducts);
                Toast toastDeleteAllProduct = Toast.makeText(this, R.string.action_delete_all, Toast.LENGTH_SHORT);
                toastDeleteAllProduct.show();
                return true;
            case R.id.action_exit:
                Toast toastExit = Toast.makeText(this, R.string.action_exit, Toast.LENGTH_SHORT);
                toastExit.show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    this.finishAffinity();
                } else {
                    this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        allProducts.deleteProduct(tableLayout, v.getId());
        ChangeSumOfProducts(textViewSumOfProducts);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            return;
        }
        String curCost = editTextCost.getText().toString();
        String curCount = editTextCount.getText().toString();
        ProductElement prodElement;
        Product prod;

        if (curCost.isEmpty()) {
            return;
        }
        if (new BigDecimal(curCost).equals(BigDecimal.ZERO)) {
            return;
        }

        if (!curCount.isEmpty()) {
            if (new BigDecimal(curCount).equals(BigDecimal.ZERO)) {
                return;
            }
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

        prod = new Product(prodElement, tableRow, textViewCost, textViewCount, textViewValue, buttonDeleteProduct);

        tableLayout.addView(tableRow, lpView);
        allProducts.addProduct(prod);
        ChangeSumOfProducts(textViewSumOfProducts);
        buttonDeleteProduct.setText((String.valueOf(prod.getProductIndex())));

        editTextCost.setText("");
        editTextCount.setText("");
    }

    private void ChangeSumOfProducts(TextView v) {
        v.setText(allProducts.getSumValueOfProductsString());

        int compareSumValueOfProductsWithLimit = limitValue.subtract(allProducts.getSumValueOfProducts()).compareTo(BigDecimal.ZERO);

        if (compareSumValueOfProductsWithLimit < 1
                && currentBackgroundColor.equals(MainActivity.WHITE_FLAG)) {

            currentBackgroundColor = MainActivity.RED_FLAG;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tableLayout.setBackgroundColor(getResources().getColor(R.color.colorRed, getTheme()));
                linearLayoutLimitAndTotal.setBackgroundColor(getResources().getColor(R.color.colorDarkRed, getTheme()));
            } else {
                tableLayout.setBackgroundColor(getResources().getColor(R.color.colorRed));
                linearLayoutLimitAndTotal.setBackgroundColor(getResources().getColor(R.color.colorDarkRed));
            }
        }

        if (compareSumValueOfProductsWithLimit >= 0
                && currentBackgroundColor.equals(MainActivity.RED_FLAG)) {

            currentBackgroundColor = MainActivity.WHITE_FLAG;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tableLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite, getTheme()));
                linearLayoutLimitAndTotal.setBackgroundColor(getResources().getColor(R.color.colorLightGreen, getTheme()));
            } else {
                tableLayout.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                linearLayoutLimitAndTotal.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
            return true;
        }
    }

}
