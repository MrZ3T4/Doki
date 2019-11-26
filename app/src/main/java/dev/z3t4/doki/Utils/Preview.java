package dev.z3t4.doki.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import dev.z3t4.doki.R;

public class Preview extends Dialog {

    private Preview(@NonNull Context context) {
        this(context, R.style.PauseDialog);
    }

    /**
     * Constructor For Preview
     *
     * @param context    Context
     * @param themeResId Theme
     */
    private Preview(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    /**
     * dismiss this dialog when touch
     *
     * @param event MotionEvent
     * @return is consume this event
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                this.dismiss();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    /**
     * show this dialog
     */
    @Override
    public void show() {
        super.show();
    }

    /**
     * dismiss this dialog
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }

    /**
     * Builder for Preview
     */
    public static class Builder {
        private Preview preview;
        private Context mContext;
        private int mColor = 0x666666;

        /**
         * Constructor for Builder
         *
         * @param context Context
         */
        public Builder(Context context) {
            this.mContext = context;
            preview = new Preview(mContext);
        }

        /**
         * set content view for Preview
         *
         * @param layoutResID Layout
         * @return Builder
         */
        public Builder setContentView(@LayoutRes int layoutResID) {
            this.preview.setContentView(layoutResID);
            return this;
        }

        /**
         * set content view for Preview
         *
         * @param view View
         * @return Builder
         */
        public Builder setContentView(@NonNull View view) {
            this.preview.setContentView(view);
            return this;
        }

        public Builder setBackground(@ColorInt int colorResId) {
            this.mColor = colorResId;
            return this;
        }

        /**
         * return an Instance of Preview
         *
         * @return Preview
         */
        public Preview build() {
            ColorDrawable colorDrawable = new ColorDrawable(mColor);

            Window window = preview.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(colorDrawable);
            }
            return preview;
        }
    }
}
