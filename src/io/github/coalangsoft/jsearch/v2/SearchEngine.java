package io.github.coalangsoft.jsearch.v2;

import io.github.coalangsoft.jsearch.JSearchResult;
import io.github.coalangsoft.lib.data.Func;

import java.io.Serializable;
import java.util.List;

public interface SearchEngine<T> extends Serializable {

    List<T> query(Func<T,Boolean>... criteria);

}
