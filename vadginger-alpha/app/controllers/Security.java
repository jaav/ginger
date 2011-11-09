package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import models.VadGingerUser;

//import models.User;
import play.Logger;

public class Security extends Secure.Security {
  static boolean authentify(String userID, String password) {
    // create admin user if no users currently exist
    // don't forget to change the password and remove this code later
	if (VadGingerUser.count() == 0) {
      VadGingerUser adminUser = new VadGingerUser();
      adminUser.userID = "admin";
      adminUser.setPassword("admin");
      adminUser.save();
    }
    VadGingerUser user = VadGingerUser.find("byUserID", userID).first();
    if (user == null) {
      flash.error("Invalid userid or password.");
      return false;
    }
    //String passwordHash = md5(password);
    String passwordHash = play.libs.Codec.encodeBASE64(Security.md5(password));
    boolean match = user != null && user.passwordHash.equals(passwordHash);
    if (match) {
    	session.put("userId", user.id);
      user.loginCount++;
      user.merge();
      user.save();
    }
    return match;
  }

  public static String md5(String password) {
    byte[] bytesOfMessage = password.getBytes();
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      Logger.fatal(e, "System configuration error");
      return null;
    }
    byte[] thedigest = md.digest(bytesOfMessage);
    String passwordHash = new String(thedigest);
    return play.libs.Codec.encodeBASE64(passwordHash);
  }

  static boolean check(String profile) {
    VadGingerUser user = VadGingerUser.find("byUserID", connected()).first();
    return user.role != null && user.role.name().equalsIgnoreCase(profile);
  }
}

