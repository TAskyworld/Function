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
 * 百度定位实现demo
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
			 mLocationClient = new LocationClient(mycontext);     //声明LocationClient类
		}
		 mLocationClient.registerLocationListener( myListener );    //注册监听函数
	 }
	/**
	 * 配置并开启定位
	 */
	public void initLocationclient() {
	        LocationClientOption option = new LocationClientOption();
	        option.setLocationMode(LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
	        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
	        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
	        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
	        option.setOpenGps(true);//可选，默认false,设置是否使用gps
	        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
	        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
	        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
	option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
	        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
	option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
	        mLocationClient.setLocOption(option);//设置 LocationClientOption
	        mLocationClient.start();//启动定位sdk
	        mLocationClient.requestLocation();//请求定位，异步返回，结果在locationListener中获取.
	    }
	 /**
	  * 结束定位SDK
	  */
	public void setLocationClientStop() {
		mLocationClient.stop();
	}
	/**
	 * 定位监听事件
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
	            sb.append(location.getTime()); //时间
	            sb.append("\nerror code : ");
	            sb.append(location.getLocType());//获取定位类型
	            sb.append("\nlatitude : ");
	            sb.append(location.getLatitude());//获取纬度坐标
	            sb.append("\nlontitude : ");
	            sb.append(location.getLongitude());//获取经度
	            sb.append("\nradius : ");
	            sb.append(location.getRadius());//获取定位精度,默认值0.0f
	            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
	                sb.append("\nspeed : ");
	                sb.append(location.getSpeed());// 单位：公里每小时
	                sb.append("\nsatellite : ");
	                sb.append(location.getSatelliteNumber());
	                sb.append("\nheight : ");
	                sb.append(location.getAltitude());// 单位：米
	                sb.append("\ndirection : ");
	                sb.append(location.getDirection());// 单位度
	                sb.append("\naddr : ");
	                sb.append(location.getAddrStr()); //地址信息
	                sb.append("\ndescribe : ");
	                sb.append("gps定位成功");
	                message.obj = location.getAddrStr().trim();
	            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
	                sb.append("\naddr : ");
	                sb.append(location.getAddrStr()); //地址信息
	                sb.append("\noperationers : ");
	                sb.append(location.getOperators()); //运营商信息
	                sb.append("\ndescribe : ");
	                sb.append("网络定位成功");
	                location.getProvince(); location.getCity();// 省、市
	                location.getStreet(); location.getBuildingName();//街道、建筑物名字
	                message.obj = location.getAddrStr().trim();
	            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
	                sb.append("\ndescribe : ");
	                sb.append("离线定位成功，离线定位结果也是有效的");
	            } else if (location.getLocType() == BDLocation.TypeServerError) {
	                sb.append("\ndescribe : ");
	                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
	            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
	                sb.append("\ndescribe : ");
	                sb.append("网络不同导致定位失败，请检查网络是否通畅");
	            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
	                sb.append("\ndescribe : ");
	                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
	            }
	            sb.append("\nlocationdescribe : ");
	                sb.append(location.getLocationDescribe());// 位置语义化信息
	                List<Poi> list = location.getPoiList();// POI数据
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
