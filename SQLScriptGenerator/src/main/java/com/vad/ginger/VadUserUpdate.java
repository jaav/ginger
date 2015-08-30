package com.vad.ginger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class VadUserUpdate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = checkFileExists(args[0]);
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		try {
			
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String line = reader.readLine(); // reading first line which has symbol
				while ((line = reader.readLine()) != null) {
						String[] tokens = line.split(",");
						buffer.append("UPDATE VadGingerUser SET passwordHash = '"+ tokens[2]+"', emailAddress = '"+tokens[3]+"' WHERE userID = '"+tokens[1]+"';");
						buffer.append("\n");
				}
				reader.close();
			
		} catch(Exception e) {e.printStackTrace();}
		writeBufferToFile("user_id_updates.sql", buffer);
		
	}

	private static File checkFileExists(String fileName) {
		File f = new File(fileName);
		if (f.isFile())
			return f;
		else {
			System.out.println(fileName + " does not exist.");
			System.exit(0);
			return null;
		}
	}
	
	private static void writeBufferToFile(String fileName, StringBuffer buffer) {
		String filename = fileName;
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			writer.write(buffer.toString());
			writer.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(1);
		}
		if (fileName.indexOf("-")>-1) {
			writeBufferToFile(fileName.substring(0, fileName.indexOf("-")) +".sql", buffer);
		}
	}

}
