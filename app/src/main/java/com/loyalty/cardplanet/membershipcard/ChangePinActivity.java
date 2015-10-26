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
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;


public class ChangePinActivity extends ActionBarActivity {

    EditText oldPin, newPin, confPin;
    Button changePin;

    NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    private Context context;
    String accountNo;
    int account;

    TextView accountText, tapTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);

        oldPin = (EditText)findViewById(R.id.oldpin);
        oldPin.setVisibility(View.INVISIBLE);

        newPin = (EditText)findViewById(R.id.newpin);
        newPin.setVisibility(View.INVISIBLE);

        confPin = (EditText)findViewById(R.id.confnewpin);
        confPin.setVisibility(View.INVISIBLE);

        changePin = (Button)findViewById(R.id.changebutton);
        changePin.setVisibility(View.INVISIBLE);

        accountText = (TextView)findViewById(R.id.accountTxt);
        accountText.setVisibility(View.INVISIBLE);

        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEditTexts())
                {
                    if (confPin.getText().toString().trim().matches(newPin.getText().toString().trim()))
                    {
                        account = Integer.parseInt(accountNo);
                        Pin pinUser = new Pin(oldPin.getText().toString(), newPin.getText().toString(),
                                confPin.getText().toString(), account);
                        authenticate(pinUser);

                    }
                    else
                    {
                        Toast.makeText(ChangePinActivity.this, "'New Password' and 'Confirm Password' must match", Toast.LENGTH_LONG).show();
                    }

                }
                /*else
                {
                    Toast.makeText(ChangePinActivity.this, "fail", Toast.LENGTH_LONG).show();
                }*/
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

    private void authenticate(Pin pinUser)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.changeUserPinInBackground(pinUser, new GetPinCallback() {
            @Override
            public void done(Pin returnedPinUser) {

                if (returnedPinUser == null) {
                    showPinFailMessage();
                }
                else {
                    showSuccessMessage();
                }
            }
        });
    }

    private boolean validateEditTexts()
    {
        boolean valid = true;

        if(oldPin.getText().toString().trim().equals(""))
        {
            oldPin.setError("Please provide the old PIN");
            valid = false;
        }

        if(newPin.getText().toString().trim().equals(""))
        {
            newPin.setError("Please provide a new PIN");
            valid = false;
        }

        if(confPin.getText().toString().trim().equals(""))
        {
            confPin.setError("Please confirm the new PIN");
            valid = false;
        }


        return valid;

    }

    private void showPinFailMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePinActivity.this);
        alertDialog.setMessage("Sorry, You entered a wrong old PIN");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }

    private void showSuccessMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangePinActivity.this);
        alertDialog.setMessage("PIN changed successfully");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(ChangePinActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("new_window", true); //sets new window
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        alertDialog.create();
        alertDialog.show();
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
            this.accountText.setVisibility(View.VISIBLE);
            this.oldPin.setVisibility(View.VISIBLE);
            this.newPin.setVisibility(View.VISIBLE);
            this.confPin.setVisibility(View.VISIBLE);
            this.changePin.setVisibility(View.VISIBLE);

        }
        else
        {
            this.accountText.setVisibility(View.INVISIBLE);
            this.oldPin.setVisibility(View.INVISIBLE);
            this.newPin.setVisibility(View.INVISIBLE);
            this.confPin.setVisibility(View.INVISIBLE);
            this.changePin.setVisibility(View.INVISIBLE);
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
        getMenuInflater().inflate(R.menu.menu_change_pin, menu);
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
