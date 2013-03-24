package ch.zhaw.jroute.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class HttpHandler {

	public static ArrayList<String> makeRequest(String newURL)
			throws IOException {
		ArrayList<String> blaa = new ArrayList<String>();

		URL url = new URL(newURL);
		URLConnection yc = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;
		{
			while ((inputLine = in.readLine()) != null) {
				blaa.add(inputLine);
			}
			in.close();
		}
		return blaa;
	}

	public static void getNode(int nodeID) throws IOException {
		System.out
				.println(makeRequest("http://www.openstreetmap.org/api/0.6/node/"
						+ nodeID));
	}

	public static void getWay(int wayID) throws IOException {
		System.out
				.println(makeRequest("http://www.openstreetmap.org/api/0.6/way/"
						+ wayID));
	}

}
