package it.volpini.vgi.manager;
//package it.volpini.vgi.utils.geo;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.imageio.stream.ImageOutputStream;
//
//import org.geotools.coverage.grid.GridCoverage2D;
//import org.geotools.gce.geotiff.GeoTiffWriter;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.locationtech.jts.geom.Point;
//import org.mockito.Mockito;
//import org.opengis.referencing.FactoryException;
//import org.opengis.referencing.NoSuchAuthorityCodeException;
//import org.opengis.referencing.operation.TransformException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import it.volpini.vgi.dao.UserLocationDao;
//import it.volpini.vgi.domain.Legenda;
//import it.volpini.vgi.domain.UserLocation;
//import it.volpini.vgi.domain.VgiUser;
//import it.volpini.vgi.manager.GeometryManager;
//
//@RunWith(SpringRunner.class)
//public class GeometryUtilsTest {
//	
//	@TestConfiguration
//    static class ExportFeatureUtilsTestContextConfiguration {
//		
//		@Bean
//		protected GeometryManager geometryUtils () {
//			return new GeometryManager();
//		}
//		
//    }
//	
//	@MockBean
//	private UserLocationDao dao;
//	 
//	    
//	@Autowired
//	private GeometryManager utils;
//	
//	@Before
//	public void setUp() throws FactoryException, TransformException {
//		List<UserLocation> locations = new ArrayList<>(1);
//		Legenda legenda1 = new Legenda(1L);
//		Legenda legenda2 = new Legenda(2L);
//		VgiUser user = new VgiUser(1L);
//		UserLocation loc = new UserLocation();
//		loc.setId(1L);
//		loc.setDescrizione("desc");
//		loc.setNome("nome");
//		loc.setVgiUser(user);
//		loc.setLegenda(legenda1);
//		Point point = utils.getPoint(12.429482f, 41.908097f);
//		point.setSRID(3857);
//		loc.setLocation(point);
//		loc.setLongitude((float) point.getCoordinate().x);
//		loc.setLatitude((float) point.getCoordinate().y);
//		UserLocation loc1 = new UserLocation();
//		loc1.setId(1L);
//		loc1.setDescrizione("desc");
//		loc1.setNome("nome");
//		loc1.setVgiUser(user);
//		Point point1 = utils.getPoint(13.027683f, 42.471504f);
//		point1.setSRID(3857);
//		loc1.setLocation(point1);
//		loc1.setLongitude((float) point1.getCoordinate().x);
//		loc1.setLatitude((float) point1.getCoordinate().y);
//		loc1.setLegenda(legenda1);
//		UserLocation loc2 = new UserLocation();
//		loc2.setId(1L);
//		loc2.setDescrizione("desc");
//		loc2.setNome("nome");
//		loc2.setVgiUser(user);
//		Point point2 = utils.getPoint(12.884308f, 42.211055f);
//		point2.setSRID(3857);
//		loc2.setLocation(point2);
//		loc2.setLongitude((float) point2.getCoordinate().x);
//		loc2.setLatitude((float) point2.getCoordinate().y);
//		loc2.setLegenda(legenda2);
//		UserLocation loc3 = new UserLocation();
//		loc3.setId(1L);
//		loc3.setDescrizione("desc");
//		loc3.setNome("nome");
//		loc3.setVgiUser(user);
//		Point point3 = utils.getPoint(12.436294f, 41.898833f);
//		point3.setSRID(3857);
//		loc3.setLocation(point3);
//		loc3.setLongitude((float) point3.getCoordinate().x);
//		loc3.setLatitude((float) point3.getCoordinate().y);
//		loc3.setLegenda(legenda2);
//		locations.add(loc);
//		locations.add(loc1);
//		locations.add(loc2);
//		locations.add(loc3);
//		Mockito.when(dao.findAll()).thenReturn(locations);
//	}
//	
//	@Test
//	public void testRasterize() throws NoSuchAuthorityCodeException, IOException, FactoryException {
//
//		String dir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/image.GeoTIFF";
//		GridCoverage2D pointsRaster = utils.rasterize(dao.findAll());
//		
////		ByteArrayOutputStream  baos = new ByteArrayOutputStream();
////		final ImageOutputStream imageOutStream = ImageIO.createImageOutputStream(baos);
////		final GeoTiffWriter writer = new GeoTiffWriter(imageOutStream);
////		writer.write(pointsRaster, null);
//		utils.writeRaster(pointsRaster, dir);
//	}
//	
//	@Test
//	public void testHeatmap() throws NoSuchAuthorityCodeException, IOException, FactoryException {
//
//		String dir = getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/heatMap.GeoTIFF";
//		GridCoverage2D pointsRaster = utils.generateHeathMap(dao.findAll());
//		
////		ByteArrayOutputStream  baos = new ByteArrayOutputStream();
////		final ImageOutputStream imageOutStream = ImageIO.createImageOutputStream(baos);
////		final GeoTiffWriter writer = new GeoTiffWriter(imageOutStream);
////		writer.write(pointsRaster, null);
//		utils.rasterToBytes(pointsRaster);
//	}
//
//}
