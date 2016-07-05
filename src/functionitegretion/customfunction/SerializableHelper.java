package functionitegretion.customfunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class SerializableHelper {
	private static SerializableHelper _serializer;

	public static SerializableHelper Instantiation() {
		if (_serializer == null) {
			_serializer = new SerializableHelper();
		}
		return _serializer;
	}
	
	// [start] 实现了Serializable接口的序列化与反序列化
	/**
	 * 使用writeObject写入Xml
	 * @param path 持久化对象路径
	 * @param object 需要序列化的对象
	 */
	public void doSerializer(String path,Object object){
		try{
			FileOutputStream fstream = new FileOutputStream(new File(path));
			ObjectOutputStream stream = new ObjectOutputStream(fstream);
			stream.writeObject(object);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 使用readObject解析持久化对象数据
	 * @param path 持久化对象数据地址
	 * @return 反序列化的对象
	 */
	public Object doDeserialize(String path){
		try{
			FileInputStream instream = new FileInputStream(new File(path));
			ObjectInputStream stream = new ObjectInputStream(instream);
			return stream.readObject();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	// [end]
	
	// [start] XmlSerializer序列化
	private void Serializer(Object object,XmlSerializer serializer){
		try{
			Field[] fields = object.getClass().getFields();
			for (Field item : fields) {
				serializer.startTag(null, item.getName());
				if(item.getType().getName().toString().equals("java.util.ArrayList")){
					Serializer(item.getType(), serializer);
				}
				else{
					serializer.text(item.get(object) == null ? "" : item.get(object).toString());
				}
				serializer.endTag(null, item.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 单个对象序列化成Xml
	 * 
	 * @param object
	 *            待序列化的对象
	 * @param fos
	 *            Xml输出流
	 * @return
	 */
	public void doSerializer(Object object, String path) {
		try {
			FileOutputStream stream = new FileOutputStream(path);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(stream, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, object.getClass().getSimpleName());
			
			Serializer(object, serializer);
			
			serializer.endTag(null, object.getClass().getSimpleName());
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * List<T>序列化成Xml
	 * 
	 * @param list
	 *            待序列化的对象
	 * @param fos
	 *            Xml输出流
	 */
	public <T> void doSerializer(List<T> list, String path) {
		try {
			FileOutputStream stream = new FileOutputStream(path);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(stream, "UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, list.getClass().getSimpleName());
			for (T item : list) {
				serializer.startTag(null, item.getClass().getSimpleName());
				
				Serializer(item, serializer);
				
				serializer.endTag(null, item.getClass().getSimpleName());
			}

			serializer.endTag(null, list.getClass().getSimpleName());
			serializer.endDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// [end]
	
	// [start] XStream序列化xml
	/**
	 * XStream生成xml
	 * @param obj 待序列化对象
	 * @param path 序列化xml地址
	 */
	public void doXStreamToXml(Object obj, String path){
		try {
			FileOutputStream stream = new FileOutputStream(path);
			XStream x = new XStream(new DomDriver());
			stream.write(x.toXML(obj).getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * XStream解析xml
	 * @param path 待解析xml地址
	 * @return 解析结果
	 */
	public Object doXSteamToObject(String path){
		try{
			FileInputStream stream = new FileInputStream(path);
			byte [] buffer = new byte[stream.available()];   
			stream.read(buffer);
			XStream x = new XStream(new DomDriver());
			x.autodetectAnnotations(true);
			return x.fromXML(EncodingUtils.getString(buffer, "UTF-8"));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	// [end]
	
	// [start] 对应XmlSerializer的解析 未实现(枚举反射问题)
	@SuppressWarnings("unchecked")
	public <T> List<T> doDeserialize(T obj, String path){
		List<T> result = null;
		try {
			FileInputStream stream = new FileInputStream(path);
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(stream,"utf-8");
			
			String classname = obj.getClass().getSimpleName();
			Field[] fields = obj.getClass().getFields();
			String name = "";
			for (int i = parser.getEventType(); i != XmlPullParser.END_DOCUMENT; i = parser.next()) {  
				switch (i) {  
				case XmlPullParser.START_TAG:  
					name = parser.getName();
					if ("ArrayList".equals(name)) { 
						result = new ArrayList<T>();
					} else if (classname.equals(name)) {  
						obj = (T)obj.getClass().newInstance();
					}else{
						for(Field item : fields){
							if(item.getName().equals(name)){
								item.set(obj, parser.nextText());
							}
						}
					}
					break;
				case XmlPullParser.END_TAG:  
					if (parser.getName().equals(classname)) {  
						result.add(obj);  
						obj = (T)obj.getClass().newInstance();
					}  
					break;  
				}  
			}
		} catch (Exception e) {			
			e.printStackTrace();
		}
		return result;
	}
	// [end]
} 
