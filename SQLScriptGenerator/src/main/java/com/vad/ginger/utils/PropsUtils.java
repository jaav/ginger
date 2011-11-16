package com.vad.ginger.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtils {

	private static String sectorsFile;

	private static String locationsFile;

	private static String activityTypeFile;

	private static String materialsFile;

	private static String itemsFile;

	private static String attendantTypeFile;

	private static String organizationFile;

	private static String subOrganizationFile;

	private static String activityFile;

	private static String userIdFile;

	private static String roleIdFile;

	private static String userRolesFile;

	private static String dbName;
	
	private static String postCodesFile;
	
	private static String clusterFile;

	public PropsUtils() throws Exception {
		Properties props = new Properties();
		File file = new File("app.config");
		InputStream is = new FileInputStream(file);
		props.load(is);
		sectorsFile = props.getProperty("sectors_file");
		locationsFile = props.getProperty("locations_file");
		postCodesFile = props.getProperty("postcodes_file");
		activityTypeFile = props.getProperty("activity_type_file");
		materialsFile = props.getProperty("materials_file");
		itemsFile = props.getProperty("items_file");
		attendantTypeFile = props.getProperty("attendant_type_file");
		organizationFile = props.getProperty("organization_file");
		subOrganizationFile = props.getProperty("sub_organization_file");
		activityFile = props.getProperty("activity_file");
		userIdFile = props.getProperty("user_id_file");
		roleIdFile = props.getProperty("role_id_file");
		userRolesFile = props.getProperty("user_roles_file");
		clusterFile = props.getProperty("cluster_file");
		dbName = props.getProperty("db_name");
		is.close();
	}

	public static String getSectorsFile() {
		return sectorsFile;
	}

	public static String getLocationsFile() {
		return locationsFile;
	}

	public static String getActivityTypeFile() {
		return activityTypeFile;
	}

	public static String getMaterialsFile() {
		return materialsFile;
	}

	public static String getItemsFile() {
		return itemsFile;
	}

	public static String getAttendantTypeFile() {
		return attendantTypeFile;
	}

	public static String getOrganizationFile() {
		return organizationFile;
	}

	public static String getSubOrganizationFile() {
		return subOrganizationFile;
	}

	public static String getActivityFile() {
		return activityFile;
	}

	public static String getUserIdFile() {
		return userIdFile;
	}

	public static String getRoleIdFile() {
		return roleIdFile;
	}

	public static String getUserRolesFile() {
		return userRolesFile;
	}

	public static String getDbName() {
		return dbName;
	}

	public static String getPostCodesFile() {
		return postCodesFile;
	}

	public static String getClusterFile() {
		return clusterFile;
	}

}
