package db;

import java.util.List;
import java.util.Set;



public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();

	/**
	 * Register one user
	 * 
	 * @param userId
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @return boolean
	 */

}
