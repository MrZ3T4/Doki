package dev.z3t4.doki.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import dev.z3t4.doki.ui.AnimeFragment;
import dev.z3t4.doki.ui.BroadcastFragment;
import dev.z3t4.doki.ui.CalendarFragment;
import dev.z3t4.doki.ui.DirectoryFragment;
import dev.z3t4.doki.ui.HistoryFragment;
import dev.z3t4.doki.ui.LibraryFragment;
import dev.z3t4.doki.ui.MangaFragment;
import dev.z3t4.doki.ui.NewsFragment;

public class AnimeViewPager {

    public void setupViewPager(ViewPager2 viewPager, FragmentManager fm, Lifecycle lifecycle){
        AnimeViewPagerAdapter adapter = new AnimeViewPagerAdapter(fm, lifecycle);

        adapter.addFragment(new BroadcastFragment());
        adapter.addFragment(new HistoryFragment());
        adapter.addFragment(new CalendarFragment());
        adapter.addFragment(new DirectoryFragment());

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);

    }

    public class AnimeViewPagerAdapter extends FragmentStateAdapter {

        ArrayList<Fragment> fragments = new ArrayList<>();

        AnimeViewPagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
            super(fm, lifecycle);
        }

        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragments.get(position);
        }

        @Override
        public int getItemCount() {
            return fragments.size();
        }
    }

}
