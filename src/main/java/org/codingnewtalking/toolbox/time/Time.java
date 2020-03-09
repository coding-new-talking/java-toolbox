package org.codingnewtalking.toolbox.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.time.zone.ZoneRulesProvider;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author lixinjie
 * @since 2020-03-02
 */
public class Time {

	public static void main(String[] args) {
		System.out.println(ZoneId.getAvailableZoneIds().size());
		System.out.println(ZoneId.getAvailableZoneIds());
		System.out.println(ZoneId.SHORT_IDS.size());
		System.out.println(ZoneId.SHORT_IDS);
		System.out.println(ZoneId.systemDefault());
		
		System.out.println(ZoneOffset.MAX.getId());
		System.out.println(ZoneOffset.MIN.getId());
		System.out.println(ZoneOffset.UTC.getId());
		
		System.out.println(ZoneOffset.ofHours(8).getId());
		System.out.println(ZoneOffset.ofHours(-8).getId());
		
		System.out.println(ZoneId.of("America/Chicago"));
		System.out.println(LocalDateTime.now());
		System.out.println(LocalDateTime.now(ZoneId.of("America/Chicago")));
		System.out.println(LocalDateTime.now(ZoneOffset.of("-6")));
		System.out.println(LocalDateTime.now(ZoneOffset.ofHours(-6)));
		System.out.println();
		System.out.println(LocalDateTime.now(ZoneOffset.of("Z")));
		System.out.println(LocalDateTime.now(ZoneOffset.of("+0")));
		System.out.println(LocalDateTime.now(ZoneOffset.of("-0")));
		System.out.println(LocalDateTime.now(ZoneOffset.ofHours(+0)));
		System.out.println(LocalDateTime.now(ZoneOffset.ofHours(-0)));
		System.out.println();
		System.out.println(LocalDateTime.MAX);
		System.out.println(LocalDateTime.MIN);
		
		System.out.println(ZoneId.of("Asia/Shanghai").getClass());
		System.out.println(ZoneId.of("Asia/Shanghai").normalized().getClass());
		System.out.println(ZoneId.of("America/Chicago").getClass());
		System.out.println(ZoneId.of("America/Chicago").normalized().getClass());
		
		System.out.println(ZoneRulesProvider.getAvailableZoneIds().size());
		System.out.println(ZoneRulesProvider.getAvailableZoneIds());
		System.out.println(ZoneRulesProvider.getRules("Asia/Shanghai", false));
		System.out.println(ZoneRulesProvider.getRules("America/Chicago", false));
		System.out.println(ZoneRulesProvider.getRules("Asia/Shanghai", false).getTransitions());
		System.out.println(ZoneRulesProvider.getRules("America/Chicago", false).getTransitions());
		System.out.println(ZoneRulesProvider.getRules("Asia/Shanghai", false).getTransitionRules());
		System.out.println(ZoneRulesProvider.getRules("America/Chicago", false).getTransitionRules());
		
		System.out.println();
		Calendar cn = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
		Calendar en = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
		Calendar us = Calendar.getInstance(TimeZone.getTimeZone("America/Chicago"));
		Calendar bf = Calendar.getInstance();
		bf.set(Calendar.YEAR, 1969);
		System.out.println(getDate(cn));
		System.out.println(getDate(en));
		System.out.println(getDate(us));
		//System.out.println(getDate(bf));
		System.out.println(cn.getTimeInMillis());
		System.out.println(en.getTimeInMillis());
		System.out.println(us.getTimeInMillis());
		//System.out.println(bf.getTimeInMillis());
		
		System.out.println();
		LocalDateTime cnldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
		LocalDateTime enldt = LocalDateTime.now(ZoneId.of("Europe/London"));
		LocalDateTime usldt = LocalDateTime.now(ZoneId.of("America/Chicago"));
		System.out.println(cnldt);
		System.out.println(enldt);
		System.out.println(usldt);
		System.out.println(cnldt.toEpochSecond(ZoneOffset.of("+8")));
		System.out.println(enldt.toEpochSecond(ZoneOffset.of("Z")));
		System.out.println(usldt.toEpochSecond(ZoneOffset.of("-6")));
		System.out.println();
		
		ZoneOffsetTransition zot = ZoneOffsetTransition.of(LocalDateTime.now().withNano(0), ZoneOffset.of("+8"), ZoneOffset.of("-6"));
		System.out.println(zot.getDateTimeBefore());
		System.out.println(zot.getDateTimeAfter());
		
		ZoneRules rules = ZoneId.of("Asia/Shanghai").getRules();
		LocalDateTime someTime = LocalDateTime.now();
		ZoneOffset offset = rules.getOffset(someTime);
		
		ZoneRules usaRules = ZoneId.of("America/Chicago").getRules();
		LocalDateTime chinaTime = LocalDateTime.now().withNano(0);
		
		ZoneRules chinaRules = ZoneId.of("Asia/Shanghai").getRules();
		ZoneOffset chinaOffset = chinaRules.getOffset(chinaTime);
		Instant instant = chinaTime.toInstant(chinaOffset);
		
		ZoneOffset usaOffset = usaRules.getOffset(instant);
		ZoneOffsetTransition china2usa = ZoneOffsetTransition.of(chinaTime, chinaOffset, usaOffset);
		System.out.println(china2usa.getDateTimeBefore());
		System.out.println(china2usa.getDateTimeAfter());
		
		Date now = new Date();
		System.out.println(now);
		System.out.println(now.getTime());
		System.out.println(System.currentTimeMillis());
		
		Calendar before = Calendar.getInstance();
		before.set(Calendar.YEAR, 1969);
		System.out.println(before.getTimeInMillis());
	}

	static String getDate(Calendar c) {
		return c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.DAY_OF_MONTH) + " "
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
	}
}
