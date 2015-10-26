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
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;


public class WriteCardActivity extends ActionBarActivity {

    NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilters;
    private Context context;
    String account;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_card);

        userLocalStore = new UserLocalStore(this);

        Intent myIntent = getIntent();
        account = myIntent.getStringExtra("account");

        context = getApplicationContext();

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

       /* if (nfcAdapter!=null && nfcAdapter.isEnabled())
        {
            Toast.makeText(this, "NFC is available :)", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this, "NFC not available :(", Toast.LENGTH_LONG).show();
        }*/


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
            startActivity(new Intent(WriteCardActivity.this, MerchantLogin.class));
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }


    @Override
    protected void onNewIntent(Intent intent) {


        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        NdefMessage ndefMessage = createNdefMessage(account+"");

        writeNdefMessage(tag, ndefMessage);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(WriteCardActivity.this);
        alertDialog.setMessage("Card Written Successfully!");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(WriteCardActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("new_window", true); //sets new window
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        //alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();

        super.onNewIntent(intent);
    }



    @Override

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
    protected void onPause() {
        if (nfcAdapter != null) nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    private void formatTag(Tag tag, NdefMessage ndefMessage)
    {
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);

        if (ndefFormatable == null)
        {
            Toast.makeText(this, "Tag is not NDEF formatable", Toast.LENGTH_LONG).show();
            return;
        }

        try
        {
            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();
            //Toast.makeText(this, "Tag has be written successfully!", Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            Log.e("formatTag: ", e.getMessage());
        }


    }



    private void writeNdefMessage(Tag tag, NdefMessage ndefMessage)
    {
        try
        {
            if (tag == null)
            {
                Toast.makeText(this, "tag object can not be null", Toast.LENGTH_LONG).show();
                return;
            }

            Ndef ndef = Ndef.get(tag);

            if (ndef == null)
            {
                formatTag(tag, ndefMessage);
            }
            else
            {
                ndef.connect();

                if (!ndef.isWritable()) {
                    Toast.makeText(this, "Tag is write protected", Toast.LENGTH_LONG).show();
                    ndef.close();
                    return;
                }

                ndef.writeNdefMessage(ndefMessage);
                //Toast.makeText(this, "Tag has be written successfully!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e)
        {
            Log.e("writeNdefmessage: ", e.getMessage());
        }

    }

    private NdefRecord createTextRecord (String content)
    {
        try
        {
            byte language [];
            language = Locale.getDefault().getLanguage().getBytes("UTF-8");

            final byte [] text = content.getBytes("UTF-8");

            final int languageSize = language.length;
            final  int textLength = text.length;

            final ByteArrayOutputStream payload = new ByteArrayOutputStream(1 + languageSize + textLength);

            payload.write((byte) (languageSize & 0x1F));
            payload.write(language, 0, languageSize);
            payload.write(text, 0, textLength);



            return new NdefRecord(NdefRecord.TNF_MIME_MEDIA, new String("application/" + context.getPackageName())
                    .getBytes(Charset.forName("US-ASCII")), new byte[0], payload.toByteArray());

        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("createTextRecord: ", e.getMessage());
        }
        return null;
    }

    private NdefMessage createNdefMessage(String content)
    {
        NdefRecord ndefRecord = createTextRecord(content);

        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord,
                NdefRecord.createApplicationRecord(context.getPackageName())});
        return ndefMessage;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_card, menu);
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
