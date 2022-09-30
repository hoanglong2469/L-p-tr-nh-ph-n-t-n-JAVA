package app;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

import entity.Student;

public class App {
	public static void main(String[] args) {
		
		SubmissionPublisher<Student> publisher = new SubmissionPublisher<Student>();
		
		Subscriber<Student> subscriber = new Subscriber<Student>() {

			@Override
			public void onSubscribe(Subscription subscription) {
				subscription.request(1);
			}

			@Override
			public void onNext(Student item) {
				System.out.println(item);
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
		};
		
		publisher.subscribe(subscriber);
		
		publisher.submit(new Student(100, "Lan Nguyen"));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		publisher.close();
	}
}

