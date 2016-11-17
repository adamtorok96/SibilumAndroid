package hu.adam.sibilum;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        String username = getUsername();

        if( username.isEmpty() ) {
            showText(R.string.error_empty_username_field);
            return;
        }

        startLogin(username);
    }

    private void startLogin(String username) {

        if( ((App)getApplication()).getServer().login(username) ) {
            startActivity(new Intent(this, ChannelsActivity.class));
        } else
            showText(R.string.error_login_failed);
        
    }

    private String getUsername() {
        return mEtUsername.getText().toString();
    }

    private void showText(int resId) {
        Snackbar.make(mRlActivity, resId, Snackbar.LENGTH_LONG).show();
    }
}
