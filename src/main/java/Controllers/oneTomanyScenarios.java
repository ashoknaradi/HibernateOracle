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

public class oneTomanyScenarios {
	
	public static void main(String[] args) {
		//loadget();
		//loadUniversityONFE();
		//getUniversityNPE();
		//getUniversityLIE();
		//loadLazy();
		//getLazy();
		//loadEager();
		//getEager();
		updateOrMerge();
	}

	private static void updateOrMerge() {
		System.out.println("Hibernate one to many (Annotation) updateOrMerge method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University u1 = (University) session.get(University.class, 6);
		System.out.println(u1.getUniversityName());
		u1.setUniversityName("TU");
		session.close();		
		
		Session detachToPersistSession = HibernateUtil.getSessionFactory().openSession();
		detachToPersistSession.get(University.class, 6);
		//detachToPersistSession.update(u1);//NonUniqueObjectException Occurs
		detachToPersistSession.merge(u1);//update in DB
		detachToPersistSession.beginTransaction().commit();
		System.out.println(u1.getUniversityName());
		detachToPersistSession.close();		
	}

	private static void loadget() {
		System.out.println("Hibernate one to many (Annotation) loadget method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University getUniversity = (University) session.get(University.class, 6);//DB Hit & university query
		System.out.println(getUniversity.getUniversityLocation());
		System.out.println("get College Name =======  " + getUniversity.getCollege().get(1).getCollegeName());//college query
		session.clear();//clears the session need to again entered to DB for getting details
		
		College loadCollege = (College) session.load(College.class, 7);//No DB Hit
		System.out.println("load College Name ===== " + loadCollege.getCollegeName());////Join Query
		System.out.println("load University Name =======  " + loadCollege.getUniversity().getUniversityName());//Existing Join Query
		
		System.out.println("Load-Get Done!......");
		session.close();	
	}

	private static void loadLazy() {
		System.out.println("Hibernate one to many (Annotation) loadLazy method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  loadLazyUniversity = (University) session.load(University.class, 6);//No DB Hit
		System.out.println(loadLazyUniversity.getUniversityName());//University Table query
		System.out.println(loadLazyUniversity.getCollege().get(0).getCollegeName());//College Table query
		System.out.println("Load-Lazy-University Done!......");
		session.close();	
	}

	private static void getLazy() {
		System.out.println("Hibernate one to many (Annotation) getLazy method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  getLazyUniversity = (University) session.get(University.class, 6);//DB Hit
		System.out.println(getLazyUniversity.getUniversityName());//University Table query
		System.out.println(getLazyUniversity.getCollege().get(0).getCollegeName());//College Table query
		System.out.println("Get-Lazy-University Done!......");
		session.close();			
	}

	private static void loadEager() {
		System.out.println("Hibernate one to many (Annotation) loadEager method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  loadEagerUniversity = (University) session.load(University.class, 6);//No DB Hit
		System.out.println(loadEagerUniversity.getUniversityName());//Join query
		System.out.println(loadEagerUniversity.getCollege().get(0).getCollegeName());
		System.out.println("Load-Eager-University Done!......");
		session.close();	
	}

	private static void getEager() {	
		System.out.println("Hibernate one to many (Annotation) getEager method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  getEagerUniversity = (University) session.get(University.class, 6);//DB Hit and Join query
		System.out.println(getEagerUniversity.getUniversityName());
		System.out.println(getEagerUniversity.getCollege().get(0).getCollegeName());
		System.out.println("Get-Eager-University Done!......");
		session.close();			

	}

	private static void getUniversityNPE() {
		System.out.println("Hibernate one to many (Annotation) read method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  getUniversityNPE = (University) session.get(University.class, 66);
		//NullPointerException becoz no details are founded with required identifier University class in DB
		if(getUniversityNPE != null) {
			System.out.println("get College Name =======  " + getUniversityNPE.getCollege().get(1).getCollegeName());
		} else {
			System.out.println("University is null");
		}
		System.out.println("get University NullPointerException NPE Done!......");
		session.close();	
	}

	private static void loadUniversityONFE() {
		System.out.println("Hibernate one to many (Annotation) read method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  loadUniversityONFE = (University) session.load(University.class, 66);
		//ObjectNotFoundException becoz no details are founded with required identifier University class in DB
		System.out.println("get College Name =======  " + loadUniversityONFE.getCollege().get(1).getCollegeName());
		
		System.out.println("load University ObjectNotFoundException Done!......");
		session.close();	
	}

	private static void getUniversityLIE() {
		System.out.println("Hibernate one to many (Annotation) get University Lazy Initialization Exception method()");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction beginTransaction = session.beginTransaction();

		University  getUniversityLILE = (University) session.load(University.class, 6);//No DB Hit
		System.out.println(getUniversityLILE.getUniversityName());//University Table
		session.clear();//LazyInitializationException occures 
		//To fix change University fetch type as FetchType.EAGER
		System.out.println(getUniversityLILE.getCollege().get(0).getCollegeName());//College Table
		System.out.println("University Lazy Initialization Exception Done!......");
		session.close();			
	}

}