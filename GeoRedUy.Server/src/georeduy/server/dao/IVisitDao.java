// IVisitDao

// Interfaz de acceso a la colección de visitas de la base de datos.

package georeduy.server.dao;

import georeduy.server.logic.model.Comment;
import georeduy.server.logic.model.Visit;

import java.awt.Point;
import java.util.List;

import org.bson.types.ObjectId;

public interface IVisitDao {

	// guardar visita en la base de datos
	public void saveVisit (Visit visit);

	// guardar visita en la base de datos
	public void addVisitComment (ObjectId visitId, Comment comment);

	// obtener visita a partir del id
	public Visit find (ObjectId visitId);

	// obtener visitas del usuario
	public List <Visit> findByUser (String userId);

	// obtener visitas del usuario, sistema paginado
	public List <Visit> findByUser (String userId, int from, int count);
}
