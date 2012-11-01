// VisitDaoImpl

// Implementación de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class VisitDaoImpl extends BasicDAO <Visit, ObjectId> implements IVisitDao {
	
	// constantes
	
	private static String idString = "id";
	private static String userIdString = "userId";
	private static String visitIdString = "visitId";
	
	// daos externos

	private ICommentDao commentDao =  new CommentDaoImpl();
	private IUserDao userDao =  new UserDaoImpl();
	private ISiteDao siteDao =  new SiteDaoImpl();
	
	// constructor
	
    public VisitDaoImpl() {
        super (Visit.class, MongoConnectionManager.instance().getDb());
    }
    
    // resolver referencias de claves foráneas
    
    private void resolveReferences (List <Visit> visits) {
    	for (Visit visit : visits) {
    		resolveReferences (visit);
    	}
    }

    private void resolveReferences (Visit visit) {
		visit.setRealUser (userDao.find (new ObjectId (visit.getUserId ())));
		visit.setRealSite (siteDao.find (new ObjectId (visit.getSiteId ())));
		
		List <Comment> comments = new ArrayList <Comment> ();
		for (String commentId : visit.getCommentsIds ()) {
			comments.add (commentDao.find (new ObjectId (commentId)));
		}
		visit.setRealComments (comments);
    }
    
    // funciones del dao

	// guardar visita en la base de datos
	@Override
	public void saveVisit (Visit visit) {
	    this.save (visit);
	}

	// obtener visita a partir del id
	@Override
	public Visit find (ObjectId visitId) {
    	Visit visit = get (visitId);
        resolveReferences (visit);
        return visit;
	}

	// obtener visitas del usuario
	@Override
	public List <Visit> findByUser (String userId) {
    	List <Visit> visits = createQuery().field (userIdString).equal(userId).asList();
    	resolveReferences (visits);
    	return visits;	
	}

	// obtener visitas del usuario, sistema paginado
	@Override
	public List <Visit> findByUser (String userId, int from, int count) {
    	List <Visit> visits = createQuery().field (userIdString).equal(userId).offset(from).limit(count).asList();
    	resolveReferences (visits);
    	return visits;	
	}
}
