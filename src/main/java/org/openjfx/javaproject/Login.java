package org.openjfx.javaproject;

final class Login {
	private Login() {
		
	}
	public static boolean validateLogin(String Username, String password) {
		User findUser = UserStore.findUserbyName(Username);
		if(findUser == null) {
			return false;
		} else {
			boolean truePassword = findUser.checkPassword(password);
			if(truePassword == true) {
				return true;
			} else {
				return false;
			}
		}
	}
}