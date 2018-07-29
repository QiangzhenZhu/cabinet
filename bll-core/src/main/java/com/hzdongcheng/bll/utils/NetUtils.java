package com.hzdongcheng.bll.utils;

import com.hzdongcheng.components.toolkits.utils.Log4jUtils;
import com.hzdongcheng.components.toolkits.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;


/**
 *
 * @author Administrator
 */
public class NetUtils {
	static Log4jUtils log = Log4jUtils.createInstanse(NetUtils.class);
	/**
	 * URL地址是否存在
	 * 
	 * @param URLName
	 * @return
	 */
	static boolean exists(String URLName) {
		try {
			if (!StringUtils.contains(URLName, "http://") && !StringUtils.contains(URLName, "https://")) {
				URLName = "http://" + URLName;
			}
			// 设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();

			/*
			 * 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE
			 * 以上方法之一是合法的，具体取决于协议的限制。
			 */
			con.setRequestMethod("HEAD");
			con.setConnectTimeout(10000);// 超时时间10秒

			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	protected static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux下的命令，一般取eth0作为本地主网卡
			process = Runtime.getRuntime().exec("ifconfig eth0");
			// 显示信息中包含有mac地址信息
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("硬件地址");
				if (index >= 0) {// 找到了
					// 取出mac地址并去除2边空格
					mac = line.substring(index + "硬件地址".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("linux方式未获取到网卡地址");
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}

	public static String getMac() {
		try {
			Enumeration<NetworkInterface> el;
			el = NetworkInterface.getNetworkInterfaces();
			while (el.hasMoreElements()) {
				byte[] mac = el.nextElement().getHardwareAddress();
				if (mac == null)
					continue;

				StringBuffer sb = new StringBuffer("");
				for (int i = 0; i < mac.length; i++) {
					if (i != 0) {
						sb.append(":");
					}
					// 字节转换为整数
					int temp = mac[i] & 0xff;
					String str = Integer.toHexString(temp);
					if (str.length() == 1) {
						sb.append("0" + str);
					} else {
						sb.append(str);
					}
				}
				return sb.toString().toUpperCase();
			}
		} catch (SocketException e){
			log.error("GET LOCAL MAC FAILED" + e.getMessage());
			return null;
		}
		catch(NullPointerException e) {
			log.error("GET LOCAL MAC FAILED" + e.getMessage());
			return null;
		}
		return null;
	}

	public static String getIP() {
		try {
			Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
			while (el.hasMoreElements()) {
				NetworkInterface net = el.nextElement();
				Enumeration<InetAddress> addresses = net.getInetAddresses();
				InetAddress ip = null;
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						return ip.getHostAddress();
					}
				}
			}

		} catch (SocketException e){
			log.error("GET LOCAL IP FAILED" + e.getMessage());
		}
		catch(NullPointerException e) {
			log.error("GET LOCAL IP FAILED" + e.getMessage());
			return null;
		}
		return null;
	}
}
