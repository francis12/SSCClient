package com.ssc.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	public static final String DEFAULT_FORMAT_STR          = "yyyy-MM-dd";

	public static void main(String[] args) {
		String date = DateUtil.getWebsiteDatetime("http://www.baidu.com");
		System.out.println(date);
	}

	public static Date String2Date(String date, String format)  {
		Date result = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			result =  sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  result;
	}

	public static String date2String(Date date, String formatStr) {
		if (null == date)
			return "";
		if (formatStr == null)
			formatStr = DEFAULT_FORMAT_STR;
		DateFormat df = createFormatter(formatStr);
		return df.format(date);
	}
	public static DateFormat createFormatter(String pattern) {
		return new SimpleDateFormat(pattern);
	}
	/**
	 * 获取指定网站的日期时间
	 *
	 * @param webUrl
	 * @return
	 * @author SHANHY
	 * @date   2015年11月27日
	 */
	public static String getWebsiteDatetime(String webUrl){
		try {
			URL url = new URL(webUrl);// 取得资源对象
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect();// 发出连接
			long ld = uc.getDate();// 读取网站日期时间
			Date date = new Date(ld);// 转换为标准时间对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);// 输出北京时间
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/***
	 * 日期加减分钟
	 *
	 * @return 减一天：2014-11-23或(加一天：2014-11-25)
	 */
	public static Date addMinutes(int amount, Date date) {
		Calendar cl = Calendar.getInstance();

		cl.setTime(date);
		cl.add(Calendar.MINUTE, amount);

		date = cl.getTime();
		return date;
	}
	public static Date addHourss(int amount, Date date) {
		Calendar cl = Calendar.getInstance();

		cl.setTime(date);
		cl.add(Calendar.HOUR_OF_DAY, amount);

		date = cl.getTime();
		return date;
	}
}
