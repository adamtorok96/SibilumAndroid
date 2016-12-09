package hu.adam.sibilum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONException;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.User;
import hu.adam.sibilum.network.api.NewUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnApiResult {

    private RelativeLayout mRlActivity;
    private Button mBtnLogin;
    private EditText mEtUsername;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitViews();
    }

    private void InitViews() {
        mRlActivity     = (RelativeLayout)findViewById(R.id.activity_login);
        mBtnLogin       = (Button)findViewById(R.id.btnLogin);
        mEtUsername     = (EditText)findViewById(R.id.etUsername);
        mProgressBar    = (ProgressBar)findViewById(R.id.progressBar);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String username = mEtUsername.getText().toString();

        if( username.isEmpty() ) {
            Utils.snackbar(mRlActivity, R.string.error_empty_username_field);
            return;
        }

        enableProgressBar();

        startLogin(username);
    }


    private void startLogin(String username) {
        new NewUser(this, username, 111).start();
    }

    private void enableProgressBar() {
        if( !Utils.isMainThread() ) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    enableProgressBar();
                }
            });

            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        mBtnLogin.setEnabled(false);
    }

    private void disableProgressBar() {
        if( !Utils.isMainThread() ) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    disableProgressBar();
                }
            });

            return;
        }

        mProgressBar.setVisibility(View.GONE);
        mBtnLogin.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if( item.getItemId() == R.id.item_settings ) {
            startActivity(new Intent(this, SettingsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(String api, String response) {
        disableProgressBar();

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
        disableProgressBar();

        Utils.snackbar(mRlActivity, R.string.error_login_failed);
    }
}
