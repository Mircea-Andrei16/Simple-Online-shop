package com.example.online.shop.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	private final Directory indexDirectory;

    public Indexer(String indexDirectoryPath) throws IOException {
        indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
    }
	
	public static Document createDocument(String name, String description, String color) {
		Document document = new Document();
		Field nameField = new TextField("name", name, Field.Store.YES);
		document.add(nameField);
		Field descriptionField = new TextField("description", description, Field.Store.YES);
		document.add(descriptionField);
		Field colorField = new TextField("color", color, Field.Store.YES);
		document.add(colorField);
		
		return document;
	}

	public void indexDocument(Document[] documentList) throws Exception {
		Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(indexDirectory, config);
        
        for (Document document : documentList) {
        	writer.addDocument(document);
		}
        
        writer.close();
    }
}
