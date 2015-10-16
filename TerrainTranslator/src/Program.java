
public class Program {

	    public static void main(String[] args) {
	        TerrainTranslator terrTrans = new TerrainTranslator();
	        terrTrans.countTerrainData();
	        //terrTrans.printTerrain();
			terrTrans.saveTerrainData("terrain");
	        terrTrans.saveImage();
	        System.out.println("Done");
	    }
}
