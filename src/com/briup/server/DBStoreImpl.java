package com.briup.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Properties;


import com.briup.util.BIDR;
import com.briup.util.Configuration;
import com.briup.util.ConfigurationImpl;
import com.briup.util.ConnectionFactory;
import com.briup.util.Logger;
import com.briup.woss.ConfigurationAWare;
import com.briup.woss.server.DBStore;

public class DBStoreImpl implements DBStore,ConfigurationAWare{
	
	private Logger logger;
	@Override
	public void init(Properties properties) {
		
	}

	@Override
	public void saveToDB(Collection<BIDR> collection) throws Exception {
			
			BIDR bidr = null;
			String tableName = "";
			Connection con = null;
			PreparedStatement ps =null;
			con = ConnectionFactory.getConnetion();
			logger.info("成功连接数据库");
			
			List<BIDR> list = (List<BIDR>)collection;
			
			for(int i=0;i<list.size();i++){
				bidr = list.get(i);
				
				//获取上线时间
				Timestamp date = bidr.getLogin_date();
				//将Timestamp类型转换成String 类型
				String str = date.toString();
				//通过" "分割，获取年月日 09-04-12 00:00:00
				String[] split = str.split(" ");
				//通过"-"分割
				String[] string = split[0].split("-");
				
				//04  判断：如果第一个数字不是0，那么截取字符串的后一个数字进行拼接
				if(string[2].substring(0, 1).equals("0")){
					String string2 = string[2].substring(1,2);
					tableName = "t_detail_"+string2;
				}else{
					tableName = "t_detail_"+string[2];
				}
				
			
			String sql = "insert into "+tableName+" (AAA_login_name,login_ip,login_date,logout_date,nas_ip,time_duration) values(?,?,?,?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, bidr.getAAA_login_name());
			ps.setString(2, bidr.getLogin_ip());
			ps.setTimestamp(3, bidr.getLogin_date());
			ps.setTimestamp(4, bidr.getLogout_date());
			ps.setString(5, bidr.getNAS_ip());
			ps.setInt(6, bidr.getTime_deration());
			
			ps.execute();
			ps.close();
			
			
		}
		logger.info("开始存入数据库");
				logger.info("存入数据库结束");
				ConnectionFactory.close(ps, con);
				logger.info("关闭数据库连接");
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		
		ConfigurationImpl c = (ConfigurationImpl) configuration;
		try {
			logger = c.getLogger();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
