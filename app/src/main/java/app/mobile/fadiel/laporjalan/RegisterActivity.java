package app.mobile.fadiel.laporjalan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.bt_register);

        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);

        tvSignIn = (TextView) findViewById(R.id.tv_signin);

        buttonRegister.setOnClickListener(this);
        tvSignIn.setOnClickListener(this);
    }

    private void registerUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(etEmail.getText().toString())){
            //Jika tidak ada text e-mail
            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(etPassword.getText().toString())){
            //Jika tidak ada text password
            Toast.makeText(this,"Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(etPassword.length() < 8){
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 8 characters",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setMessage("Registering, please wait...");
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Checking if success
                            if (task.isSuccessful()) {
                                //Start profile activity jika sukses register
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                //User gagal register
                                Toast.makeText
                                        (RegisterActivity.this, "Register failed.. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
            finish();
        }
        if(view == tvSignIn){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

}
