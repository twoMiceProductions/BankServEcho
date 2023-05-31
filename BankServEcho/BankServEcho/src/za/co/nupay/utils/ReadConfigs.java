package za.co.nupay.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class ReadConfigs {
    public static final String USER;
    public static final String PASSWORD;
    public static final String WMQ_QUEUE_MANAGER;
    public static final String REPLYTO_Q_NAME;
    public static final String INITIALCONTEXTURL;
    public static final String CONNECTIONFACTORY;
    public static final String DESTINATION;

     
    
    static {
        XMLConfiguration configRead = null;
        try {
            configRead = new XMLConfiguration("BSEchoR.xml");
            System.out.println("BSEchoR.xml");
        } catch (ConfigurationException e) {
            e.printStackTrace();

        }

        USER = configRead.getString("user");
        PASSWORD = configRead.getString("password");
        WMQ_QUEUE_MANAGER = configRead.getString("qManager");
        REPLYTO_Q_NAME = configRead.getString("replyToQName");
        INITIALCONTEXTURL = configRead.getString("initialContextUrl");
        CONNECTIONFACTORY = configRead.getString("connectionFactoryFromJndi");
        DESTINATION = configRead.getString("destinationFromJndi");

    }
}