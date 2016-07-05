package functionitegretion.customfunction.wifi;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

/**
 * WIFI������
 * 
 * @author TA
 */
public class WifiUnitl {
	private static WifiUnitl unitl;
	private WifiManager manager;
	private Context context;

	private WifiUnitl(Context context) {
		this.context = context;
		manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
	}

	/**
	 * ����ģʽ
	 */
	public static WifiUnitl getInstantiation(Context context) {
		if (unitl == null) {
			unitl = new WifiUnitl(context);
		}
		return unitl;
	}

	/**
	 * Wifi�Ƿ��ѿ���
	 */
	public boolean isWifiEnabled() {
		return manager.isWifiEnabled();
	}
	/**
	 * ��ȡ�����ӵ�wifi��ssid
	 */
	public String getConnectedWifi() {
		String ssid = null;
		if (isWifiEnabled()) {
			ssid = manager.getConnectionInfo().getSSID().replace("\"", "");
		}
		return ssid;
	}
	/**
	 * ��ȡ�������ù���Wifi
	 */
	public List<WifiConfiguration> getConfiguredWifi() {
		return manager.getConfiguredNetworks();
	}
    /**
     * ��ȡ���������õ�Wifi����
     */
	public List<String> getConfiguredWifiName() {
		List<WifiConfiguration> listWifi = getConfiguredWifi();
		List<String> listname = new ArrayList<String>();
//		String[] WifiName = new String[listcof.size()];
		for(int i = 0; i < listWifi.size(); i++){
//			WifiName[i] = (String) listcof.get(i).SSID.subSequence(1, listcof.get(i).SSID.length()-1);
			listname.add((String) listWifi.get(i).SSID.subSequence(1, listWifi.get(i).SSID.length()-1));
		}
		return listname;
	}

	/**
	 * ����SSID��ȡWifi����
	 */
	public WifiConfiguration isExsits(String SSID) {
		List<WifiConfiguration> configs = manager.getConfiguredNetworks();
		for (WifiConfiguration cfg : configs) {
			if (cfg.SSID.equals("\"" + SSID + "\"")) {
				return cfg;
			}
		}
		return null;
	}

	/**
	 * ���ӵ�ָ��Wifi
	 */
	public void connectToWifi(String name) {
		if (!isWifiEnabled()) {
			manager.setWifiEnabled(true);
		}

		while (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				Thread.currentThread();
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}

		for (WifiConfiguration wifi : manager.getConfiguredNetworks()) {
			if (wifi.SSID.equals(name)) {
				manager.enableNetwork(manager.addNetwork(wifi), false);
				break;
			}
		}
	}

	/**
	 * �������ӵ�Wifi
	 */
	public void connectToWifi(String SSID, String Password) {
		if (!isWifiEnabled()) {
			manager.setWifiEnabled(true);
		}

		while (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				Thread.currentThread();
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}

		WifiConfiguration cfg = initConfiguration(SSID, Password);
		if (cfg == null)
			return;

		for (WifiConfiguration wifi : manager.getConfiguredNetworks()) {
			manager.removeNetwork(wifi.networkId);
		}

		while (!isWifiConnected()) {
			manager.enableNetwork(manager.addNetwork(cfg), false);
		}
	}

	/**
	 * ���Wifi�Ƿ�������
	 */
	public boolean isWifiConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifiNetworkInfo.isConnected();
	}

	/**
	 * ����SSID�������ʼ��Wifi����
	 */
	private WifiConfiguration initConfiguration(String SSID, String Password) {
		WifiConfiguration cfg = new WifiConfiguration();
		cfg.allowedAuthAlgorithms.clear();
		cfg.allowedGroupCiphers.clear();
		cfg.allowedKeyManagement.clear();
		cfg.allowedPairwiseCiphers.clear();
		cfg.allowedProtocols.clear();
		cfg.SSID = "\"" + SSID + "\"";
		/**
		 * �����뷽ʽ cfg.wepKeys[0] = "";
		 * cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		 * cfg.wepTxKeyIndex = 0;
		 */

		/**
		 * WEP����ģʽ cfg.preSharedKey = "\""+Password+"\""; cfg.hiddenSSID = true;
		 * cfg
		 * .allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
		 * cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
		 * cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		 * cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
		 * cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
		 * cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
		 * cfg.wepTxKeyIndex = 0;
		 */

		cfg.preSharedKey = "\"" + Password + "\"";
		cfg.hiddenSSID = true;
		cfg.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
		cfg.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
		cfg.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
		cfg.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
		cfg.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
		cfg.status = WifiConfiguration.Status.ENABLED;

		return cfg;
	}
}
