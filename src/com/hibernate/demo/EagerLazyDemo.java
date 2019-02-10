package com.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.demo.entity.Course;
import com.hibernate.demo.entity.Instructor;
import com.hibernate.demo.entity.InstructorDetail;
import com.hibernate.demo.entity.Student;

public class EagerLazyDemo {

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
			int theId = 1;
			
			Instructor tempInstructor = session.get(Instructor.class, theId);
			
			System.out.println("Print - Instructor: "+tempInstructor);
			
			//GET COURSES FOR INSTRUCTOR
			//CALLING THE GETTERS BEFORE CLOSING THE SESSION
			System.out.println("Print - Courses: "+tempInstructor.getCourses());
			
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
