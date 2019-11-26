package dev.z3t4.doki.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.z3t4.doki.Pojo.Anime;
import dev.z3t4.doki.R;
import dev.z3t4.doki.Utils.Preview;
import dev.z3t4.doki.Utils.Thumbnails;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {

    private ArrayList<Anime> directory;
    private Context context;

    public DirectoryAdapter(ArrayList<Anime> directory, Context context) {
        this.directory = directory;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.directory_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Anime anime = directory.get(position);

        Thumbnails.PicassoDirectory(anime.getPoster(), holder.imageView);
        holder.textView.setText(anime.getTitle());

        holder.cardView.setOnLongClickListener(view -> {

            View v = View.inflate(context, R.layout.long_preview, null);

            AppCompatImageView imageView = v.findViewById(R.id.preview_poster);
            AppCompatTextView title = v.findViewById(R.id.preview_title);
            AppCompatTextView sinopsis = v.findViewById(R.id.preview_sinopsis);

            title.setText(anime.getTitle());
            sinopsis.setText(anime.getSinopsis());

            Thumbnails.PicassoDirectory(anime.getPoster(), imageView);

            new Preview.Builder(context)
                    .setBackground(0x1B1B1B)
                    .setContentView(v)
                    .build()
                    .show();

            return false;
        });

    }

    @Override
    public int getItemCount() {
        return directory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.anime_poster) AppCompatImageView imageView;
        @BindView(R.id.anime_title) AppCompatTextView textView;
        @BindView(R.id.anime_cardview) MaterialCardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }
}
