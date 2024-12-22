package manyToOneAnn;

import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Impl {

  public static void main(String[] args) {
  // Load the configuration and build the SessionFactory
  Configuration configuration = HibernateConfig.getConfig();
  configuration.addAnnotatedClass(Department.class);
  configuration.addAnnotatedClass(Employee.class);
  
  ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
  		.applySettings(configuration.getProperties())
  		.build();
  SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  System.out.println("SessionFactory created successfully.");
  // Create a session
  Session session = sessionFactory.openSession();
  Transaction transaction = null;

  
  try {
      transaction = session.beginTransaction();

      // Create a Department
      Department department = new Department();
      department.setName("IT");

      // Create Employees
      Employee employee1 = new Employee();
      employee1.setName("John Doe");
      employee1.setDepartment(department); // Set the department for the employee

      Employee employee2 = new Employee();
      employee2.setName("Jane Smith");
      employee2.setDepartment(department); // Set the department for the employee

      // Add Employees to the Department
      Set<Employee> employees = new HashSet();
      employees.add(employee1);
      employees.add(employee2);
      department.setEmployees(employees);

      // Save the Department (Cascade will save the employees automatically)
      session.persist(department);


      transaction.commit();
  } catch (Exception e) {
      if (transaction != null) transaction.rollback();
      e.printStackTrace();
  } finally {
      session.close();
      sessionFactory.close();
  }
}
}