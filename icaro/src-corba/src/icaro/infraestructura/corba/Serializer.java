package icaro.infraestructura.corba;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

public class Serializer {
	
	public static String serialize(Object obj) {
		String serialized = null;
		try {
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream outputStream;

			
			
			outputStream = new ObjectOutputStream(new BufferedOutputStream(
					byteArray));
			outputStream.flush();
			outputStream.writeObject(obj);
			outputStream.flush();
			outputStream.close();
			
			byte[] b = Base64.encodeBase64(byteArray.toByteArray());
			
			serialized = new String(b);
			
			
			
			byteArray.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serialized;
	}
	
	public static Object deserialize(String obj) {
		Object deserialized = null;
		ByteArrayInputStream byteArray = new ByteArrayInputStream(
				Base64.decodeBase64(obj.getBytes()));
		try {
			ObjectInputStream inputStream = new ObjectInputStream(
					new BufferedInputStream(byteArray));
			deserialized = inputStream.readObject();
			inputStream.close();
			byteArray.close();	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deserialized;
	}
	

}
