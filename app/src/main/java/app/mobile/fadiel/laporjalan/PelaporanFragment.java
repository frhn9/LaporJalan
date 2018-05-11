package app.mobile.fadiel.laporjalan;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by FadieL on 5/11/2018.
 */

public class PelaporanFragment extends Fragment {

    private View view;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_pelaporan, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity().getApplication(), LoginActivity.class);
            startActivity(intent);
        }

        return view;
    }
}
