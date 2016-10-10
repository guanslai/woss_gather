package com.briup.main;

import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.ConfigurationImpl;
import com.briup.woss.server.DBStore;
import com.briup.woss.server.Server;

public class DBStoreImplTest {
	public static void main(String[] args) {
	
		ConfigurationImpl configurationImpl = new ConfigurationImpl();
		try {
			Server server = configurationImpl.getServer();
			DBStore dbStore = configurationImpl.getDBStore();
			Collection<BIDR> collection = server.revicer();
			dbStore.saveToDB(collection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
