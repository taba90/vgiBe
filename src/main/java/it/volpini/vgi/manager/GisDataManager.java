package it.volpini.vgi.manager;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.data.DefaultTransaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.data.simple.SimpleFeatureStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.volpini.vgi.domain.UserLocation;

@Component
public class GisDataManager extends RasterizerManager{

	@Autowired
	private FeatureManager featureMan;
	
	
	public String toShapeFile (String pathToFile, List<UserLocation> locations) throws Exception {
		File shapeFile = getNewShapeFile(pathToFile);
		ShapefileDataStoreFactory dataStoreFactory = new ShapefileDataStoreFactory();

        Map<String, Serializable> params = new HashMap<String, Serializable>();
        params.put("url", shapeFile.toURI().toURL());
        params.put("create spatial index", Boolean.TRUE);

        ShapefileDataStore newDataStore = (ShapefileDataStore) dataStoreFactory.createNewDataStore(params);
        newDataStore.createSchema(featureMan.getUserLocationFeatureType(null));
        
        DefaultTransaction transaction = new DefaultTransaction("create");

        String typeName = newDataStore.getTypeNames()[0];
        SimpleFeatureSource featureSource = newDataStore.getFeatureSource(typeName);

        if (featureSource instanceof SimpleFeatureStore) {
            SimpleFeatureStore featureStore = (SimpleFeatureStore) featureSource;

            featureStore.setTransaction(transaction);
            try {
                featureStore.addFeatures(featureMan.createFeatures(locations, newDataStore.getSchema()));
                transaction.commit();

            } catch (Exception problem) {
                problem.printStackTrace();
                transaction.rollback();

            } finally {
                transaction.close();
            }
        } else {
            System.out.println(typeName + " does not support read/write access");
            System.exit(1);
        }
        return shapeFile.getAbsolutePath();
	}
	
	
	private static File getNewShapeFile(String dirPath) {
        String newPath = dirPath + "erdkunder_locations.shp";

        File newFile = new File(newPath); //chooser.getSelectedFile();

        return newFile;
    }
}
