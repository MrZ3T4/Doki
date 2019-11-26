package dev.z3t4.doki.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.Adapter.DirectoryAdapter;
import dev.z3t4.doki.Cloud.Directory;
import dev.z3t4.doki.Pojo.Anime;
import dev.z3t4.doki.R;
import dev.z3t4.doki.Utils.GenericContext;
import dev.z3t4.doki.Utils.ImageScrollListener;
import dev.z3t4.doki.Utils.SortBy;
import dev.z3t4.doki.Utils.Thumbnails;

public class DirectoryFragment extends Fragment {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.progressbar) ProgressBar progressBar;

    private Directory directory = new Directory();

    private ArrayList<Anime> tvArraylist, moviesArraylist, ovaArraylist, specialArraylist;
    private ArrayList<Anime> globalArrayList = new ArrayList<>();

    private int count = 0;

    private SortBy sortBy = new SortBy();

    private ImageScrollListener imageScrollListener = new ImageScrollListener(2);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);
        ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addOnScrollListener(imageScrollListener);

        localBroadcastManager();
        directory.getDirectoryFLV();

        return view;
    }

    private void manageImageLoading() {
        if (count == 4) {
            new Handler().postDelayed(() ->{
                    recyclerView.removeOnScrollListener(imageScrollListener);
                    Thumbnails.ResumeRequest(2);
        },4000);
        }
    }

    private void localBroadcastManager() {
        LocalBroadcastManager.getInstance(GenericContext.getContext())
                .registerReceiver(getDirectory, new IntentFilter("directory"));

        LocalBroadcastManager.getInstance(GenericContext.getContext())
                .registerReceiver(getSortBy, new IntentFilter("sortBy") );
    }

    private BroadcastReceiver getDirectory = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switchArraylist(intent, true);
            if (count == 4) {
                sortBy.getArrayListByTitle(globalArrayList);
                setAdapter(globalArrayList);
                manageImageLoading();
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    private BroadcastReceiver getSortBy = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int directory_sort = intent.getIntExtra("directory_type", 0);
            sortArraylist(directory_sort);
        }
    };

    private void sortArraylist(int directory_sort) {
        switch (directory_sort) {
            case 1:
                setAdapter(globalArrayList);
                break;
            case 2:
                setAdapter(tvArraylist);
                break;
            case 3:
                setAdapter(moviesArraylist);
                break;
            case 4:
                setAdapter(ovaArraylist);
                break;
            case 5:
                setAdapter(specialArraylist);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private void switchArraylist(Intent intent, boolean fromAsynckTask) {
        int directory_type = intent.getIntExtra("directory_type", 0);
        switch (directory_type){
            case 1:
                tvArraylist = (ArrayList<Anime>) intent.getSerializableExtra("tv");
                //setAdapter(tvArraylist);
                globalArrayList.addAll(tvArraylist);
                count++;
                break;
            case 2:
                moviesArraylist = (ArrayList<Anime>) intent.getSerializableExtra("movies");
                //setAdapter(moviesArraylist);
                globalArrayList.addAll(moviesArraylist);
                count++;
                break;
            case 3:
                ovaArraylist = (ArrayList<Anime>) intent.getSerializableExtra("ova");
                //setAdapter(ovaArraylist);
                globalArrayList.addAll(ovaArraylist);
                count++;
                break;
            case 4:
                specialArraylist = (ArrayList<Anime>) intent.getSerializableExtra("special");
                //setAdapter(specialArraylist);
                globalArrayList.addAll(specialArraylist);
                count++;
                break;
        }
    }

    private void setAdapter(ArrayList<Anime> arraylist) {
        DirectoryAdapter directoryAdapter = new DirectoryAdapter(arraylist, getActivity());
        recyclerView.setAdapter(directoryAdapter);
        recyclerView.getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                        for (int i = 0; i < recyclerView.getChildCount(); i++) {
                            View v = recyclerView.getChildAt(i);
                            v.setAlpha(0.0f);
                            v.animate().alpha(1.0f)
                                    .setDuration(300)
                                    .setStartDelay(i * 50)
                                    .start();
                        }

                        return true;
                    }
                });
        directoryAdapter.notifyDataSetChanged();
    }

}
