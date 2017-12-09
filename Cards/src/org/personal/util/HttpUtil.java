package org.personal.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class HttpUtil {
	private static final int CONNECT_TIMEOUT = 5000;
	private static final int SOCKET_TIMEOUT = 5000;
	private static final int CONNECTION_REQUEST_TIMEOUT = 5000;
	private static final int CONNECTION_POOL_SIZE = 100;

	private static PoolingHttpClientConnectionManager connMgr;
	private static RequestConfig requestConfig;
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class.getName());

	private static Gson gson = new Gson();

	static {
		// 设置连接池
		connMgr = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		connMgr.setMaxTotal(CONNECTION_POOL_SIZE);
		connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(CONNECT_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(SOCKET_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}
	
	public static String doGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = null;
		HttpGet httpGet = new HttpGet(url.trim());
		CloseableHttpResponse response = null;
		try {

			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			resultString = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			logger.error("error ",e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error("error ",e);
				}
			}
		}
		return resultString;

	}

	public static boolean delete(String url, Map<String, String> headParams) {
		HttpDelete d = new HttpDelete(url);
		// 设置head
		if (headParams != null && headParams.size() > 0) {
			for (Map.Entry<String, String> head : headParams.entrySet()) {
				d.setHeader(head.getKey(), head.getValue());
			}
		}
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(d);
			if(response.getStatusLine().getStatusCode() == 200) {
				return true;
			}
			else {
				return false;
			}
		} catch (IOException e) {
			logger.error("error ",e);
			return false;
		} finally {
			if (response != null && response.getEntity() != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error("error ",e);
				}
			}
		}
	}

		/**
	 * 进行post请求，传入url、head、body参数（键值对形式），该函数会将组成body的键值对转为json串后作为body进行请求
	 * 
	 * @param url
	 * @param headParams
	 * @param bodyParams
	 * @return
	 */
	public static String doPost(String url, Map<String, String> headParams, Map<String, Object> bodyParams) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = null;
		HttpPost httpPost = new HttpPost(url.trim());
		CloseableHttpResponse response = null;

		try {
			httpPost.setConfig(requestConfig);

			// 设置head
			if (headParams != null && headParams.size() > 0) {
				for (Map.Entry<String, String> head : headParams.entrySet()) {
					httpPost.setHeader(head.getKey(), head.getValue());
				}
			}
			String json = null;
			// 设置body
			if (bodyParams != null && bodyParams.size() > 0) {

				json = gson.toJson(bodyParams);
				StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
				httpPost.setEntity(stringEntity);
			}
			logger.info("http send url:{},jsonStr:{}",url,json);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			resultString = EntityUtils.toString(entity, "UTF-8");
			logger.info("http util request url:{} result:{}",url,resultString);
		} catch (Exception e) {
			logger.error("error ",e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (Exception e) {
					logger.error("error ",e);
				}
			}
		}
		return resultString;
	}
	
	public static String doPostByParam(String url, Map<String, String> bodyParams) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String resultString = null;
		HttpPost httpPost = new HttpPost(url.trim());
		CloseableHttpResponse response = null;

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		if (bodyParams != null && bodyParams.size() > 0) {
			for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		try {
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			resultString = EntityUtils.toString(entity, "UTF-8");
		} catch (IOException e) {
			logger.error("error ",e);
		} finally {
			if (response != null) {
				try {
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					logger.error("error ",e);
				}
			}
		}
		return resultString;
	}
}