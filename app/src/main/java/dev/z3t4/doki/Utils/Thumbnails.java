package dev.z3t4.doki.Utils;

import android.view.View;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.z3t4.doki.ParallaxEffect.ParallaxImageView;

import static dev.z3t4.doki.Utils.Constants.twitter;

public class Thumbnails {

    public static void getRP2Image(String image, ParallaxImageView imageView){

        if (image.contains("<img")) {
            String img = image.substring
                    (image.indexOf("\" src=\"https"),
                    image.indexOf("\" class"))
                    .substring(7);

            PicassoParallax(img, imageView);
        }

    }

    public static void getCrunchyrollImage(String image, ParallaxImageView imageView) {

        if (image.contains("<img")) {
            String img = image.substring(image.indexOf("src=\""),
                    image.indexOf("\" alt")).substring(5);
            PicassoParallax(img, imageView);
        }

    }

    public static void getKudasaiImage(String image, ParallaxImageView imageView) {

        if (image.contains("<img")) {

            imageView.setVisibility(View.VISIBLE);
            String img = image
                    .substring(image.indexOf("<img src=\""),
                            image.indexOf("\" alt")).substring(10).replace("http", "https")
                    .replaceAll("httpss", "https");
            PicassoParallax(img, imageView);

        } else if (image.contains("\" src=\"")) {

            if (image.contains("youtube")) {
                String getYoutubeThumb = image.substring(image.indexOf("\" src=\"https"));
                String img = "https://img.youtube.com/vi/" + getYoutubeThumb.substring(getYoutubeThumb.indexOf("d/")
                        , getYoutubeThumb.indexOf("?")).replaceFirst("d/", "") + "/0.jpg";

                PicassoParallax(img, imageView);

            } else if (image.contains("dailymotion")) {
                String getDailymotionThumb = image
                        .substring(image.indexOf("\" src=\""),
                                image.indexOf("\" a")).substring(47);
                String img = "https://www.dailymotion.com/thumbnail/video/" + getDailymotionThumb;

                PicassoParallax(img, imageView);

            } else if (image.contains("<center>")) {
                PicassoParallax(twitter, imageView);
                }

        } else { imageView.setVisibility(View.GONE);
            Picasso.get().cancelRequest(imageView); }


    }

    private static void PicassoParallax(String url, ParallaxImageView imageView){
        Picasso.get()
                .load(url)
                .resize(600,400)
                .centerCrop()
                .into(imageView);
    }

    public static void PicassoCircle(String url, CircleImageView imageView){
        Picasso.get()
                .load(url)
                .into(imageView);
    }

}
