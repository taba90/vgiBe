package it.volpini.vgi.manager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import org.springframework.stereotype.Component;

import it.volpini.vgi.domain.UserLocation;

@Component
public class GeometryManager extends RasterizerManager{

	
	public static CoordinateReferenceSystem defaultCrs;
	
	public static final String DEFAULT_EPSG="3857";
	
	static {
		
			try {
				defaultCrs = CRS.decode(DEFAULT_EPSG);
			} catch (NoSuchAuthorityCodeException e) {
				e.printStackTrace();
			} catch (FactoryException e) {
				e.printStackTrace();
			}
	}
	
	public GeometryManager () {
	}
	
	public Coordinate reproject(String srcCRS, String trgtCRS, double y, double x) throws FactoryException, TransformException {
        CoordinateReferenceSystem srcSRID = CRS.decode("EPSG:" + srcCRS);
        CoordinateReferenceSystem trgtSRID = CRS.decode("EPSG:" + trgtCRS);
        MathTransform mathTransform = CRS.findMathTransform(srcSRID, trgtSRID, true);
        DirectPosition2D srcDirectPosition2D = new DirectPosition2D(srcSRID, x, y);
        DirectPosition2D destDirectPosition2D = new DirectPosition2D();
        mathTransform.transform(srcDirectPosition2D, destDirectPosition2D);
        Coordinate coordinates = new Coordinate(destDirectPosition2D.y, destDirectPosition2D.x);
        return coordinates;

    }
	
	
	public Point getPointReprojecting(float longitude, float latitude, String srcCRS, String trgtCRS) throws FactoryException, TransformException {
		GeometryFactory gf = getGeometryFactory();
		Coordinate coordinates= reproject(srcCRS, trgtCRS, longitude, latitude);
		return gf.createPoint(coordinates);
	}

	
	public ReferencedEnvelope getBoundingBoxFromPoints (List<UserLocation> locations,  CoordinateReferenceSystem crs) {
		
		Comparator<UserLocation> compareLat = Comparator.comparing(UserLocation::getLatitude);
		Comparator<UserLocation> compareLon = Comparator.comparing(UserLocation::getLongitude);
		locations.sort(compareLat);
		double maxLat = locations.get(locations.size()-1).getLocation().getY();
		double minLat = locations.get(0).getLocation().getY();
		locations.sort(compareLon);
		double maxLon = locations.get(locations.size()-1).getLocation().getX();
		double minLon = locations.get(0).getLocation().getX();
		return new ReferencedEnvelope(maxLon, minLon, maxLat, minLat, crs);
	}
	
	
	public Point getPoint(float y, float x) {
		GeometryFactory gf = getGeometryFactory();
		Coordinate coordinates = new Coordinate(y, x);
        return gf.createPoint(coordinates);
	}
	
	public GeometryFactory getGeometryFactory () {
		return new GeometryFactory(new PrecisionModel(), 3857);
	}

	public static CoordinateReferenceSystem createCrs(String epsg) throws Exception {
		try {
			return CRS.decode(epsg);
		} catch (NoSuchAuthorityCodeException ex) {
			throw new Exception(ex);
		} catch (FactoryException fe) {
			throw new Exception(fe);
		}
	}
	
	
}
