package com.briup.main;

import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.ConfigurationImpl;
import com.briup.woss.client.Client;
import com.briup.woss.client.Gather;

public class ClientImplTest {
	public static void main(String[] args) {
		
		ConfigurationImpl configurationImpl = new ConfigurationImpl();
		try {
			Gather gather = configurationImpl.getGather();
			Client client = configurationImpl.getClient();
			Collection<BIDR> collection = gather.gather();
			client.send(collection);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
