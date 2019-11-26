package dev.z3t4.doki.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.News.Feed;
import dev.z3t4.doki.ParallaxEffect.ParallaxRecyclerView;
import dev.z3t4.doki.R;
import dev.z3t4.doki.Utils.GenericContext;
import dev.z3t4.doki.Utils.ImageScrollListener;
import dev.z3t4.doki.Utils.Thumbnails;

public class NewsFragment extends Fragment {

    @BindView(R.id.recyclerview)
    ParallaxRecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private ImageScrollListener imageScrollListener = new ImageScrollListener(1);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.host_news, container, false);

        ButterKnife.bind(this, view);

        Feed feed = new Feed(getActivity(), recyclerView, progressBar );
        feed.execute();
        manageImageLoading();

        return view;
    }

    private void manageImageLoading() {
        recyclerView.addOnScrollListener(imageScrollListener);
    }
}
