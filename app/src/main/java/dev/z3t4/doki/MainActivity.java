package dev.z3t4.doki;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.ui.AnimeFragment;
import dev.z3t4.doki.ui.LibraryFragment;
import dev.z3t4.doki.ui.MangaFragment;
import dev.z3t4.doki.ui.NewsFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_view) BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.app_bar_layout) AppBarLayout appBarLayout;

    private AnimeFragment animeFragment = new AnimeFragment();
    private MangaFragment mangaFragment = new MangaFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private LibraryFragment libraryFragment = new LibraryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            changeFragment(animeFragment, AnimeFragment.class.getSimpleName());
        }

        ButterKnife.bind(this);
        setupBottomNavigationView();
        setupAppbarLayout();

    }

    private void setupAppbarLayout() {
    setSupportActionBar(toolbar);
    }


    private void setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.anime:
                    changeFragment(animeFragment, AnimeFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    break;
                case R.id.manga:
                    changeFragment(mangaFragment, MangaFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    break;
                case R.id.news:
                    changeFragment(newsFragment, NewsFragment.class.getSimpleName());
                    changeAppBarBackground(true);
                    break;
                case R.id.library:
                    changeFragment(libraryFragment, LibraryFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    break;

            }

            return true;
        });
    }

    private void changeAppBarBackground(boolean b) {
    if (b){
        appBarLayout.setBackgroundResource(R.drawable.tab_divider);
    } else {
        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.appbar_bottom_tab_color));
    }

    }

    public void changeFragment(Fragment fragment, String tagFragmentName) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment currentFragment = mFragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName);
        if (fragmentTemp == null) {
            fragmentTemp = fragment;
            fragmentTransaction
                    .setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out)
                    .add(R.id.framelayout, fragmentTemp, tagFragmentName);
        } else {
            fragmentTransaction.setCustomAnimations(R.anim.alpha_in, R.anim.alpha_out).show(fragmentTemp);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();
    }

}
