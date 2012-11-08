package georeduy.server.persistence;


import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.Event;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.StoreProduct;
import georeduy.server.logic.model.Tag;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;
import georeduy.server.logic.model.Visit;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public final class MongoConnectionManager {

	private static final MongoConnectionManager INSTANCE = new MongoConnectionManager();

	private final Datastore db;
	public static final String DB_NAME = "geouy";

	private MongoConnectionManager() {
		try {
			Mongo m = new Mongo("localhost", 27017);
			Morphia morphia = new Morphia();

			morphia.map(User.class).map(UserData.class);
			morphia.map(Comment.class);
			morphia.map(Event.class);
			morphia.map(Site.class);
			morphia.map(Visit.class);
			morphia.map(Tag.class);
			morphia.map(Retailer.class);
			morphia.map(RetailStore.class);
			morphia.map(Product.class);
			morphia.map(StoreProduct.class);

			db = morphia.createDatastore(m, DB_NAME);
			db.ensureIndexes();
		}
		catch (Exception e) {
			throw new RuntimeException("Error initializing mongo db", e);
		}
	}

	public static MongoConnectionManager instance() {
		return INSTANCE;
	}

	public Datastore getDb() {
		return db;
	}
}