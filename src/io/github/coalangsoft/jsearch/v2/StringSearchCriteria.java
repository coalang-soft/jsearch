package io.github.coalangsoft.jsearch.v2;

import io.github.coalangsoft.lib.data.Func;

public class StringSearchCriteria<T> implements Func<T,Boolean> {

    private final String string;

    public StringSearchCriteria(String string){
        this.string = string;
    }

    @Override
    public Boolean call(T t) {
        return (t + "").contains(string);
    }

    public boolean equals(Object o){
        if(o instanceof StringSearchCriteria){
            return ((StringSearchCriteria) o).string.equals(string);
        }
        return false;
    }

}
