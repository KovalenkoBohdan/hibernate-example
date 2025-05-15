package repository.impl;

import entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import repository.GenericDao;

import java.util.List;
import java.util.Objects;

public class StudentsRepository implements GenericDao<Student, Long> {
    EntityManagerFactory managerFactory;

    public StudentsRepository(EntityManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Override
    public void save(Student student) {
        EntityManager manager = this.managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.persist(student);
            transaction.commit();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (transaction.isActive())
                manager.close();
        }
    }


    @Override
    public Student findById(Long id) {
        EntityManager manager = managerFactory.createEntityManager();
        return manager
                .createQuery("SELECT p FROM Student p WHERE p.id = :id", Student.class)
                .setParameter("id", Objects.requireNonNull(id)).getSingleResult();
    }

    @Override
    public Student findByEmail(String email) {
        String sql = "FROM Student st WHERE st.email = :email";
        try (EntityManager manager = this.managerFactory.createEntityManager()) {
            return manager
                    .createQuery(sql, Student.class)
                    .setParameter("email", Objects.requireNonNull(email))
                    .getResultList().getFirst();
        }

    }

    @Override
    public List<Student> findAll() {
        try (EntityManager manager = this.managerFactory.createEntityManager()) {
            return manager.createQuery("SELECT s FROM Student s LEFT JOIN FETCH s.homeworks", Student.class).getResultList();
        }

    }

    @Override
    public Student update(Student student) {
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Student merge = manager.merge(student);
            transaction.commit();
            return merge;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        finally {
            manager.close();
        }

    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager manager = this.managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Student student = manager.find(Student.class, id);
            if (student != null) {
                manager.remove(student);
                transaction.commit();
                return true;
            } else
                transaction.rollback();
            return false;
        } catch (Exception e) {
            if (transaction.isActive())
                transaction.rollback();
            return false;
        } finally {
            if (transaction.isActive())
                manager.close();
        }

    }


}
