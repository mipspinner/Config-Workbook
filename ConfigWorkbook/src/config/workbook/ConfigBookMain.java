package config.workbook;
//Importing following classes
import com.sforce.soap.partner.fault.ApiFault;
import com.sforce.soap.partner.fault.LoginFault;
import com.sforce.ws.ConnectionException;

import java.io.IOException;
import java.util.*;

import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.sforce.soap.metadata.*;

public class ConfigBookMain {

	public static void main(String[] args) {
		//Creating an object of this class
		ConfigBookMain config = new ConfigBookMain();
		//Establishing a connection 
		MetadataConnection metadataConnection = config.login(args);
		//Checking if the connection has been establish properly
		if (metadataConnection != null) {
			System.out.println("Logged in Successfully...");
			System.out.println("Retrieving Metadata.....");

			try {
				//Retrieving metadata
				FileBasedMetadataCalls.retrieve(metadataConnection);
				System.out.println("Metadata Retrieved...");
				System.out.println("Writing to Config Book...");
				//Writing information into configbook 
				CreateConfigBook.ConfigBookWrite();
				System.out.println("ConfigBook Created Successfully...");
			} catch (SAXException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				System.out
						.println("Please check that configuration file has been configured as per documentation and user has write access to the path specified.");
			} catch (ParserConfigurationException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}
	//Method to establish connection
	private MetadataConnection login(String[] args) {
		MetadataConnection metadataConnection = null;
		Properties prop = ConfigurationProperties.getPropValues();

		try {
			//Checks if all the parameters has been entered
			if (args.length == 3) {
				System.out.println("Logging In...");
				metadataConnection = SalesforceLogin.getMetadataConnection(
						args[0], args[1], args[2]);
			} else if (args.length == 1 && Boolean.parseBoolean(args[0])) {
				System.out.println("Logging In...");
				if (prop.getProperty("username") != null
						&& prop.getProperty("password") != null
						&& prop.getProperty("endpoint") != null) {

					metadataConnection = SalesforceLogin.getMetadataConnection(
							prop.getProperty("username"),
							prop.getProperty("password"),
							prop.getProperty("endpoint"));
				} else {
					System.out.println("Please Enter all the Details!");
				}
			} else {
				//Displays form  
				SalesforceLoginForm loginForm = new SalesforceLoginForm();
				loginForm.setVisible(true);
				loginForm
						.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
			}
			return metadataConnection;
		} catch (LoginFault ex) {
			System.out.println(ex.getExceptionMessage());
		} catch (ApiFault ex) {
			System.out.println(ex.getExceptionMessage());
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return null;
	}
}