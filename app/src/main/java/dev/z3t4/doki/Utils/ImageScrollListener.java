package dev.z3t4.doki.Utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageScrollListener extends RecyclerView.OnScrollListener {

    private int request;

    public ImageScrollListener(int request){
        this.request = request;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            Thumbnails.ResumeRequest(request);
        }
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            Thumbnails.PauseRequest(request);
        }
        super.onScrollStateChanged(recyclerView, newState);
    }
}
