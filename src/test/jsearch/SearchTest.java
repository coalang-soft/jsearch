package test.jsearch;

import io.github.coalangsoft.jsearch.v2.BasicSearchEngine;
import io.github.coalangsoft.jsearch.v2.SmartSearchEngine;
import io.github.coalangsoft.jsearch.v2.StringSearchCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchTest {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList("+","-","*","/","plus","minus","mal","geteilt","operator","test"));
		SmartSearchEngine<String> se = new SmartSearchEngine<String>(new BasicSearchEngine<>((v) -> list));
		System.out.println(se.query(new StringSearchCriteria("t")));
		list.addAll(Arrays.asList("t", "t2"));
		System.out.println(se.query(new StringSearchCriteria("t")));
		System.out.println(se.query(new StringSearchCriteria("t")));
		System.out.println(se.query(new StringSearchCriteria("t")));
	}
	
}
