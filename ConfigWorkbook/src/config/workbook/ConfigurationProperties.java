package config.workbook;
//Importing following files
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigurationProperties {
	//Method to get values from property file
	public static Properties getPropValues() {

		Properties prop = new Properties();
		//Initialising outputfile
		OutputStream outputFile = null;
		//Initialising inputfile
		InputStream inputFile = null;
		//Creating new file named "config.properties"
		File f = new File("config.properties");
		try {
			//Checks if file already exists
			if (!f.exists()) {
				outputFile = new FileOutputStream(f);
				prop.store(outputFile, null);
			}
			//Creates and load input file
			inputFile = new FileInputStream(f);
			prop.load(inputFile);
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		return prop;
	}
	//Method to set values in property file
	public static void setPropValues(Properties prop) {

		try {
			prop.store(new FileOutputStream("config.properties"), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
