package georeduy.server.dao;

import georeduy.server.logic.model.Tag;
import georeduy.server.persistence.MongoConnectionManager;

import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.dao.BasicDAO;

public class TagDaoImpl extends BasicDAO<Tag, ObjectId> implements ITagDao {

    public TagDaoImpl() {
        super(Tag.class, MongoConnectionManager.instance().getDb());
    }

	@Override
    public void saveTag(Tag tag) {
	    this.save(tag);
    }
	
    @Override
    public Tag find(ObjectId tagId) {
        return get(tagId);
    }

	@Override
    public Tag findByName(String name) {
    	List<Tag> tags = createQuery().field("name").equal(name).asList();
    	if (tags.size() == 1)
    		return tags.get(0);
    	else
    		return null;
    }

	@Override
    public List<Tag> getTags(int from, int count) {
		return createQuery().offset(from).limit(count).asList();
    }
}
