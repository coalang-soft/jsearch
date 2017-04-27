package io.github.coalangsoft.jsearch;

public class JSearchResult<T> implements Comparable<JSearchResult<T>>{
	
	private int rank = 0;
	private final T value;
	
	JSearchResult(T value){
		this.value = value;
	}
	
	void upgrade(){
		rank++;
	}
	
	public int getRank(){
		return rank;
	}

	@Override
	public int compareTo(JSearchResult<T> other) {
		int r = other.rank;
		if(r == rank){
			return 0;
		}
		return r < rank ? -1 : 1;
	}

	@Override
	public String toString() {
		return "JSearchResult [rank=" + rank + ", value=" + value + "]";
	}

	public T getValue() {
		return value;
	}
	
}
