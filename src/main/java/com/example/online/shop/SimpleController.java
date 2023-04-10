package com.example.online.shop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.online.shop.lucene.ElementList;
import com.example.online.shop.lucene.Indexer;
import com.example.online.shop.lucene.Searcher;

@Controller
public class SimpleController {
	
    private Searcher searcher;

	@GetMapping("/")
    public String initFrontEnd(Model model) throws Exception {
    	model.addAttribute("search", new SearchQuery());
    	String indexDirectoryPath = "index";
    	Indexer indexer = new Indexer(indexDirectoryPath);
        ElementList elementList = new ElementList();
        elementList.createDocumentList();
        indexer.indexDocument(elementList.getDocumentList());
        searcher = new Searcher(indexDirectoryPath);
        return "index";
    }
    
    @PostMapping("/search")
    public String processSearchForm(@ModelAttribute("search") SearchQuery search, Model model) throws Exception {
        String query = search.getQuery();
        
        int numResults = 10;
        TopDocs topDocs = searcher.search(query, numResults);
        
        Document document = null;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            document = searcher.getDocument(scoreDoc);
        }
        
        if(document == null) {
        	return "index";
        }else {
        	String productName = document.get("name");
        	model.addAttribute("name", document.get("name"));
        	model.addAttribute("description", document.get("description"));
        	model.addAttribute("color", document.get("color"));
        	model.addAttribute("price",ElementList.getPrice(productName));
        	model.addAttribute("image",ElementList.getImage(productName));
        	return "oneItem";
        }
    }
    
    @GetMapping("/autocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam String term) throws IOException {
      List<String> suggestions = new ArrayList<>();
      List<String> listSuggested = ElementList.getSuggested(term);
      for (String string : listSuggested) {
		suggestions.add(string);
	}
      
      return suggestions;
    }
    
    @RequestMapping("/download")
    public ResponseEntity<InputStreamResource> downloadTextFileExample1() throws FileNotFoundException {
    	String fileName = "sample.pdf";
    	
    	// Download file with InputStreamResource
    	File exportedFile = new File("E:\\onlineShop\\online.shop\\online.shop\\src\\main\\resources\\static\\download\\sample.pdf");
    	FileInputStream fileInputStream = new FileInputStream(exportedFile);
    	InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
    	
    	return ResponseEntity.ok()
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
    			.contentType(MediaType.TEXT_PLAIN)
    			.contentLength(exportedFile.length())
    			.body(inputStreamResource);
    }

}
