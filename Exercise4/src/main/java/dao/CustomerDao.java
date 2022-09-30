package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.AggregatePublisher;
import com.mongodb.reactivestreams.client.MongoCollection;

import db.DBConnection;
import entity.Customer;

public class CustomerDao {
	private MongoCollection<Customer> cusCol;

	public CustomerDao() {

		PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();

		CodecRegistry codecRegistry  = CodecRegistries.fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(pojoCodecProvider));

		cusCol = DBConnection
				.getInstance()
				.getMongoClient()
				.getDatabase("BikeStores")
				.getCollection("customers", Customer.class).withCodecRegistry(codecRegistry);

	}
	// Cách 1
	//	db.customers.aggregate([{$match: { phones: { $exists: true } } }, 
	//	{$project: { first_name: 1, last_name: 1,email:1,registration_date:1, phones: 1,address:1, n: { $size: '$phones' } } },
	//	{$match:{n:{$gte:2}}}])
	public List<Customer> getCustomersPhonesGTE2() {

		List<Customer> customers = new ArrayList<Customer>();
		CountDownLatch latch = new CountDownLatch(1);

		//		AggregatePublisher<Customer> publisher = cusCol.aggregate(Arrays.asList(
		//				Document.parse("{$match: { phones: { $exists: true } } }"),
		//				Document.parse("{$project: { first_name: 1, last_name: 1,email:1,registration_date:1, phones: 1,address:1, n: { $size: '$phones' } } }"),
		//				Document.parse("{$match:{n:{$gte:2}}}")
		//				));
		
		//The utility the MongoDB Java driver builder classes provide
		AggregatePublisher<Customer> publisher = cusCol.aggregate(Arrays.asList(
				Aggregates.match(Filters.exists("phones", true)),
				Aggregates.project(Projections.fields(Projections.include("first_name", "last_name", "email", "registration_date", "phones", "address"), Projections.computed("n", Projections.computed("$size", "$phones")))),
				Aggregates.match(Filters.gte("n", 2))
				));

		publisher.subscribe(new Subscriber<Customer>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Customer t) {
				customers.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				onComplete();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return customers;
	}
	
	// Cách 2
	//	 db.customers.aggregate([{$match:{phones:{$exists:true}}},
	//	                         {$match:{$expr:{$gte:[{$size:'$phones'},2]}}}])
	public List<Customer> getCustomersPhones_GTE2() {

		List<Customer> customers = new ArrayList<Customer>();
		CountDownLatch latch = new CountDownLatch(1);

		//		AggregatePublisher<Customer> publisher = cusCol.aggregate(Arrays.asList(
		//				Document.parse("{$match: { phones: { $exists: true } } }"),
		//				Document.parse("{$match:{$expr:{$gte:[{$size:'$phones'},2]}}}") //Viết lại này bằng cách sử dụng các lớp mà MongoDB Java driver builder cung cấp, link: https://www.mongodb.com/docs/drivers/java/sync/v4.6/fundamentals/builders/
		//				));
		AggregatePublisher<Customer> publisher = cusCol.aggregate(Arrays.asList(
				Aggregates.match(Filters.exists("phones", true)),
				Aggregates.match(Filters.expr(Document.parse("{$gte:[{$size:'$phones'},2]}")))
//				Document.parse("{$match:{$expr:{$gte:[{$size:'$phones'},2]}}}") 
//				new Document("$match", new Document("$expr", new Document("$gte", Arrays.asList(new Document("$size", "$phones"), 2))))
				));

		publisher.subscribe(new Subscriber<Customer>() {
			private Subscription s;

			@Override
			public void onSubscribe(Subscription s) {
				this.s = s;
				this.s.request(1);
			}

			@Override
			public void onNext(Customer t) {
				customers.add(t);
				this.s.request(1);
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				onComplete();
			}

			@Override
			public void onComplete() {
				latch.countDown();
			}
		});

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return customers;
	}


	public void addCustomer(Customer customer) {

		CountDownLatch latch = new CountDownLatch(1);

		Publisher<InsertOneResult> publisher = cusCol.insertOne(customer);
		Subscriber<InsertOneResult> subscriber = new Subscriber<InsertOneResult>() {

			@Override
			public void onSubscribe(Subscription s) {
				s.request(1);
			}

			@Override
			public void onNext(InsertOneResult t) {
				System.out.println(t.getInsertedId());
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				onComplete();
			}

			@Override
			public void onComplete() {
				latch.countDown();
				System.out.println("Completed!");
			}
		};

		publisher.subscribe(subscriber);

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//		try {
		//			Thread.sleep(1000);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
	}
}




