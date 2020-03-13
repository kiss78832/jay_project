package Date_Example;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.junit.Test;

/*
 * Date API 範例
 */
public class Date01 {

	/*
	 * (1).LocalDate、LocalTime、LocalDateTime 所創的實體都是「不可變」，採用ISO-8601的國際標準化組織。	test01()
	 * (2).Instant: 時間戳記(以Unix元年: 1970年1月1日00:00:00到某時間的毫秒值) 					   	test02()
	 * (3).Duration: 計算兩個 " 時間 " 之間的區隔。													test03()
	 * (4).Period: 計算兩個 " 日期 " 之間的區隔。													test04()
	 * (5).TemporalAdjusters: 時間校正器工具類。 (計算下個禮拜幾、某月的月初末，某年的年初末)。				test05()
	 * (6).TemporalAdjuster: 自定義工作日範例。												test06()
	 * (7).DateTimeFormatter: 格式化時間、日期，自定義日期格式										test07()
	 * (8).ZonedDate、ZonedTime、ZonedDateTime: 時區操作
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
		//	Duration.between(x,y) 計算兩個 " 時間 " 之間的區隔
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
		
		//	Period.between(x,y)  計算兩個 " 日期 " 之間的區隔
		Period period = Period.between(date01, date02);
		System.out.println("date01 與 date02相差幾年: "+period.getYears());
		System.out.println("date01 與 date02相差幾月: "+period.getMonths());
		System.out.println("date01 與 date02相差幾日: "+period.getDays());
	}
	
	@Test
	public void test05() {
		/*
		 *	TemporalAdjuster: 是一個介面底下 Temporal adjustInto(Temporal temporal)方法 。
		 *
		 *	Temporal: Temporal包中定義了整個日期時間框架的基礎：各種時間單位、時間調節器，以及在年月日時分秒中用到的各種屬性，
		 *			  Java8中的日期時間類都是實現了temporal包中的時間單位。
		 *
		 * 	TemporalAdjusters: 針對常用的方法集合起來變成工具來使用。
		 * 				(1).dayOfWeekInMonth -> 返回同一個月中每週的第幾天
		 *				(2).firstDayOfMonth -> 返回當月的第一天
		 *				(3).firstDayOfNextMonth -> 返回下月的第一天
		 *				(4).firstDayOfNextYear -> 返回下一年的第一天
		 *				(5).firstDayOfYear -> 返回本年的第一天
		 *				(6).firstInMonth -> 返回同一個月中第一個星期幾
		 *				(7).lastDayOfMonth -> 返回當月的最後一天
	 	 *				(8).lastDayOfNextMonth -> 返回下月的最後一天
		 *				(9).lastDayOfNextYear -> 返回下一年的最後一天
		 * 				(10).lastDayOfYear -> 返回本年的最後一天
		 *				(11).lastInMonth -> 返回同一個月中最後一個星期幾
		 *				(12).next / previous -> 返回後一個/前一個給定的星期幾
		 *				(13).nextOrSame / previousOrSame -> 返回後一個/前一個給定的星期幾，如果這個值滿足條件，直接返回
		 */
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		//withXXX 都是針對某年某月某日來去修改
		LocalDateTime changMonth  = now.withDayOfMonth(10);
		System.out.println(changMonth);
		
		//with()方法的另一個過載方法，它接收一個TemporalAdjuster引數，可以使我們更加靈活的調整日期
		LocalDateTime test = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		System.out.println(test);
	}
	
	@Test
	public void test06() {
		/*
		 *	自定義 : 利用TemporalAdjuster: 是一個介面底下 Temporal adjustInto(Temporal temporal)方法 。
		 */
		//	題目 : 下個工作日
		LocalDateTime nowTime = LocalDateTime.now();
		LocalDateTime workTime = nowTime.with((t)->{
			LocalDateTime dateTime = (LocalDateTime) t;
			DayOfWeek week = dateTime.getDayOfWeek();
			
			//	禮拜五使用就加三天
			if(week.equals(DayOfWeek.FRIDAY)) {
				return dateTime.plusDays(3);
			}
			//	禮拜五使用就加二天
			if(week.equals(DayOfWeek.SATURDAY)) {
				return dateTime.plusDays(2);
			}
			//	其餘加一天
			return dateTime.plusDays(1);
		});
		System.out.println("我的下個工作天是:  "+workTime);
	}
	
	@Test
	public void test07() {
		/*
		 *	格式化: 時間日期  DateTimeFormatter
		 */
		DateTimeFormatter format01 = DateTimeFormatter.ISO_DATE;
		DateTimeFormatter format02 = DateTimeFormatter.ISO_WEEK_DATE;
		DateTimeFormatter format03 = DateTimeFormatter.BASIC_ISO_DATE;
		
		LocalDateTime time = LocalDateTime.now();
		
		String dateFormat01 = time.format(format01);
		String dateFormat02 = time.format(format02);
		String dateFormat03 = time.format(format03);
		System.out.println(dateFormat01);
		System.out.println(dateFormat02);
		System.out.println(dateFormat03);
		
		System.out.println("------------------------------");
		
		//	自定義時間格式
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
		String str = myFormat.format(time);
		System.out.println(str);
		
		System.out.println("------------------------------");
		
		//	解析回原本的格式
		LocalDateTime newDate = time.parse(str,myFormat);
		System.out.println(newDate);
	}
	
	@Test
	public void test08() {
		//	所以支援的時區，可參考  Asia/Taipei America/New_York
		//	System.out.println(ZoneId.getAvailableZoneIds());
		
		//	紐約現在的時間
		LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
		System.out.println(dateTime);
		
		//	台灣時間與  UTC ，顯示與時區差距多少
		LocalDateTime dateTime01 = LocalDateTime.now(ZoneId.of("Asia/Taipei"));
		ZonedDateTime test = dateTime01.atZone(ZoneId.of("Asia/Taipei"));
		System.out.println(test);
		
	}
}
