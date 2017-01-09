package Models;

import java.io.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeMapManager {
    private static final String SAVING_PATH = ".\\user_data\\";
    private KnowledgeMap map;

    public KnowledgeMapManager() {
        this.map = null;
        File dir = new File(SAVING_PATH);
        if (!dir.exists())
            dir.mkdirs();
    }

    public KnowledgeMapManager(KnowledgeMap map) {
        this();
        this.map = map;
    }

    public void register(KnowledgeMap map) {
        this.map = map;
    }

    public KnowledgeMap getMap() {
        return this.map;
    }

    public void saveMapAs(String filePath) {
        try {
            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(this.map);
            }
            System.out.printf("Serialized data is saved in " + filePath + "\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void saveMap() {
        this.saveMapAs(KnowledgeMapManager.SAVING_PATH + this.map.getName() + ".ser");
    }

    public void readMap(String filePath) {
        try {
            try (FileInputStream fileIn = new FileInputStream(filePath); 
                    ObjectInputStream in = new ObjectInputStream(fileIn)) {
                this.register((KnowledgeMap) in.readObject());
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println(filePath + " not found");
            c.printStackTrace();
        }
    }
    
    private void testReadMap(String filename) {
        String filePath = KnowledgeMapManager.SAVING_PATH + filename + ".ser";
        try {
            try (FileInputStream fileIn = new FileInputStream(filePath); 
                    ObjectInputStream in = new ObjectInputStream(fileIn)) {
                this.register((KnowledgeMap) in.readObject());
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println(filePath + " not found");
            c.printStackTrace();
        }
    }
    
    private void testSaveMapAs(String filename) {
        String filePath = KnowledgeMapManager.SAVING_PATH + filename + ".ser";
        try {
            try (FileOutputStream fileOut = new FileOutputStream(filePath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(this.map);
            }
            System.out.printf("Serialized data is saved in " + filePath + "\n");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        KnowledgeMapManager manager = new KnowledgeMapManager();
        
        KnowledgeMap map = new KnowledgeMap("Test");
       
        manager.register(map);
        
        map.addCatagory("Test");
        System.out.println(map.containsCatagory("Test"));
        map.addKnowledgeNodeTo("Test", new Symptom("a", "", "", "", "", ""));
        
        manager.saveMap();
        
        manager.testReadMap("Test");
        System.out.println(manager.map.getKnowldegeNodeFrom("Test", "a").getSignificance());
        
    }

}
