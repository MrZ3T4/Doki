package dev.z3t4.doki.Cloud;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import dev.z3t4.doki.Utils.DirectoryToArrayList;

import static dev.z3t4.doki.Utils.Constants.animeflv_movies;
import static dev.z3t4.doki.Utils.Constants.animeflv_ova;
import static dev.z3t4.doki.Utils.Constants.animeflv_special;
import static dev.z3t4.doki.Utils.Constants.animeflv_tv;

public class Directory {

    private StorageReference mainStorage = FirebaseStorage.getInstance().getReference();
    private String url;
    private int type;

    public void getDirectoryFLV(){
        getFirebaseJson(animeflv_tv);
        getFirebaseJson(animeflv_movies);
        getFirebaseJson(animeflv_ova);
        getFirebaseJson(animeflv_special);
    }

    private void getFirebaseJson(String child){

        StorageReference storage = mainStorage.child(child);
        storage.getDownloadUrl().addOnSuccessListener(uri -> {

            url = uri.toString();
            System.out.println(url);

            switch (child) {
                case animeflv_tv:
                    type = 1;
                    break;
                case animeflv_movies:
                    type = 2;
                    break;
                case animeflv_ova:
                    type = 3;
                    break;
                case animeflv_special:
                    type = 4;
                    break;
            }

            DirectoryToArrayList directoryToArrayList = new DirectoryToArrayList(url, type);
            directoryToArrayList.execute();

        });
    }

}
