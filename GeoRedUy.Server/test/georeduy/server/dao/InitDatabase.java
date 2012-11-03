package georeduy.server.dao;

import georeduy.server.logic.controllers.ProductsController;
import georeduy.server.logic.controllers.RetailController;
import georeduy.server.logic.controllers.SessionController;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class InitDatabase {

    private static IUserDao userDao;
    private static ISiteDao siteDao;

    @Before
    public void before() {
        userDao = new UserDaoImpl();
        siteDao = new SiteDaoImpl();
    }

    private User createUsers() {

        User user = userDao.findByUserName("Admin");
        if (user == null) {
            user = new User();
            user.setPassword("1234");
            user.setUserName("Admin");
            UserData userData = new UserData();
            userData.setEmail("admin@geored.uy");
            user.setUserData(userData);
            List<String> roles = new ArrayList<String>();
            roles.add(Roles.REG_USER);
            roles.add(Roles.ADMIN);
            user.setRoles(roles);
            userDao.saveUser(user);
        }
        
        user = userDao.findByUserName("Agustin");
        if (user == null) {
            user = new User();
            user.setPassword("1234");
            user.setUserName("Agustin");
            UserData userData = new UserData();
            userData.setEmail("agustin@geored.uy");
            user.setUserData(userData);
            List<String> roles = new ArrayList<String>();
            roles.add(Roles.REG_USER);
            user.setRoles(roles);
            userDao.saveUser(user);
        }
        
        Site site = new Site();
        site.setName ("Sitio A");
        site.setDescription ("Es un sitio admirable");
    	Double[] coordinates = {-34.89, -56.13};
    	site.setCoordinates(coordinates);
        site.setAddress ("Calle A");
        site.setImageUrl ("site_a.jpg");
        site.setTags (null);
        siteDao.saveSite(site);

        return user;
    }

    // crear empresas con sus locales
    private void createRetailers() throws Exception {
    	// nike
    	Retailer retailer = new Retailer();
    	retailer.setName ("Nike");
    	retailer.setDescription ("Marca de vestimenta deportiva");
    	retailer.setImageUrl ("nike.jpg");
    	
    	User user = new User();
    	user.setUserName ("nikeadmin");
    	retailer.setUser (user);
    	
    	RetailController.getInstance ().NewRetailer (retailer);

    	// productos nike
    	Product product = new Product();
    	product.setName("Championes Shox");
    	product.setDescription("Calzado para correr más");
    	product.setImageUrl("nike_shox.jpg");
    	product.setPrice("4900");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName("Pelota Total 90");
    	product.setDescription("Balón para meter más goles");
    	product.setImageUrl("nike_total90.jpg");
    	product.setPrice("1200");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	// mcdonalds
    	retailer = new Retailer ();
    	retailer.setName ("McDonald's");
    	retailer.setDescription ("Restaurantes de comida rápida");
    	retailer.setImageUrl ("mcd.jpg");
    	
    	user = new User();
    	user.setUserName ("mcadmin");
    	retailer.setUser (user);
    	
    	RetailController.getInstance ().NewRetailer (retailer);

    	// productos mcdonald's
    	product = new Product();
    	product.setName("McCombo 1");
    	product.setDescription("Big Mac, refresco y papas fritas");
    	product.setImageUrl("mcd_mccombo1.jpg");
    	product.setPrice("220");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName("McTwist Lapataia");
    	product.setDescription("Helado de crema con dulce de leche");
    	product.setImageUrl("mcd_mctwist_lapataia.jpg");
    	product.setPrice("80");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	// multiahorro
    	retailer = new Retailer ();
    	retailer.setName ("Multiahorro");
    	retailer.setDescription ("Cadena de supermercados");
    	retailer.setImageUrl ("miltiahorro.jpg");
    	
    	user = new User();
    	user.setUserName ("multiadmin");
    	retailer.setUser (user);
    	
    	RetailController.getInstance ().NewRetailer (retailer);

    	// productos multiahorro
    	product = new Product();
    	product.setName("Manzanas");
    	product.setDescription("Las manzanas más ricas");
    	product.setImageUrl("multi_manzanas.jpg");
    	product.setPrice("20");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	product = new Product();
    	product.setName("Pan flauta");
    	product.setDescription("El pan más crocante");
    	product.setImageUrl("multi_panflauta.jpg");
    	product.setPrice("15");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	product = new Product();
    	product.setName("Exprimido de naranja");
    	product.setDescription("El jugo más azucarado");
    	product.setImageUrl("multi_exprimido.jpg");
    	product.setPrice("15");
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    }

    @Test
    public void LoadUsers() throws Exception {
    	createUsers();
    	//createRetailers ();
    }

}
