package dev.z3t4.doki.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import dev.z3t4.doki.Adapter.NewsAdapter;
import dev.z3t4.doki.ParallaxEffect.ParallaxRecyclerView;
import dev.z3t4.doki.Pojo.NewsPojo;
import dev.z3t4.doki.Utils.CustomItemDecorator;
import dev.z3t4.doki.Utils.PreCachingLayoutManager;
import dev.z3t4.doki.Utils.SortBy;

import static dev.z3t4.doki.Utils.Constants.SRC_ANMO;
import static dev.z3t4.doki.Utils.Constants.SRC_CRUNCHYROLL;
import static dev.z3t4.doki.Utils.Constants.SRC_KUDASAI;
import static dev.z3t4.doki.Utils.Constants.SRC_RP2;

public class Feed extends AsyncTask<Void,Void,Void> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ParallaxRecyclerView recyclerView;

    private SortBy sortBy = new SortBy();
    private ArrayList<NewsPojo> newsPojoArrayList = new ArrayList<>();

    public Feed(Context context, ParallaxRecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        getCrunchyroll();
        getRamenPara2();
        getKudasai();
        getANMO();
        return null;
    }

    private void getANMO() { getFeed(SRC_ANMO); }

    private void getKudasai() {
        getFeed(SRC_KUDASAI);
    }

    private void getCrunchyroll() {
        getFeed(SRC_CRUNCHYROLL);
    }

    private void getRamenPara2() {
        getFeed(SRC_RP2);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        sortBy.getArrayListByDate(newsPojoArrayList);

        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        NewsAdapter adapter = new NewsAdapter(context, newsPojoArrayList);

        recyclerView.setLayoutManager(new PreCachingLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomItemDecorator(60));

    }

    private void getFeed(String source) {

        try {
            Document doc = Jsoup.connect(source).parser(Parser.xmlParser()).get();
            if (doc != null) {
                Elements getNews = doc.select("item");
                for (Element content : getNews) {

                    NewsPojo newsPojo = new NewsPojo();
                    String title, autor, date, image, url, src, kategory, category;

                    title = content.getElementsByTag("title").text();
                    //description = content.getElementsByTag("description").text();
                    date = content.getElementsByTag("pubDate").text();
                    url = content.getElementsByTag("link").text();
                    kategory = content.getElementsByTag("category").text();
                    category = getCategory(kategory, source);
                    src = getSrc(source);

                    if (source.equals(SRC_CRUNCHYROLL)){
                        autor = "Crunchyroll";
                    } else {
                        autor = content.getElementsByTag("dc:creator").text();
                    }
                    if (!source.equals(SRC_ANMO)) {
                        image = content.getElementsByTag("content:encoded").text();
                        newsPojo.setImage(image);
                    }

                    newsPojo.setTitle(title);
                    newsPojo.setAutor(autor);
                    newsPojo.setDate(date);
                    newsPojo.setUrl(url);
                    newsPojo.setSrc(src);
                    newsPojo.setCategory(category);

                    newsPojoArrayList.add(newsPojo);
                }


            } else {
                System.out.println("--Document is null--");
            }
        } catch (IOException e) {
            switch (source){
                case SRC_ANMO:
                    getANMO();
                    break;
                case SRC_KUDASAI:
                    getKudasai();
                    break;
                case SRC_CRUNCHYROLL:
                    getCrunchyroll();
                    break;
                case SRC_RP2:
                    getRamenPara2();
                    break;
            }
            e.printStackTrace();
        }

    }

    private String getSrc(String source) {
        String src = "";
        switch (source){
            case SRC_ANMO:
                src = "ANMO Sugoi";
                break;
            case SRC_KUDASAI:
                src = "SomosKudasai";
                break;
            case SRC_CRUNCHYROLL:
                src = "Crunchyroll";
                break;
            case SRC_RP2:
                src = "RamenParaDos";
                break;
        }
        return src;
    }

    private String getCategory(String kategory, String source) {
    String category;
    if (!source.equals(SRC_CRUNCHYROLL)) {

        if (kategory.contains("Reseñas y Reviews")) {
            category = "Reseñas y Reviews";
        } else if (kategory.contains("Noticia")) {
            category = "Noticias";
        } else if (kategory.contains("Reseñas Manga")){
            category = "Reseñas Manga";
        } else if (kategory.contains("Anime")){
            category = "Anime";
        } else if (kategory.contains("Videojuegos")){
            category = "Videojuegos";
        } else {
            category = "Noticias";
        }
    } else {
        category = "Noticias";
    }
    return category;
    }

}
