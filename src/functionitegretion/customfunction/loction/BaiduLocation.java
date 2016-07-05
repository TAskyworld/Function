package functionitegretion.customfunction.loction;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.location.Poi;
/**
 * �ٶȶ�λʵ��demo
 * @author TA
 *
 */
public class BaiduLocation {
	private LocationClient mLocationClient = null;
	private BDLocationListener myListener = new MyLocationListener();
	private Context mycontext;
	private Handler handler;
	 public BaiduLocation(Context context,Handler handlers){
		 this.mycontext = context;
		 this.handler = handlers;
		 if (mLocationClient == null) {
			 mLocationClient = new LocationClient(mycontext);     //����LocationClient��
		}
		 mLocationClient.registerLocationListener( myListener );    //ע���������
	 }
	/**
	 * ���ò�������λ
	 */
	public void initLocationclient() {
	        LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(LocationMode.Battery_Saving);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
	        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
	        option.setScanSpan(1000);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
	        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
	        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
	        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
	        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
	        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
	option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
	        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
	option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
	        mLocationClient.setLocOption(option);//���� LocationClientOption
	        mLocationClient.start();//������λsdk
	        mLocationClient.requestLocation();//����λ���첽���أ������locationListener�л�ȡ.
	    }
	 /**
	  * ������λSDK
	  */
	public void setLocationClientStop() {
		mLocationClient.stop();
	}
	/**
	 * ��λ�����¼�
	 * @author TA
	 *
	 */
	 public class MyLocationListener implements BDLocationListener {
		 
	        @Override
	        public void onReceiveLocation(BDLocation location) {
	            //Receive Location
	        	Message message = new Message();
	            StringBuffer sb = new StringBuffer(256);
	            sb.append("time : ");
	            sb.append(location.getTime()); //ʱ��
	            sb.append("\nerror code : ");
	            sb.append(location.getLocType());//��ȡ��λ����
	            sb.append("\nlatitude : ");
	            sb.append(location.getLatitude());//��ȡγ������
	            sb.append("\nlontitude : ");
	            sb.append(location.getLongitude());//��ȡ����
	            sb.append("\nradius : ");
	            sb.append(location.getRadius());//��ȡ��λ����,Ĭ��ֵ0.0f
	            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
	                sb.append("\nspeed : ");
	                sb.append(location.getSpeed());// ��λ������ÿСʱ
	                sb.append("\nsatellite : ");
	                sb.append(location.getSatelliteNumber());
	                sb.append("\nheight : ");
	                sb.append(location.getAltitude());// ��λ����
	                sb.append("\ndirection : ");
	                sb.append(location.getDirection());// ��λ��
	                sb.append("\naddr : ");
	                sb.append(location.getAddrStr()); //��ַ��Ϣ
	                sb.append("\ndescribe : ");
	                sb.append("gps��λ�ɹ�");
	                message.obj = location.getAddrStr().trim();
	            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
	                sb.append("\naddr : ");
	                sb.append(location.getAddrStr()); //��ַ��Ϣ
	                sb.append("\noperationers : ");
	                sb.append(location.getOperators()); //��Ӫ����Ϣ
	                sb.append("\ndescribe : ");
	                sb.append("���綨λ�ɹ�");
	                location.getProvince(); location.getCity();// ʡ����
	                location.getStreet(); location.getBuildingName();//�ֵ�������������
	                message.obj = location.getAddrStr().trim();
	            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
	                sb.append("\ndescribe : ");
	                sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
	            } else if (location.getLocType() == BDLocation.TypeServerError) {
	                sb.append("\ndescribe : ");
	                sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
	            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
	                sb.append("\ndescribe : ");
	                sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
	            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
	                sb.append("\ndescribe : ");
	                sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
	            }
	            sb.append("\nlocationdescribe : ");
	                sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
	                List<Poi> list = location.getPoiList();// POI����
	                if (list != null) {
	                    sb.append("\npoilist size = : ");
	                    sb.append(list.size());
	                    for (Poi p : list) {
	                        sb.append("\npoi= : ");
	                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
	                    }
	                }
	                handler.sendMessage(message);
	            Log.i("BaiduLocationApiDem", sb.toString());
	        }
	 }
}
