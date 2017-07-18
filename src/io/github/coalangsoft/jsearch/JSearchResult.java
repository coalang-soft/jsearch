package io.github.coalangsoft.jsearch;

/**
 * Represents a ranked search result provided by a {@link JSearchEngine}.
 * @author Matthias
 *
 * @param <T> the type of values to find.
 */
public class JSearchResult<T> implements Comparable<JSearchResult<T>>{
	
	//the rank - higher means found more often
	private int rank = 0;
	//the value
	private final T value;
	
	/**
	 * Creates a search result with rank 0.
	 * @param value the value
	 */
	JSearchResult(T value){
		this.value = value;
	}
	
	/**
	 * Upgrades the rank of this search result by one.
	 */
	void upgrade(){
		rank++;
	}
	
	/**
	 * Returns the rank of this search result. Higher means found more often.
	 * @return the rank.
	 */
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

	/**
	 * Returns the value of this search result.
	 * @return the value.
	 */
	public T getValue() {
		return value;
	}
	
}
