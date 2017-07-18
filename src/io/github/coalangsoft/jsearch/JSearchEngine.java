package io.github.coalangsoft.jsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.coalangsoft.lib.data.Func;
import io.github.coalangsoft.lib.data.ImutablePair;
import io.github.coalangsoft.lib.data.Pair;

/**
 * A search engine to search through a given list of things by string.
 * @author Matthias
 *
 * @param <R> - The type of objects to search and find.
 */
public class JSearchEngine<R> {

	{
		listeners = new ArrayList<Func<String,Object>>();
	}
	
	//the search index. Every entry is a pair of the query and the matching results.
	private List<Pair<String, List<R>>> index;
	//a list of listeners. These are used when a new entry (query string) is added to this search engine.
	private List<Func<String,Object>> listeners;
	
	/**
	 * Creates an "empty" search engine.
	 */
	public JSearchEngine(){
		index = new ArrayList<Pair<String, List<R>>>();
	}
	/**
	 * Creates a pre-filled search engine.
	 * @param l the search index to start with.
	 * Every entry is a pair of the query and the matching results.
	 */
	public JSearchEngine(List<Pair<String,List<R>>> l){
		this.index = l;
	}

	/**
	 * Iterates over all registered queries and runs a function for each.
	 * Also adds the function as a listener. So it is called whenever a new query is added.
	 * @param f the function.
	 */
	public void forAllKeys(Func<String,Object> f){
		for(int i = 0; i < index.size(); i++){
			f.call(index.get(i).getA());
		}
		this.listeners.add(f);
	}

	/**
	 * Adds a new key value pair to the search index.
	 * If the query (key) already exists, the value is added to the matching results.
	 * Otherwise a new entry for this query is created, with one matching result (value).
	 * If this is the case, the listeners are called. To add a listener, use {@link #forAllKeys(Func)}
	 * @param key the search query
	 * @param value the value to find
	 */
	public void add(String key, R value){
		if(key == null){
			//Nothing to add.. TODO: warn the user
			return;
		}
		
		//go through every entry of the search index
		for(int i = 0; i < index.size(); i++){
			Pair<String, List<R>> p = index.get(i);
			if(key.equals(p.getA())){
				//If the query of this part matches "key", extend its matching result list and finish.
				p.getB().add(value);
				return;
			}
		}
		Pair<String, List<R>> p = new ImutablePair<String, List<R>>(key, new ArrayList<R>());
		p.getB().add(value);
		index.add(p);
		listeners(key);
	}
	
	/**
	 * Searches for a set of queries, and returns the result as a list.
	 * The results get compared, so if there is the same value found with say two of the queries,
	 * it appears before the values with one matching query.
	 * @param query the queries to search by.
	 * @return the search result.
	 */
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

	/**
	 * Utility method to register a search result to the finished search result
	 * list provided by {@link #query(String...)}
	 * @param raw the raw data to add to the result list.
	 * @param result the result list to fill.
	 * @param values a list containing all values that are already inside the result list.
	 */
	private void registerResult(List<R> raw, List<JSearchResult<R>> result,
			List<R> values) {
		for(int i = 0; i < raw.size(); i++){
			R val = raw.get(i);
			if(values.contains(val)){
				//This value was already found. Upgrade the search result rank.
				result.get(values.indexOf(val)).upgrade();
			}else{
				//This value is new. Add a new search result for it to the results.
				values.add(val);
				result.add(new JSearchResult<R>(val));
			}
		}
	}

	/**
	 * A shorthand for the {@link #add(String, Object)} method that adds multiple values to the same key.
	 * @param key
	 * @param values
	 */
	public void addAll(String key, R... values) {
		for(int i = 0; i < values.length; i++){
			add(key, values[i]);
		}
	}
	
	/**
	 * Clones the entries from one search query and adds them to another one.
	 * @param query the search query to copy from.
	 * @param alias the search query to copy to.
	 */
	public void alias(String query, String alias){
		for(int i = 0; i < index.size(); i++){
			Pair<String, List<R>> p = index.get(i);
			if(p.getA().toLowerCase().contains(query.toLowerCase())){
				index.add(new ImutablePair<String, List<R>>(alias, p.getB()));
			}
		}
		listeners(alias);
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
