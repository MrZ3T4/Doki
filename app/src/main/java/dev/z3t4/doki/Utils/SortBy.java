package dev.z3t4.doki.Utils;

import java.util.ArrayList;
import java.util.Collections;

import dev.z3t4.doki.Pojo.NewsPojo;

public class SortBy {

    public void getArrayListByDate(ArrayList<NewsPojo> newsPojoArrayList){
        Collections
                .sort(newsPojoArrayList,
                (o1, o2) -> o2.getDate()
                        .compareTo(o1.getDate()));
}
}
