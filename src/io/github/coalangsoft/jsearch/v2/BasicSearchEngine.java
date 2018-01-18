package io.github.coalangsoft.jsearch.v2;

import io.github.coalangsoft.jsearch.JSearchResult;
import io.github.coalangsoft.lib.data.Func;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicSearchEngine<T> implements SearchEngine<T>{

    protected final Func<Void, Iterable<T>> dataFactory;

    public BasicSearchEngine(Func<Void,Iterable<T>> dataFactory){
        this.dataFactory = dataFactory;
    }

    @Override
    public List<T> query(Func<T, Boolean>... criteria) {
        ArrayList<T> results = new ArrayList<T>();

        dataFactory.call(null).forEach((d) -> {
            for(int i = 0; i < criteria.length; i++){
                if(!criteria[i].call(d)){
                    return;
                }
            }
            results.add(d);
        });

        return results;
    }

}
