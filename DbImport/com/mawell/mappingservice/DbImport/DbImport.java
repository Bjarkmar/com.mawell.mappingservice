package com.mawell.mappingservice.DbImport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DbImport {

	public static void main(String[] args) {
		String fileName = "C:/Workspace/import.csv";
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(fileName));
			String temp = reader.readLine();
			System.out.println(temp);
			reader.close();
		}catch (IOException e){
			System.out.println("Error: File not found.");
		}
		
	}

}
