package io.github.coalangsoft.jsearch.v2;

import io.github.coalangsoft.lib.data.Func;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartSearchEngine<T> implements SearchEngine<T> {

    private Map<T,List<Func<T,Boolean>>> map;
    private BasicSearchEngine<T> base;

    public SmartSearchEngine(BasicSearchEngine<T> base) {
        this.base = base;
        this.map = new HashMap<>();
    }

    public List<T> query(Func<T,Boolean>... criteria){
        List<T> quickResult = quickQuery(criteria);

        if(quickResult.size() == 0){
            return fullQuery(criteria);
        }else{
            new Thread(() -> {fullQuery(criteria);}).start();
            return quickResult;
        }
    }

    private List<T> fullQuery(Func<T, Boolean>[] criteria) {
        List<T> res = base.query(criteria);
        index(res,criteria);
        return res;
    }

    private void index(List<T> values, Func<T,Boolean>[] criteria){
        values.forEach(t -> {
            List<Func<T,Boolean>> funcs = map.get(t);
            if(funcs == null){
                funcs = new ArrayList<>();
                map.put(t,funcs);
            }
            for(int i = 0; i < criteria.length; i++){
                if(!funcs.contains(criteria[i])){
                    funcs.add(criteria[i]);
                }
            }
        });
    }

    private List<T> quickQuery(Func<T,Boolean>... criteria){
        List<T> results = new ArrayList<>();

        base.dataFactory.call(null).forEach(d -> {
            List<Func<T,Boolean>> critList = map.get(d);
            if(critList == null){
                critList = new ArrayList<>();
                map.put(d,critList);
            }
            for(int i = 0; i < criteria.length; i++){
                if(!critList.contains(criteria[i])){
                    return;
                }
            }
            results.add(d);
        });

        return results;
    }

}
