package app.mobile.fadiel.laporjalan;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private MapsFragment Maps_Fragment;
    private ViewPager viewPager;
    private AdapterSectionPager adapterSectionPager;
    private TabLayout DetailTabs;
    private PelaporanFragment Pelaporan_Fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        this.viewPager = (ViewPager) findViewById(R.id.container);

        Maps_Fragment = new MapsFragment();
        Pelaporan_Fragment = new PelaporanFragment();

        adapterSectionPager = new AdapterSectionPager(getSupportFragmentManager());
        adapterSectionPager.addFragment(Maps_Fragment);
        adapterSectionPager.addFragment(Pelaporan_Fragment);

        viewPager.setAdapter(adapterSectionPager);
        this.DetailTabs = (TabLayout) findViewById(R.id.DetailTabs);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(DetailTabs));
        DetailTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout :
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, WelcomeActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class AdapterSectionPager extends FragmentPagerAdapter {
        private final List<Fragment> listFragment = new ArrayList<>();
        public AdapterSectionPager(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment){
            listFragment.add(fragment);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }
    }

}
