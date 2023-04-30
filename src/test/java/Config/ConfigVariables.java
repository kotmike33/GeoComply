package Config;

public interface ConfigVariables extends ConfigProviderInterface{
	String URL = ConfigProviderInterface.readConfig().getString("url");
	String USER_PROFILES_DIR = ConfigProviderInterface.readConfig().getString("userProfileDir");
	String USER_PROFILE = ConfigProviderInterface.readConfig().getString("userProfile");
	String EMAIL = ConfigProviderInterface.readConfig().getString("test1.email");
	String EMAIL_SUBJECT = ConfigProviderInterface.readConfig().getString("test1.emailSubject");
	String EMAIL_MESSAGE = ConfigProviderInterface.readConfig().getString("test1.emailMessage");

}
