package georeduy.server.logic.controllers;

import georeduy.server.dao.IRetailStoreDao;
import georeduy.server.dao.IRetailerDao;
import georeduy.server.dao.IStoreProductDao;
import georeduy.server.dao.IUserDao;
import georeduy.server.dao.RetailStoreDaoImpl;
import georeduy.server.dao.RetailerDaoImpl;
import georeduy.server.dao.StoreProductDaoImpl;
import georeduy.server.dao.UserDaoImpl;
import georeduy.server.logic.model.GeoRedConstants;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;
import georeduy.server.logic.model.UserNotificationsTypes;
import georeduy.server.util.Filter;
import georeduy.server.util.Util;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class RetailController {
	// instancia del singleton
	private static RetailController s_instance = null;

	// daos
	private IRetailStoreDao retailStoreDao =  new RetailStoreDaoImpl();
	private IRetailerDao retailerDao =  new RetailerDaoImpl();
	private IUserDao userDao = new UserDaoImpl();
	private IStoreProductDao storeProductsDao = new StoreProductDaoImpl();
	
	// constructores
	
	public RetailController() {
	}

	public static RetailController getInstance() {
		if (s_instance == null) {
			s_instance = new RetailController();
		}

		return s_instance;
	}
	
	// funciones del controlador
	
	public void NewRetailer(Retailer retailer) throws Exception {
		if (retailerDao.findByName(retailer.getName()) == null) {
			// asignar adminsitrador exisxtente, o crear uno nuevo.
			User user = userDao.findByUserName(retailer.getUser().getUserName());
			if (user == null) {
				user = new User ();
				user.setUserName (retailer.getUser().getUserName());
		        user.setPassword ("1234");
		        
		        UserData userData = new UserData();
		        userData.setEmail ("admin@" + retailer.getName() + ".com.uy");
		        userData.setName (retailer.getUser().getUserName());
		        userData.setLastName (retailer.getUser().getUserName());
		        user.setUserData (userData);

	        	UserNotificationsTypes notiTypes = new UserNotificationsTypes ();
	        	notiTypes.setNotitype1_contactsVisits (false);
	        	notiTypes.setNotitype2_contactsComments (false);
	        	notiTypes.setNotitype3_contactsReviews (false);
	        	notiTypes.setNotitype4_sites (false);
	        	notiTypes.setNotitype5_products (false);
	        	notiTypes.setNotitype6_events (false);
	        	user.setNotificationsTypes (notiTypes);
	        	
		        SessionController.getInstance ().Register (user);
			}
			retailer.setUser(user);
			
			// guardar la empresa en la base de datos.
			retailerDao.saveRetailer(retailer);
			
			// asignar al usuario como administrador.
			List<String> roles = (List<String>) ((ArrayList<String>) user.getRoles()).clone();
			roles.add(Roles.RETAIL_MANAGER);
			userDao.update(user, userDao.getUpdateOperations().set("roles", roles).set("retailId", retailer.getId()));	
		}
		else {
        	throw new Exception(GeoRedConstants.RETAILER_NAME_EXISTS);
        }		
	}
	
	public void NewStore(RetailStore store, String retailerId) throws Exception {
		store.setRetailerId(retailerId);
		retailStoreDao.saveStore(store);
		
		final RetailStore storeF = store;
		NotificationsController.getInstance().BroadCast(store, new Filter() {

			@Override
            public boolean filter(String userId) {
				User user = SessionController.getInstance().GetUserById(userId);
				if (Util.distanceHaversine(storeF.getCoordinates()[0], storeF.getCoordinates()[1], 
					user.getCoordinates()[0], user.getCoordinates()[1]) <= Util.BROADCAST_RANGE) {
					return false;
				}
                return true;
            }});
	}
	
	public List<Retailer> GetRetailers(int from, int count) {
		return retailerDao.getRetailers(from, count);
	}
	
	public List<RetailStore> GetStores() {
		return retailStoreDao.getStores();
	}
	
	public List<RetailStore> GetStores(int from, int count) {
		return retailStoreDao.getStores(from, count);
	}
	
	public List<RetailStore> GetStoresRetailer(int from, int count) {
		return retailStoreDao.getStores(from, count, User.Current().getRetailId());
	}
	
	public RetailStore GetStore(String id) {
		return retailStoreDao.find(new ObjectId(id));
	}
	
	public List<RetailStore> GetByPosition(int bottomLeftLatitude, int bottomLeftLongitude, int topRightLatitude, int topRightLongitude) {
		return retailStoreDao.getNearStores(bottomLeftLatitude/1e6, bottomLeftLongitude/1e6, topRightLatitude/1e6, topRightLongitude/1e6);		
	}
}
