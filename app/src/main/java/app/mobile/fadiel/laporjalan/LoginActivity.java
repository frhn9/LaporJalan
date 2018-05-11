package app.mobile.fadiel.laporjalan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonLogin;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignUp;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        buttonLogin = (Button) findViewById(R.id.bt_login);

        progressDialog = new ProgressDialog(this);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        tvSignUp = (TextView) findViewById(R.id.tv_signup);

        buttonLogin.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);


        final SharedPreferences spEmail = PreferenceManager.getDefaultSharedPreferences(this);
        etEmail.setText(spEmail.getString("save email", ""));
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                spEmail.edit().putString("save email", s.toString()).commit();
            }
        });

        final SharedPreferences spPassword = PreferenceManager.getDefaultSharedPreferences(this);
        etPassword.setText(spPassword.getString("save password", ""));
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                spPassword.edit().putString("save password", s.toString()).commit();
            }
        });

    }

    private void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //Jika tidak ada text e-mail
            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(password)){
            //Jika tidak ada text password
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            progressDialog.setMessage("Logged in. Please wait...");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Start profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            }
                            else {
                                //User gagal login
                                Toast.makeText
                                        (LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if(view == buttonLogin){
            userLogin();
        }
        if(view == tvSignUp){
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, WelcomeActivity.class));
    }
}
