package com.loyalty.cardplanet.membershipcard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class confPurchaseActivity extends ActionBarActivity {

    String confName;
    int confAccount, confAmount, confPin;
    TextView tvName, tvAccount, tvAmount;
    Button confButton;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_purchase);

        tvName = (TextView)findViewById(R.id.tvName);
        tvAccount = (TextView)findViewById(R.id.tvAccount);
        tvAmount = (TextView)findViewById(R.id.tvAmount);
        confButton = (Button)findViewById(R.id.confirmBtn);

        userLocalStore = new UserLocalStore(this);

        confName = commons.passedName;
        confAccount = commons.passedAccount;
        confAmount = commons.passedAmount;
        confPin = commons.passedPin;

        tvName.setText(confName);
        tvAmount.setText(confAmount+"");
        tvAccount.setText(confAccount+"");

        confButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User loggedInUser = userLocalStore.getLoggedInUser();
                String merchName = loggedInUser.merchName;

                User user = new User(confAccount, confPin, confAmount, merchName);

                confirm(user);
            }
        });
    }

    private void confirm(User user)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.confirmUserPurchaseInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {

                if (returnedUser == null)
                {
                    showBalFailMessage();
                }

                else
                {
                    showSuccessMessage();
                    //success(returnedUser);
                }
            }
        });
    }

    private void showBalFailMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(confPurchaseActivity.this);
        alertDialog.setMessage("Sorry, You do not have sufficient balance");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }


    private void showSuccessMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(confPurchaseActivity.this);
        alertDialog.setMessage("Transaction Successful");

        alertDialog.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(confPurchaseActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("new_window", true); //sets new window
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        //alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conf_purchase, menu);
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
