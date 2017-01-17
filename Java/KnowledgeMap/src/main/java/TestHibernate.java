
import Models.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Gavin
 */
public class TestHibernate {

    private static SessionFactory factory;

    public static void main(String[] args) {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            factory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        KnowledgeMap map = new KnowledgeMapImpl("Test");
        map.addCatagory("Test");
        System.out.println(map.containsCatagory("Test"));
        map.addKnowledgeNodeTo("Test", new KnowledgeNodeImpl("a", "Symptom", "", "", "", "", ""));

        TestHibernate t = new TestHibernate();
        Integer id = t.addMap(map);
        
        KnowledgeMap map2 = t.getMap(id);
        
        System.out.println(map2.getKnowldegeNodeFrom("Test", "a").chineseFormattedInformation());
    }

    public Integer addMap(KnowledgeMap map) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer mapID = null;
        try {
            tx = session.beginTransaction();
            mapID = (Integer) session.save(map);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mapID;
    }

    public KnowledgeMap getMap(Integer id) {
        Session session = factory.openSession();
        Transaction tx = null;
        KnowledgeMap map = null;
        try {
            tx = session.beginTransaction();
            map = (KnowledgeMap) session.get(KnowledgeMap.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return map;
    }
}
