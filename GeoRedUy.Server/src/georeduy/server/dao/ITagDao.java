package georeduy.server.dao;

import georeduy.server.logic.model.Tag;

import java.util.List;

import org.bson.types.ObjectId;

public interface ITagDao {

    public void saveTag(Tag tag);

    public Tag find(ObjectId tagId);

    public Tag findByName(String name);

    public List<Tag> getTags(int from, int count);
}
