package org.codingnewtalking.toolbox.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * @author lixinjie
 * @since 2018-01-17
 */
public class DateUtils {
	
	private static final SimpleDateFormat sdf14 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat sdf16 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	private static final SimpleDateFormat sdf19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getDateStr14() {
		return getDateStr14(new Date());
	}
	
	public static String getDateStr14(Date date) {
		return sdf14.format(date);
	}
	
	public static String getDateStr16() {
		return getDateStr16(new Date());
	}
	
	public static String getDateStr16(Date date) {
		return sdf16.format(date);
	}
	
	public static String getDateStr19() {
		return getDateStr19(new Date());
	}
	
	public static String getDateStr19(Date date) {
		return sdf19.format(date);
	}
	
	public static boolean greatThan(Date time) {
		return greatThan(time, new Date());
	}
	
	public static boolean greatThan(Date time1, Date time2) {
		return time1.after(time2);
	}
	
	public static boolean lessThan(Date time) {
		return lessThan(time, new Date());
	}
	
	public static boolean lessThan(Date time1, Date time2) {
		return time1.before(time2);
	}

	public static Date nextCronDate(Date baseTime, String cronExpression) {
		return new CronSequenceGenerator(cronExpression).next(baseTime);
	}
	
	public static Date addInterval(long interval, IntervalUnit unit) {
		return addInterval(new Date(), interval, unit);
	}
	
	public static Date addInterval(Date baseTime, long interval, IntervalUnit unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(baseTime);
		switch (unit) {
			case Second: c.set(Calendar.SECOND, (int)(c.get(Calendar.SECOND) + interval)); return c.getTime();
			case Minute: c.set(Calendar.MINUTE, (int)(c.get(Calendar.MINUTE) + interval)); return c.getTime();
			case Hour: c.set(Calendar.HOUR_OF_DAY, (int)(c.get(Calendar.HOUR_OF_DAY) + interval)); return c.getTime();
			case Day: c.set(Calendar.DAY_OF_MONTH, (int)(c.get(Calendar.DAY_OF_MONTH) + interval)); return c.getTime();
			case Week: c.set(Calendar.WEEK_OF_YEAR, (int)(c.get(Calendar.WEEK_OF_YEAR) + interval)); return c.getTime();
			case Month: c.set(Calendar.MONTH, (int)(c.get(Calendar.MONTH) + interval)); return c.getTime();
			case Quarter: c.set(Calendar.MONTH, (int)(c.get(Calendar.MONTH) + interval * 3)); return c.getTime();
			case Year: c.set(Calendar.YEAR, (int)(c.get(Calendar.YEAR) + interval)); return c.getTime();
			default : return c.getTime();
		}
	}
	
	public static Date subInterval(long interval, IntervalUnit unit) {
		return subInterval(new Date(), interval, unit);
	}
	
	public static Date subInterval(Date baseTime, long interval, IntervalUnit unit) {
		return addInterval(baseTime, -interval, unit);
	}
	
	public static long dateDiff(Date time, IntervalUnit unit) {
		return dateDiff(new Date(), time, unit);
	}
	
	public static long dateDiff(Date baseTime, Date time, IntervalUnit unit) {
		switch (unit) {
			case Second: return dateDiffBySecond(baseTime, time);
			case Minute: return dateDiffByMinute(baseTime, time);
			case Hour: return dateDiffByHour(baseTime, time);
			case Day: return dateDiffByDay(baseTime, time);
			case Week: return dateDiffByWeek(baseTime, time);
			case Month: return dateDiffByMonth(baseTime, time);
			case Quarter: return dateDiffByQuarter(baseTime, time);
			case Year: return dateDiffByYear(baseTime, time);
			default : return 0;
		}
	}
	
	public static long dateDiffByQuarter(Date time) {
		return dateDiffByQuarter(new Date(), time);
	}
	
	public static long dateDiffByQuarter(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		long years = c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
		long month1 = c.get(Calendar.MONTH);
		long month2 = bc.get(Calendar.MONTH);
		month1 = (month1 >= 0 && month1 <= 2) ? 0 : (month1 >= 3 && month1 <= 5) ? 3 : (month1 >= 6 && month1 <= 8) ? 6 : 9;
		month2 = (month2 >= 0 && month2 <= 2) ? 0 : (month2 >= 3 && month2 <= 5) ? 3 : (month2 >= 6 && month2 <= 8) ? 6 : 9;
		long months = month1 - month2;
		return (years * 12 + months) / 3;
	}
	
	public static long dateDiffByWeek(Date time) {
		return dateDiffByWeek(new Date(), time);
	}
	
	public static long dateDiffByWeek(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (7 * 24 * 60 * 60 * 1000);
	}
	
	public static long dateDiffBySecond(Date time) {
		return dateDiffBySecond(new Date(), time);
	}
	
	public static long dateDiffBySecond(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (1000);
	}
	
	public static long dateDiffByMinute(Date time) {
		return dateDiffByMinute(new Date(), time);
	}
	
	public static long dateDiffByMinute(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (60 * 1000);
	}
	
	public static long dateDiffByHour(Date time) {
		return dateDiffByHour(new Date(), time);
	}
	
	public static long dateDiffByHour(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (60 * 60 * 1000);
	}
	
	public static long dateDiffByDay(Date time) {
		return dateDiffByDay(new Date(), time);
	}
	
	public static long dateDiffByDay(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		bc.set(Calendar.HOUR_OF_DAY, 0);
		bc.set(Calendar.MINUTE, 0);
		bc.set(Calendar.SECOND, 0);
		bc.set(Calendar.MILLISECOND, 0);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return (c.getTimeInMillis() - bc.getTimeInMillis()) / (24 * 60 * 60 * 1000);
	}
	
	public static long dateDiffByMonth(Date time) {
		return dateDiffByMonth(new Date(), time);
	}
	
	public static long dateDiffByMonth(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		long years = c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
		long months = c.get(Calendar.MONTH) - bc.get(Calendar.MONTH);
		return years * 12 + months;
	}
	
	public static long dateDiffByYear(Date time) {
		return dateDiffByYear(new Date(), time);
	}
	
	public static long dateDiffByYear(Date baseTime, Date time) {
		Calendar bc = Calendar.getInstance();
		bc.setTime(baseTime);
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		return c.get(Calendar.YEAR) - bc.get(Calendar.YEAR);
	}
	
}
