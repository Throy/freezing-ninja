package georeduy.server.dao;

import georeduy.server.logic.controllers.ProductsController;
import georeduy.server.logic.controllers.RetailController;
import georeduy.server.logic.controllers.SessionController;
import georeduy.server.logic.model.Product;
import georeduy.server.logic.model.Retailer;
import georeduy.server.logic.model.RetailStore;
import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.Site;
import georeduy.server.logic.model.User;
import georeduy.server.logic.model.UserData;

import java.util.ArrayList;
import java.util.Date;
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

    private void createUsers() {
    	
    	// usuarios

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
        
        // sitios
        
        Site site = new Site();
        site.setName ("Puerto del Buceo");
        site.setDescription ("Paseo maritimo");
    	Double[] coordinates1 = {-34.908, -56.133};
    	site.setCoordinates (coordinates1);
        site.setAddress ("Rambla del Buceo");
        site.setImageUrl ("site_buceo.jpg");
        site.setTags (null);
        siteDao.saveSite(site);

        site = new Site();
        site.setName ("Parador del Kibon");
        site.setDescription ("Local de fiestas");
    	Double[] coordinates2 = {-34.911, -56.138};
    	site.setCoordinates (coordinates2);
        site.setAddress ("Rambla de Kibon");
        site.setImageUrl ("site_kibon.jpg");
        site.setTags (null);
        siteDao.saveSite(site);

        site = new Site();
        site.setName ("Punta de Trouville");
        site.setDescription ("Parque publico");
    	Double[] coordinates3 = {-34.920, -56.148};
    	site.setCoordinates (coordinates3);
        site.setAddress ("Rambla de Trouville");
        site.setImageUrl ("site_kibon.jpg");
        site.setTags (null);
        siteDao.saveSite(site);

        site = new Site();
        site.setName ("Punta Carretas");
        site.setDescription ("Fin de Montevideo");
    	Double[] coordinates4 = {-34.930, -56.160};
    	site.setCoordinates (coordinates4);
        site.setAddress ("Rambla de Punta Carretas");
        site.setImageUrl ("site_kibon.jpg");
        site.setTags (null);
        siteDao.saveSite(site);
    }

    // crear empresas con sus locales
    private void createRetailers() throws Exception {
    	// empresa nike
    	Retailer retailer = new Retailer();
    	retailer.setName ("Nike");
    	retailer.setDescription ("Marca de vestimenta deportiva");
    	retailer.setImageUrl ("nike.jpg");
    	
    	User user = new User();
    	user.setUserName ("nikeadmin");
    	retailer.setUser (user);
    	
    	RetailController.getInstance ().NewRetailer (retailer);

    	// productos nike
    	final String productNikeChampiones = "Championes Shox";
    	final String productNikeCamiseta = "Camiseta Barcelona";
    	final String productNikePelota = "Pelota Total 90";
    	
    	Product product = new Product();
    	product.setName (productNikeChampiones);
    	product.setDescription ("Calzado para correr más");
    	product.setImageUrl ("nike_shox.jpg");
    	product.setPrice ("4900");
    	product.setReleaseDate (new Date (2012, 10, 6, 4, 9));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName (productNikeCamiseta);
    	product.setDescription ("Remera para sudar mas");
    	product.setImageUrl ("nike_barca.jpg");
    	product.setPrice ("3800");
    	product.setReleaseDate (new Date (2012, 10, 6, 3, 8));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName (productNikePelota);
    	product.setDescription ("Balón para meter más goles");
    	product.setImageUrl ("nike_total90.jpg");
    	product.setPrice ("1200");
    	product.setReleaseDate (new Date (2012, 10, 6, 12, 0));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	// locales nike
    	RetailStore store = new RetailStore();
    	store.setName ("Nike MVD");
    	store.setPhoneNumber ("2628 NIKE");
    	store.setImageUrl ("nike_mvd.jpg");
    	store.setAddress ("Herrera casi 26 de Marzo");
    	Double[] coordinatesNike1 = {-34.903, -56.139};
    	store.setCoordinates (coordinatesNike1);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productNikeChampiones);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productNikeCamiseta);

    	store = new RetailStore();
    	store.setName ("Nike Carretas");
    	store.setPhoneNumber ("2712 NIKE");
    	store.setImageUrl ("nike_carr.jpg");
    	store.setAddress ("Ellauri");
    	Double[] coordinatesNike2 = {-34.923, -56.157};
    	store.setCoordinates (coordinatesNike2);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productNikeChampiones);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productNikePelota);
    	
    	// empresa mcdonalds
    	retailer = new Retailer ();
    	retailer.setName ("McDonald's");
    	retailer.setDescription ("Restaurantes de comida rápida");
    	retailer.setImageUrl ("mcd.jpg");
    	
    	user = new User();
    	user.setUserName ("mcadmin");
    	retailer.setUser (user);
    	
    	RetailController.getInstance ().NewRetailer (retailer);

    	// productos mcdonald's
    	final String productMcCombo = "McCombo 1";
    	final String productMcCajita = "Cajita Feliz";
    	final String productMcTwist = "McTwist Lapataia";
    	
    	product = new Product();
    	product.setName (productMcCombo);
    	product.setDescription ("Big Mac, refresco y papas fritas");
    	product.setImageUrl ("mcd_mccombo1.jpg");
    	product.setPrice ("220");
    	product.setReleaseDate (new Date (2012, 10, 6, 22, 0));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName (productMcCajita);
    	product.setDescription ("Hamburguesita, papitas y sorpsita");
    	product.setImageUrl ("mcd_cajita_feliz.jpg");
    	product.setPrice ("160");
    	product.setReleaseDate (new Date (2012, 10, 6, 16, 0));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());

    	product = new Product();
    	product.setName (productMcTwist);
    	product.setDescription ("Helado de crema con dulce de leche");
    	product.setImageUrl ("mcd_mctwist_lapataia.jpg");
    	product.setPrice ("80");
    	product.setReleaseDate (new Date (2012, 10, 6, 8, 8));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	// locales mcdonald's
    	store = new RetailStore();
    	store.setName ("McDonald's MVD");
    	store.setPhoneNumber ("2628 RMCD");
    	store.setImageUrl ("mcd_mvd.jpg");
    	store.setAddress ("Herrera casi 26 de Marzo");
    	Double[] coordinatesMcd1 = {-34.904, -56.138};
    	store.setCoordinates (coordinatesMcd1);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMcCombo);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMcCajita);

    	store = new RetailStore();
    	store.setName ("McDonald's Carretas");
    	store.setPhoneNumber ("2712 RMCD");
    	store.setImageUrl ("mcd_carr.jpg");
    	store.setAddress ("Ellauri");
    	Double[] coordinatesMcd2 = {-34.924, -56.158};
    	store.setCoordinates (coordinatesMcd2);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMcCombo);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMcTwist);
    	
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
    	final String productMultiManzanas = "Manzanas";
    	final String productMultiPanFlauta = "Pan flauta";
    	final String productMultiExprimido = "Exprimido de naranja";
    	
    	product = new Product();
    	product.setName (productMultiManzanas);
    	product.setDescription("Las manzanas mas ricas");
    	product.setImageUrl("multi_manzanas.jpg");
    	product.setPrice("20");
    	product.setReleaseDate (new Date (2012, 10, 6, 20, 2));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	product = new Product();
    	product.setName (productMultiPanFlauta);
    	product.setDescription("El pan mas crocante");
    	product.setImageUrl("multi_panflauta.jpg");
    	product.setPrice("15");
    	product.setReleaseDate (new Date (2012, 10, 6, 15, 0));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	product = new Product();
    	product.setName (productMultiExprimido);
    	product.setDescription("El jugo mas acido");
    	product.setImageUrl("multi_exprimido.jpg");
    	product.setPrice("35");
    	product.setReleaseDate (new Date (2012, 10, 6, 3, 5));
    	ProductsController.getInstance ().newProduct (product, retailer.getId ());
    	
    	// locales multiahorro
    	store = new RetailStore();
    	store.setName ("Multiahorro MVD");
    	store.setPhoneNumber ("2628 AHOR");
    	store.setImageUrl ("mcd_mvd.jpg");
    	store.setAddress ("Herrera casi 26 de Marzo");
    	Double[] coordinatesMul1 = {-34.905, -56.137};
    	store.setCoordinates (coordinatesMul1);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMultiPanFlauta);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMultiManzanas);

    	store = new RetailStore();
    	store.setName ("Multiahorro Carretas");
    	store.setPhoneNumber ("2712 AHOR");
    	store.setImageUrl ("mcd_carr.jpg");
    	store.setAddress ("Ellauri");
    	Double[] coordinatesMul2 = {-34.925, -56.159};
    	store.setCoordinates (coordinatesMul2);
    	RetailController.getInstance ().NewStore (store, retailer.getId ());
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMultiPanFlauta);
    	ProductsController.getInstance().addStoreProduct (store.getId (), productMultiExprimido);
    }

    @Test
    public void LoadUsers() throws Exception {
    	createUsers();
    	createRetailers ();
    }

}
