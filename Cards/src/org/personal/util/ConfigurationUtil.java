package org.personal.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.personal.util.pojo.InfoProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

public class ConfigurationUtil {
	private static Logger logger = LoggerFactory.getLogger(ConfigurationUtil.class.getName());
	public static BeanFactory beanFactory;
	
	private static final ConfigurationFactory factory = new ConfigurationFactory("propertyConfig.xml");
	private static Configuration config ;
	
	public static Map<Integer,InfoProxy> infoProxyConfig = new HashMap<Integer, InfoProxy>();
	public static final ExecutorService loggerPool = Executors.newSingleThreadExecutor();
	static{
		try {
			System.out.println("factory----"+factory);
			config = factory.getConfiguration();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
	static {
		logger.info("finish loading configuration:");
		String temp = "";
		
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = config.getKeys();
		
		while(iterator.hasNext()){
			temp = iterator.next();
			logger.info(temp + " -->"+ config.getString(temp));
		}
	}
	public static final int CHAT_REDIS_PROT = config.getInt("app.card.port");
	
	public static final String CHAT_REDIS_HOST = config.getString("app.card.addr");
	
	public static final String CHAT_REDIS_PWD = config.getString("app.card.pwd");
	//redis密码开关
	public static final boolean CHAT_REDIS_PWD_OPEN = config.getBoolean("card.redis.pwd.open", false);
}
