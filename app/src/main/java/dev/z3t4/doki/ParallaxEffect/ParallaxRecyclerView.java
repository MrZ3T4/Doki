package dev.z3t4.doki.ParallaxEffect;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ParallaxRecyclerView extends RecyclerView {

    private OnScrollListener scrollListener;

    public ParallaxRecyclerView(Context context) {
        super(context);
        init();
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParallaxRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOnScrollListener(defaultListener);
    }

    private OnScrollListener defaultListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (scrollListener != null)
                scrollListener.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                ViewHolder viewHolder = recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
                if (viewHolder instanceof ParallaxViewHolder) {
                    ((ParallaxViewHolder) viewHolder).animateImage();
                }
            }

            if (scrollListener != null)
                scrollListener.onScrolled(recyclerView, dx, dy);
        }
    };

    @SuppressWarnings("deprecation")
    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        if (listener != defaultListener)
            this.scrollListener = listener;
        else
            super.setOnScrollListener(listener);
    }

}
