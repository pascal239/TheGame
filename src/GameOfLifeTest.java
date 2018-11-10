import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GameOfLifeTest {

	Spielfeld test;

	@Before
	public void initialize() {

		test = new Spielfeld();
		test.fillDimension();

	}

	@Test
	public void testAllFeld() {

		for (int i = 0; i < test.dimesion.length; i++) {
			for (int j = 0; j < test.dimesion.length; j++) {
				assertEquals(test.dimesion[i][j] == false, true);
			}
		}
	}
}