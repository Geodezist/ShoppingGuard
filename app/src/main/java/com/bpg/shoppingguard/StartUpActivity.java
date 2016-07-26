package com.bpg.shoppingguard;

import android.content.Intent;

import java.math.BigDecimal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartUpActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.bpg.shoppingguard.MESSAGE";
    private static final String TAG = "StartUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
    }

    public void toMainActivity(View v) {

        String message = ((EditText) findViewById(R.id.editTextMoneyLimit)).getText().toString();
        BigDecimal checkMoneyLimit = BigDecimal.valueOf(-1);

        try {
            checkMoneyLimit = new BigDecimal(message);
        } catch (Exception e) {
            Log.d(TAG, "User input is '" + message + "'. " + e.getLocalizedMessage());
        }

        if (checkMoneyLimit.compareTo(BigDecimal.ZERO) != 1) {
            Log.d(TAG, "User input is '" + checkMoneyLimit.compareTo(BigDecimal.ZERO) + "' compared with 0");
            Toast toastErrorMoneyLimitValue = Toast.makeText(this, R.string.ErrorMoneyLimitValue, Toast.LENGTH_SHORT);
            toastErrorMoneyLimitValue.show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }
}
