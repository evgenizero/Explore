package bg.uni_sofia.fmi.explore;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bg.uni_sofia.fmi.explore.models.UserModel;
import bg.uni_sofia.fmi.explore.providersWrappers.UserProviderWrapper;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by eyanev on 5/15/15.
 */
public class RegistrationActivity extends ActionBarActivity implements View.OnClickListener {
    @InjectView(R.id.toolbar) Toolbar mToolbar;
    @InjectView(R.id.registration_usernameET) EditText mUsername;
    @InjectView(R.id.registration_passwordET) EditText mPassword;
    @InjectView(R.id.registration_confirmtPassET) EditText mConfirmPassword;
    @InjectView(R.id.registration_signUpBtn) Button mSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setListeners();
    }

    private void setListeners() {
        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registration_signUpBtn: {
                //TODO validate registration form
                UserModel user = new UserModel(mUsername.getText().toString(), mPassword.getText().toString());
                if(UserProviderWrapper.insertSingleUser(RegistrationActivity.this, user)) {
                    Intent intent = new Intent(RegistrationActivity.this, bg.uni_sofia.fmi.explore.MyNavigationDrawer.class);
                    startActivity(intent);
                    ((ExploreApplication)getApplication()).setCurrentUser(user);
                } else {
                    Toast.makeText(RegistrationActivity.this, "Creating user failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
