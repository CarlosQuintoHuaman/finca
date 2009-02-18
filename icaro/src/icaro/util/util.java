package icaro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class util {

	public util(){}
	public static String getStrDate() {
		GregorianCalendar c=new GregorianCalendar();
		 int m = c.get(GregorianCalendar.MONTH) + 1;
		 int d = c.get(GregorianCalendar.DATE);
		 String mm = Integer.toString(m);
		 String dd = Integer.toString(d);
		 return "" +(d < 10 ? "0" + dd : dd)+"-"+(m < 10 ? "0" + mm : mm)+"-"+c.get(GregorianCalendar.YEAR);
		}
	
	public static Date StrToDate(String f) throws ParseException{
		
		SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date d=sdf.parse(f); 
		return d;
	}
}
