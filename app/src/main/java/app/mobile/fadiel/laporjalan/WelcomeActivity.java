package app.mobile.fadiel.laporjalan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button BtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        BtRegister = (Button) findViewById(R.id.bt_register);

        BtRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == BtRegister){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
