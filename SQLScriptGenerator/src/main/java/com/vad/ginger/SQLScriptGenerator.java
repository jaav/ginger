package com.vad.ginger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.vad.ginger.utils.PropsUtils;

public class SQLScriptGenerator {

	/**
	 * @param args
	 */
	public static Hashtable<String, String> sectorIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String, String> sectorOuderKeyId = new Hashtable<String, String>();
	public static Hashtable<String, String> locationIdKeyId = new Hashtable<String,String>();
	public static Hashtable<String, String> organizationIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String, String> activityTypeIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String, String> postCodes = new Hashtable<String, String>();
	public static Hashtable<String,String> clusterIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String,String> roleIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String,String> userIdKeyId = new Hashtable<String, String>();
	public static Hashtable<String, Integer> centrumIdKeyId = new Hashtable<String, Integer>();
	public static Hashtable<String, Integer> lokaalCities = new Hashtable<String, Integer>();
	
	public static Set<String> centrums = new HashSet<String>();
	public static StringBuffer sAJBuffer = new StringBuffer();
	public static int emptyLoc = 0;
	
	
	public static void main(String[] args) throws Exception {
		addCentrums();
		new PropsUtils();
		if(!isEmptyOrNull(PropsUtils.getUserIdFile()))
			generateUserIdInsertStatements();
		/*if(!isEmptyOrNull(PropsUtils.getRoleIdFile()))
			generateRoleIdInsertStatements();
		if(!isEmptyOrNull(PropsUtils.getUserRolesFile()))
			generateUserRolesInsertStatements();*/
		if (!isEmptyOrNull(PropsUtils.getSectorsFile()))
			generateSectorInsertStatement();
		if (!isEmptyOrNull(PropsUtils.getLocationsFile()) && !isEmptyOrNull(PropsUtils.getPostCodesFile()))
			generateLocationsInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getActivityTypeFile()))
			generateActivityTypeInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getItemsFile()))
			generateItemsInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getMaterialsFile()))
			generateMaterialsInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getAttendantTypeFile()))
			generateAttendantsInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getOrganizationFile())
				|| !isEmptyOrNull(PropsUtils.getSubOrganizationFile()))
			generateOrganizationInsertStatements();
		if (!isEmptyOrNull(PropsUtils.getActivityFile()))
			generateActivityInsertStatements();

		System.out.println(":: emptyLoc " + emptyLoc);
	}

	private static void generateUserRolesInsertStatements() {
		File f = checkFileExists(PropsUtils.getUserRolesFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		//buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[UserRoles] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line =  reader.readLine(); // reading first line which has symbol
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				buffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[UserRoles] ([UserId],[RoleId]) VALUES ('"+tokens[0]+"','"+roleIdKeyId.get(tokens[1]) +"')");	
				buffer.append("\n");
				k = i;i++;
		}
		buffer.append("\n");
		buffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[UserRoles] ([UserId],[RoleId]) VALUES ('{2D62FE78-F86D-4DAA-8C7F-E19D25963077}','2')");
		//buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[UserRoles] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("user_roles.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	}

	private static void generateRoleIdInsertStatements() {
		File f = checkFileExists(PropsUtils.getRoleIdFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Roles] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine(); // reading first line which has symbol
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				roleIdKeyId.put(tokens[1],i+"");
				buffer.append("INSERT INTO [dbo].[Roles] ([RoleId],[RoleName]) VALUES ('"+i+"','"+tokens[2]+"' )");
				buffer.append("\n");
				k = i;i++;
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Roles] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("role_id.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	}

	private static void generateUserIdInsertStatements() {
		File f = checkFileExists(PropsUtils.getUserIdFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[VadGingerUser] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine(); // reading first line which has symbol
		int user_id_num = 2;
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\t");
				userIdKeyId.put(tokens[2].toLowerCase(), user_id_num+"");
				buffer.append("INSERT INTO [dbo].[VadGingerUser] ([id],[userID],[passwordHash], [emailAddress], [role], [loginCount], [centrumId_id], [IsActive]) VALUES ('"+user_id_num+"','"+tokens[2]+"','"+"123456"+"','"+"admin@vadginger.be"+"', '0','0', "+centrumIdKeyId.get(removeQuote(tokens[2]).substring(0,3).toUpperCase())+", 1)");
				buffer.append("\n");
				user_id_num++;
				k = i;i++;
				centrums.add(removeQuote(tokens[2]).substring(0,3).toUpperCase());
		}
		user_id_num++;
		buffer.append("INSERT INTO [dbo].[VadGingerUser] ([id],[userID],[passwordHash], [emailAddress], [role], [loginCount],[IsActive]) VALUES ('"+user_id_num+"','UNKNOWN_USER','"+"123456"+"','"+"admin@vadginger.be"+"', '0','0',1)\n");
		user_id_num++;
		buffer.append("INSERT INTO [dbo].[VadGingerUser] ([id],[userID],[passwordHash], [emailAddress], [role], [loginCount],[IsActive]) VALUES ('"+user_id_num+"','admin','"+"SkR2dnY3M3Z2NzBENzcrOTc3KzlCZSsvdmUrL3ZVUHZ2NzBCWkdvPQ=="+"','"+"admin@vadginger.be"+"', '1','0',1)\n");
		userIdKeyId.put("UNKNOWN_USER".toLowerCase(),user_id_num+"");
		buffer.append("\nSET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[VadGingerUser] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("1-user_id.sql", buffer);
		reader.close();
		
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	
		
	}

	private static void generateActivityInsertStatements() {
		File f = checkFileExists(PropsUtils.getActivityFile());
		StringBuffer buffer = new StringBuffer();
		StringBuffer materialBuffer = new StringBuffer();
		StringBuffer itemsBuffer = new StringBuffer();
		StringBuffer activityTypeBuffer = new StringBuffer();
		StringBuffer sectorBuffer = new StringBuffer();
		StringBuffer targetBuffer = new StringBuffer();
		StringBuffer evalBuffer = new StringBuffer();
		StringBuffer locationBuffer = new StringBuffer();
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		materialBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		itemsBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		itemsBuffer.append("\n");
		materialBuffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Activity] ON");
		buffer.append("\n");
		materialBuffer.append("\n");
		itemsBuffer.append("\n");
		activityTypeBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		activityTypeBuffer.append("\n");
		sectorBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		sectorBuffer.append("\n");
		targetBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		targetBuffer.append("\n");
		evalBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		evalBuffer.append("\n");
		locationBuffer.append("USE ["+ PropsUtils.getDbName()+"]");
		locationBuffer.append("\n");
		String line = reader.readLine(); // reading first line which has symbol
		
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.replaceAll("'","''").split("\t");
				centrums.add(removeQuote(tokens[4]));
				int isReported = (tokens[73] == "1") ? 1: 0; 
				String userId = userIdKeyId.get(tokens[5].replace("\"", "").trim().toLowerCase());
				if (userId == null) {
					userId = userIdKeyId.get("UNKNOWN_USER".toLowerCase());
					
				}
				String orgId = "";
				if (removeQuote(tokens[16]).equals("1")) {
					//System.out.println(removeQuote(tokens[15])+","+removeQuote(tokens[16]));
					 orgId = organizationIdKeyId.get(removeQuote(tokens[15]));
					// System.out.println(":: 1 OrgId = " + orgId );
				}
				else {
					//System.out.println("==HERE");
					orgId = organizationIdKeyId.get(removeQuote(tokens[15]) + "," + removeQuote(tokens[16]));
					//System.out.println(":: 2 OrgId = " + orgId );
				}
				//if (orgId == null)
					//System.out.println("++>" + tokens[15].replace("\"", "") + "," + tokens[16].replace("\"", "") );
				buffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Activity] ([id],[Duur],[Evaluvated],[InternalActivity],[Reported],[TotalParticipants],[organizationId_id],[userId_id], [beschrijving], [Activity_date], [centrumId_id], [IsActive]) VALUES " +
						"("+ tokens[0].replace("\"", "") + ","+tokens[3].replace("\"", "") + ","+tokens[69].replace("\"", "")+ ","+((tokens[87]=="TRUE")?1:0) + ","+
						isReported + ","+tokens[54].replace("\"", "")+ ","+ orgId + ",'"+userId+ "','"+removeQuote(tokens[2])+"',"+tokens[1]+","+centrumIdKeyId.get(removeQuote(tokens[4]).toUpperCase())+", 1)");
				if (userId == null)
					System.out.println(tokens[5]);
				buffer.append("\n");
				loadMaterialBuffers(materialBuffer, tokens);
				loadItemsInBuffer(itemsBuffer, tokens);
				loadActivityTypeBuffer(activityTypeBuffer, tokens);
				loadSectorBuffer(sectorBuffer, tokens);
				loadTargetsBuffer(targetBuffer, tokens); //TODO, secotrs
				loadEvalBuffer(evalBuffer, tokens);
				loadLocationsBuffer(locationBuffer, tokens);
				//System.out.println(tokens[55] + tokens[61]);
				
		}
		reader.close();
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Activity] OFF");
		buffer.append("\n");
		buffer.append("GO");
		materialBuffer.append("\n");
		materialBuffer.append("GO");
		itemsBuffer.append("\n");
		itemsBuffer.append("GO");
		evalBuffer.append("\n");
		evalBuffer.append("GO");
		sectorBuffer.append("\n");
		sectorBuffer.append("GO");
		targetBuffer.append("\n");
		targetBuffer.append("GO");
		activityTypeBuffer.append("\n");
		activityTypeBuffer.append("GO");
		locationBuffer.append("\n");
		locationBuffer.append("GO");
		writeBufferToFile("11-activity.sql", buffer);
		writeBufferToFile("12-materialInActivity.sql", materialBuffer);
		writeBufferToFile("13-itemsInActivity.sql", itemsBuffer);
		writeBufferToFile("14-activityTypeJunction.sql", activityTypeBuffer);
		writeBufferToFile("15-sectorActivity.sql", sectorBuffer);
		writeBufferToFile("16-activityTargets.sql", targetBuffer);
		writeBufferToFile("17-activityEvaluvators.sql", evalBuffer);
		writeBufferToFile("18-location_update.sql", locationBuffer);
		writeBufferToFile("19-sector_activity_junction.sql", sAJBuffer);
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
		System.out.println(":: Centrums length " + centrums.size());
		ArrayList<String> list = new ArrayList<String>(centrums);
		Collections.sort(list);
		Iterator<String> centrumIt = list.iterator();
		while (centrumIt.hasNext())
			System.out.println(centrumIt.next());
		
		/*for (String key: organizationIdKeyId.keySet())
			System.out.println(key + "\t" + organizationIdKeyId.get(key));*/
			}

	private static void loadLocationsBuffer(StringBuffer locationBuffer,
			String[] tokens) {
		if (removeQuote(tokens[82]).trim().equals("") || removeQuote(tokens[82]).trim().equals("0"))
			emptyLoc++;
		if(removeQuote(tokens[82]).equals("1")) {
			String locationId = "";
			String postCodeVal = removeQuote(tokens[85]).trim();
			postCodeVal = postCodeVal.substring(0, postCodeVal.length()-1) + "0";
			int postCodeInt = 0;
			try {
				postCodeInt = Integer.parseInt(postCodeVal);
			} catch(Exception e) {
				postCodeInt = 5000;
			}
			if (!removeQuote(tokens[85]).trim().equals("0") && postCodes.containsKey(postCodeVal)) {
				//System.out.println( "ppp : "+ postCodeVal);
				String postCodeId = postCodes.get(postCodeVal).toLowerCase();
				if (postCodeId.indexOf(":::")>-1)
				{
					//System.out.println(postCodeId);
					for(String postCode: postCodeId.split(":::")) {
						locationId = locationIdKeyId.get(postCode.toLowerCase());
						if (locationId != null && locationId.indexOf(",")>0)
							locationId = locationIdKeyId.get(locationId);
						if (locationId == null) {
							//System.out.println("NotFound "+postCode);
						}
						else {
							//System.out.println("Found "+postCode);
							break;
						}
					}
					
				}
				else  {
					int iddd = 0;
					iddd = lokaalCities.get(postCodes.get(postCodeVal));
					if (iddd==0) {
						System.out.println(":+: POSTCODE " +removeQuote(tokens[85]).trim());
					}
					locationId = iddd +"";
					if (locationId != null && locationId.indexOf(",")>0)
						locationId = locationIdKeyId.get(locationId);
					
					}
				if (locationId == null || locationId.equals("")) {
					System.out.println(":: POSTCODE " +removeQuote(tokens[85]).trim());
				}
				locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+locationId+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
			}			
		}
		if (removeQuote(tokens[82]).equals("2")) {
			String locationId = clusterIdKeyId.get(removeQuote(tokens[86]));
			if (locationId == null)
				System.out.println(":*: CLUSTERId " +removeQuote(tokens[86]).trim());
			locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+locationId+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
		}
		if (removeQuote(tokens[82]).equals("3")) {
			String locationId = (1010 + Integer.parseInt(removeQuote(tokens[83]))) +"" ;
			locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+locationId+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
		}
		if(removeQuote(tokens[82]).equals("4")) {
			String locationId = (1018 + Integer.parseInt(removeQuote(tokens[84]))) +"" ;
			locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+locationId+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
		}
		if (removeQuote(tokens[82]).equals("5"))
			locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+1025+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
		if (removeQuote(tokens[82]).equals("6"))
			locationBuffer.append("UPDATE ["+ PropsUtils.getDbName()+"].[dbo].[Activity] SET [locationId_id] = "+1026+" WHERE [id] = "+removeQuote(tokens[0])+"\n");
	}

	private static void loadEvalBuffer(StringBuffer evalBuffer, String[] tokens) {
		if (removeQuote(tokens[69]).equals("1")) {
			if  (removeQuote(tokens[72]).trim().equals(""))
				evalBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityEvaluvators] ([activityId_id],[evalTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+((removeQuote(tokens[70]).equals("TRUE"))? 1 : 2)+")\n");
			else
				evalBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityEvaluvators] ([activityId_id] ,[evaluvatorsId_id],[evalTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+ removeQuote((tokens[72])) +","+((removeQuote(tokens[70]).equals("TRUE"))? 1 : 2)+")\n");
		}
	}

	private static void loadTargetsBuffer(StringBuffer targetBuffer,
			String[] tokens) {
		if (removeQuote(tokens[38]).equals("1")) {
			if (removeQuote(tokens[39]).equals("TRUE"))
			targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+2+")\n");
			if (removeQuote(tokens[40]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+3+")\n");
			if (removeQuote(tokens[41]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+4+")\n");
			//int secActJunc = 42;
			for (int secAct = 1; secAct < 9; secAct++) {
				//System.out.println(tokens[secAct+41]);
				if (removeQuote(tokens[secAct+41]).equalsIgnoreCase("true")) {
					sAJBuffer.append("INSERT INTO ActivitySectors (activityId_id, sectorId_id) VALUES ("+ removeQuote(tokens[0]) +","+ sectorOuderKeyId.get(secAct+"") +");\n");
				}
				
			}
		}
		if (removeQuote(tokens[38]).equals("2")) {
			if (removeQuote(tokens[50]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+6+")\n");
			if (removeQuote(tokens[51]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+7+")\n");
			if (removeQuote(tokens[52]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+8+")\n");
			if (removeQuote(tokens[53]).equals("TRUE"))
				targetBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTargets] ([activityId_id],[attendantTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+9+")\n");
		}
	}

	private static void loadSectorBuffer(StringBuffer sectorBuffer,
			String[] tokens) {
		if(removeQuote(tokens[7]).equals("TRUE")) {
			String a = "1";
			String b = removeQuote(tokens[17]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[8]).equals("TRUE")) {
			String a = "2";
			String b = removeQuote(tokens[18]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[9]).equals("TRUE")) {
			String a = "3";
			String b = removeQuote(tokens[19]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[10]).equals("TRUE")) {
			String a = "4";
			String b = removeQuote(tokens[20]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[11]).equals("TRUE")) {
			String a = "5";
			String b = removeQuote(tokens[21]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[12]).equals("TRUE")) {
			String a = "6";
			String b = removeQuote(tokens[22]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[13]).equals("TRUE")) {
			String a = "7";
			String b = removeQuote(tokens[23]);
			if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ sectorIdKeyId.get(a+","+b)+")\n");
		}
		if(removeQuote(tokens[14]).equals("TRUE")) {
			//String a = "1";
			//String b = removeQuote(tokens[17]);
			//if (!b.equals("0"))
				sectorBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[SectorActivityJunction] ([activityId_id],[sectorId_id]) VALUES ("+removeQuote(tokens[0])+","+ 97+")\n");
		}
	}

	private static void loadActivityTypeBuffer(StringBuffer activityTypeBuffer,
			String[] tokens) {
		if (removeQuote(tokens[24]).equals("2"))
			activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+7+")\n");
		if (removeQuote(tokens[24]).equals("1")) {
			int k = 24;
			for (int i = 1; i <6; i++) {
			if(removeQuote(tokens[k+i]).equals("TRUE"))
				activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+activityTypeIdKeyId.get("1," + i)+")\n");
		}}
		if (removeQuote(tokens[24]).equals("3")) {
			int k = 29;
			for (int i = 1; i <3; i++)
			if(removeQuote(tokens[k+i]).equals("TRUE"))
				activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+activityTypeIdKeyId.get("3," + i)+")\n");
		}
		if (removeQuote(tokens[24]).equals("4")) {
			int k = 31;
			for (int i = 1; i <5; i++)
			if(removeQuote(tokens[k+i]).equals("TRUE"))
				activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+activityTypeIdKeyId.get("4," + i)+")\n");
		} 
		if (removeQuote(tokens[24]).equals("5")) {
			int k = 35;
			for (int i = 1; i <3; i++)
			if(removeQuote(tokens[k+i]).equals("TRUE"))
				activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+activityTypeIdKeyId.get("5," + i)+")\n");
		}
		if (removeQuote(tokens[24]).equals("6")) {
			activityTypeBuffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[ActivityTypeJunction]([activityId_id],[activityTypeId_id]) VALUES ("+removeQuote(tokens[0])+","+19+")\n");
		}
	}
	
	private static String removeQuote(String str) {
		return str.replace("\"","").trim().toUpperCase();
	}

	private static void loadItemsInBuffer(StringBuffer itemsBuffer,
			String[] tokens) {
		if (removeQuote(tokens[55]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 1+")\n");
		if (removeQuote(tokens[56]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 2+")\n");
		if (removeQuote(tokens[57]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 3+")\n");
		if (removeQuote(tokens[58]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 4+")\n");
		if (removeQuote(tokens[59]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 5+")\n");
		if (removeQuote(tokens[60]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 6+")\n");
		if (removeQuote(tokens[61]).equals("TRUE"))
			itemsBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[ItemsInActivity] ([activityId_id],[itemId_id]) VALUES ("+removeQuote(tokens[0])+","+ 7+")\n");
	}

	private static void loadMaterialBuffers(StringBuffer materialBuffer,
			String[] tokens) {
		if (removeQuote(tokens[62]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0]) +",6)\n");
		if (removeQuote(tokens[63]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",1)\n");
		if (removeQuote(tokens[64]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",2)\n");
		if (removeQuote(tokens[65]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",3)\n");
		if (removeQuote(tokens[66]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",4)\n");
		if (removeQuote(tokens[67]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",5)\n");
		if (removeQuote(tokens[68]).equals("TRUE"))
			materialBuffer.append("INSERT INTO  ["+ PropsUtils.getDbName()+"].[dbo].[MaterialsInActivity] ([activityId_id],[materialId_id]) VALUES ("+ removeQuote(tokens[0] )+",7)\n");
	}
	
	private static void generateSubOrganizationInsertStatements(int i, int k) {

		File f = checkFileExists(PropsUtils.getSubOrganizationFile());
		StringBuffer buffer = new StringBuffer();
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Organisaties] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine(); // reading first line which has symbol
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.replaceAll("'", "''").split("\t");
				centrums.add(removeQuote(tokens[4]));
				String time = tokens[3];//.substring(6, 10) + "-" + tokens[3].substring(3,5) + "-" + tokens[3].substring(0,2) + tokens[3].substring(10);
				//time = time + ":00";
				String userId = userIdKeyId.get(tokens[5].toLowerCase());
				if (userId == null)
					userId = userIdKeyId.get("UNKNOWN_USER".toLowerCase());
				buffer.append("INSERT INTO [dbo].[Organisaties] ([id],[Naam],[ouder_id],[userId_id], [IsActive],[centrumId_id]) VALUES " +
						"("+i+",'"+tokens[1].replace("\"", "")+"',"+tokens[2]+","+"'"+userId+"',1,"+ centrumIdKeyId.get(removeQuote(tokens[4]).toUpperCase())+")");
				organizationIdKeyId.put(tokens[2].trim() + "," + tokens[0].trim(), i+ "");
				//System.out.println(tokens[2].trim() + "," + tokens[0].trim() +"=>"+ i+ "");
				buffer.append("\n");
				k = i;i++;
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Organisaties] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("10-sub_organisaties.sql", buffer);
		reader.close();
		/*for (String key: organizationIdKeyId.keySet()) {
			System.out.println(key+"==="+organizationIdKeyId.get(key));
		}*/
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
		
	}

	private static void generateOrganizationInsertStatements() {
		File f = checkFileExists(PropsUtils.getOrganizationFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Organisaties] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = reader.readLine(); // reading first line which has symbol
		while ((line = reader.readLine()) != null) {
				String[] tokens = line.replaceAll("'", "''").split("\t");
				centrums.add(removeQuote(tokens[7]));
				//System.out.println(tokens[9]);
				String time = tokens[9];//.substring(6, 10) + "-" + tokens[9].substring(3,5) + "-" + tokens[9].substring(0,2) + tokens[9].substring(10);
				//time = time + ":00";
				//System.out.println(tokens[8] + "\n" + );
				String userId = userIdKeyId.get(tokens[8].toLowerCase());
				if (userId == null)
					userId = userIdKeyId.get("UNKNOWN_USER".toLowerCase());
				buffer.append("INSERT INTO [dbo].[Organisaties] ([id],[Naam],[OrganisatieNetwerk],[Adres],[Postcode],[Gemeente],[Land],[userId_id], [centrumId_id], [IsActive]) VALUES " +
						"("+tokens[0]+",'"+tokens[1].replace("\"", "")+"','"+tokens[2]+"','"+tokens[3]+"','"+tokens[4]+"','"+tokens[5]+"','"+tokens[6]+"','"+userId+"',"+centrumIdKeyId.get(removeQuote(tokens[7]).toUpperCase())+", 1)");
				buffer.append("\n");
				organizationIdKeyId.put(tokens[0].trim(), tokens[0].trim());
				if (Integer.parseInt(tokens[0]) > i)
					i = Integer.parseInt(tokens[0]);
				
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Organisaties] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("9-organisaties.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
		i++;
		generateSubOrganizationInsertStatements(i++, k++);
	}

	private static void generateAttendantsInsertStatements() {
		File f = checkFileExists(PropsUtils.getAttendantTypeFile());
		StringBuffer buffer = new StringBuffer();
		StringBuffer buffer1 = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer1.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[TargetType] ON");
		buffer.append("\n");
		buffer1.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[AttendantType] ON");
		buffer1.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				head = line.substring(symbol.length()).trim();
				buffer.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[TargetType] ([id],[Beschrijving], [IsActive]) VALUES ("+i+",'"+ head+"',1)");
				buffer.append("\n");
				k = i;i++;
			}
			else {
				head = line.trim();
				buffer1.append("INSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES ("+ i +", "+ k +", '"+ head +"')");
				buffer1.append("\n");i++;
			}
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[TargetType] OFF");
		buffer.append("\n");
		buffer.append(buffer1);
		buffer.append("\n");
		buffer.append("\nSET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[AttendantType] OFF");
		buffer.append("\nSET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvators] ON");
		buffer.append("\nINSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvators] ([id], [Naam],[IsActive]) VALUES (1, 'Jijzelf', 1)");
		buffer.append("\nINSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvators] ([id], [Naam], [IsActive]) VALUES (2, 'Een externe persoon of organisatie', 1)");
		buffer.append("\nINSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvators] ([id], [Naam], [IsActive]) VALUES (3, 'Beide', 1)");
		buffer.append("\nSET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvators] OFF");
		buffer.append("\nSET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvation_Type] ON");
		buffer.append("\nINSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvation_Type] ([id],[EvalType]) VALUES (1,'Mondeling')");
		buffer.append("\nINSERT INTO ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvation_Type] ([id],[EvalType]) VALUES (2,'Schriftel')");
		buffer.append("\nSET IDENTITY_INSERT ["+ PropsUtils.getDbName()+"].[dbo].[Evaluvation_Type] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("8-attendant_type.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	
		
	}

	private static void generateMaterialsInsertStatements() {
		File f = checkFileExists(PropsUtils.getMaterialsFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Materials] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				head = line.substring(symbol.length()).trim();
				buffer.append("INSERT INTO [dbo].[Materials] ([id],[Naam], [IsActive]) VALUES ("+i+",'"+head+"',1)");
				buffer.append("\n");
				k = i;i++;
			}
			else {
				head = line.trim();
				buffer.append("INSERT INTO [dbo].[Items] ([Materials],[Naam],[Ouder]) VALUES ("+ i +",'"+ head +"',"+k+")");
				buffer.append("\n");i++;
			}
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Materials] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("7-materials.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	
		
	}

	private static void generateItemsInsertStatements() {
		File f = checkFileExists(PropsUtils.getItemsFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Items] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				head = line.substring(symbol.length()).trim();
				buffer.append("INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES ("+i+",'"+head+"',1)");
				buffer.append("\n");
				k = i;i++;
			}
			else {
				head = line.trim();
				buffer.append("INSERT INTO [dbo].[Items] ([id],[Naam],[Ouder]) VALUES ("+ i +",'"+ head +"',"+k+")");
				buffer.append("\n");i++;
			}
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Items] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("6-items.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	
		
	}

	private static void generateActivityTypeInsertStatements() {
		File f = checkFileExists(PropsUtils.getActivityTypeFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		int a = 0;
		int b = 0;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[ActivityType] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				a++;
				b = 0;
				head = line.substring(symbol.length()).trim();
				buffer.append("INSERT INTO [dbo].[ActivityType] ([id],[Naam],[IsActive]) VALUES ("+ i +",'"+ head +"',1)");
				buffer.append("\n");
				k = i;i++;
			}
			else {
				b++;
				head = line.trim();
				buffer.append("INSERT INTO [dbo].[ActivityType] ([id],[Naam],[ouder_id], [IsActive]) VALUES ("+ i +",'"+ head +"',"+k+", 1)");
				activityTypeIdKeyId.put(a+ "," + b, i+"");
				buffer.append("\n");i++;
			}
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[ActivityType] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("5-activity_type.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
		/*for(String key: activityTypeIdKeyId.keySet()) {
			System.out.println("ActType == "+ key + ":: " + activityTypeIdKeyId.get(key));
		}*/
	
		
	}

	private static void generateLocationsInsertStatements() {
		
		File f = checkFileExists(PropsUtils.getLocationsFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		int a = 0;
		int b = 1;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Locations] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				a++;b=1;
				head = line.substring(symbol.length()).trim().replace("\'", "\'\'");
				buffer.append("INSERT INTO [dbo].[Locations] ([id],[Naam],[IsActive]) VALUES ("+ i +",'"+ head +"',1)");
				buffer.append("\n");
				k = i;i++;
			}
			else {
				if (head.toLowerCase().trim().indexOf("mald")>-1) {
					System.out.println(">>> HEAD " + head);
				}
				locationIdKeyId.put(a+","+b, i+"");
				locationIdKeyId.put(head.toLowerCase(), a+","+b);
				head = line.trim().replace("\'", "\'\'");
				buffer.append("INSERT INTO [dbo].[Locations] ([id],[Naam],[ouder_id],[IsActive]) VALUES ("+ i +",'"+ head +"',"+k+",1)");
				if (k==1) {
					lokaalCities.put(head.trim(), i);
				}
				buffer.append("\n");i++;
				b++;
			}
		}
		/*for (String key: locationIdKeyId.keySet()) {
			System.out.println(key + "==="+locationIdKeyId.get(key));
		}*/
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Locations] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("3-locations.sql", buffer);
		reader.close();
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
		//System.out.println("==================================");
		for (String key: lokaalCities.keySet()) {
			System.out.println(key);
			
		}
		//System.out.println("==================================");
		loadPostCodesFile();
		addClustersToLocations(i++);
		
	}

	private static void addClustersToLocations(int i) {
		File f = checkFileExists(PropsUtils.getClusterFile());
		StringBuffer buffer = new StringBuffer();
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Locations] ON");
		buffer.append("\n");
		int k = 0;
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			line = reader.readLine();
			while((line=reader.readLine())!=null) {
				++i;
				String tokens[] = line.split("\t");
				centrums.add(tokens[2]);
				buffer.append("INSERT INTO [dbo].[Locations] ([id],[Naam],[IsCluster], [centrumId_id],[IsActive]) VALUES ("+ i +",'"+ tokens[1].replace("\'", "\'\'") +"',1, "+centrumIdKeyId.get(removeQuote(tokens[2]).toUpperCase())+",1)\n");
				clusterIdKeyId.put(tokens[0], i+"");
				k = i;i++;
				for (int p = 5; p < tokens.length; p++) {
					if (!tokens[p].trim().equals("")&&tokens[p]!=null&&!tokens[p].trim().equals("0")&&tokens[p].trim().charAt(tokens[p].length()-1) == '0') {
						buffer.append("INSERT INTO [dbo].[CityClusterJunction] ([clusterId_id],[cityId_id]) VALUES ("+ k +","+lokaalCities.get(postCodes.get(tokens[p].trim())) +")\n");
						i++;
					}
				}
			}
			buffer.append("\n");
			buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Locations] OFF");
			buffer.append("\n");
			buffer.append("GO");
			writeBufferToFile("4-clusters.sql", buffer);
		}catch(Exception e){e.printStackTrace();
		System.out.println(line);}
		
	}

	private static void loadPostCodesFile() {
		try {
		File f = checkFileExists(PropsUtils.getPostCodesFile());
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line = null;
		while ((line=reader.readLine())!=null) {
			
			String tokens[] = line.split(",");
			if (lokaalCities.get(tokens[1].trim())!=null) {
			/*if (postCodes.get(tokens[0])!=null) {
				postCodes.put(tokens[0], postCodes.get(tokens[0]) +":::"+tokens[1]);
				}
			else*/ postCodes.put(tokens[0], tokens[1]);
		}}
		
		/*for(String key: postCodes.keySet()) {
			System.out.println(key + "==> " + postCodes.get(key));
		}*/
		} catch(Exception e) {e.printStackTrace();}
	}

	private static void generateSectorInsertStatement() {
		File f = checkFileExists(PropsUtils.getSectorsFile());
		StringBuffer buffer = new StringBuffer();
		int i = 1;
		int k = 0;
		int a = 0;
		int b = 1;
		int ouder = 1;
		buffer.append("USE  ["+ PropsUtils.getDbName()+"]");
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Sectors] ON");
		buffer.append("\n");
		try {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String head = null;
		String line = reader.readLine(); // reading first line which has symbol
		String symbol = line.split("=")[1]; // ugly
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(symbol)) {
				a++;
				b = 1;
				head = line.substring(symbol.length()).trim();
				buffer.append("INSERT INTO [dbo].[Sectors] ([id],[Naam],[IsActive]) VALUES ("+ i +",'"+ head +"', 1)");
				buffer.append("\n");
				
				k = i;i++;
				sectorOuderKeyId.put(ouder+"",k+"");ouder++;
			}
			else {
				sectorIdKeyId.put(a + "," + b, i+"");
				head = line.trim();
				buffer.append("INSERT INTO [dbo].[Sectors] ([id],[Naam],[ouder_id],[IsActive]) VALUES ("+ i +",'"+ head +"',"+k+",1)");
				buffer.append("\n");i++;
				b++;
			}
		}
		buffer.append("\n");
		buffer.append("SET IDENTITY_INSERT  ["+ PropsUtils.getDbName()+"].[dbo].[Sectors] OFF");
		buffer.append("\n");
		buffer.append("GO");
		writeBufferToFile("2-sectors.sql", buffer);
		reader.close();
		
		} catch (FileNotFoundException fnfe) {
			// this should never happen...
		} catch (IOException ie) {
			System.out.println(":: Unable to read file");
			ie.printStackTrace();
			System.exit(1);			
		}
	
		Enumeration<String> oud =sectorOuderKeyId.keys();
		while(oud.hasMoreElements()) {
			String key = oud.nextElement();
			//System.out.println(key + "+++>" + sectorOuderKeyId.get(key));
		}
		
	}
	
	private static void writeBufferToFile(String fileName, StringBuffer buffer) {
		String filename = "/media/mynewdrive/sql_files3/" + fileName;
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

	private static boolean isEmptyOrNull(String propertyString) {
		return propertyString == null || propertyString.trim().equals("");
	}
	
	private static void addCentrums() {
		centrumIdKeyId.put("123",1);
		centrumIdKeyId.put("AAL",2);
		centrumIdKeyId.put("AHA",3);
		centrumIdKeyId.put("ALT",4);
		centrumIdKeyId.put("BOO",5);
		centrumIdKeyId.put("BRT",6);
		centrumIdKeyId.put("CAD",7);
		centrumIdKeyId.put("CAT",8);
		centrumIdKeyId.put("DEI",9);
		centrumIdKeyId.put("DEL",10);
		centrumIdKeyId.put("DIE",11);
		centrumIdKeyId.put("GEE",12);
		centrumIdKeyId.put("GOO",13);
		centrumIdKeyId.put("HAL",14);
		centrumIdKeyId.put("HAV",15);
		centrumIdKeyId.put("HEI",16);
		centrumIdKeyId.put("HER",17);
		centrumIdKeyId.put("JEF",18);
		centrumIdKeyId.put("KAS",19);
		centrumIdKeyId.put("KEM",20);
		centrumIdKeyId.put("KIE",21);
		centrumIdKeyId.put("LAR",22);
		centrumIdKeyId.put("LEU",23);
		centrumIdKeyId.put("LIE",24);
		centrumIdKeyId.put("MEA",25);
		centrumIdKeyId.put("MEC",26);
		centrumIdKeyId.put("MID",27);
		centrumIdKeyId.put("NWV",28);
		centrumIdKeyId.put("OOS",29);
		centrumIdKeyId.put("PAG",30);
		centrumIdKeyId.put("PAS",31);
		centrumIdKeyId.put("PDM",32);
		centrumIdKeyId.put("PRI",33);
		centrumIdKeyId.put("REG",34);
		centrumIdKeyId.put("ROE",35);
		centrumIdKeyId.put("SLE",36);
		centrumIdKeyId.put("SNS",37);
		centrumIdKeyId.put("SUI",38);
		centrumIdKeyId.put("TES",39);
		centrumIdKeyId.put("TIE",40);
		centrumIdKeyId.put("TUR",41);
		centrumIdKeyId.put("VAD",42);
		centrumIdKeyId.put("VLB",43);
		centrumIdKeyId.put("WET",44);
		centrumIdKeyId.put("WVL",45);

		StringBuffer centrumBuffer = new StringBuffer();
		for (String key: centrumIdKeyId.keySet()) {
			centrumBuffer.append("INSERT INTO Centrum (id,Naam,IsActive) VALUES ("+ centrumIdKeyId.get(key) +",'"+ key +"',1);\n");
		}
		writeBufferToFile("0-centrums.sql", centrumBuffer);

	}

}
