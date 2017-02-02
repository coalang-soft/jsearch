package io.github.coalangsoft.jsearch;

import java.util.ArrayList;
import java.util.Collections;

import io.github.coalangsoft.lib.data.ImutablePair;
import io.github.coalangsoft.lib.data.Pair;

public class JSearchEngine<R> {
	
	private ArrayList<Pair<String, ArrayList<R>>> index;
	
	public JSearchEngine(){
		index = new ArrayList<Pair<String, ArrayList<R>>>();
	}
	
	public void add(String key, R value){
		if(key == null){
			return;
		}
		for(int i = 0; i < index.size(); i++){
			Pair<String, ArrayList<R>> p = index.get(i);
			if(key.equals(p.getA())){
				p.getB().add(value);
				return;
			}
		}
		Pair<String, ArrayList<R>> p = new ImutablePair<String, ArrayList<R>>(key, new ArrayList<R>());
		p.getB().add(value);
		index.add(p);
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
				Pair<String, ArrayList<R>> p = index.get(k);
				if(p.getA().toLowerCase().contains(q.toLowerCase())){
					registerResult(p.getB(), result, values);
				}
			}
		}
		
		Collections.sort(result);
		return result;
	}

	private void registerResult(ArrayList<R> raw, ArrayList<JSearchResult<R>> result,
			ArrayList<R> values) {
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
			Pair<String, ArrayList<R>> p = index.get(i);
			if(p.getA().toLowerCase().contains(query.toLowerCase())){
				index.add(new ImutablePair<String, ArrayList<R>>(alias, p.getB()));
			}
		}
	}

	@Override
	public String toString() {
		return "JSearchEngine [index=" + index + "]";
	}
	
}
