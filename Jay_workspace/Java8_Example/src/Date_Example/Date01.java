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
 * Date API �d��
 */
public class Date01 {

	/*
	 * (1).LocalDate�BLocalTime�BLocalDateTime �ҳЪ����鳣�O�u���i�ܡv�A�ĥ�ISO-8601����ڼзǤƲ�´�C	test01()
	 * (2).Instant: �ɶ��W�O(�HUnix���~: 1970�~1��1��00:00:00��Y�ɶ����@���) 					   	test02()
	 * (3).Duration: �p���� " �ɶ� " �������Ϲj�C													test03()
	 * (4).Period: �p���� " ��� " �������Ϲj�C													test04()
	 * (5).TemporalAdjusters: �ɶ��ե����u�����C (�p��U��§���X�B�Y�몺��쥽�A�Y�~���~�쥽)�C				test05()
	 * (6).TemporalAdjuster: �۩w�q�u�@��d�ҡC												test06()
	 * (7).DateTimeFormatter: �榡�Ʈɶ��B����A�۩w�q����榡										test07()
	 * (8).ZonedDate�BZonedTime�BZonedDateTime: �ɰϾާ@
	 */
	@Test
	public void test01() {
		//	���o�{�b�ɶ� : �~���ɤ���    LocalDateTime.now()
		LocalDateTime dateTime = LocalDateTime.now();
		System.out.println(dateTime);
		
		System.out.println("----------------------------");
		
		//	�ۤv�]�m�ɶ�   LocalDateTime.of()
		LocalDateTime dateTime2 = LocalDateTime.of(2050, 01, 01, 12, 12);
		System.out.println(dateTime2);
		
		System.out.println("----------------------------");
		
	   /* LocalDateTime�O�~���ɤ���A�ҥH�n���ɶ��B��i�H�w�� " �~���ɤ��� " �ާ@�C 
		* LocalDate�N�u��������B��i��ާ@�C      LocalTime�N�u���ɶ��i��B��ާ@�C
		* 	�`�N : ���ޫ��ާ@���|���ͤ@�ӷs������C
		*/ 
		LocalDateTime dateTime3 = dateTime2.plusYears(5).minusMonths(2);
		System.out.println(dateTime3);
		
		System.out.println("----------------------------");
		
		//	���o��@�����N��getXXX()
		System.out.println(dateTime.getYear());
		System.out.println(dateTime.getDayOfMonth());
		System.out.println(dateTime.getMonth());//	�^��
		System.out.println(dateTime.getMonthValue());//	����
		System.out.println(dateTime.getDayOfWeek());//	§���X(�^��)
	}
	
	@Test
	public void test02() {
		//	Instant.now()   �q�{���UTC�ɰϡA��L�ªv�зǮɶ��C
		Instant instant = Instant.now();
		System.out.println("��L�ªv�зǮɶ�: "+instant);
		
		System.out.println("----------------------------");
		
		//	instant.atOffset()  �x�_�ɰ�+8:00
		OffsetDateTime taipeiTime = instant.atOffset(ZoneOffset.ofHours(8));
		System.out.println("taipeiTime: "+taipeiTime);
		
		System.out.println("----------------------------");
		
		//	instant.toEpochMilli()   �ɶ��ন�@��
		System.out.println("�@��: "+instant.toEpochMilli());
		
		System.out.println("----------------------------");
		
		//	Instant.ofEpochSecond()    1970-01-01 00:00:00 + ��
		Instant SecondChangDate = Instant.ofEpochSecond(60);
		System.out.println(SecondChangDate);
	}
	
	@Test
	public void test03() throws InterruptedException {
		//	Duration.between(x,y) �p���� " �ɶ� " �������Ϲj
		//	�]��ϥ�LocalDateTime�t�C�h�p��
		Instant start = Instant.now();
		
		Thread.sleep(5000); //	��5000�@��
		
		Instant end = Instant.now();
		
		//getXXX���Y�u����BtoXXX���Y���@��B�����B�ɵ���...
		Duration duration = Duration.between(start, end);
		System.out.println("ISO�榡: "+duration);
		System.out.println("----------------------");
		System.out.println("Start ��� End �ۮt: "+duration.toMillis()+"����");
	}
	
	@Test
	public void test04() {
		LocalDate date01 = LocalDate.of(2015, 01, 01);
		LocalDate date02 = LocalDate.now();
		
		//	Period.between(x,y)  �p���� " ��� " �������Ϲj
		Period period = Period.between(date01, date02);
		System.out.println("date01 �P date02�ۮt�X�~: "+period.getYears());
		System.out.println("date01 �P date02�ۮt�X��: "+period.getMonths());
		System.out.println("date01 �P date02�ۮt�X��: "+period.getDays());
	}
	
	@Test
	public void test05() {
		/*
		 *	TemporalAdjuster: �O�@�Ӥ������U Temporal adjustInto(Temporal temporal)��k �C
		 *
		 *	Temporal: Temporal�]���w�q�F��Ӥ���ɶ��ج[����¦�G�U�خɶ����B�ɶ��ո`���A�H�Φb�~���ɤ����Ψ쪺�U���ݩʡA
		 *			  Java8��������ɶ������O��{�Ftemporal�]�����ɶ����C
		 *
		 * 	TemporalAdjusters: �w��`�Ϊ���k���X�_���ܦ��u��ӨϥΡC
		 * 				(1).dayOfWeekInMonth -> ��^�P�@�Ӥ뤤�C�g���ĴX��
		 *				(2).firstDayOfMonth -> ��^��몺�Ĥ@��
		 *				(3).firstDayOfNextMonth -> ��^�U�몺�Ĥ@��
		 *				(4).firstDayOfNextYear -> ��^�U�@�~���Ĥ@��
		 *				(5).firstDayOfYear -> ��^���~���Ĥ@��
		 *				(6).firstInMonth -> ��^�P�@�Ӥ뤤�Ĥ@�ӬP���X
		 *				(7).lastDayOfMonth -> ��^��몺�̫�@��
	 	 *				(8).lastDayOfNextMonth -> ��^�U�몺�̫�@��
		 *				(9).lastDayOfNextYear -> ��^�U�@�~���̫�@��
		 * 				(10).lastDayOfYear -> ��^���~���̫�@��
		 *				(11).lastInMonth -> ��^�P�@�Ӥ뤤�̫�@�ӬP���X
		 *				(12).next / previous -> ��^��@��/�e�@�ӵ��w���P���X
		 *				(13).nextOrSame / previousOrSame -> ��^��@��/�e�@�ӵ��w���P���X�A�p�G�o�ӭȺ�������A������^
		 */
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		//withXXX ���O�w��Y�~�Y��Y��ӥh�ק�
		LocalDateTime changMonth  = now.withDayOfMonth(10);
		System.out.println(changMonth);
		
		//with()��k���t�@�ӹL����k�A�������@��TemporalAdjuster�޼ơA�i�H�ϧڭ̧�[�F�����վ���
		LocalDateTime test = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
		System.out.println(test);
	}
	
	@Test
	public void test06() {
		/*
		 *	�۩w�q : �Q��TemporalAdjuster: �O�@�Ӥ������U Temporal adjustInto(Temporal temporal)��k �C
		 */
		//	�D�� : �U�Ӥu�@��
		LocalDateTime nowTime = LocalDateTime.now();
		LocalDateTime workTime = nowTime.with((t)->{
			LocalDateTime dateTime = (LocalDateTime) t;
			DayOfWeek week = dateTime.getDayOfWeek();
			
			//	§�����ϥδN�[�T��
			if(week.equals(DayOfWeek.FRIDAY)) {
				return dateTime.plusDays(3);
			}
			//	§�����ϥδN�[�G��
			if(week.equals(DayOfWeek.SATURDAY)) {
				return dateTime.plusDays(2);
			}
			//	��l�[�@��
			return dateTime.plusDays(1);
		});
		System.out.println("�ڪ��U�Ӥu�@�ѬO:  "+workTime);
	}
	
	@Test
	public void test07() {
		/*
		 *	�榡��: �ɶ����  DateTimeFormatter
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
		
		//	�۩w�q�ɶ��榡
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy�~MM��dd�� HH:mm:ss");
		String str = myFormat.format(time);
		System.out.println(str);
		
		System.out.println("------------------------------");
		
		//	�ѪR�^�쥻���榡
		LocalDateTime newDate = time.parse(str,myFormat);
		System.out.println(newDate);
	}
	
	@Test
	public void test08() {
		//	�ҥH�䴩���ɰϡA�i�Ѧ�  Asia/Taipei America/New_York
		//	System.out.println(ZoneId.getAvailableZoneIds());
		
		//	�ì��{�b���ɶ�
		LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("America/New_York"));
		System.out.println(dateTime);
		
		//	�x�W�ɶ��P  UTC �A��ܻP�ɰϮt�Z�h��
		LocalDateTime dateTime01 = LocalDateTime.now(ZoneId.of("Asia/Taipei"));
		ZonedDateTime test = dateTime01.atZone(ZoneId.of("Asia/Taipei"));
		System.out.println(test);
		
	}
}
