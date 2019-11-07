package com.startup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CheckValueExist {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner sc = new Scanner(new File("/home/pranit/temp/valueInside.txt"));
		
		int x = 0;
		Set<Integer> valuesInside = new HashSet<Integer>();
		while(sc.hasNextLine()){
			x = sc.nextInt();
			valuesInside.add(x);
		}
		
		sc = new Scanner(new File("/home/pranit/temp/valuesToCheck.txt"));
		
		int y = 0;
		while(sc.hasNextLine()){
			y = sc.nextInt();
			if(valuesInside.contains(y))
				System.out.println(y+"\tTrue");
			else
				System.out.println(y+"\tFalse");
		}
	}

}
