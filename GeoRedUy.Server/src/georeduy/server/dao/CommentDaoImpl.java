// VisitDaoImpl

// Implementación de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.User;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class CommentDaoImpl extends BasicDAO <Comment, ObjectId> implements ICommentDao {

	// daos externos

	// private static IVisitDao visitDao =  new VisitDaoImpl();

	// constructor

	public CommentDaoImpl() {
		super (Comment.class, MongoConnectionManager.instance().getDb());
	}

	// resolver referencias de claves foráneas

	/*
	private void resolveReferences (List <Comment> comments) {
		for (Comment comment : comments) {
			resolveReferences (comment);
		}
	}

	// *** borrado por stack overflow por recursión indefinida ***
	private void resolveReferences (Comment comment) {
		//comment.setRealVisit (visitDao.find (new ObjectId (comment.getVisitId ())));
	}
	*/

	// funciones del dao

	// guardar comentario en la base de datos
	@Override
	public void saveComment (Comment comment) {
		this.save (comment);
	}

	// obtener comentario a partir del id
	@Override
	public Comment find (ObjectId commentId) {
		Comment comment = get (commentId);
		//resolveReferences (comment);
		return comment;
	}

	// obtener comentarios del usuario
	@Override
	public List <Comment> findByUser (String userId) {
		// obtener comentarios
		List <Comment> commentsFull = createQuery().asList();
		//resolveReferences (commentsFull);

		// filtrar lista
		List <Comment> commentsFiltered = new ArrayList <Comment> ();
		/*
		for (Comment comment : commentsFull) {
			if (comment.getRealVisit ().getUserId () == User.Current().getId()) {
				commentsFiltered.add (comment);
			}
		}
		*/
		
		return commentsFiltered;
	}

	// obtener comentarios del usuario, sistema paginado
	@Override
	public List <Comment> findByUser (String userId, int from, int count) {
		// obtener comentarios
		List <Comment> commentsFull = createQuery().asList();
		//resolveReferences (commentsFull);

		// filtrar lista
		List <Comment> commentsFiltered = new ArrayList <Comment> ();
		/*
		for (Comment comment : commentsFull) {
			if (comment.getRealVisit ().getUserId () == User.Current().getId()) {
				commentsFiltered.add (comment);
			}
		}
		*/
		
		// *** falta paginar ***
		
		return commentsFiltered;
	}
}
