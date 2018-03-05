package com.gasmanager.viacheslav.gasmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gasmanager.viacheslav.gasmanager.util.IabHelper;
import com.gasmanager.viacheslav.gasmanager.util.IabResult;
import com.gasmanager.viacheslav.gasmanager.util.Purchase;


public class PaymentActivity extends AppCompatActivity {
    IabHelper mHelper;
    String SKU_SMALL = "tip1";
    String SKU_MEDIUM = "tip2";
    String SKU_LARGE = "tip3";
    String TAG = "Circle-Synth";
    int RC_REQUEST = 10001;
    private Toast toast = null;
    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: "
                    + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            if (result.isFailure()) {
                toast("purchase error" + result);
                // setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                toast("error verification");
                // setWaitScreen(false);
                return;
            }

            Log.d(TAG, "Purchase successful.");


        }
    };
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase
                    + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
                return;

            //check which SKU is consumed here and then proceed.

            if (result.isSuccess()) {

                Log.d(TAG, "Consumption successful. Provisioning.");

                toast(getResources().getString(R.string.thank_you));
            } else {
                toast("error" + result);
            }


            Log.d(TAG, "End consumption flow.");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();

        RelativeLayout tip1 = findViewById(R.id.tip1);
        RelativeLayout tip2 = findViewById(R.id.tip2);
        RelativeLayout tip3 = findViewById(R.id.tip3);

        String base64EncodedPublicKey = getResources().getString(R.string.app_license);
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(false);


        tip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDonation(1);
            }
        });
        tip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDonation(2);
            }
        });
        tip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDonation(3);
            }
        });

        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set
        // this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    toast("error" + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null)
                    return;

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                //   --commented out here as we didn't need it for donation purposes.
                // Log.d(TAG, "Setup successful. Querying inventory.");
                // mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.dispose();
            mHelper = null;
        }
    }

    //DO NOT SKIP THIS METHOD
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + ","
                + data);
        if (mHelper == null)
            return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    /**
     * Verifies the developer payload of a purchase.
     */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /**Follow google guidelines to create your own payload string here, in case it is needed.
         *Remember it is recommended to store the keys on your own server for added protection
         USE as necessary*/

        return true;
    }

    //the button clicks send an int value which would then call the specific SKU, depending on the
    //application
    public void makeDonation(int value) {
        //check your own payload string.
        String payload = "";

        switch (value) {
            case (1):
                mHelper.launchPurchaseFlow(this, SKU_SMALL, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
                System.out.println("small purchase");
                break;
            case (2):
                mHelper.launchPurchaseFlow(this, SKU_MEDIUM, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
                System.out.println("medium purchase");
                break;
            case (3):
                mHelper.launchPurchaseFlow(this, SKU_LARGE, RC_REQUEST,
                        mPurchaseFinishedListener, payload);
                System.out.println("large purchase");
                break;
            default:
                break;
        }

    }

    private void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getApplicationContext(), "",
                            Toast.LENGTH_SHORT);
                }
                toast.setText(msg);
                toast.show();
            }
        });
    }

}