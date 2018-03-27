package Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Pojos.College;
import Pojos.Student;
import Pojos.University;
import util.HibernateUtil;

public class oneToManyMain {
	public static void main(String[] args) {
		//saveUnidirectional();
		//saveBidirectional();
		read();
	}

	private static void read() {
		System.out.println("Hibernate one to many (Annotation) read method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		Query collegeQuery = session.createQuery("from College where collegeName='RK'");
		List<College> collegeList = collegeQuery.list();
		for (College college : collegeList) {
			System.out.println("college.getUniversity().getUniversityName()======== "
					+ college.getUniversity().getUniversityName());
		}
		
		Query universityQuery = session.createQuery("from University where universityName='JNTU'");
		List<University> universityList = universityQuery.list();
		for( University u1 : universityList) {
			System.out.println("student.getPassport().getPassportID()======== " + u1.getUniversityLocation());
		}
		System.out.println("Read Done!......");
		session.close();
	}

	private static void saveUnidirectional() {
		System.out.println("Hibernate one to one (Annotation)");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University university = new University();
		university.setUniversityName("OU");
		university.setUniversityLocation("Hyderabad");

		College college = new College();
		college.setCollegeName("MGIT");
		college.setCollegeLocation("Hyderabad");

		College college1 = new College();
		college1.setCollegeName("Vasavi");
		college1.setCollegeLocation("Hyderabad");
		//One-To-Many
		List<College> list = new ArrayList<College>();
		list.add(college);
		list.add(college1);
		university.setCollege(list);
		session.save(university);
	    //Many-To-One          	 
		/*college.setUniversity(university);
		college1.setUniversity(university);
		session.save(college);
		session.save(college1);*/

		beginTransaction.commit();
		System.out.println("Done");
		session.close();
	}
	
	private static void saveBidirectional() {
			System.out.println("Hibernate one to one (Annotation)");
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction beginTransaction = session.beginTransaction();
	
			University university = new University();
			university.setUniversityName("JNTU");
			university.setUniversityLocation("Hyderabad");
	
			College college = new College();
			college.setCollegeName("VREC");
			college.setCollegeLocation("Nizamabad");
	
			College college1 = new College();
			college1.setCollegeName("RK");
			college1.setCollegeLocation("Nizamabad");
			//Many-To-One          	 
			college.setUniversity(university);
			college1.setUniversity(university);
			session.save(college);
			session.save(college1);
	
			beginTransaction.commit();
			System.out.println("Done");
			session.close();
		}

}