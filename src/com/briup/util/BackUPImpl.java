package com.briup.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import com.briup.woss.ConfigurationAWare;

public class BackUPImpl implements BackUP,ConfigurationAWare{

	private String path;
	
	@Override
	public void init(Properties properties) {
		path = properties.getProperty("backPath");
	}

	@Override
	public Object load(String str, boolean arg1) throws Exception {
		
		String newPath = path+"/"+str;
		
		if(arg1==true){
			
		File file = new File(newPath);
		ObjectInputStream ois = null;
		Object o = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			o = ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(ois!=null)
				ois.close();
		}
		return o;
		}
		
		return null;
	}

	@Override
	public void store(String str, Object o, boolean arg) throws Exception {
		
		String newPath = path+"/"+str;
		
		if(arg==true){
			ObjectOutputStream oos = null;
			
			try {
				oos = new ObjectOutputStream(new FileOutputStream(newPath));
				oos.writeObject(o);
				oos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				if(oos!=null)
					oos.close();
			}
		}
		
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		ConfigurationImpl c = (ConfigurationImpl) configuration;
		init(c.getProperties());
	}

}
