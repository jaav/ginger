package com.vad.ginger;

import java.io.*;


public class ParticipantsUpdate {

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
      String number;
      int amount = 0;
				while ((line = reader.readLine()) != null) {
						String[] tokens = line.split("\\t");
            number = tokens[54];
            int participants = Integer.parseInt(number);
            if(participants==0) amount=0;
            else if(participants==1) amount=1;
            else if(participants==11) amount=2;
            else if(participants==2) amount=6;
            else if(participants==3) amount=17;
            else if(participants==4) amount=37;
            else if(participants==5) amount=75;
            else if(participants==6) amount=150;
						buffer.append("UPDATE Activity SET TotalParticipants = "+ amount+" WHERE id = "+tokens[0]+";");
						buffer.append("\n");
				}
				reader.close();
			
		} catch(Exception e) {e.printStackTrace();}
		writeBufferToFile("participants_updates.sql", buffer);
		
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
