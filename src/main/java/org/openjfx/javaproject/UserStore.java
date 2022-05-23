package org.openjfx.javaproject;
import java.util.ArrayList;
//this is a static class -- should not be able to instantiate this.
final class UserStore {
	public static ArrayList<User> userStore = new ArrayList<>();
	private UserStore() {
		
	}
	public static User findUser(int userID) {
		for(int i = 0; i<userStore.size(); i++) {
			if(userStore.get(i).getUserID() == userID) {
				return(userStore.get(i));
			}
		}
		return null;
	}
	public static User findUserbyName(String userName) {
		for(int i = 0; i<userStore.size(); i++) {
			if(userStore.get(i).getUserName() == userName)  {
				return(userStore.get(i));
			}
		}
		return null;
	}
	public static String getUsernameByID(int userID) {
		for(int i = 0; i<userStore.size(); i++) {
			if(userStore.get(i).getUserID() == userID) {
				return(userStore.get(i).getUserName());
			}
		}
		return null;
	}
	public static void addUser(User user) {
		userStore.add(user);
	}
	public static boolean userExists(String userName) {
		for(int i = 0; i < userStore.size(); i++) {
			if(userStore.get(i).getUserName() == userName) {
				return true;
			}
		}
		return false;
	}
}