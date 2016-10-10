package com.briup.main;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Collection;

import com.briup.util.BIDR;
import com.briup.util.ConfigurationImpl;
import com.briup.woss.client.Gather;

public class GatherTest {
	public static void main(String[] args) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("a.txt")));
			ConfigurationImpl c = new ConfigurationImpl();
			Gather g = c.getGather();
			Collection<BIDR> gather = g.gather();
			for(BIDR b:gather){
				pw.println(b.getAAA_login_name()+" --- "+b.getNAS_ip()+" --- "+b.getLogin_date()+" --- "+b.getLogout_date()+" --- "+b.getTime_deration()+" --- "+b.getLogin_ip());
			}
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
