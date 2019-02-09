package it.volpini.vgi.utils;

import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

@Component
public class GeometryUtils {
	
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
	
	public Point getPointFromLonLat(float longitude, float latitude) throws FactoryException, TransformException {
		GeometryFactory gf = getGeometryFactory();
		Coordinate coordinates= reproject("4326", "3857", longitude, latitude);
		return gf.createPoint(coordinates);
	}
	
	public Point getPoint(float y, float x) {
		GeometryFactory gf = getGeometryFactory();
		Coordinate coordinates = new Coordinate(y, x);
        return gf.createPoint(coordinates);
	}
	
	public GeometryFactory getGeometryFactory () {
		return new GeometryFactory(new PrecisionModel(), 3857);
	}

}
