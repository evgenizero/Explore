package bg.uni_sofia.fmi.explore;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import bg.uni_sofia.fmi.explore.contracts.UserContract;
import bg.uni_sofia.fmi.explore.models.UserModel;
import bg.uni_sofia.fmi.explore.providersWrappers.UserProviderWrapper;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{

    @InjectView(R.id.login_btn) Button mLoginBtn;
    @InjectView(R.id.login_usernameET) EditText mUsername;
    @InjectView(R.id.login_passwordET) EditText mPassword;
    @InjectView(R.id.login_registerBtn) Button mRegisterBtn;
    @InjectView(R.id.login_keepInCB) CheckBox mKeepIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        setListeners();

        if(((ExploreApplication)getApplication()).getCurrentUser() != null) {
            startMainScreen();
        }
    }

    private void setListeners() {
        mLoginBtn.setOnClickListener(this);
        mRegisterBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.login_btn: {
                //TODO validate form
                UserModel userModel = UserProviderWrapper.getUserByCredentials(LoginActivity.this, mUsername.getText().toString(), mPassword.getText().toString());
                if(userModel != null) {
                    ((ExploreApplication)getApplication()).setCurrentUser(userModel);
                    startMainScreen();

                    if(mKeepIn.isChecked()) {
                        UserProviderWrapper.setCurrentUser(LoginActivity.this, userModel);
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.login_registerBtn: {
                Intent intent = new Intent(LoginActivity.this, bg.uni_sofia.fmi.explore.RegistrationActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void startMainScreen() {
        Intent intent = new Intent(LoginActivity.this, bg.uni_sofia.fmi.explore.MyNavigationDrawer.class);
        startActivity(intent);
    }
}
