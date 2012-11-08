// ReviewDaoImpl

// Implementación de acceso a la colección de evaluaciones de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Review;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class ReviewDaoImpl extends BasicDAO <Review, ObjectId> implements IReviewDao {

	// daos externos

	// private static IPurchaseDao purchaseDao =  new PurchaseDaoImpl();

	// constructor

	public ReviewDaoImpl() {
		super (Review.class, MongoConnectionManager.instance().getDb());
	}

	// funciones del dao

	// guardar evaluación en la base de datos
	@Override
	public void saveReview (Review review) {
		this.save (review);
	}

	// obtener evaluación a partir del id
	@Override
	public Review find (ObjectId reviewId) {
		Review review = get (reviewId);
		return review;
	}

	// obtener evaluaciones del usuario
	@Override
	public List <Review> findByUser (String userId) {
		// obtener comentarios
		List <Review> reviewsFull = createQuery().asList();
		//resolveReferences (reviewsFull);

		// filtrar lista
		List <Review> reviewsFiltered = new ArrayList <Review> ();
		/*
		for (Review review : reviewsFull) {
			if (review.getRealPurchase ().getUserId () == User.Current().getId()) {
				reviewsFiltered.add (review);
			}
		}
		*/
		
		return reviewsFiltered;
	}

	// obtener evaluaciones del usuario, sistema paginado
	@Override
	public List <Review> findByUser (String userId, int from, int count) {
		// obtener evaluaciones
		List <Review> reviewsFull = createQuery().asList();
		//resolveReferences (reviewsFull);

		// filtrar lista
		List <Review> reviewsFiltered = new ArrayList <Review> ();
		/*
		for (Review review : reviewsFull) {
			if (review.getRealPurchase ().getUserId () == User.Current().getId()) {
				reviewsFiltered.add (review);
			}
		}
		*/
		
		// *** falta paginar ***
		
		return reviewsFiltered;
	}
}
