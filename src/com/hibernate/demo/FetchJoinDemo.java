package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.hibernate.demo.entity.Course;
import com.hibernate.demo.entity.Instructor;
import com.hibernate.demo.entity.InstructorDetail;
import com.hibernate.demo.entity.Student;

public class FetchJoinDemo {

	public static void main(String[] args) {
		
		//CREATE SESSION FACTORY
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Instructor.class)
								.addAnnotatedClass(InstructorDetail.class)
								.addAnnotatedClass(Course.class)
								.buildSessionFactory();
		
		//CREATE SESSION
		Session session = factory.getCurrentSession();
		
		try {
			
			//START A TRANSACTION
			session.beginTransaction();
			
			//GET THE INSTRUCTOR FROM DB
			//OPTION 2 :  WITH HQL	
			int theId = 1;
			
			Query<Instructor> query = session.createQuery("select i from Instructor i "
					+ "JOIN FETCH i.courses "
					+ "where i.id=:theInstructorId", 
					Instructor.class);
			
			//SET PARAMETER
			
			query.setParameter("theInstructorId", theId);
			
			//EXECUTE QUERY AND GET INSTRUCTOR // GET ALL AT ONCE
			Instructor tempInstructor = query.getSingleResult();
			System.out.println("Print - Instructor: "+tempInstructor);
			
			//COMMIT TRANSACTION
			session.getTransaction().commit();
			
			//CLOSE THE SESSION
			session.close();
			
			System.out.println("Print - Done!");
			
		}
		finally {
			//ADD CLEAN UP CODE
			session.close();
			
			factory.close();
		}
		
		
	}

}
