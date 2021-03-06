package comp3350.pbbs.business;

import comp3350.pbbs.application.Main;
import comp3350.pbbs.persistence.DataAccessController;
import comp3350.pbbs.persistence.DataAccessI;

/**
 * AccessUser
 * Group4
 * PBBS
 * <p>
 * This class creates an AccessUser class for the business layer.
 */
public class AccessUser {
	private DataAccessI dataAccess; // variable for the stubDatabase

	/**
	 * This method creates StubDatabase and initializes all the fields
	 */
	public AccessUser() {
		dataAccess = DataAccessController.getDataAccess(Main.dbName);
	}

	/**
	 * Getter for username
	 *
	 * @return username
	 */
	public String getUsername() {
		return dataAccess.getUsername();
	}

	/**
	 * setter for username, used when renaming
	 *
	 * @param newUsername new username
	 */
	public void setUsername(String newUsername) {
		dataAccess.setUsername(newUsername);
	}
}
