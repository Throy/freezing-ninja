// ICommentDao

// Interfaz de acceso a la colección de comentarios de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;

import java.util.List;

import org.bson.types.ObjectId;

public interface ICommentDao {

	// guardar comentario en la base de datos
	public void saveComment (Comment comment);

	// obtener comentario a partir del id
	public Comment find (ObjectId commentId);

	// obtener comentarios del usuario
	public List <Comment> findByUser (String userId);

	// obtener comentarios del usuario, sistema paginado
	public List <Comment> findByUser (String userId, int from, int count);
}
