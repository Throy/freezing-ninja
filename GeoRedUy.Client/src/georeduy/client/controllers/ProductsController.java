// ProductsController

// controlador para los casos de uso de productos:
// ver datos de productos, comprar producto, evaluar producto.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Comment;
import georeduy.client.model.Product;
import georeduy.client.model.Purchase;
import georeduy.client.model.PurchaseItem;
import georeduy.client.model.RetailStore;
import georeduy.client.model.Review;
import georeduy.client.model.User;
import georeduy.client.util.GeoRedClient;
import georeduy.client.util.OnCompletedCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;

public class ProductsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static ProductsController _instance = null;
	
	// *********
	// variables
	// *********
	
	// empresa y local donde se realiza la compra
	private String _paypalMail;
	private String _retailerId;
	private String _storeId;

	// productos del local donde se realiza la compra
	private List <Product> _products;

	// items de la compra.
	// son parejas <productId, units>. 
	private HashMap <String, Integer> _productUnits;

	// precios de los productos.
	// son parejas <productId, price>. 
	private HashMap <String, String> _productPrices;
	
	// locales traídos por el mapa
	private List <RetailStore> _stores;
	
	// *************
	// constructores
	// *************
	
	private ProductsController () {
		
	}
	
	public static ProductsController getInstance() {
		if (_instance == null) {
			_instance = new ProductsController ();
		}
		
		return _instance;
	}

	// *******
	// métodos
	// *******
	
	// obtener datos de los productos.
	
	public void getProducts (String retailerId, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("id", retailerId);
    	GeoRedClient.GetAsync("/Products/GetByStore", params, callback);
	}
	
	
	public String getStorePayPalMail()
	{
		return _paypalMail;
	}
	
	public void setStorePayPalMail(String mail)
	{
		_paypalMail = mail;
	}
	
	// obtener datos del producto.
	
	public Product getProduct (String productId) {
		Product product = null;
		for (Product product_idx : _products) {
			if (product_idx.getId ().equals (productId)) {
				return product_idx;
			}
		}
		return product;
	}

	// iniciar compra nueva.
	public void purchaseNew (String retailerId, String storeId, List <Product> products, HashMap <String, String> productPrices) {
		_retailerId = retailerId;
		_storeId = storeId;
		_products = products;
        _productUnits = new HashMap <String, Integer> ();
        _productPrices = productPrices;
	}

	// agregar producto a la compra.
	public void purchaseAddItemUnits (String productId, int units) {
		_productUnits.put (productId, units);
	}
	
	// obtener cantidad de unidades del producto en la compra.
	
	public int purchaseGetItemUnits (String productId) {
		// devolver la cantidad actual
		try {
			return _productUnits.get (productId);
		}
		
		// devolver 1 (compra estándar)
		catch (Exception e) {
			return 1;
		}
	}

	// obtener productos.
	public List <Product> purchaseGetProducts () {
		return _products;
	}

	// obtener unidades de productos de la compra.
	public HashMap <String, Integer> purchaseGetUnits () {
		return _productUnits;
	}

	// obtener precios de productos de la compra.
	public HashMap <String, String> purchaseGetPrices () {
		return _productPrices;
	}
	
	// devuelve true si la compra es válida.
	public boolean purchaseIsValid () {
		
		// iterar en los items de la compra
		Iterator <Entry <String, Integer>> iter = _productUnits.entrySet().iterator();
		while (iter.hasNext()) {
			// obtener item
			Entry <String, Integer> hashItem = iter.next();
			
			// si tiene al menos 1 unidad, devolver true.
			if (hashItem.getValue() >= 1) {
				return true;
			}
		}

		return false;
	}
	
	// realizar compra de productos.
	public void purchaseConfirm (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar items de la compra
		List <PurchaseItem> items = new ArrayList <PurchaseItem>();
		Iterator <Entry <String, Integer>> iter = _productUnits.entrySet().iterator();
		while (iter.hasNext()) {
			// obtener item
			Entry <String, Integer> hashItem = iter.next();
			
			// sólo agregar ítems con al menos 1 unidad
			if (hashItem.getValue() >= 1) {
				PurchaseItem purchaseItem = new PurchaseItem ();
				purchaseItem.setProductId (hashItem.getKey ());
				purchaseItem.setUnits (hashItem.getValue());
				items.add (purchaseItem);
			}
		}
		
		// agregar compra al llamadao al servicio
		Purchase purchase = new Purchase();
		purchase.setItems (items);
		purchase.setRetailerId (_retailerId);
		purchase.setStoreId (_storeId);

        Gson gson = new Gson();
		params.put ("purchaseInfo", gson.toJson(purchase));
		
		// llamar al servicio
    	GeoRedClient.PostAsync("/Products/Purchases/New", params, callback);
	}

	// realizar compra de productos.
	public double purchaseGetPricetotal () {
		double pricetotal = 0;
		
		// iterar en los items.
		Iterator <Entry <String, Integer>> iter = _productUnits.entrySet().iterator();
		while (iter.hasNext()) {
			// obtener item
			Entry <String, Integer> item = iter.next();
			
			// multiplicar precio por unidades.
			pricetotal += Double.parseDouble (_productPrices.get (item.getKey ())) * item.getValue();
		}

		return pricetotal;
	}

	// obtener compras
	public void getPurchases (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
    	GeoRedClient.GetAsync("/Products/Purchases/GetByUser", params, callback);
	}
	
	public void getStoreById (String storeId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		params.put ("id", storeId);
    	GeoRedClient.GetAsync("/Retail/GetStore", params, callback);
	}
	
	public void getStoresByPosition (int bottomLeftLatitude, int bottomLeftLongitude, int topRightLatitude, int topRightLongitude, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		params.put ("bottomLeftLatitude", Integer.toString(bottomLeftLatitude));
		params.put ("bottomLeftLongitude", Integer.toString(bottomLeftLongitude));
		params.put ("topRightLatitude", Integer.toString(topRightLatitude));
		params.put ("topRightLongitude", Integer.toString(topRightLongitude));
		
    	GeoRedClient.GetAsync("/Retail/GetByLocation", params, callback);
	}
	
	// cachear los locales traídos por el mapa
	public void setStoresByPosition (List <RetailStore> stores) {
		_stores = stores;
	}

	// obtener los locales traídos por el mapa
	public List <RetailStore> getStoresByPosition () {
		return _stores;
	}

	// publicar evaluación de un producto.
	public void publishPurchaseReview (String purchaseId, String text, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
        Gson gson = new Gson();
		
		// agregar parámetros
		Review review = new Review ();
		review.setText (text);
		review.setPurchaseId (purchaseId);
		params.put ("reviewInfo", gson.toJson(review));
		
    	GeoRedClient.PostAsync("/Products/Reviews/New", params, callback);
	}
	
	// evaluaciones
	
	// obtener datos de las evaluaciones del usuario.
	
	public void getComments (OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
    	GeoRedClient.GetAsync("/Products/Reviews/GetByUser", params, callback);
	}
	
	// obtener datos de una evaluación del usuario.
	
	public void getComment (String reviewId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		
		// agregar parámetros
		params.put ("reviewId", reviewId);
		
    	GeoRedClient.GetAsync("/Products/Reviews/GetById", params, callback);	
	}
}
