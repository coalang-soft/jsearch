package test.jsearch;

import io.github.coalangsoft.jsearch.JSearchEngine;

public class SearchTest {
	
	public static void main(String[] args) {
		JSearchEngine<String> se = new JSearchEngine<String>();
		se.addAll("operator", "+", "-", "*", "/");
		se.addAll("comparison", "lt", "gt", "eq", "le", "ge", "eq");
		se.addAll("variable", "pi", "e", "tau", "etau");
		se.addAll("circle", "pi", "tau");
		System.out.println(se.query("circle", "variable"));
	}
	
}
