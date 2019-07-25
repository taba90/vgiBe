package it.volpini.vgi.manager;

import java.util.List;

import org.geotools.feature.AttributeTypeBuilder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.feature.type.GeometryType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import it.volpini.vgi.domain.UserLocation;

public class FeatureManager {

	
	
	private SimpleFeatureType userLocationType;
	
	
	
	public SimpleFeatureType getUserLocationFeatureType(String epsg) throws Exception {
		if (userLocationType == null) {
			CoordinateReferenceSystem crs;
			if (epsg != null && !epsg.trim().equals("")) {
				crs = GeometryManager.createCrs(epsg);
			}else {
				crs = GeometryManager.defaultCrs;
			}
			SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();
			b.setName("Location");
			b.add("the_geom", Point.class);
			b.add("id", Long.class);
			b.add("desc", String.class);
			b.add("nome", String.class);
			b.add("longitude", Float.class);
			b.add("latitude", Float.class);
			b.add("idLegenda", Integer.class);
			b.crs(crs);
			AttributeTypeBuilder build = new AttributeTypeBuilder();
			build.setNillable(true);
			build.setBinding(Point.class);
			build.setCRS(crs);
			GeometryType geometryType = build.buildGeometryType();
			GeometryDescriptor geomDescr = build.buildDescriptor("the_geom", geometryType);
			b.set(geomDescr);
			userLocationType = b.buildFeatureType();
		}
		return userLocationType;
	}
	
	
	
	public FeatureCollection<SimpleFeatureType, SimpleFeature> createFeatures (List<UserLocation> locations, SimpleFeatureType type) {
		DefaultFeatureCollection collection = new DefaultFeatureCollection();
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(type);
		for (UserLocation l : locations) {
			collection.add(createFeature(l, featureBuilder));
		}
		return collection;
	}
	
	public SimpleFeature createFeature(UserLocation location, SimpleFeatureBuilder builder) {
		builder.add(location.getLocation());
		builder.add(location.getId());
		builder.add(location.getDescrizione());
		builder.add(location.getNome());
		builder.add(location.getLongitude());
		builder.add(location.getLatitude());
		builder.add(location.getLegenda().getId().intValue());
		return builder.buildFeature(null);
	}
	
	
}
