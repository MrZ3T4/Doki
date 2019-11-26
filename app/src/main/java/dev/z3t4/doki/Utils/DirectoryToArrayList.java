package dev.z3t4.doki.Utils;

import android.content.Intent;
import android.os.AsyncTask;


import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

import dev.z3t4.doki.Pojo.Anime;


public class DirectoryToArrayList extends AsyncTask<Void, Void, Void> {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<Anime> tvArrayList, moviesArrayList, ovaArrayList, specialArrayList;

    private String JSON_URL;
    private int directory_type;

    public DirectoryToArrayList(String JSON_URL, int directory_type) {
        this.JSON_URL = JSON_URL;
        this.directory_type = directory_type;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        arrayFilter(directory_type, null, false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        arrayFilter(directory_type, null, false, true);

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            System.out.println("conectando...");
            String json = Jsoup.connect(JSON_URL).ignoreContentType(true).get().body().text();

            JSONArray animes = new JSONArray(json);

            for (int position = 0; position < animes.length(); position++) {

                JSONObject jsonObject = animes.getJSONObject(position);

                String title = jsonObject.getString("title");
                String sinopsis = jsonObject.getString("sinopsis");
                String poster = jsonObject.getString("poster").substring(20);
                String type = jsonObject.getString("type");
                String rating = jsonObject.getString("rating");

                Anime anime = new Anime();

                anime.setTitle(title);
                anime.setPoster(poster);
                anime.setSinopsis(sinopsis);
                anime.setType(type);
                anime.setRating(rating);

                arrayFilter(directory_type, anime, true, false);

            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void arrayFilter(int directory_type, Anime anime, boolean doInBackground, boolean postExecute) {

        switch (directory_type){
            case 1:
                if (doInBackground && !postExecute){
                    tvArrayList.add(anime);
                } else if (!doInBackground && !postExecute){
                    tvArrayList = new ArrayList<>();
                } else {
                    sendBroadcast("tv", tvArrayList, directory_type);
                }
                break;
            case 2:
                if (doInBackground && !postExecute){
                    moviesArrayList.add(anime);
                } else if (!doInBackground && !postExecute){
                    moviesArrayList = new ArrayList<>();
                } else {
                    sendBroadcast("movies", moviesArrayList, directory_type);
                }
                break;
            case 3:
                if (doInBackground && !postExecute){
                    ovaArrayList.add(anime);
                } else if (!doInBackground && !postExecute){
                    ovaArrayList = new ArrayList<>();
                } else {
                    sendBroadcast("ova", ovaArrayList, directory_type);
                }
                break;
            case 4:
                if (doInBackground && !postExecute){
                    specialArrayList.add(anime);
                } else if (!doInBackground && !postExecute){
                    specialArrayList = new ArrayList<>();
                } else {
                    sendBroadcast("special", specialArrayList, directory_type);
                }
                break;
        }
    }

    private void sendBroadcast(String name, ArrayList arrayList, int directory_type){
        Intent intent = new Intent("directory");
        intent.putExtra(name, arrayList);
        intent.putExtra("directory_type", directory_type);
        LocalBroadcastManager.getInstance(GenericContext.getContext()).sendBroadcast(intent);
    }

}
