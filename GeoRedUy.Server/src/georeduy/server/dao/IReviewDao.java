// ICommentDao

// Interfaz de acceso a la colección de comentarios de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Review;

import java.util.List;

import org.bson.types.ObjectId;

public interface IReviewDao {

	// guardar evaluación en la base de datos
	public void saveReview (Review review);

	// obtener evaluación a partir del id
	public Review find (ObjectId reviewId);

	// obtener evaluaciones del usuario
	public List <Review> findByUser (String userId);

	// obtener evaluaciones del usuario, sistema paginado
	public List <Review> findByUser (String userId, int from, int count);
}
