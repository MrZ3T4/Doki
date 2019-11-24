package dev.z3t4.doki.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.News.Feed;
import dev.z3t4.doki.ParallaxEffect.ParallaxRecyclerView;
import dev.z3t4.doki.R;

public class NewsFragment extends Fragment {

    @BindView(R.id.recyclerview)
    ParallaxRecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_news, container, false);

        ButterKnife.bind(this, view);

        Feed feed = new Feed(getActivity(), recyclerView );
        feed.execute();

        return view;
    }
}
