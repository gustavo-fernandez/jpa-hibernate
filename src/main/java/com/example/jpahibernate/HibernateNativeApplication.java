package com.example.jpahibernate;

import com.example.jpahibernate.entity.Person;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class HibernateNativeApplication {

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.addAnnotatedClass(Person.class);
		Map<String, String> mapConfig = Map.of(
			"hibernate.connection.username", "root",
			"hibernate.connection.password", "12345678",
			"hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver",
			"hibernate.connection.url", "jdbc:mysql://localhost:3306/people",
			"hibernate.hbm2ddl.auto", "create-drop"
		);
		SessionFactory sessionFactory = config.buildSessionFactory(
			new StandardServiceRegistryBuilder().applySettings(mapConfig).build()
		);

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			Person p = new Person();
			p.setDni("12345678");
			p.setName("Juan");
			p.setLastName("Rivas");

			session.save(p);

			session.getTransaction().commit();
		}

		try (Session session = sessionFactory.openSession()) {
			Query<Person> query = session.createQuery("select c from Person c where c.dni = '12345678'", Person.class);
			Person person = query.getSingleResult();
			System.out.println(">>>> " + person);
		}

		sessionFactory.close();
	}

}
