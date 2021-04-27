package com.example.intentsplayground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.intentsplayground.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding b;
    int qty = 0;
    private int minValue, maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        eventHandler();
        receiveData();

        sendDataBack();
    }


    private void sendDataBack() {
        b.btnSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (qty >= minValue && qty <= maxValue) {

                    Intent replyIntent = new Intent(MainActivity.this, IntentPlaygroundActivity.class);
                    replyIntent.putExtra(Constants.FINAL_DATA, qty);

                    setResult(RESULT_OK, replyIntent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "NOT VALID", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void receiveData() {


        // check bundle is not null
        if (getIntent().getExtras() != null) {
            minValue = getIntent().getExtras().getInt(Constants.MIN_VALUE, 0);
            maxValue = getIntent().getExtras().getInt(Constants.MAX_VALUE, 0);

            qty = getIntent().getExtras().getInt(Constants.INITIAL_DATA, Integer.MIN_VALUE);

            b.btnSendBack.setVisibility(View.VISIBLE);
            b.qty.setText(qty + "");

        }
    }

    private void eventHandler() {
        b.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });

        b.incBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });
    }

    //To increase the quantity
    private void increaseQuantity() {
        b.qty.setText(++qty + "");
    }

    //To decrease the quantity
    private void decreaseQuantity() {

        if (qty == 0) {
            Toast.makeText(this, "Quantity is already 0", Toast.LENGTH_SHORT).show();
            return;
        }

        b.qty.setText(--qty + "");
    }
}