package com.briup.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

@SuppressWarnings("all")
public class ParseXML {
	
	private static List<Element> list;
	public static String ParseToGather(){
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element element:saxFile){
			if("gather".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("AAA-filePath".equals(e.getName())){
						str = e.getText();
					}
				}
			}
		}
		
		return str;
	}
	
	public static String ParseToLogger(){
		String str = "";
		List<Element> saxFile = saxFile();
		
		for(Element element:saxFile){
			if("logger".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("file".equals(e.getName())){
						str = e.getText();
					}
				}
			}
		}
		return str;
	}
	
	public static String ParseToBackUp(){
		String str = "";
		List<Element> saxFile = saxFile();
		
		for(Element element:saxFile){
			if("Back".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("back".equals(e.getName())){
						str = e.getText();
					}
				}
			}
		}
		return str;
	}
	
	public static Map<String,String> ParseToClient(){
		Map<String,String> map = new HashMap<String,String>();
		List<Element> saxFile = saxFile();
		for(Element element:saxFile){
			if("client".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("port".equals(e.getName())){
						String str = e.getText();
						map.put("port", str);
					}else if("ip".equals(e.getName())){
						String str = e.getText();
						map.put("ip", str);
					}
				}
			}
		}
		return map;
	}

	public static String ParseToServer(){
		String port = "";
		List<Element> saxFile = saxFile();
		for(Element element:saxFile){
			if("server".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("serverport".equals(e.getName())){
						port = e.getText();
					}
				}
			}
		}
		return port;
	}

	public static Map<String,String> ParseToDbStore(){
		Map<String,String> map = new HashMap<String,String>();
		List<Element> saxFile = saxFile();
		for(Element element:saxFile){
			if("dbStore".equals(element.getName())){
				List<Element> list2 = element.elements();
				for(Element e:list2){
					if("url".equals(e.getName())){
						String str = e.getText();
						map.put("url", str);
					}else if("userName".equals(e.getName())){
						String str = e.getText();
						map.put("userName", str);
					}else if("userPwd".equals(e.getName())){
						String str = e.getText();
						map.put("userPwd", str);
					}else{
						String str = e.getText();
						map.put("driver", str);
					}
				}
			}
		}
		return map;
	}
	
	private static List<Element> saxFile(){
		SAXReader reader = new SAXReader();
		File file = new File("src/com/briup/file/conf.xml");
		try {
			Document document = reader.read(file);
			Element root = document.getRootElement();
			list = root.elements();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
