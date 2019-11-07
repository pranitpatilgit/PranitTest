package com.startup;

public class StartUp {

	public static void main(String[] args) throws InterruptedException {
		System.out.print("Starting the Application");
		for (int i = 0; i < 5; i++) {
			Thread.sleep(1000);
			System.out.print(".");
		}
		System.out.println("\nApplication Started Succesfully.");
	}

}
