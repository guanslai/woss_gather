package com.briup.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import com.briup.util.BIDR;
import com.briup.util.BackUPImpl;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationImpl;
import com.briup.util.LoggerImpl;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.client.Gather;

public class GatherImpl implements Gather,ConfigurationAWare{
	
	private Map<String,BIDR> map ;
	private Collection<BIDR> collection;
	private boolean flag;
	private String backPath;
	private String gatherPath;
	private LoggerImpl log ;
	private BackUPImpl backUp;
	
	@Override
	public void init(Properties properties) {
		//获取备份文件的路径
		backPath = properties.getProperty("backPath");
		//获取采集文件的路
		gatherPath = properties.getProperty("gatherPath");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<BIDR> gather() throws Exception {
		
		//获取备份的路径，给出文件名称
		String backFilePath = backPath+"/backup";
		collection = new ArrayList<BIDR>();
		map = new HashMap<String,BIDR>();
		File file = new File(backFilePath);
		if(file.exists()){
			log.info("开始加载备份文件");
			map = (Map<String, BIDR>) backUp.load("backup", true);
			log.info("加载备份文件结束");
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(gatherPath)));
			log.info("开始采集数据");
			while(!flag){
				String readLine = br.readLine();
				//如果读取的内容为空，就终止循环
				if(readLine==null){
					flag=true;
				}else{
					String[] str = readLine.split("[|]");
					/*
						如果是8，即下线，通过map将下线时间获取，并且将上线的时间通过对象得到，
						计算出上线的的时长，将完整的对象放到list中
					 */
					if(str[2].equals("8")){
						
						Timestamp logout_date = new Timestamp(Long.parseLong(str[3])*1000);
						BIDR bidr = map.get(str[4]);
						map.remove(str[4]);
						Timestamp date = bidr.getLogin_date();
						long time = date.getTime()/1000;
						long time2 = Long.parseLong(str[3]);
						int time_deration = (int)(time2-time)/60;
						bidr.setLogout_date(logout_date);
						bidr.setTime_deration(time_deration);
						collection.add(bidr);
						/*
						如果是7，即上线，创建对象，因不知道下线时间所以将该对象的下线时间，
						上网的时长设置为null，并且放到map中，map因为查询方便，
						并且value值可以被覆盖，所以用来作为中转
						*/
						 
					}else if(str[2].equals("7")){
						
						BIDR bidr = new BIDR();
						//设置对象的用户名
						bidr.setAAA_login_name(str[0]);
						//设置对象的NASip
						bidr.setNAS_ip(str[1]);
						//设置上线的日期
						bidr.setLogin_date(new Timestamp(Long.parseLong(str[3])*1000));
						//设置对象的ip地址
						bidr.setLogin_ip(str[4]);
						//设置对象的下线日期
						bidr.setLogout_date(null);
						//设置对象的上网时长
						bidr.setTime_deration(null);
						map.put(str[4], bidr);
					}
				}
			}
			
		log.info("结束数据采集");	
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(br!=null)
				br.close();
		}
		
		log.info("备份开始");
		backUp.store("backup", map, true);
		log.info("备份结束");
		return collection;
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		try {
			ConfigurationImpl c = (ConfigurationImpl)configuration;
			backUp = (BackUPImpl) c.getBackup();
			init(c.getProperties());
			log = (LoggerImpl) c.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
