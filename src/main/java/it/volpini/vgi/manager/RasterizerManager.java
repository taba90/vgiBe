package it.volpini.vgi.manager;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.stream.ImageOutputStream;

import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.process.vector.HeatmapProcess;
import org.opengis.util.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;

import it.volpini.vgi.domain.UserLocation;

public class RasterizerManager {
	
	@Autowired
	private GeometryManager geoMan;
	
	@Autowired
	private FeatureManager featMan;
	
	
	public GridCoverage2D generateHeathMap (List<UserLocation> locations, Integer width, Integer height, Integer kernelRadius, Integer pixelPerCell) throws Exception {
		
		ReferencedEnvelope bounds = geoMan.getBoundingBoxFromPoints(locations, GeometryManager.defaultCrs);
		
		ProgressListener monitor = null;
        HeatmapProcess process = new HeatmapProcess();
        SimpleFeatureCollection coll = DataUtilities.simple(featMan.createFeatures(locations, featMan.getUserLocationFeatureType(null)));
        return process.execute(coll, kernelRadius, null, pixelPerCell, bounds, width, height, monitor);
	}
	
	public String writeRaster (GridCoverage2D raster, String pathToRaster) throws IllegalArgumentException, IOException {
		GeoTiffWriter writer = new GeoTiffWriter(new File (pathToRaster));
		writer.write(raster, null);
		writer.dispose();
		return pathToRaster;
		
	}
	
	public byte [] rasterToBytes (GridCoverage2D raster) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GeoTiffWriter writer = new GeoTiffWriter(baos);
		writer.write(raster, null);
		writer.dispose();
		return baos.toByteArray();
	}
	
	public byte[] getBytesFromRaster(ImageOutputStream ios) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(255);
        // byte imageByte;
        long counter = 0; 

        while (true) {
                try {
                        bos.write(ios.readByte());
                        counter++;
                } catch (EOFException e) {
                        System.out.println("End of Image Stream");
                        break;
                } catch (IOException e) {
                        System.out.println("Error processing the Image Stream");
                        break;
                }
        }
        System.out.println("Total bytes read=" + counter);
        byte[] retValue = bos.toByteArray();
        return retValue;
}
}
