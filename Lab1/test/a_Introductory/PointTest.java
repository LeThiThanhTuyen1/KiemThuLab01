package a_Introductory;


import org.junit.Before;
import org.junit.Test;
import java.lang.Integer;
import static org.junit.Assert.*;

public class PointTest {
	Point p1, p2, p3;
	
	@Before
	public void setUp() throws Exception {
		p1 = new Point(7, 9);
		p2 = new Point(-3, -30);
		p3 = new Point(-10, 3);
	}
	
	@Test
	public void testAdd() {
		Point res1 = p1.add(p2);
		Point res2 = p1.add(p3);
		
		assertEquals((Integer) 4, res1.x);
		assertEquals(Integer.valueOf(-21), res1.y);
		assertEquals(Integer.valueOf(-3), res2.x);
		//assertEquals(Integer.valueOf(12), res2.x);
		assertEquals(Integer.valueOf(12), res2.y);
	}
	
	@Test
	public void testSub() {
		Point res1 = p1.sub(p2);
		Point res2 = p1.sub(p3);
		
		//assertEquals((Integer) 4, res1.x);
		assertEquals((Integer) 10, res1.x);
		//assertEquals(Integer.valueOf(-21), res1.y);
		assertEquals(Integer.valueOf(39), res1.y);
		//assertEquals(Integer.valueOf(-3), res2.x);
		assertEquals(Integer.valueOf(17), res2.x);
		//assertEquals((Integer) 12, res2.x);
		assertEquals((Integer) 6, res2.y);
	}

}
