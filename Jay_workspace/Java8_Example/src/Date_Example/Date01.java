package Date_Example;

import java.time.LocalDateTime;

import org.junit.Test;

/*
 * Date API �d��
 */
public class Date01 {

	/*
	 * (1).LocalDate�BLocalTime�BLocalDateTime �ҳЪ����鳣�O�u���i�ܡv�A�ĥ�ISO-8601����ڤ���зǡC	test01()
	 * (2).Instant : �ɶ��W�O(�HUnix���~: 1970�~1��1��00:00:00��Y�ɶ����@���) 					   	test02()
	 * 
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
}
