package logic;

import java.util.ArrayList;
import java.util.HashMap;
//Made this Class so that JsonParsing can be done single time and assign field without declare reader or Type
public class MapData {
	    private ArrayList<ArrayList<String>> tiles;
	    private ArrayList<ArrayList<String>> objects;
	    private HashMap<String, StaticObjectDetail> staticObjectsDetail;
	    private HashMap<String, DynamicObjectDetail> dynamicObjectsDetail;

		public ArrayList<ArrayList<String>> getTiles() {
			return tiles;
		}
		public ArrayList<ArrayList<String>> getObjects() {
			return objects;
		}
		public HashMap<String, StaticObjectDetail> getStaticObjectsDetail() {
			return staticObjectsDetail;
		}
		public HashMap<String, DynamicObjectDetail> getDynamicObjectsDetail() { 
			return dynamicObjectsDetail; 
		}

}
