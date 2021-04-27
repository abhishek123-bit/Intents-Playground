package com.example.intentsplayground;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.intentsplayground.databinding.ActivityIntentPlaygroundBinding;

public class IntentPlaygroundActivity extends AppCompatActivity {
    private static final int REQUEST_COUNT = 100;
    ActivityIntentPlaygroundBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();

        setupExplicitIntent();

        setupImplicitIntent();

        sendDataToMainActivity();
    }


    private void sendDataToMainActivity() {
        b.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendData = b.sendData.getText().toString().trim();

                if (sendData.isEmpty()) {
                    b.sendData.setError("Please enter data");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.INITIAL_DATA, Integer.parseInt(sendData));
                bundle.putInt(Constants.MIN_VALUE, 0);
                bundle.putInt(Constants.MAX_VALUE, 100);

                Intent intent = new Intent(IntentPlaygroundActivity.this, MainActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_COUNT);

            }
        });
    }

    //Initialize Layout
    private void setupLayout() {
        b = ActivityIntentPlaygroundBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        setTitle("Intents Playground");
    }


    //Explicit Intent
    private void setupExplicitIntent() {
        b.sendExplicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntentPlaygroundActivity.this, MainActivity.class));

            }
        });
    }


    //Implicit Intent
    private void setupImplicitIntent() {
        b.sendImplicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = b.data.getText().toString().trim();
                if (str.isEmpty()) {
                    b.data.setError("Please enter data");
                    return;
                }
                int id = b.radioGroup.getCheckedRadioButtonId();
                if (id == R.id.open_webPage) {
                    openWebPage(str);
                } else if (id == R.id.dial_no) {
                    openDialer(str);

                } else if (id == R.id.share_text) {
                    openShareMenu(str);
                } else {
                    Toast.makeText(IntentPlaygroundActivity.this, "Please Select a option", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void openShareMenu(String text) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);

        intent.setType("text/plain");


        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        /*Fire!*/
        startActivity(Intent.createChooser(intent, "Share text via"));
        hideError();

    }

    private void openDialer(String number) {
        if (!number.matches("^\\d{10}$")) {
            b.data.setError("Please enter valid number ");
            return;
        }
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)));
        hideError();

    }

    private void openWebPage(String url) {
        if (!url.matches("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$")) {
            b.data.setError("Please enter valid url");
            return;
        }
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        hideError();
    }


    //Utils
    private void hideError() {
        b.data.setError(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COUNT && resultCode == RESULT_OK) {
            b.finalData.setText("The final count value is " + data.getIntExtra(Constants.FINAL_DATA, 0));
            b.finalData.setVisibility(View.VISIBLE);
        }
    }
}