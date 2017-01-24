/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gavin
 */
import KnowledgeNodeModels.KnowledgeNode;
import KnowledgeNodeModels.KnowledgeNodeImpl;
import java.util.List;
import java.util.Properties;
 
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
 
/**
 * Hello world!
 * 
 */
public class App {
    private static SessionFactory sessionFactory = null;  
    private static ServiceRegistry serviceRegistry = null;  
       
    private static SessionFactory configureSessionFactory() throws HibernateException {  
        Configuration configuration = new Configuration();  
        configuration.configure();  
         
        Properties properties = configuration.getProperties();
            
        serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();          
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
         
        return sessionFactory;  
    }
     
    public static void main(String[] args) {
        // Configure the session factory
        configureSessionFactory();
         
        Session session = null;
        Transaction tx=null;
         
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
             
            // Creating Contact entity that will be save to the sqlite database
            KnowledgeNode a = new KnowledgeNodeImpl(1, "A1", "A", "A", "First", "Source", "AAA", "AAAA");
            KnowledgeNode b = new KnowledgeNodeImpl(2, "B1", "B", "B", "Second", "Source", "BBB", "BBBB");
            a.addDestination(b);
            // Saving to the database
            session.save(a);
            session.save(b);
             
            // Committing the change in the database.
            session.flush();
            tx.commit();
             
            // Fetching saved data
            List<KnowledgeNode> contactList = session.createQuery("from KnowledgeNode").list();
             
            for (KnowledgeNode contact : contactList) {
                System.out.println(contact.chineseFormattedInformation());
            }
             
        } catch (Exception ex) {
            ex.printStackTrace();
             
            // Rolling back the changes to make the data consistent in case of any failure 
            // in between multiple database write operations.
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }
}