package dev.z3t4.doki.ParallaxEffect;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class ParallaxViewHolder extends RecyclerView.ViewHolder implements ParallaxImageView.ParallaxImageListener {

    private ParallaxImageView parallaxImageView;

    public abstract int getParallaxImageId();

    public ParallaxViewHolder(View itemView) {
        super(itemView);

        parallaxImageView = itemView.findViewById(getParallaxImageId());
        parallaxImageView.setListener(this);
    }

    @Override
    public int[] requireValuesForTranslate() {
        if (itemView.getParent() == null) {
            // Not added to parent yet!
            return null;
        } else {
            int[] itemPosition = new int[2];
            itemView.getLocationOnScreen(itemPosition);

            int[] recyclerPosition = new int[2];
            ((RecyclerView) itemView.getParent()).getLocationOnScreen(recyclerPosition);

            return new int[]{itemPosition[1], ((RecyclerView) itemView.getParent()).getMeasuredHeight(), recyclerPosition[1]};
        }
    }

    public void animateImage() {
        getParallaxImageView().doTranslate();
    }

    public ParallaxImageView getParallaxImageView() {
        return parallaxImageView;
    }
}

