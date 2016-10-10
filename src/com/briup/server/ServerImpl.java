package com.briup.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationImpl;
import com.briup.util.LoggerImpl;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class ServerImpl implements Server,ConfigurationAWare {

	private ServerSocket server;
	private int port;
	private LoggerImpl logger;
	private DBStore store;
	
	
	@Override
	public void init(Properties properties) {
		//从XML文件中获取服务器端口号
		port = Integer.parseInt(properties.getProperty("serverPort"));
	}

	@Override
	public Collection<BIDR> revicer() throws Exception {
	
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				shutdown();
			}
		};
		server = new ServerSocket(port);
		while(true){
			logger.info("等待客户端连接");
			Socket client = server.accept();
			Thread t = new MyThread(client,store,logger);
			t.start();
			
			timer.schedule(timerTask, 100000);
			
		}
	}

	@Override
	public void shutdown() {
		System.exit(0);
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		ConfigurationImpl c = (ConfigurationImpl) configuration;
		try {
			logger = (LoggerImpl) c.getLogger();
			store = c.getDBStore();
			init(c.getProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


class MyThread extends Thread{
	//创建一个客户端
	private Socket client;
	private ObjectInputStream ois;
	private Collection<BIDR> collection;
	private DBStore store;
	private LoggerImpl logger;
	
	
	public MyThread(Socket client,DBStore store,LoggerImpl logger) {
		this.client = client;
		this.store = store;
		this.logger = logger;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(client.getInputStream());
			logger.info("创建对象输出流");
			collection = (Collection<BIDR>) ois.readObject();
			store.saveToDB(collection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ois!=null)
				try {
					ois.close();
					logger.info("关闭对象输出流");
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
