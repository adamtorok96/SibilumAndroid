package hu.adam.sibilum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import org.json.JSONException;

import hu.adam.sibilum.interfaces.OnApiResult;
import hu.adam.sibilum.models.User;
import hu.adam.sibilum.network.api.NewUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnApiResult {

    private static final String PREF_SAVE_USERNAME  = "save_username";
    private static final String PREF_USERNAME       = "username";

    private RelativeLayout mRlActivity;
    private Button mBtnLogin;
    private EditText mEtUsername;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullscreen();
        setContentView(R.layout.activity_login);

        InitViews();
    }

    private void setFullscreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void InitViews() {
        mRlActivity     = (RelativeLayout)findViewById(R.id.activity_login);
        mBtnLogin       = (Button)findViewById(R.id.btnLogin);
        mEtUsername     = (EditText)findViewById(R.id.etUsername);
        mProgressBar    = (ProgressBar)findViewById(R.id.progressBar);

        mBtnLogin.setOnClickListener(this);

        mEtUsername.setText(getSavedUsername());
    }

    private String getSavedUsername() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        return sp.getBoolean(PREF_SAVE_USERNAME, true) ? sp.getString(PREF_USERNAME, "") : "";
    }

    private void saveUsername(String username) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        if( sp.getBoolean(PREF_SAVE_USERNAME, true) ) {
            SharedPreferences.Editor edit = sp.edit();

            edit.putString(PREF_USERNAME, username);

            edit.apply();
        }
    }

    @Override
    public void onClick(View v) {
        String username = mEtUsername.getText().toString();

        if( username.isEmpty() ) {
            Utils.snackbar(mRlActivity, R.string.error_empty_username_field);
            return;
        }

        runOnUiThread(mEnableProgressBar);

        startLogin(username);
    }


    private void startLogin(String username) {
        saveUsername(username);

        new NewUser(this, username).start();
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
        runOnUiThread(mDisableProgressBar);

        User user;

        try {
            user = User.fromString(response);
        } catch (JSONException e) {
            Utils.snackbar(mRlActivity, R.string.error_failed_to_login);
            return;
        }

        App.get().setUser(user);

        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFail(String api) {
        runOnUiThread(mDisableProgressBar);

        Utils.snackbar(mRlActivity, R.string.error_failed_to_login);
    }

    public Runnable mEnableProgressBar = new Runnable() {
        @Override
        public void run() {
            mProgressBar.setVisibility(View.VISIBLE);
            mBtnLogin.setEnabled(false);
        }
    };

    public Runnable mDisableProgressBar = new Runnable() {
        @Override
        public void run() {
            mProgressBar.setVisibility(View.GONE);
            mBtnLogin.setEnabled(true);
        }
    };
}
