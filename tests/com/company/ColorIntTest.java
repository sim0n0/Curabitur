package com.company;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColorIntTest {

	protected ColorInt c;
	protected static String[] cTab = {
            "#3079ab",
            "#e15258",
            };
	@Before
	public void setUp() throws Exception {
		c = new ColorInt();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetColor() {
		assertEquals("#e15258", c.getCouleur(1));
	}

}
