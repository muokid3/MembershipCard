package com.loyalty.cardplanet.membershipcard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MerchantLogin extends ActionBarActivity implements View.OnClickListener {
    EditText etUsername, etPassword;
    Button btnLogin;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_login);

        etUsername = (EditText)findViewById(R.id.usernameTxt);
        etPassword = (EditText)findViewById(R.id.passwordTxt);
        btnLogin = (Button)findViewById(R.id.loginBtn);


        btnLogin.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==true)
        {
            startActivity(new Intent(MerchantLogin.this, MainActivity.class));
            finish();
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginBtn:
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String name = "";

                User user = new User(name, username, password);

                authenticate(user);

                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_merchant_login, menu);
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

    private void authenticate(User user)
    {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchMerchantDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null) {
                    showErrorMessage();
                } else {
                    logUserIn(returnedUser);
                }
            }
        });
    }
    private void showErrorMessage()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MerchantLogin.this);
        alertDialog.setMessage("Wrong Email or Password");
        alertDialog.setPositiveButton("Ok", null);
        alertDialog.create();
        alertDialog.show();
    }
    private void logUserIn(User returnedUser)
    {
        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
