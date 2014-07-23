package config.workbook;

//Importing following files
import java.util.Properties;

import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SalesforceLogin {

	public static LoginResult result = new LoginResult();

	public static LoginResult login(String username, String password,
			String endpoint) throws ConnectionException {
		// Fetching values from config file
		Properties prop = ConfigurationProperties.getPropValues();

		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(username);
		config.setPassword(password);
		config.setAuthEndpoint(endpoint);
		config.setManualLogin(true);
		config.setServiceEndpoint(endpoint);

		LoginResult result = (new PartnerConnection(config)).login(username,
				password);
		// Set values of username,password,endpoint
		prop.setProperty("username", username);
		prop.setProperty("password", password);
		prop.setProperty("endpoint", endpoint);

		ConfigurationProperties.setPropValues(prop);

		return result;
	}

	// Logins into salesforce and returns partner connection
	public static PartnerConnection getPartnerConnection(String username,
			String password, String endpoint) throws ConnectionException {

		if (result.getSessionId() == null) {
			result = login(username, password, endpoint);
		}
		ConnectorConfig config = new ConnectorConfig();
		config.setManualLogin(true);
		config.setSessionId(result.getSessionId());
		config.setServiceEndpoint(result.getServerUrl());
		PartnerConnection partnerConnection = new PartnerConnection(config);
		partnerConnection.setSessionHeader(result.getSessionId());

		return partnerConnection;
	}

	// Logins into salesforce and returns metadata connection
	public static MetadataConnection getMetadataConnection(String username,
			String password, String endpoint) throws ConnectionException {

		if (result.getSessionId() == null) {
			result = login(username, password, endpoint);
		}
		ConnectorConfig config = new ConnectorConfig();
		config.setServiceEndpoint(result.getMetadataServerUrl());
		config.setSessionId(result.getSessionId());
		MetadataConnection metadataConnection = new MetadataConnection(config);

		return metadataConnection;
	}
}
