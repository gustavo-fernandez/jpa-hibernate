package com.example.jpahibernate;

import com.example.jpahibernate.entity.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JpaHibernateApp {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PeoplePU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    entityManager.getTransaction().begin();

    Person p = new Person();
    p.setName("Con Jpa");
    p.setLastName("Jpa Last Name");
    p.setDni("98765432");

    entityManager.persist(p);

    entityManager.getTransaction().commit();

    TypedQuery<Person> typedQuery = entityManager.createQuery("select c from Person c where c.dni = :dni", Person.class)
      .setParameter("dni", "98765432");
    Person person = typedQuery.getSingleResult();
    System.out.println(">>>> " + person);

    entityManager.close();
  }

}
