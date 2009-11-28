package br.ufrgs.f180.math;

/**
 * Auxiliary functions for every mean.
 * 
 * @author Gabriel
 * 
 */
public class MathUtils {

	/**
	 * Makes a three's rule to normalize a value in a 0..1 interval
	 * 
	 * @param min
	 *            lowest saturation point. Values below this will return 0
	 * @param max
	 *            highest saturation point. Values above this will return 1
	 * @param value
	 * @return a double value between 0 and 1
	 * @throws Exception
	 */
	public static double normalize(double min, double max, double value)
			throws Exception {
		return normalize(min, max, value, 0, 1);
	}

	/**
	 * Makes a three's rule to normalize a value in a 0..1 interval
	 * 
	 * @param min
	 *            lowest saturation point. Values below this will return 0
	 * @param max
	 *            highest saturation point. Values above this will return 1
	 * @param value
	 * 
	 * @param start beginning of the normalization interval
	 * @param end ending of the normalization interval
	 * 
	 * @return a double value between start and end
	 * @throws Exception
	 * 
	 */
	public static double normalize(double min, double max, double value,
			double start, double end) throws Exception {
		if (max <= min)
			throw new Exception(
					"Min cannot be higher than max. Values entered are not valid.");
		if (end <= start)
			throw new Exception(
					"End cannot be higher than start. Values entered are not valid.");
		if (value >= max)
			return end;
		if (value <= min)
			return start;

		double i1 = max - min;
		double i2 = end - start;
		double y = (value - min) * i2 / i1;
		return y + start;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("0..1  " + normalize(0, 1, 0.5, 0, 1));
		System.out.println("-1..1  " + normalize(0, 1, 0.5, -1, 1));
		System.out.println("1..3  " + normalize(0, 1, 0.5, 1, 3));

		System.out.println("0..1  " + normalize(1, 2, 1.5, 0, 1));
		System.out.println("-1..1  " + normalize(1, 2, 1.5, -1, 1));
		System.out.println("1..3  " + normalize(1, 2, 1.5, 1, 3));

	}
}
