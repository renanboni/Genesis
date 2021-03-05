package db;

import db.models.DBPlayer;
import main.Server;
import model.Hash;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import java.io.File;

public class Database {

    protected final SessionFactory factory;
    protected final Server server;

    public Database(File configFile, Server server) {
        factory = new Configuration().configure(configFile).buildSessionFactory();

        this.server = server;
    }

    public DBPlayer getPlayer(String username, String password) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        DBPlayer dbPlayer = (DBPlayer) session.createCriteria(DBPlayer.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password))
                .uniqueResult();

        session.getTransaction().commit();

        return dbPlayer;
    }

    public void savePlayer(DBPlayer player) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        session.save(player);

        session.getTransaction().commit();
    }
}
