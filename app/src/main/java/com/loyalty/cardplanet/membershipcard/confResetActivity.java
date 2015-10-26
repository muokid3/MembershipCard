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
import android.widget.EditText;
import android.widget.Toast;


public class confResetActivity extends ActionBarActivity {
    EditText resetCode, newPIN,confPIN;
    Button resetBtn;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_reset);

        userLocalStore = new UserLocalStore(this);

        resetCode = (EditText)findViewById(R.id.resetTxt);
        newPIN = (EditText)findViewById(R.id.newPIN);
        confPIN = (EditText)findViewById(R.id.confPIN);
        resetBtn = (Button)findViewById(R.id.resetBtn);

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEditTexts())
                {
                    if (newPIN.getText().toString().trim().matches(confPIN.getText().toString().trim()))
                    {
                        int resetAccount = commons.resetAccount;
                        User user = new User(resetAccount, resetCode.getText().toString(),Integer.parseInt(newPIN.getText().toString()));
                        authenticate(user);

                    }
                    else
                    {
                        Toast.makeText(confResetActivity.this, "'New PIN' and 'Confirm PIN' must match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==false)
        {
            startActivity(new Intent(confResetActivity.this, MerchantLogin.class));
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    private void authenticate(User user)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.resetUserPinInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {

                if (returnedUser == null) {
                    showPinFailMessage();
                } else {
                    showSuccessMessage();
                }
            }
        });
    }

    private void showPinFailMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(confResetActivity.this);
        alertDialog.setMessage("Sorry, You entered an invalid Reset Code");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }

    private void showSuccessMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(confResetActivity.this);
        alertDialog.setMessage("PIN changed successfully");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(confResetActivity.this, MainActivity.class);
                Bundle b = new Bundle();
                b.putBoolean("new_window", true); //sets new window
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        alertDialog.create();
        alertDialog.show();
    }

    private boolean validateEditTexts()
    {
        boolean valid = true;

        if(resetCode.getText().toString().trim().equals(""))
        {
            resetCode.setError("Please provide your Reset Code");
            valid = false;
        }

        if(newPIN.getText().toString().trim().equals(""))
        {
            newPIN.setError("Please provide a New PIN");
            valid = false;
        }

        if(confPIN.getText().toString().trim().equals(""))
        {
            confPIN.setError("Please confirm your new PIN");
            valid = false;
        }

        return valid;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conf_reset, menu);
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
