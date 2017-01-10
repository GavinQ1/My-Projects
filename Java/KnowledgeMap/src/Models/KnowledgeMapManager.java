package Models;

import java.io.*;

/**
 *
 * @author Gavin
 */
public class KnowledgeMapManager {
    private static final String DEFAULT_SAVING_PATH = ".\\user_data\\";
    private KnowledgeMap map;
    private String saving_path;

    public KnowledgeMapManager() {
        this.map = null;
        this.saving_path = null;
        File dir = new File(DEFAULT_SAVING_PATH);
        if (!dir.exists())
            dir.mkdirs();
    }

    public KnowledgeMapManager(KnowledgeMap map) {
        this();
        this.map = map;
    }

    public void register(KnowledgeMap map) {
        this.map = map;
        this.saving_path = null;
    }
    
    public void register(KnowledgeMap map, String filePath) {
        this.map = map;
        this.saving_path = filePath;
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
        String path = this.saving_path;
        if (path == null)
            path = KnowledgeMapManager.DEFAULT_SAVING_PATH;
        this.saveMapAs(path + this.map.getName() + ".ser");
    }

    public void readMap(String filePath) {
        try {
            try (FileInputStream fileIn = new FileInputStream(filePath); 
                    ObjectInputStream in = new ObjectInputStream(fileIn)) {
                this.register((KnowledgeMap) in.readObject(), filePath);
            }
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println(filePath + " not found");
            c.printStackTrace();
        }
    }
    
    private void testReadMap(String filename) {
        String filePath = KnowledgeMapManager.DEFAULT_SAVING_PATH + filename + ".ser";
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
        String filePath = KnowledgeMapManager.DEFAULT_SAVING_PATH + filename + ".ser";
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
        map.addKnowledgeNodeTo("Test", new KnowledgeNode("a", "Symptom", "", "", "", "", ""));
        
        manager.saveMap();
        
        manager.testReadMap("Test");
        System.out.println(manager.map.getKnowldegeNodeFrom("Test", "a").getSignificance());
        
    }

}
