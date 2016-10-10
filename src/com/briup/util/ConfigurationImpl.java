package com.briup.util;

import java.io.File;

import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.briup.client.ClientImpl;
import com.briup.client.GatherImpl;
import com.briup.server.DBStoreImpl;
import com.briup.server.ServerImpl;
import com.briup.util.BackUP;
import com.briup.util.Configuration;
import com.briup.util.Logger;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;


@SuppressWarnings("all")
public class ConfigurationImpl implements Configuration{
	
	private String gatherPath;
	private String backPath;
	private String clientPort;
	private String clientIp;
	private String driver;
	private String url;
	private String userName;
	private String passWord;
	private String serverPort;
	
	private  List<Element> list;
	private Properties properties = new Properties();
	
	
	public BackUP getBackup() throws Exception {
		
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("Back".equals(e.getName())){
				str = e.attributeValue("class");
				List<Element> list2 = e.elements();
				for(Element e1:list2){
					if("back".equals(e1.getName())){
						properties.setProperty("backPath", e1.getText());
					}
				}
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		BackUPImpl back = (BackUPImpl) object;
		back.setConfiguration(this);
		return back;
	}

	@Override
	public Client getClient() throws Exception {
		
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("client".equals(e.getName())){
				str = e.attributeValue("class");
				List<Element> list2 = e.elements();
				for(Element e1 : list2){
					if("port".equals(e1.getName())){
						properties.setProperty("port", e1.getText());
					}else{
						properties.setProperty("ip", e1.getText());
					}
				}
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		Client client = (Client) object;
		ClientImpl clientImpl = (ClientImpl) client;
		clientImpl.setConfiguration(this);
		return client;
	}

	@Override
	public DBStore getDBStore() throws Exception {
		
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("dbStore".equals(e.getName())){
				str = e.attributeValue("class");
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		DBStoreImpl dbStore = (DBStoreImpl) object;
		dbStore.setConfiguration(this);
		return dbStore;
	}

	@Override
	public Gather getGather() throws Exception {
		
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("gather".equals(e.getName())){
				str = e.attributeValue("class");
				List<Element> list2 = e.elements();
				for(Element e1:list2){
					if("AAA-filePath".equals(e1.getName())){
						properties.setProperty("gatherPath", e1.getText());
					}
				}
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		Gather g = (Gather) object;
		GatherImpl gather = (GatherImpl) g;
		gather.setConfiguration(this);
		return g;
	}

	@Override
	public Logger getLogger() throws Exception {
		
		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("logger".equals(e.getName())){
				str = e.attributeValue("class");
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		Logger logger = (Logger) object;
		return logger;
	}

	@Override
	public Server getServer() throws Exception {

		String str = "";
		List<Element> saxFile = saxFile();
		for(Element e:saxFile){
			if("server".equals(e.getName())){
				str = e.attributeValue("class");
				List<Element> list2 = e.elements();
				for(Element e1:list2){
					if("serverport".equals(e1.getName())){
						properties.setProperty("serverPort", e1.getText());
					}
				}
			}
		}
		Class<?> c = Class.forName(str);
		Object object = c.newInstance();
		ServerImpl server = (ServerImpl) object;
		server.setConfiguration(this);
		return server;
	}
	
	private List<Element> saxFile(){
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

	public Properties getProperties() {
		return properties;
	}
}
