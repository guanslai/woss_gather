package com.briup.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationImpl;
import com.briup.util.LoggerImpl;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Client;

public class ClientImpl implements Client,ConfigurationAWare{
	
	private Socket client;
	private ObjectOutputStream oos = null;
	private String ip;
	private int port;
	private LoggerImpl logger;
	
	
	@Override
	public void init(Properties properties) {
		//从配置文件中获取连接的端口号
		port = Integer.parseInt(properties.getProperty("port")) ;
		//获取ip地址
		ip = properties.getProperty("ip");
	}

	//连接服务器端，并将解析的文件以对象流的形式传输到服务器端
	@Override
	public void send(Collection<BIDR> collection) throws Exception {
		
		
		
		client = new Socket(ip,port);
		logger.info("客户端开始传输数据");
		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(collection);
		oos.flush();
		logger.info("客户端结束传输数据");
		if(oos!=null)
			oos.close();
		logger.info("关闭对象输出流");
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		ConfigurationImpl c = (ConfigurationImpl)configuration;
		try {
			init(c.getProperties());
			logger = (LoggerImpl) c.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
