package hu.adam.sibilum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.User;
import hu.adam.sibilum.network.api.NewUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnApiResult {

    RelativeLayout mRlActivity;
    Button mBtnLogin;
    EditText mEtUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRlActivity     = (RelativeLayout)findViewById(R.id.activity_login);
        mBtnLogin       = (Button)findViewById(R.id.btnLogin);
        mEtUsername     = (EditText) findViewById(R.id.etUsername);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = mEtUsername.getText().toString();

        if( username.isEmpty() ) {
            Utils.snackbar(mRlActivity, R.string.error_empty_username_field);
            return;
        }

        startLogin(username);
    }


    private void startLogin(String username) {
        new NewUser(this, username, 111).start();
    }

    @Override
    public void onSuccess(String api, String response) {
        User user;

        try {
            user = User.fromString(response);
        } catch (JSONException e) {
            Utils.snackbar(mRlActivity, R.string.error_login_failed);
            return;
        }

        App.get().setUser(user);

        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFail(String api) {
        Utils.snackbar(mRlActivity, R.string.error_login_failed);
    }
}
