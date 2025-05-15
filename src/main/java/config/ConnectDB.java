package config;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectDB {
    public static EntityManagerFactory conect() {
        return Persistence.createEntityManagerFactory("Hibernate-example");
    }

}
