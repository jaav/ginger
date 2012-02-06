package com.vad.ginger;

import java.io.*;


public class InternalActivityUpdate {

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
				String line = null; // reading first line which has symbol
				while ((line = reader.readLine()) != null) {
				  String[] tokens = line.replaceAll("'","''").split("\t");
            String internalActivity = tokens[87].toUpperCase();
            if(internalActivity.equals("TRUE")){
              buffer.append("UPDATE Activity SET InternalActivity = TRUE where Id = "+tokens[0]+";");
              buffer.append("\n");
            }
				}
				reader.close();
			
		} catch(Exception e) {e.printStackTrace();}
		writeBufferToFile("internal_activity_updates.sql", buffer);
		
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
