package icaro.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class util {

	public util(){}
	public static String getStrDateSQL() {
		GregorianCalendar c=new GregorianCalendar();
		 int m = c.get(GregorianCalendar.MONTH) + 1;
		 int d = c.get(GregorianCalendar.DATE);
		 String mm = Integer.toString(m);
		 String dd = Integer.toString(d);
		 return "" +c.get(GregorianCalendar.YEAR)+"-"+(m < 10 ? "0" + mm : mm)+"-"+(d < 10 ? "0" + dd : dd)
		 +" "+getStrTime();
		}
	
	public static String getStrDateSQL2() {
		GregorianCalendar c=new GregorianCalendar();
		 int m = c.get(GregorianCalendar.MONTH) + 1;
		 int d = c.get(GregorianCalendar.DATE);
		 String mm = Integer.toString(m);
		 String dd = Integer.toString(d);
		 return ""+(d < 10 ? "0" + dd : dd)+"-"+(m < 10 ? "0" + mm : mm)+"-"+c.get(GregorianCalendar.YEAR);
		}
	
	public static String getStrDateSQL2(Date c) {
		int m = c.getMonth()+1;
		 int d = c.getDate();
		 String mm = Integer.toString(m);
		 String dd = Integer.toString(d);
		 return ""+(d < 10 ? "0" + dd : dd)+"-"+(m < 10 ? "0" + mm : mm)+"-"+(c.getYear()+1900);
		}
	public static String getStrDateSQL(Date c) {
		
		 int m = c.getMonth()+1;
		 int d = c.getDate();
		 String mm = Integer.toString(m);
		 String dd = Integer.toString(d);
		 return "" +(c.getYear()+1900)+"-"+(m < 10 ? "0" + mm : mm)+"-"+(d < 10 ? "0" + dd : dd)
		 +" "+getStrTime();
		}
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
	

	
	public static Date StrToDateSQL(String f) throws ParseException{
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	
	public static String getStrTime(Date c) {
		
		 int h = c.getHours();
		 int m = c.getMinutes();
		 int s = c.getSeconds();
		 String hh=Integer.toString(h);
		 String mm=Integer.toString(m);
		 String ss=Integer.toString(s);
		 return (h < 10 ? "0" + hh : hh)+":"+(m < 10 ? "0" + mm : mm)+":"+(s < 10 ? "0" + ss : ss);
		 
		}
	
	public static String formatoHora(Timestamp h){
		return String.valueOf(h).substring(11, 16);
		 
	}
}
