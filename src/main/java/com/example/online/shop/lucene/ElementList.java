package com.example.online.shop.lucene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;

public class ElementList {
	
	private Document[] documentList;
	
	private static final Map<String, String[]> map;
	
	static {
		Map<String, String[]> amap = new HashMap<>();
		// Clothing
		String[] list1 = new String[2];
		list1[0] = "100";
		list1[1] = "clothing/coat.jpg";
		amap.put("HOODED PARKA", list1);
		
		String[] list2 = new String[2];
		list2[0] = "15";
		list2[1] = "clothing/hat.jpg";
		amap.put("BLACK HAT", list2);
		
		String[] list3 = new String[2];
		list3[0] = "35";
		list3[1] = "clothing/shorts.jpg";
		amap.put("SHORTS", list3);
		
		String[] list4 = new String[2];
		list4[0] = "40";
		list4[1] = "clothing/tshirt.jpg";
		amap.put("ADIDAS SHIRT", list4);
		
		// various
		String[] list5 = new String[2];
		list5[0] = "150";
		list5[1] = "various/backpack.jpg";
		amap.put("BACKPACK", list5);
		
		String[] list6 = new String[2];
		list6[0] = "70.04";
		list6[1] = "various/buds.jpg";
		amap.put("WIRELESS EARBUDS", list6);
		
		String[] list7 = new String[2];
		list7[0] = "35";
		list7[1] = "various/glasses.jpg";
		amap.put("SILVER SUNGLASSES", list7);
		
		String[] list8 = new String[2];
		list8[0] = "24.30";
		list8[1] = "various/water.jpg";
		amap.put("STRAW METAL BOTTLE", list8);
		
		// Shoes
		String[] list9 = new String[2];
		list9[0] = "100";
		list9[1] = "shoes/ultraboost-22.jpg";
		amap.put("ULTRABOOST 22", list9);
		
		String[] list10 = new String[2];
		list10[0] = "230";
		list10[1] = "shoes/mccartney.jpg";
		amap.put("MCCARTNEY", list10);
		
		String[] list11 = new String[2];
		list11[0] = "150";
		list11[1] = "ultraboost-light.jpg";
		amap.put("ULTRABOOST LIGHT", list11);
		
		String[] list12 = new String[2];
		list12[0] = "200";
		list12[1] = "shoes/ultraboost-running.jpg";
		amap.put("ULTRABOOST RUNNING", list12);
		
		
		
		map = Collections.unmodifiableMap(amap);
	}
	
	public void createDocumentList() {
		documentList = new Document[12];
		
		documentList[0] = Indexer.createDocument("Hooded parka", "Adidas coat", "black");
		documentList[1] = Indexer.createDocument("BLACK HAT", "Adidas hat", "black");
		documentList[2] = Indexer.createDocument("SHORTS", "Adidas shorts", "blue");
		documentList[3] = Indexer.createDocument("ADIDAS SHIRT", "Adidas shirt", "black");
		documentList[4] = Indexer.createDocument("BACKPACK", "Adidas backpack", "black");
		documentList[5] = Indexer.createDocument("WIRELESS EARBUDS", "Adidas buds", "black");
		documentList[6] = Indexer.createDocument("SILVER SUNGLASSES", "Adidas sunglasses", "silver");
		documentList[7] = Indexer.createDocument("STRAW METAL BOTTLE", "Adidas straw bottle", "blue");
		documentList[8] = Indexer.createDocument("ULTRABOOST 22", "Adidas running shoes", "blue/red");
		documentList[9] = Indexer.createDocument("MCCARTNEY", "Adidas casual shoes.", "yellow");
		documentList[10] = Indexer.createDocument("ULTRABOOST LIGHT", "Adidas running shoes", "black");
		documentList[11] = Indexer.createDocument("ULTRABOOST RUNNING", "Adidas running shoes", "white/red");
	}
	
	public Document[] getDocumentList() {
		return documentList;
	}
	
	public static String getPrice(String name) {
		return map.get(name)[0];
	}
	
	public static String getImage(String name) {
		return map.get(name)[1];
	}

	
	static List<String> suggest = new ArrayList<>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		add("hooded parka");
		add("black hat");
		add("shorts");
		add("adidas shirt");
		add("backpack");
		add("wirreless earbuds");
		add("silver sunglasses");
		add("straw metal bottle");
		add("ultraboost 22");
		add("mccartney");
		add("ultraboost light");
		add("ultraboost running");
	}};
	
	public static List<String> getSuggested(String term) {
		List<String> suggesterList = new ArrayList<>();
		
		for (@SuppressWarnings("unused") String string : suggest) {
			if(!term.equals("") && string.startsWith(term)) {
				suggesterList.add(string);
			}
		}
		return suggesterList;
	}
}
