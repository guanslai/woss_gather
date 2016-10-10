package com.briup.main;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

	public static void main(String[] args) {
		Timer t = new Timer();
		t.schedule(new TimerTask(){
			public void run(){
				System.out.println("test...");
			}
		}, 0, 4000);
	}
}
