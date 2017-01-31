#JSearch - Search Engine Base for Java
A Searchengine is useful for many things (Searching for Websites, Files, Texts, ...). With JSearch, you can setup a Searchengine easily:

```
//Get a JSearchEngine - In this case we want to find Strings
JSearchEngine<String> se = new JSearchEngine<String>();

//Add information (first parameter is the keyword)
se.addAll("operator", "+", "-", "*", "/");
se.addAll("comparison", "lt", "gt", "eq", "le", "ge", "eq");
se.addAll("variable", "pi", "e", "tau", "etau");
se.addAll("circle", "pi", "tau");

//And Search
System.out.println(se.query("circle", "variable"));
```

##JSearch extensions
[JSearchFX - Find, highlight and select JavaFX Nodes](https://github.com/coalang-soft/jsearchfx)
