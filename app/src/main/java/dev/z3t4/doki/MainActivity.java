package dev.z3t4.doki;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    @BindView(R.id.fab) FloatingActionButton fab;

    private AnimeFragment animeFragment = new AnimeFragment();
    private MangaFragment mangaFragment = new MangaFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private LibraryFragment libraryFragment = new LibraryFragment();

    private MenuItem sort;
    private int directory_type;

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
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0){
                fab.show();
            } else {
                fab.hide();
            }
        });
    }


    private void setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()){

                case R.id.anime:
                    changeFragment(animeFragment, AnimeFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    sort.setVisible(true);
                    break;
                case R.id.manga:
                    changeFragment(mangaFragment, MangaFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    sort.setVisible(false);
                    break;
                case R.id.news:
                    changeFragment(newsFragment, NewsFragment.class.getSimpleName());
                    changeAppBarBackground(true);
                    sort.setVisible(false);
                    break;
                case R.id.library:
                    changeFragment(libraryFragment, LibraryFragment.class.getSimpleName());
                    changeAppBarBackground(false);
                    sort.setVisible(false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        sort = menu.findItem(R.id.sort);

        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.all:
                    item.setChecked(true);
                    directory_type = 1;
                    break;
                case R.id.tv:
                    item.setChecked(true);
                    directory_type = 2;
                    break;
                case R.id.movies:
                    item.setChecked(true);
                    directory_type = 3;
                    break;
                case R.id.ova:
                    item.setChecked(true);
                    directory_type = 4;
                    break;
                case R.id.special:
                    item.setChecked(true);
                    directory_type = 5;
                    break;
                    default: directory_type = 0;

            }

            sendSortBy(directory_type);

            return false;
        });
        return true;
    }

    private void sendSortBy(int directory_type) {
        Intent intent = new Intent("sortBy");
        intent.putExtra("directory_type", directory_type);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
