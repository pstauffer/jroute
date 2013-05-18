package ch.zhaw.jroute.performance;

import java.io.IOException;

public class PerfomanceBox {
	static long startTime;
	static long endTime;
	static long tookTime;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BoxHandlerWithElements boxElements = new BoxHandlerWithElements();
		BoxHandlerXpath boxXpath = new BoxHandlerXpath();
		BoxHandlerXPathRegex boxXpathRegex = new BoxHandlerXPathRegex();

		try {

			startTime = System.currentTimeMillis();
			boxXpathRegex.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			endTime = System.currentTimeMillis();
			tookTime = endTime - startTime;
			System.out
					.println("method boxXpathRegex took: " + tookTime + " ms");

			startTime = System.currentTimeMillis();
			boxElements.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			endTime = System.currentTimeMillis();
			tookTime = endTime - startTime;
			System.out.println("method boxElements took: " + tookTime + " ms");

			startTime = System.currentTimeMillis();
			boxXpath.getAllWays(-85.13076, 34.90578, -85.11613, 34.91437);
			endTime = System.currentTimeMillis();
			tookTime = endTime - startTime;
			System.out.println("method boxXpath took: " + tookTime + " ms");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
