package ch.zhaw.jroute.view.layer;

import gov.nasa.worldwind.avlist.*;
import gov.nasa.worldwind.geom.*;
import gov.nasa.worldwind.layers.mercator.*;
import gov.nasa.worldwind.util.*;

import java.net.*;

/**
 * Represent a layer which connects to openstreetmap and shows the maptiles
 * @author yk
 */
public class OSMMapnikLayer extends BasicMercatorTiledImageLayer
{
    public OSMMapnikLayer()
    {
        super(makeLevels());
    }

    /**
     * Sets all config values to get the right data
     * @return
     */
    private static LevelSet makeLevels()
    {
        AVList params = new AVListImpl();
        // width of map tile
        params.setValue(AVKey.TILE_WIDTH, 256);
        // height of map tile
        params.setValue(AVKey.TILE_HEIGHT, 256);
        // part of the mapserver name
        params.setValue(AVKey.DATA_CACHE_NAME, "Earth/OSM-Mercator/OpenStreetMap Mapnik");
        // other part of the mapserver
        params.setValue(AVKey.SERVICE, "http://a.tile.openstreetmap.org/");
        params.setValue(AVKey.DATASET_NAME, "h");
        params.setValue(AVKey.FORMAT_SUFFIX, ".png");
        params.setValue(AVKey.NUM_LEVELS, 16);
        params.setValue(AVKey.NUM_EMPTY_LEVELS, 0);
        params.setValue(AVKey.LEVEL_ZERO_TILE_DELTA, new LatLon(Angle
            .fromDegrees(22.5d), Angle.fromDegrees(45d)));
        params.setValue(AVKey.SECTOR, new MercatorSector(-1.0, 1.0, Angle.NEG180, Angle.POS180));
        params.setValue(AVKey.TILE_URL_BUILDER, new URLBuilder());

        return new LevelSet(params);
    }

    /**
     * Constructs the url to get the right data from the mapserver
     * @author yk
     *
     */
    private static class URLBuilder implements TileUrlBuilder
    {
        public URL getURL(Tile tile, String imageFormat)
            throws MalformedURLException
        {
            return new URL(tile.getLevel().getService()
                + (tile.getLevelNumber() + 3) + "/" + tile.getColumn() + "/"
                + ((1 << (tile.getLevelNumber()) + 3) - 1 - tile.getRow()) + ".png");
        }
    }

    @Override
    public String toString()
    {
        return "OpenStreetMap";
    }
}