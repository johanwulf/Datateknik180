package fractal;

import koch.Koch;
import mountain.Mountain;

public class FractalApplication {
	public static void main(String[] args) {
		Fractal[] fractals = new Fractal[2];
		fractals[0] = new Mountain(600);
		fractals[1] = new Koch(300);
		new FractalView(fractals, "Fraktaler", 600, 600);
	}

}
