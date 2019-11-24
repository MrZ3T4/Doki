package dev.z3t4.doki.Adapter;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import dev.z3t4.doki.ParallaxEffect.ParallaxViewHolder;
import dev.z3t4.doki.Pojo.NewsPojo;
import dev.z3t4.doki.R;
import dev.z3t4.doki.Utils.DateManager;
import dev.z3t4.doki.Utils.Thumbnails;
import saschpe.android.customtabs.CustomTabsHelper;
import saschpe.android.customtabs.WebViewFallback;

import static dev.z3t4.doki.Utils.Constants.ANMO;
import static dev.z3t4.doki.Utils.Constants.Crunchyroll;
import static dev.z3t4.doki.Utils.Constants.KUDASAI;
import static dev.z3t4.doki.Utils.Constants.RP2;
import static dev.z3t4.doki.Utils.Constants.anmo_logo;
import static dev.z3t4.doki.Utils.Constants.crunchy_logo;
import static dev.z3t4.doki.Utils.Constants.kudasai_logo;
import static dev.z3t4.doki.Utils.Constants.rp2_logo;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private DateManager dateManager = new DateManager();

    private Context context;
    private ArrayList<NewsPojo> newsPojoArrayList;

    public NewsAdapter(Context context, ArrayList<NewsPojo> newsPojoArrayList) {
        this.context = context;
        this.newsPojoArrayList = newsPojoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_model, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder view, int position) {
        NewsPojo current = newsPojoArrayList.get(position);

        String header = context.getResources().getString(R.string.header);
        String niceDate = dateManager.convertToNiceDate(current.getDate());

        view.getParallaxImageView().reuse();
        view.getParallaxImageView().setParallaxRatio(1f);

        view.title.setText(current.getTitle());
        /*view.autor.setText(Html.fromHtml
                ( "<font color=\"#000000\">" + current.getAutor()
                        + "</font>" + header + niceDate));
*/
        view.autor.setText(current.getAutor().concat(header).concat(niceDate));

        view.source.setText("#".concat(current.getCategory()));

        switch (current.getSrc()){
            case ANMO:
                view.getParallaxImageView().setVisibility(View.GONE);
                Thumbnails.PicassoCircle(anmo_logo, view.source_img);
                break;
           case KUDASAI:
                Thumbnails.getKudasaiImage(current.getImage(), view.getParallaxImageView());
                Thumbnails.PicassoCircle(kudasai_logo, view.source_img);
                break;
            case Crunchyroll:
                Thumbnails.getCrunchyrollImage(current.getImage(), view.getParallaxImageView());
                Thumbnails.PicassoCircle(crunchy_logo, view.source_img);
                break;
            case RP2:
                Thumbnails.getRP2Image(current.getImage(), view.getParallaxImageView());
                Thumbnails.PicassoCircle(rp2_logo, view.source_img);
                break;
        }

        view.cardView.setOnClickListener(v -> {
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .addDefaultShareMenuItem()
                    //.setToolbarColor(context.getResources().getColor(R.color.toolBarStatusBarColor))
                    .setShowTitle(true)
                    .build();
            CustomTabsHelper.addKeepAliveExtra
                    (context, customTabsIntent.intent);
            CustomTabsHelper.openCustomTab
                    (context, customTabsIntent,
                            Uri.parse(current.getUrl()),
                            new WebViewFallback());

        });

    }

    @Override
    public int getItemCount() {
        return newsPojoArrayList.size();
    }

    public class ViewHolder extends ParallaxViewHolder {

        @BindView(R.id.news)
        CardView cardView;
        @BindView(R.id.title)
        AppCompatTextView title;
        @BindView(R.id.autor)
        AppCompatTextView autor;
        @BindView(R.id.date)
        AppCompatTextView date;
        @BindView(R.id.source)
        AppCompatTextView source;
        @BindView(R.id.source_img) CircleImageView source_img;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getParallaxImageId() {
            return R.id.parallax_iv;
        }
    }
}
