package com.briup.main;

import java.util.Properties;

import com.briup.util.BackUP;
import com.briup.util.ConfigurationImpl;
import com.briup.woss.client.Gather;

public class ConfigrationTest {
	public static void main(String[] args) {
		ConfigurationImpl c = new ConfigurationImpl(); 
		try {
			Gather gather = c.getGather();
			BackUP backup = c.getBackup();
			Properties p = c.getProperties();
			System.out.println(gather.toString()+"----"+backup.toString());
			System.out.println(p.getProperty("backPath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
