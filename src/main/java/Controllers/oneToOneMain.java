package Controllers;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Pojos.Passport;
import Pojos.Student;
import util.HibernateUtil;

public class oneToOneMain {
	public static void main(String[] args) {
		save();
		//read();
	}

	private static void read() {
		System.out.println("Hibernate one to one (Annotation) read method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();	
		Query query = session.createQuery("from Student where studentName='as'");
		List<Student> list = query.list();
		for( Student student : list) {
			System.out.println("student.getPassport().getPassportID()======== " + student.getPassport().getPassportID());
			System.out.println("student.getPassport().getPassportNumber()======== " + student.getPassport().getPassportNumber());
		}
		System.out.println("Done!......");
		session.close();
	}

	private static void save() {
		System.out.println("Hibernate one to one (Annotation)");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		Student student = new Student();
		student.setStudentName("ashok");
		student.setStudentCity("hyd");
		student.setStudentMobile("999999");
		Passport passport = new Passport();
		passport.setPassportNumber("10E1A");

		student.setPassport(passport);
		passport.setStudent(student);
		//session.persist(passport);
		session.save(passport);
		beginTransaction.commit();
		System.out.println("Done");
		session.close();
	}
}