import java.lang.reflect.Array;

public class Spielfeld {

	boolean[][] dimesion = new boolean[3][3];

	public void fillDimension() {

		for (int i = 0; i < dimesion.length; i++) {
			for (int j = 0; j < dimesion.length; j++) {
				dimesion[i][j] = false;
			}
		}

	}

	public static void main(String[] args) {

	}

}
