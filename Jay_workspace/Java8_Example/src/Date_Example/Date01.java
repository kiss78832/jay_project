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
 * Date API �d��
 */
public class Date01 {

	/*
	 * (1).LocalDate�BLocalTime�BLocalDateTime �ҳЪ����鳣�O�u���i�ܡv�A�ĥ�ISO-8601����ڼзǤƲ�´�C	test01()
	 * (2).Instant: �ɶ��W�O(�HUnix���~: 1970�~1��1��00:00:00��Y�ɶ����@���) 					   	test02()
	 * (3).Duration: �p���� " �ɶ� " �������Ϲj													test03()
	 * (4).Period: �p���� " ��� " �������Ϲj													test04()
	 * 
	 *	�i��:19���}�l
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
		
		//	��Ӥ�����ۤ��
		Period period = Period.between(date01, date02);
		System.out.println("date01 �P date02�ۮt�X�~: "+period.getYears());
		System.out.println("date01 �P date02�ۮt�X��: "+period.getMonths());
		System.out.println("date01 �P date02�ۮt�X��: "+period.getDays());
	}
}
