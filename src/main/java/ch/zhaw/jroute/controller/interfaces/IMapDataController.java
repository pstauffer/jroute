package ch.zhaw.jroute.controller.interfaces;

import java.io.IOException;
import java.util.List;

public interface IMapDataController {
	public void getDataForMapSection(double lon1, double lat1, double lon2, double lat2,List<String> filterList) throws IOException;

}
