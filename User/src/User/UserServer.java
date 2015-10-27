package User;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/**
 * Created by Jeven on 26/10/2015.
 */
public class UserServer {
	public void startServer(int port) {
		try {
			// 允许最大连接数
			int backLog = 10;
			InetSocketAddress inetSock = new InetSocketAddress(port);
			HttpServer httpServer = HttpServer.create(inetSock, backLog);

			httpServer.createContext("/", new HandlerTestA());
			// 显示已经处理的请求数，采用线程池
			// httpServer.createContext("/test",new HandlerTestB());B
			httpServer.setExecutor(null);
			httpServer.start();
			System.out.println("HttpServer Start success on port : " + port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 直接处理请求
	class HandlerTestA implements HttpHandler {

		public void handle(HttpExchange httpExchange) throws IOException {
			String IM_UID;// 用户ID
			String nickname;// 用户昵称
			String type;
			String text;
			HashMap<String, List<String>> params;
			// String homeAddress = null;
			// String companyAddress = null;
			// HashMap<String,String> userInfor = null;
			// HashMap<String,String> friendRel = null;

			// get request
			URI url = httpExchange.getRequestURI();
			String tmp = url.toString();
			tmp = URLDecoder.decode(tmp, "UTF-8");
			StringBuilder sb = new StringBuilder(tmp);
			String req = sb.deleteCharAt(0).toString();
			params = getQueryParams(tmp);
			String res = "";

			// extracting user info
			if (!req.equals("") || req != null) {
				try {
					Users us = new Users();
					if (req.contains("IM_UID")) {
						IM_UID = params.get("IM_UID").get(0);
						if (!IM_UID.equals("")) {
							if (req.contains("signUp")) {// 是注册请求
								if (req.contains("nickname=")) {
									nickname = params.get("nickname").get(0);
									String homeAddress = null;
									String companyAddress = null;
									if(req.contains("homeAddress=")){										
										homeAddress= params.get("homeAddress").get(0);
									}
									if(req.contains("companyAddress=")){										
										companyAddress= params.get("companyAddress").get(0);
									}
									//System.out.println(params.get("address"));
									if (us.signUp(IM_UID, nickname, homeAddress, companyAddress)) {
										res = "User registered successfully.";
									} else {
										res = "User already existed.";
									}	
								} else {
									res = "Illegal query string...";
								}
							} else if (req.contains("profile")) {// 是修改请求
								if (req.contains("friendIM_UID")) {
									String friendIM_UID = params.get("friendIM_UID").get(0);
									if (req.contains("addFriend")) {
										if (us.addFriend(IM_UID, friendIM_UID)) {
											res = "Friend added successfully.";
										} else {
											res = "No friendIM_UID.";
										}
									} else if (req.contains("deleteFriend")) {
										if (us.deleteFriend(IM_UID, friendIM_UID)) {
											res = "Friend deleted successfully.";
										} else {
											res = "No friendIM_UID.";
										}
									} else {
										res = "Illegal query string";
									}
								} else if (req.contains("revise")) {
									if (req.contains("type") && req.contains("text")) {
										type = params.get("type").get(0);
										text = params.get("type").get(0);
										if (us.revise(IM_UID, type, text)) {
											res = "Profile modified successfully.";
										} else {
											res = "User cannot be found.";
										}
									} else {
										res = "Illegal query string";
									}
								} else {
									res = "No friendIM_UID";
								}
							} else {
								res = "Illegal query string";
							}
						} else {
							res = "No IM_UID";
						}
					} else {
						res = "Illegal query string.";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// push the response
			// res = new String(res.getBytes(), "UTF-8");
			httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
			httpExchange.sendResponseHeaders(200, res.getBytes().length);
			OutputStream os = httpExchange.getResponseBody();
			os.write(res.getBytes("UTF-8"));
			os.close();
		}
	}

	public static HashMap<String, List<String>> getQueryParams(String url) {
		try {
			HashMap<String, List<String>> params = new HashMap<String, List<String>>();
			String[] urlParts = url.split("\\?");
			if (urlParts.length > 1) {
				String query = urlParts[1];
				for (String param : query.split("&")) {
					String[] pair = param.split("=");
					String key = URLDecoder.decode(pair[0], "UTF-8");
					String value = "";
					if (pair.length > 1) {
						value = URLDecoder.decode(pair[1], "UTF-8");
					}
					List<String> values = params.get(key);
					if (values == null) {
						values = new ArrayList<String>();
						params.put(key, values);
					}
					values.add(value);
				}
			}

			return params;
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError(ex);
		}
	}

	public static void main(String[] arvgs) {
		if (arvgs.length < 1) {
			System.out.println("please appoint the socket port!");
			System.exit(0);
		}
		int port = Integer.valueOf(arvgs[0]);
		UserServer ss = new UserServer();
		ss.startServer(port);
	}

}
