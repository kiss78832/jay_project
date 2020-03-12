package Date_Example;

import java.time.LocalDateTime;

import org.junit.Test;

/*
 * Date API 範例
 */
public class Date01 {

	/*
	 * (1).LocalDate、LocalTime、LocalDateTime 所創的實體都是「不可變」，採用ISO-8601的國際日期標準。	test01()
	 * (2).Instant : 時間戳記(以Unix元年: 1970年1月1日00:00:00到某時間的毫秒值) 					   	test02()
	 * 
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
}
