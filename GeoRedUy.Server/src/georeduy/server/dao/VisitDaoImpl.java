// VisitDaoImpl

// Implementación de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.Purchase;
import georeduy.server.logic.model.Visit;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

public class VisitDaoImpl extends BasicDAO <Visit, ObjectId> implements IVisitDao {
	
	// nombres de los campos de Visit
	
	private static String idString = "id";
	private static String userIdString = "userId";
	private static String visitIdString = "visitId";
	private static String commentsString = "comments";
	private static String date = "date";
	
	// daos externos

	private static IUserDao userDao =  new UserDaoImpl();
	private static ISiteDao siteDao =  new SiteDaoImpl();
	
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

    @Override
    public void resolveReferences (Visit visit) {
		visit.setRealUser (userDao.find (new ObjectId (visit.getUserId ())));
		visit.setRealSite (siteDao.find (new ObjectId (visit.getSiteId ())));
		
		/*
		List <Comment> comments = new ArrayList <Comment> ();
		for (String commentId : visit.getCommentsIds ()) {
			comments.add (commentDao.find (new ObjectId (commentId)));
		}
		visit.setRealComments (comments);
		*/
    }
    
    // funciones del dao

	// guardar visita en la base de datos
	@Override
	public void saveVisit (Visit visit) {
	    this.save (visit);
	}

	// guardar visita en la base de datos
	@Override
	public void addVisitComment (ObjectId visitId, Comment comment) {
		/*
		Visit visit = get (visitId);
		List <String> commentsIds = visit.getCommentsIds ();
		commentsIds.add (commentId);
		visit.setCommentsIds (commentsIds);
		this.save (visit);
		*/
		
		Query <Visit> query = ds.createQuery(Visit.class).field (idString).equal (visitId);
		UpdateOperations <Visit> ops = ds.createUpdateOperations(Visit.class).add (commentsString, comment);
		ds.update (query, ops);
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
	
	// obtener visitas de periodo
	public List <Visit> findByPeriod (Date start, Date end) {
		System.out.println("---> visits desde -->" + start);
		System.out.println("---> visits hasta -->" + end);		
		Query <Visit> query = ds.createQuery (Visit.class).field (date).greaterThan(start).order(date);
		List<Visit> v = query.asList();
		List<Visit> retorno = new ArrayList<Visit>();
		
		System.out.println("---> antes resultado -->" + v);
		
		for (Visit visit : v) {
			if(visit.getDate().before(end)){
				retorno.add(visit);
			}
			
		}
		
		System.out.println("---> resultado -->" + retorno);
    	return retorno;
	}

}
