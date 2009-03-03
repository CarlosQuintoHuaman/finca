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
	public static String getStrTime() {
		GregorianCalendar c=new GregorianCalendar();
		 int h = c.get(GregorianCalendar.HOUR_OF_DAY);
		 int m = c.get(GregorianCalendar.MINUTE);
		 int s = c.get(GregorianCalendar.SECOND);
		 String hh=Integer.toString(h);
		 String mm=Integer.toString(m);
		 String ss=Integer.toString(s);
		 return (h < 10 ? "0" + hh : hh)+":"+(m < 10 ? "0" + mm : mm)+":"+(s < 10 ? "0" + ss : ss);
		 
		}
}
