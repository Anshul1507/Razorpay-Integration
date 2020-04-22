package com.netlify.anshulgupta.razorpayintegration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "MainActivity";
    Button btnPay;
    EditText etAmount;
    int amount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Checkout.preload(getApplicationContext());

        btnPay = findViewById(R.id.btn_pay);
        etAmount = findViewById(R.id.et_amount);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etAmount.getText().toString().equals("")) {
                    amount = Integer.parseInt(etAmount.getText().toString()) * 100; //getting amount from et
                    startPayment();
                }else{
                    Toast.makeText(getApplicationContext(), "Please enter some amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void startPayment() {
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(amount));

            JSONObject notes = new JSONObject();
            notes.put("uid","12345");
            notes.put("duration",Integer.parseInt("5"));

            options.put("notes",notes);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Succeed.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this,"Payment Failed.",Toast.LENGTH_SHORT).show();
    }
}
