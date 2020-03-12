package Date_Example;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;

import org.junit.Test;

/*
 * Date API 範例
 */
public class Date01 {

	/*
	 * (1).LocalDate、LocalTime、LocalDateTime 所創的實體都是「不可變」，採用ISO-8601的國際標準化組織。	test01()
	 * (2).Instant: 時間戳記(以Unix元年: 1970年1月1日00:00:00到某時間的毫秒值) 					   	test02()
	 * (3).Duration: 計算兩個 " 時間 " 之間的區隔													test03()
	 * (4).Period: 計算兩個 " 日期 " 之間的區隔													test04()
	 * 
	 *	進度:19章開始
	 */
	@Test
	public void test01() {
		//	取得現在時間 : 年月日時分秒    LocalDateTime.now()
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(dateTime);
		
		System.out.println("----------------------------");
		
		//	自己設置時間   LocalDateTime.of()
		LocalDateTime dateTime2 = LocalDateTime.of(2050, 01, 01, 12, 12);
		System.out.println(dateTime2);
		
		System.out.println("----------------------------");
		
	   /* LocalDateTime是年月日時分秒，所以要做時間運算可以針對 " 年月日時分秒 " 操作。 
		* LocalDate就只能對日期的運算進行操作。      LocalTime就只能對時間進行運算操作。
		* 	注意 : 不管怎麼操作都會產生一個新的實體。
		*/ 
		LocalDateTime dateTime3 = dateTime2.plusYears(5).minusMonths(2);
		System.out.println(dateTime3);
		
		System.out.println("----------------------------");
		
		//	取得單一部分就用getXXX()
		System.out.println(dateTime.getYear());
		System.out.println(dateTime.getDayOfMonth());
		System.out.println(dateTime.getMonth());//	英文
		System.out.println(dateTime.getMonthValue());//	中文
		System.out.println(dateTime.getDayOfWeek());//	禮拜幾(英文)
	}
	
	@Test
	public void test02() {
		//	Instant.now()   默認獲取UTC時區，格林威治標準時間。
		Instant instant = Instant.now();
		System.out.println("格林威治標準時間: "+instant);
		
		System.out.println("----------------------------");
		
		//	instant.atOffset()  台北時區+8:00
		OffsetDateTime taipeiTime = instant.atOffset(ZoneOffset.ofHours(8));
		System.out.println("taipeiTime: "+taipeiTime);
		
		System.out.println("----------------------------");
		
		//	instant.toEpochMilli()   時間轉成毫秒
		System.out.println("毫秒: "+instant.toEpochMilli());
		
		System.out.println("----------------------------");
		
		//	Instant.ofEpochSecond()    1970-01-01 00:00:00 + 秒
		Instant SecondChangDate = Instant.ofEpochSecond(60);
		System.out.println(SecondChangDate);
	}
	
	@Test
	public void test03() throws InterruptedException {
		//	也能使用LocalDateTime系列去計算
		Instant start = Instant.now();
		
		Thread.sleep(5000); //	睡5000毫秒
		
		Instant end = Instant.now();
		
		//getXXX裡頭只有秒、toXXX裡頭有毫秒、分鐘、時等等...
		Duration duration = Duration.between(start, end);
		System.out.println("ISO格式: "+duration);
		System.out.println("----------------------");
		System.out.println("Start 比較 End 相差: "+duration.toMillis()+"豪秒");
	}
	
	@Test
	public void test04() {
		LocalDate date01 = LocalDate.of(2015, 01, 01);
		LocalDate date02 = LocalDate.now();
		
		//	兩個日期互相比較
		Period period = Period.between(date01, date02);
		System.out.println("date01 與 date02相差幾年: "+period.getYears());
		System.out.println("date01 與 date02相差幾月: "+period.getMonths());
		System.out.println("date01 與 date02相差幾日: "+period.getDays());
	}
}
