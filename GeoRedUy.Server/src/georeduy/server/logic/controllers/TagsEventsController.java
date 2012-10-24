package georeduy.server.logic.controllers;

import georeduy.server.dao.ITagDao;
import georeduy.server.dao.TagDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.Tag;

import java.util.List;

public class TagsEventsController {
	private static TagsEventsController s_instance = null;
	
	private ITagDao tagDao =  new TagDaoImpl();
	
	public TagsEventsController() {
	}

	public static TagsEventsController getInstance() {
		if (s_instance == null) {
			s_instance = new TagsEventsController();
		}

		return s_instance;
	}
	
	public void NewTag(Tag tag) throws Exception {
        if (tagDao.findByName(tag.getName().trim()) == null) {
        	tag.setName(tag.getName().trim());
        	tagDao.saveTag(tag);
        } else {
        	throw new Exception(GeoRedConstants.TAG_NAME_EXISTS);
        }
	}
	
	public List<Tag> Get(int from, int count) {
		return tagDao.getTags(from, count);
	}
}
