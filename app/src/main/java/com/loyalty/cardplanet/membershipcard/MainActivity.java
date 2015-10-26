package com.loyalty.cardplanet.membershipcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    UserLocalStore userLocalStore;
    TextView welcomeTxt;
    PopupWindow popupWindow;
    Button cashBtn, cardBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userLocalStore = new UserLocalStore(this);
        welcomeTxt = (TextView)findViewById(R.id.welcomeTxt);

        User loggedInUser = userLocalStore.getLoggedInUser();

        welcomeTxt.setText("Welcome "+loggedInUser.merchName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==false)
        {
            startActivity(new Intent(MainActivity.this, MerchantLogin.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void regCard(View view)
    {
        Intent regCard = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(regCard);
    }

    public void redeem(View view)
    {
        Intent redeem = new Intent(MainActivity.this, RedeemActivity.class);
        startActivity(redeem);
    }

    public void purchase(View view)
    {

        LayoutInflater layoutInflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = layoutInflater.inflate(R.layout.popup_layout, (ViewGroup)findViewById(R.id.popupLayout));
        popupWindow = new PopupWindow(layoutView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.showAtLocation(layoutView, Gravity.CENTER, 0, 0);


        cashBtn = (Button)layoutView.findViewById(R.id.cashBtn);
        cardBtn = (Button)layoutView.findViewById(R.id.cardBtn);
        cancelBtn = (Button)layoutView.findViewById(R.id.cancelBtn);

        cashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchase = new Intent(MainActivity.this, CashPurchaseActivity.class);
                startActivity(purchase);
            }
        });

        cardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchase = new Intent(MainActivity.this, PurchaseActivity.class);
                startActivity(purchase);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


    }

    public void resetPin(View view)
    {
        Intent reset = new Intent(MainActivity.this, PinSettings.class);
        startActivity(reset);
    }

    public void checkPoints(View view)
    {
        Intent check = new Intent(MainActivity.this, CheckPoints.class);
        startActivity(check);
    }
    public void logout(View view)
    {
        userLocalStore.clearUserData();
        userLocalStore.setUserLoggedIn(false);
        startActivity(new Intent(this, MerchantLogin.class));
    }
}
