package dev.z3t4.doki;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.Adapter.MainViewPager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager) ViewPager2 viewPager;
    @BindView(R.id.bottom_navigation_view) BottomNavigationView bottom_nav_view;

    private MainViewPager mainViewPager = new MainViewPager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainViewPager.setupViewPager(viewPager, getSupportFragmentManager(), getLifecycle());
        setupBottomNavigationView();

    }

    private void setupBottomNavigationView() {
        bottom_nav_view.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.anime:
                    viewPager.setCurrentItem(0, true);
                    break;
                case R.id.manga:
                    viewPager.setCurrentItem(1, true);
                    break;
                case R.id.news:
                    viewPager.setCurrentItem(2, true);
                    break;
                case R.id.library:
                    viewPager.setCurrentItem(3, true);
                    break;

            }

            return true;
        });
    }

}
