package com.loyalty.cardplanet.membershipcard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class PinSettings extends ActionBarActivity {
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_settings);

        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==false)
        {
            startActivity(new Intent(PinSettings.this, MerchantLogin.class));
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    public void changePin(View view)
    {
        Intent changePin = new Intent(PinSettings.this, ChangePinActivity.class);
        startActivity(changePin);
    }

    public void resetPin(View view)
    {
        Intent changePin = new Intent(PinSettings.this, ResetPinActivity.class);
        startActivity(changePin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reset_pin, menu);
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
