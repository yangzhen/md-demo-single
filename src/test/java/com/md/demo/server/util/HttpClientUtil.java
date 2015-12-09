package com.md.demo.server.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * HttpClientUtil 
 * @author xinyan.yang
 * @date 2015年8月17日 下午4:49:14
 *
 */
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * get请求
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public static String get(String url, Map<String, String> params,
			Map<String, String> headers) {
		String body = null;
		HttpClient httpClient = createHttpClient();
		String requestUrl = buildRequestUrl(url, params);
		HttpGet get = new HttpGet(requestUrl);
		if (headers != null) {
			Header[] requestHeaders = convertToHeader(headers);
			if (requestHeaders != null) {
				get.setHeaders(requestHeaders);
			}
		}
		body = invoke(httpClient, get);
		return body;
	}

	/**
	 * post请求
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public static String post(String url, Map<String, String> entitys,
			Map<String, String> headers) {
		String body = null;
		HttpClient httpClient = createHttpClient();
		HttpPost post = new HttpPost(url);
		if (headers != null) {
			Header[] requestHeaders = convertToHeader(headers);
			if (requestHeaders != null) {
				post.setHeaders(requestHeaders);
			}
		}
		post.setEntity(convertToUrlEncodedFormEntity(entitys));
		body = invoke(httpClient, post);
		return body;
	}
	
	/**
	 * post请求 通过body传递json数据
	 * @param url
	 * @param params
	 * @param headers
	 * @return
	 */
	public static String post(String url, String json,
			Map<String, String> headers) {
		String body = null;
		HttpClient httpClient = createHttpClient();
		HttpPost post = new HttpPost(url);
		if (headers != null) {
			Header[] requestHeaders = convertToHeader(headers);
			if (requestHeaders != null) {
				post.setHeaders(requestHeaders);
			}
		}
		post.setEntity(convertToStringEntity(json));
		body = invoke(httpClient, post);
		return body;
	}

	private static HttpClient createHttpClient() {

		HttpParams params = new BasicHttpParams();

		HttpConnectionParams.setStaleCheckingEnabled(params, false);

		HttpConnectionParams.setConnectionTimeout(params, 5 * 1000);
		HttpConnectionParams.setSoTimeout(params, 5 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);

		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE,
				new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		HttpClientParams.setRedirecting(params, true);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),
				80));
		EasySSLSocketFactory sf = new EasySSLSocketFactory();
		schemeRegistry.register(new Scheme("https", sf, 443));

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);

		return new DefaultHttpClient(cm, params);
	}

	/**
	 * 创建URI
	 * @param url
	 * @param params
	 * @return
	 */
	private static String buildRequestUrl(String url, Map<String, String> params) {
		if (null == params)
			return url;
		StringBuilder sUrl = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sUrl.append("&").append(entry.getKey()).append('=').append(entry.getValue());
		}
		if (sUrl.length() > 0) {
			url += sUrl.toString().replaceFirst("&", "?");
		}
		return url;
	}

	/**
	 * 将map转换成header
	 * @param headersMap
	 * @return
	 */
	private static Header[] convertToHeader(Map<String, String> headersMap) {
		Header[] headers = null;
		if (headersMap != null && headersMap.size() > 0) {
			headers = new Header[headersMap.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : headersMap.entrySet()) {
				headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
			}
		}
		return headers;
	}

	/**
	 * 解析成entity
	 * @param params
	 * @return
	 */
	private static UrlEncodedFormEntity convertToUrlEncodedFormEntity(
			Map<String, String> params) {
		try {
			return new UrlEncodedFormEntity(convertToList(params), HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 解析成StringEntity
	 * @param params
	 * @return
	 */
	private static StringEntity convertToStringEntity(String params){
		if(params == null){
			return null;
		}
		try {
			return new StringEntity(params,HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 将map解析成list
	 * @param params
	 * @return
	 */
	private static List<NameValuePair> convertToList(Map<String, String> params) {
		List<NameValuePair> lParams = new ArrayList<NameValuePair>();
		NameValuePair param = null;
		if (null != params && params.size() > 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				param = new BasicNameValuePair(entry.getKey(), String.valueOf(entry
						.getValue()));
				lParams.add(param);
			}
		}
		return lParams;
	}

	/**
	 * @param httpclient
	 * @param request
	 * @return
	 */
	private static String invoke(HttpClient httpclient, HttpUriRequest request) {

		HttpResponse response = sendRequest(httpclient, request);
		String body = paseResponse(response);

		return body;
	}

	/**
	 * 发送请求
	 * @param httpclient
	 * @param httpost
	 * @return
	 */
	private static HttpResponse sendRequest(HttpClient httpclient, HttpUriRequest request) {
		HttpResponse response = null;
		try {
			response = httpclient.execute(request);
		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException error", e);
			throw new RuntimeException();
		} catch (IOException e) {
			logger.error("IOException error", e);
			throw new RuntimeException();
		} catch (Exception e) {
			logger.error("server error", e);
			throw new RuntimeException();
		}
		return response;
	}

	/**
	 * 解析返回数据
	 * @param response
	 * @return
	 */
	private static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		String body = null;
		try {
			body = EntityUtils.toString(entity);
		} catch (ParseException e) {
			logger.error("ParseException error", e);
			throw new RuntimeException();
		} catch (IOException e) {
			logger.error("IOException error", e);
			throw new RuntimeException();
		}

		return body;
	}

}
