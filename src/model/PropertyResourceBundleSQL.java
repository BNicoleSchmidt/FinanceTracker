package model;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertyResourceBundleSQL {

	private ResourceBundle rb;

	String getURL() {
		return rb.getString("db.url");
	}

	String getUserName() {
		return rb.getString("db.username");
	}

	String getPassword() {
		return rb.getString("db.password");
	}

	// constructor
	public PropertyResourceBundleSQL() throws IllegalArgumentException, NullPointerException, MissingResourceException {

		System.out.println("You are in PropertyResourceBundleSQL.");

		try {
			rb = ResourceBundle.getBundle("db");
			Enumeration<String> keys = rb.getKeys();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = (String) rb.getObject(key);
				System.out.println(key + " - " + value);
			}
		}

		catch (IllegalArgumentException e) {
			System.out.println("Got the Illegal Argument Exception " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		catch (NullPointerException e) {
			System.out.println("Got the Null Pointer Exception " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		catch (MissingResourceException e) {
			System.out.println("Got the Missing Resource Exception " + e.getMessage());
			e.printStackTrace();
			throw e;
		}

		finally {
			System.err.println("Finally block executed in class PropertyResourceBundleSQL");
			// System.exit(1);
		}

	}// end of constructor for PropertyResourceBundleSQL

}// end of class PropertyResourceBundle
