package dev.z3t4.doki.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.Adapter.AnimeViewPager;
import dev.z3t4.doki.R;
import dev.z3t4.doki.Utils.GenericContext;

import static dev.z3t4.doki.Utils.Constants.broadcast;
import static dev.z3t4.doki.Utils.Constants.calendar;
import static dev.z3t4.doki.Utils.Constants.directory;
import static dev.z3t4.doki.Utils.Constants.history;

public class AnimeFragment extends Fragment {

    @BindView(R.id.anime_viewpager) ViewPager2 viewPager;
    @BindView(R.id.anime_tablayout) TabLayout tabLayout;

    private AnimeViewPager animeViewPager = new AnimeViewPager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_anime, container, false);

        ButterKnife.bind(this, view);

        animeViewPager.setupViewPager(viewPager, getChildFragmentManager(), getLifecycle());

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position){
                        case 0:
                            tab.setText(broadcast);
                            break;
                        case 1:
                            tab.setText(history);
                            break;
                        case 2:
                            tab.setText(calendar);
                            break;
                        case 3:
                            tab.setText(directory);
                            break;
                    }
        }).attach();

        return view;
    }

}
