package chap13;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {
	
	public String now() {
		SimpleDateFormat format = new SimpleDateFormat("HH시 mm분 ss초");
		Date date = new Date();
		return format.format(date);
	}
}
