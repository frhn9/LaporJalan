package app.mobile.fadiel.laporjalan;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by FadieL on 5/11/2018.
 */

public class MapsFragment extends Fragment {

    private View view;
    private TextView tvUserEmail;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.fragment_map, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity().getApplication(), LoginActivity.class);
            startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        this.tvUserEmail = (TextView) view.findViewById(R.id.tv_useremail);
        tvUserEmail.setText("Welcome "+ user.getEmail());

        return view;
    }
}
