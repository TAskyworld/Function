package functionitegretion.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

public class HttpHelpers {
	HttpClient httpClient = CustomerHttpClient.getHttpClient();
	
//	private void CheckAndOpenWifi(){
//		WifiManager wifiManager = (WifiManager)ApplicationData.context.getSystemService(Context.WIFI_SERVICE);
//		if (!wifiManager.isWifiEnabled()) {
//			wifiManager.setWifiEnabled(true);
//		}
//	}
	
	@SuppressWarnings("unused")
	private String doGet(String url) throws ClientProtocolException, IOException {
//		CheckAndOpenWifi();
		HttpGet request = new HttpGet(url);
		// ����GET���󣬲�����Ӧ����ת�����ַ���
		String response = httpClient.execute(request, new BasicResponseHandler());
		return response;
	}

	/**
	 * Post�ύ����
	 * @param url ������API��ַ
	 * @param postData ��Ҫ�ύ��JSON����
	 * @param handler �ύ��ɴ������
	 */
	public void doPost(String url, String postData, Handler handler) {
//		CheckAndOpenWifi();
		MyThread thread = new MyThread(url, postData,null, handler);
		new Thread(thread).start();
	}
	
	/**
	 * Post�ύ����
	 * @param url ������API��ַ
	 * @param params ��Ҫ�ύ�Ĳ���HashMap
	 * @param handler �ύ��ɴ������
	 */
	public void doPost(String url,Map<String, Object> params, Handler handler){
//		CheckAndOpenWifi();
		MyThread thread = new MyThread(url, null, params, handler);
		new Thread(thread).start();
	}

	public class MyThread implements Runnable {
		private Handler post;
		private String url;
		private String postData;
		private Map<String, Object> params;		

		public MyThread(String _url, String _postData,Map<String, Object> params, Handler _post) {
			this.url = _url;
			this.postData = _postData;
			this.post = _post;
			this.params = params;
		}

		@Override
		public void run() {
			String result = "";
			if(params == null){
				result = HttpHelpers.this.doPost(url, postData);
			}else{
				result = HttpHelpers.this.doPost(url, params);
			}
			Message msg = new Message();
			msg.what = 1;
			msg.obj = result;
			post.sendMessage(msg);
		}
	}
	
	private String doPost(String strAction, Map<String, Object> params){
		try{
			URL url = new URL(strAction);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("accept", "*/*");
			connection.connect();
			
			// POST����
			StringBuilder sbData = new StringBuilder();
			for(Entry<String, Object> entry : params.entrySet()){
				sbData.append(entry.getKey());
				sbData.append("=");
				sbData.append(URLEncoder.encode(entry.getValue().toString(),"utf-8"));
				sbData.append("&");
			}
			sbData.deleteCharAt(sbData.length() - 1);
			OutputStream os = (OutputStream) connection.getOutputStream();
			os.write(sbData.toString().getBytes());
			os.flush();
			os.close();
			
			// ��ȡ��Ӧ
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// �Ͽ�����
			connection.disconnect();
			String strResult = sb.toString();
			strResult = strResult.indexOf("</html>") >= 0 ? "" : strResult;
			return strResult;
		}catch(Exception e){
			return "";
		}
	}

	private String doPost(String strAction, String params) {
		try {
			// ��������
			URL url = new URL(strAction);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/Json");
			connection.connect();
			// POST����
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(params);
			out.flush();
			out.close();

			// ��ȡ��Ӧ
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			// �Ͽ�����
			connection.disconnect();
			String strResult = sb.toString();
			strResult = strResult.indexOf("</html>") >= 0 ? "" : strResult;
			return strResult;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
