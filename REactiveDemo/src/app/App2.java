package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

import entity.Student;

public class App2 {
	public static void main(String[] args) {
		
		SubmissionPublisher<Student> publisher = new SubmissionPublisher<Student>();
		
//		Hot stream vs cold stream
		
		StudentSubscriber subscriber = new StudentSubscriber();
		StudentSubscriber subscriber2 = new StudentSubscriber();
		StudentSubscriber subscriber3 = new StudentSubscriber();
		
		publisher.subscribe(subscriber);
		publisher.subscribe(subscriber2);
		publisher.subscribe(subscriber3);
		
		
		List<Student> students = Arrays.asList(new Student(100, "Le Lan"), new Student(200, "Nguyen Lan"));
		
		students.forEach(st -> publisher.submit(st));
		
		publisher.submit(new Student(400, "An"));
		
		subscriber.getStudents().forEach(st -> System.out.println(st));
		subscriber2.getStudents().forEach(st -> System.out.println(st));
		subscriber3.getStudents().forEach(st -> System.out.println(st));
		
		publisher.close();
	}
}


class StudentSubscriber implements Subscriber<Student> {
	private Subscription s;
	private List<Student> students;
	
	public StudentSubscriber() {
		students = new ArrayList<Student>();
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		this.s = subscription;
		this.s.request(1);
	}

	@Override
	public void onNext(Student item) {
		students.add(item);
		this.s.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		throwable.printStackTrace();
		onComplete();
	}

	@Override
	public void onComplete() {
		System.out.println("Completed");
	}
	
	public List<Student> getStudents() {
		return students;
	}
	
}