package ch.zhaw.jroute.routedata;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ch.zhaw.jroute.model.Way;
import ch.zhaw.jroute.model.WayStatusEnum;
import ch.zhaw.jroute.model.Waypoint;

/**
 * BoxHandler Class for API Calls to openstreetmap.org needed for getting all
 * ways and waypoints with all values
 * 
 * defined through Interface IBoxHandler
 * 
 * @author pascal
 */
public class BoxHandler implements IBoxHandler {
	private static Logger logger = Logger.getLogger("org.apache.log4j");
	private final String openStreetMapBoxURL = "http://open.mapquestapi.com/xapi/api/0.6/way[bbox=";
	private List<String> streetFilterList = new ArrayList<String>();
	private IAPIConnector apiConnector;
	private HashMap<Long,Waypoint> allWaypoints;
	private HashMap<Long,Way> allWays;
	private List<Way> effectiveWayList;

	public BoxHandler(IAPIConnector connector) {
		this.apiConnector = connector;
	}

	/**
	 * defined through Interface IBoxHandler
	 */
	@Override
	public List<Way> getAllWays(double left, double bottom, double right,
			double top) throws IOException {
		allWaypoints = new HashMap<Long,Waypoint>();
		allWays = new HashMap<Long,Way>();
		
		// start method time measuring
		long startMethodTime = System.nanoTime();

		// check for correct coordinates
		checkLatitudeCoordinates(bottom, top);
		checkLongitudeCoordinates(left, right);

		// set street filter (manually)
		// addNewStreetFilter("motorway");
		// addNewStreetFilter("tertiary");
		// addNewStreetFilter("residential");

		// use filter
		String filterForURL = createFilterForURL();

		// create url
		URL boxURL = new URL(openStreetMapBoxURL + left + "," + bottom + ","
				+ right + "," + top + "][highway=" + filterForURL + "]");

		// start timer for connection
		long startApiCallTime = System.nanoTime();

		// get document via connection
		Document document = apiConnector.getDocumentOverNewConnection(boxURL);
		
		//just for test
        /*try {
            // Prepare the DOM document for writing
            Source source = new DOMSource(document);
 
            // Prepare the output file
            File file = new File("test.xml");
            Result result = new StreamResult(file);
 
            // Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
        } catch (TransformerException e) {
        }*/

	      
	      
		// stop timer for connection
		long endApiCallTime = System.nanoTime();
		long apiCallTime = endApiCallTime - startApiCallTime;

		// create xpath instance
		XPath xpath = XPathFactory.newInstance().newXPath();
		List<Way> matchingWayList = null;
		List<Waypoint> matchingWaypointList = null;

		try {

			// get all ways, which match with the filter
			long setSelectedWaysStartTime = System.nanoTime();
			matchingWayList = getSelectedWays(document, xpath);
			long setSelectedWaysEndTime = System.nanoTime();

			// set all waypoints for the ways
			long setWaypointsForWaysStartTime = System.nanoTime();
			matchingWaypointList = setWaypointsForWays(matchingWayList, document, xpath);
			long setWaypointsForWaysEndTime = System.nanoTime();

			// get and set all waypoint values
			long setValuesForWaypointsStartTime = System.nanoTime();
			matchingWaypointList = setValuesForWaypoints(matchingWaypointList, document, xpath);
			long setValuesForWaypointsEndTime = System.nanoTime();

			// performance tests
			logger.debug("time setSelectedWaysTime : "
					+ (setSelectedWaysEndTime - setSelectedWaysStartTime)
					+ " ns");
			logger.debug("time setWaypointsForWaysTime : "
					+ (setWaypointsForWaysEndTime - setWaypointsForWaysStartTime)
					+ " ns");
			logger.debug("time setValuesForWaypointsTime : "
					+ (setValuesForWaypointsEndTime - setValuesForWaypointsStartTime)
					+ " ns");

		} catch (XPathExpressionException e) {
			logger.fatal(e);
		}

		// create wayList for return
		List<Way> wayList = new ArrayList<Way>();
		// insert all matched ways
		wayList.addAll(matchingWayList);

		// counting ways and waypoints for debugging
		int matchedWaySize = matchingWayList.size();
		int matchedWaypointSize = matchingWaypointList.size();
		int filterSize = streetFilterList.size();

		// cleanup
		matchingWaypointList.clear();
		matchingWayList.clear();
		streetFilterList.clear();

		// stop timer for connection
		long endMethodTime = System.nanoTime();
		long methodTime = endMethodTime - startMethodTime;

		// sysout for debugging
		logger.debug("get all data from openstreetmap took: " + apiCallTime
				+ " ns");
		logger.debug("running whole method took: " + methodTime + " ns");
		logger.debug("time for document processing : "
				+ (methodTime - apiCallTime) + " ns");
		logger.debug("total ways matched: " + matchedWaySize);
		logger.debug("total waypoints matched: " + matchedWaypointSize);
		logger.debug("filter size: " + filterSize);

		handleCrossRoads(wayList);
		
		//return wayList;
		return this.effectiveWayList;
	}

	private String createFilterForURL() {
		String filterURL = null;
		for (String filter : streetFilterList) {
			if (filterURL == null) {
				filterURL = filter;
			} else {
				filterURL = filterURL + "|" + filter;
			}
		}
		if (filterURL == null) {
			filterURL = "*";
		}
		return filterURL;
	}

	/**
	 * adding an additional filter-value for the streets
	 * 
	 * @param filter
	 */
	private void addNewStreetFilter(String filter) {
		streetFilterList.add(filter);
	}

	/**
	 * get and set the coordinate values for the matched waypoints also set the
	 * position for a waypoint
	 * 
	 * @param waypointlist
	 * @param document
	 * @param xpath
	 * @return 
	 * @throws XPathExpressionException
	 */
	private List<Waypoint> setValuesForWaypoints(List<Waypoint> waypointList,
			Document document, XPath xpath) throws XPathExpressionException {

		// loop trough all matching waypoints
		for (Waypoint waypoint : waypointList) {

			long waypointID = waypoint.getWaypointID();
			Node waypointInXml = (Node) xpath.compile(
					"/osm/node[@id='" + waypointID + "']").evaluate(document,
					XPathConstants.NODE);

			double lat = Double.parseDouble(waypointInXml.getAttributes()
					.getNamedItem("lat").getNodeValue());

			double lon = Double.parseDouble(waypointInXml.getAttributes()
					.getNamedItem("lon").getNodeValue());

			// set latitude and longitude for waypoint
			waypoint.setLat(lat);
			waypoint.setLon(lon);

			// set position (lon and lat in one value)
			Angle latAngle = Angle.fromDegreesLatitude(lat);
			Angle lonAngle = Angle.fromDegreesLatitude(lon);
			Position pos = new Position(latAngle, lonAngle, 0);
			waypoint.setCenter(pos);
		}
		
		
		
		return waypointList;
	}
	
	private void handleCrossRoads(List<Way> wayList){
		
		List<Way> splittedWay = new ArrayList<Way>();
		
		for(Waypoint waypoint : allWaypoints.values()){
			if(waypoint.getAddedWays().size() > 1){
				for(Way way : waypoint.getAddedWays()){
					if(way.getStart() == waypoint || way.getEnd() == waypoint){
					}else{
						splittedWay.add(way);
						way.addSplitPoint(waypoint);
					}
				}
			}
		}
		
		for(Way way : splittedWay)
		{
			effectiveWayList.remove(way);
			
			int start = 0;
			int end = 0;
			long generatedKey = 0;
			
			for(Waypoint waypoint : way.getSplitPointList()){
				end = way.getWaypointList().indexOf(waypoint);
				
				if(end<start){
					int temp = end;
					end = start;
					start = temp;
				}
				
				List<Waypoint> newWaypoints = getWaypointSubset(start,end,way.getWaypointList());
				
				Way newWay = new Way();
				newWay.setStatus(WayStatusEnum.undefined);
				
				generatedKey = way.getWayID()+1;
				
				while(allWays.containsKey(generatedKey)){
					generatedKey++;
				}
				
				newWay.setWayID(generatedKey);
				newWay.setName(String.valueOf(way.getWayID())+"Part"+start+"-"+end);
				Waypoint startPoint = newWaypoints.get(0);
				Waypoint endPoint = waypoint;
				
				newWay.setStart(startPoint);
				newWay.setEnd(endPoint);
				newWay.setWaypointList(newWaypoints);
				effectiveWayList.add(newWay);
				allWays.put(newWay.getWayID(), newWay);
				
				start = end;
			}
			
			if(end != way.getWaypointList().size()-1){
				end = way.getWaypointList().size()-1;
				List<Waypoint> newWaypoints = getWaypointSubset(start,end,way.getWaypointList());
				Way newWay = new Way();
				newWay.setStatus(WayStatusEnum.undefined);
				newWay.setName(String.valueOf(way.getWayID())+"Part"+start+"-"+end);
				
				while(allWays.containsKey(generatedKey)){
					generatedKey++;
				}
				
				newWay.setWayID(generatedKey);
				start = 0;
				end = newWaypoints.size()-1;
				Waypoint startPoint = newWaypoints.get(start);
				Waypoint endPoint = newWaypoints.get(end);
				
				newWay.setStart(startPoint);
				newWay.setEnd(endPoint);
				newWay.setWaypointList(newWaypoints);
				effectiveWayList.add(newWay);
				allWays.put(newWay.getWayID(), newWay);
			}
		}
	}
	
	private List<Waypoint> getWaypointSubset(int from, int to, List<Waypoint> targetList){
		
		List<Waypoint> resultList = new ArrayList<Waypoint>();
		
		for(int i = from; i<=to;i++)
		{
			resultList.add(targetList.get(i));
		}
		
		return resultList;
		
	}

	/**
	 * get all waypoints for ways and set a waypointlist for every way set the
	 * start and end waypoint for a way
	 * 
	 * @param waylist
	 * @param document
	 * @param xpath
	 * @return 
	 * @throws XPathExpressionException
	 */
	private List<Waypoint> setWaypointsForWays(List<Way> wayList, Document document,
			XPath xpath) throws XPathExpressionException {

		List<Waypoint> matchingWaypointList = new ArrayList<Waypoint>();
		effectiveWayList = new ArrayList<Way>();
		
		// loop trough all ways
		for (Way way : wayList) {
			long wayID = way.getWayID();

			// create new waypointlist for every way
			List<Waypoint> waypointsFromWay = new ArrayList<Waypoint>();
			NodeList wayInXml = (NodeList) xpath.compile(
					"/osm/way[@id='" + wayID + "']/nd/@ref").evaluate(document,
					XPathConstants.NODESET);

			// loop trough all childnodes
			for (int j = 0; j < wayInXml.getLength(); j++) {
				long waypointID = Long.parseLong(wayInXml.item(j)
						.getNodeValue());
				
				Waypoint waypoint = null;
				
				if(allWaypoints.containsKey(waypointID)){
					waypoint = allWaypoints.get(waypointID);
				}
				else{
					waypoint = new Waypoint(waypointID);
				}
				
				waypoint.addWay(way);

				// add waypoint to the waypointlist of the way
				waypointsFromWay.add(waypoint);

				// add waypoint to the matching waypointlist
				matchingWaypointList.add(waypoint);
				allWaypoints.put(waypointID, waypoint);
			}
			
			// get start and end waypoint
			Waypoint tempStartWaypoint = waypointsFromWay.get(0);
			int lastTempWaypoint = waypointsFromWay.size() - 1;
			Waypoint tempEndWaypoint = waypointsFromWay.get(lastTempWaypoint);

			// set start and end for the way
			way.setStart(tempStartWaypoint);
			way.setEnd(tempEndWaypoint);

			// set waypointlist for the way
			way.setWaypointList(waypointsFromWay);
		}
		
		effectiveWayList.addAll(wayList);
		
		
		return matchingWaypointList;
	}

	/**
	 * get all ways
	 * 
	 * @param document
	 * @param xpath
	 * @return 
	 * @throws XPathExpressionException
	 * @exception IllegalArgumentException
	 */
	private List<Way> getSelectedWays(Document document, XPath xpath)
			throws XPathExpressionException {
		List<Way> matchingWayList = new ArrayList<Way>();
		
		NodeList wayInXml = (NodeList) xpath.compile("/osm/way/@id").evaluate(
				document, XPathConstants.NODESET);

		for (int i = 0; i < wayInXml.getLength(); i++) {
			long id = Long.parseLong(wayInXml.item(i).getNodeValue());

			// create new way
			Way way = new Way(id);
			way.setStatus(WayStatusEnum.undefined);

			// add way to list
			matchingWayList.add(way);
			allWays.put(way.getWayID(), way);
		}

		if (matchingWayList.isEmpty()) {
			throw new IllegalArgumentException("no way found in xml file");
		}
		
		return matchingWayList;

	}

	/**
	 * check, if the longitude coordinates are correct
	 * 
	 * @param left
	 * @param right
	 * @exception IllegalArgumentException
	 */
	private void checkLongitudeCoordinates(double left, double right) {
		if (left > 90 || left < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (right > 90 || right < -90) {
			throw new IllegalArgumentException(
					"left longitude must be between -90 and 90");
		}
		if (left > right) {
			throw new IllegalArgumentException(
					"first parameter can't be bigger than second!!");
		}

	}

	/**
	 * check, if the latitude coordinates are correct
	 * 
	 * @param bottom
	 * @param top
	 * @exception IllegalArgumentException
	 */
	private void checkLatitudeCoordinates(double bottom, double top) {
		if (top > 180 || top < -180) {
			throw new IllegalArgumentException(
					"top latitude must be between -180 and 180");
		}
		if (bottom > 180 || bottom < -180) {
			throw new IllegalArgumentException(
					"bottom latitude must be between -180 and 180");
		}
		if (bottom > top) {
			throw new IllegalArgumentException(
					"first parameter can't be bigger than second!!");
		}

	}

}
