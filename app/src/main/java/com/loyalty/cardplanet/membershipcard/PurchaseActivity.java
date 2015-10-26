package com.loyalty.cardplanet.membershipcard;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;


public class PurchaseActivity extends ActionBarActivity {

    NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    private Context context;
    String accountNo;
    RelativeLayout layout;
    EditText purchaseAmount, pinNO;
    TextView accountText, tapTV;
    Button purchase;
    int amount, account, pin;
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        userLocalStore = new UserLocalStore(this);

        purchaseAmount = (EditText)findViewById(R.id.amountText); //amount to buy
        purchaseAmount.setVisibility(View.INVISIBLE);

        accountText = (TextView)findViewById(R.id.accountNoTV); //100100 etc
        accountText.setVisibility(View.INVISIBLE);

        tapTV = (TextView)findViewById(R.id.tapTV); //please tap to purchase

        pinNO = (EditText)findViewById(R.id.pin); //pin
        pinNO.setVisibility(View.INVISIBLE);

        purchase = (Button)findViewById(R.id.purchaseButton);
        purchase.setVisibility(View.INVISIBLE);

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account = Integer.parseInt(accountNo);

                if (validateEditTexts())
                {
                    amount = Integer.parseInt(purchaseAmount.getText().toString());
                    pin = Integer.parseInt(pinNO.getText().toString());

                    User loggedInUser = userLocalStore.getLoggedInUser();
                    String merchName = loggedInUser.merchName;

                    User user = new User(account, pin, amount, merchName);

                    authenticate(user);
                }

            }
        });


        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP), 0);

        IntentFilter tagDiscovered = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDiscovered = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

        intentFilters = new IntentFilter[]{tagDiscovered};

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==false)
        {
            startActivity(new Intent(PurchaseActivity.this, MerchantLogin.class));
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    private void authenticate(User user)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {

                if (returnedUser == null)
                {
                    showPinFailMessage();
                }
                else if (returnedUser.status.equals("activeFail"))
                {
                    showInactiveMessage();
                }

                else
                {
                    success(returnedUser);
                }
            }
        });
    }

    private boolean validateEditTexts()
    {
        boolean valid = true;

        if(purchaseAmount.getText().toString().trim().equals(""))
        {
            purchaseAmount.setError("Please provide the amount of Purchase");
            valid = false;
        }

        if(pinNO.getText().toString().trim().equals(""))
        {
            pinNO.setError("Please provide your PIN");
            valid = false;
        }

        return valid;

    }

    private void showPinFailMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PurchaseActivity.this);
        alertDialog.setMessage("Sorry, You entered a wrong PIN");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }
    private void showInactiveMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PurchaseActivity.this);
        alertDialog.setMessage("Sorry, Your account is inactive. Please contact admin");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }

    private void success(User returnedUser)
    {
        User newReturnedUser = returnedUser;
        commons.passedAccount = newReturnedUser.account;
        commons.passedAmount = returnedUser.amount;
        commons.passedName = returnedUser.name;
        commons.passedPin = returnedUser.intPin;

        Intent confPurchase = new Intent(PurchaseActivity.this, confPurchaseActivity.class);
        startActivity(confPurchase);
    }




    @Override
    protected void onNewIntent(Intent intent) {
        Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (parcelables != null && parcelables.length>0)
        {
            readTextFromMessage((NdefMessage) parcelables[0]);
        }
        else
        {
            Toast.makeText(this, "No NDEF message found", Toast.LENGTH_LONG).show();
        }
        super.onNewIntent(intent);
    }

    private void readTextFromMessage(NdefMessage ndefMessage)
    {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length>0)
        {
            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = getTextFromNdefRecord(ndefRecord);

            this.accountNo=tagContent;
            accountText.setText("Account NO: "+accountNo);
            this.purchase.setVisibility(View.VISIBLE);
            this.purchaseAmount.setVisibility(View.VISIBLE);
            this.pinNO.setVisibility(View.VISIBLE);
            this.accountText.setVisibility(View.VISIBLE);

        }
        else
        {
            this.layout.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "No NDEF records found", Toast.LENGTH_LONG).show();
        }

    }

    public String getTextFromNdefRecord (NdefRecord ndefRecord)
    {
        String tagContent = null;

        try
        {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding;
            if ((payload[0] & 128) == 0) textEncoding = "UTF-8";
            else textEncoding = "UTF-16";
            int languageSize = payload[0] & 0063;

            tagContent = new String(payload, languageSize+1,payload.length-languageSize-1, textEncoding);

        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("fail: ", e.getMessage());
        }
        return tagContent;
    }

    protected void onPause() {
        if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    protected void onResume()
    {
        super.onResume();

        if (nfcAdapter != null)
        {
            if (!nfcAdapter.isEnabled())
            {

                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.nfc_off,(ViewGroup) findViewById(R.id.action_settings));
                new AlertDialog.Builder(this).setView(dialoglayout)
                        .setPositiveButton("Go to NFC settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent setnfc = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                                startActivity(setnfc);
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {

                            public void onCancel(DialogInterface dialog) {
                                finish(); // exit application if user cancels
                            }
                        }).create().show();

            }
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
        }
        else
        {
            Toast.makeText(this, "Sorry, this device is not NFC enabled", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_purchase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
