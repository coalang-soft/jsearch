package io.github.coalangsoft.jsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.data.ImutablePair;
import io.github.coalangsoft.lib.data.Pair;

public class JSearchEngine<R> {

	{
		listeners = new ArrayList<Func<String,Object>>();
	}
	
	private List<Pair<String, List<R>>> index;
	private List<Func<String,Object>> listeners;
	
	public JSearchEngine(){
		index = new ArrayList<Pair<String, List<R>>>();
	}
	public JSearchEngine(List<Pair<String,List<R>>> l){
		this.index = l;
	}

	public void forAllKeys(Func<String,Object> f){
		for(int i = 0; i < index.size(); i++){
			f.call(index.get(i).getA());
		}
		this.listeners.add(f);
		System.out.println(listeners);
	}

	public void add(String key, R value){
		if(key == null){
			return;
		}
		for(int i = 0; i < index.size(); i++){
			Pair<String, List<R>> p = index.get(i);
			if(key.equals(p.getA())){
				p.getB().add(value);
				return;
			}
		}
		Pair<String, List<R>> p = new ImutablePair<String, List<R>>(key, new ArrayList<R>());
		p.getB().add(value);
		index.add(p);
		listeners(key);
	}
	
	public ArrayList<JSearchResult<R>> query(String... query){
		ArrayList<JSearchResult<R>> result = new ArrayList<JSearchResult<R>>();
		ArrayList<R> values = new ArrayList<R>();
		for(int i = 0; i < query.length; i++){
			String q = query[i];
			q = q.trim();
			if(q.isEmpty()){
				continue;
			}
			for(int k = 0; k < index.size(); k++){
				Pair<String, List<R>> p = index.get(k);
				if(p.getA().toLowerCase().contains(q.toLowerCase())){
					registerResult(p.getB(), result, values);
				}
			}
		}
		
		Collections.sort(result);
		return result;
	}

	private void registerResult(List<R> raw, List<JSearchResult<R>> result,
			List<R> values) {
		for(int i = 0; i < raw.size(); i++){
			R val = raw.get(i);
			if(values.contains(val)){
				result.get(values.indexOf(val)).upgrade();
			}else{
				values.add(val);
				result.add(new JSearchResult<R>(val));
			}
		}
	}

	public void addAll(String key, R... values) {
		for(int i = 0; i < values.length; i++){
			add(key, values[i]);
		}
	}
	
	public void alias(String query, String alias){
		for(int i = 0; i < index.size(); i++){
			Pair<String, List<R>> p = index.get(i);
			if(p.getA().toLowerCase().contains(query.toLowerCase())){
				index.add(new ImutablePair<String, List<R>>(alias, p.getB()));
			}
		}
		listeners(alias);
		System.out.println(listeners);
	}

	private void listeners(String key) {
		for(int i = 0; i < listeners.size(); i++){
			listeners.get(i).call(key);
		}
	}
	@Override
	public String toString() {
		return "JSearchEngine [index=" + index + "]";
	}
	
}
