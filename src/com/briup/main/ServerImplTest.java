package com.briup.main;

import com.briup.util.ConfigurationImpl;
import com.briup.woss.server.Server;

public class ServerImplTest {
	public static void main(String[] args) {
		
		ConfigurationImpl configurationImpl = new ConfigurationImpl();
		try {
			Server server = configurationImpl.getServer();
			server.revicer();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
