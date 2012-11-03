// ProductsController

// controlador para los casos de uso de productos:
// ver datos de productos, comprar producto, evaluar producto.

package georeduy.client.controllers;

// imports

import georeduy.client.model.Product;
import georeduy.client.model.RetailStore;
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

	// productos
	private List <Product> _products;

	// items de la compra.
	// son parejas <productId, units>. 
	private HashMap <String, Integer> _productUnits;

	// precios de los productos=.
	// son parejas <productId, price>. 
	private HashMap <String, String> _productPrices;
	
	// id del local donde se realiza la compra.
	private String _storeId;
	
	// locales tra�dos por el mapa
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
	// m�todos
	// *******

	// *** hay que ver cu�les se precisan y cu�les no. ***
	
	// obtener datos de los productos.
	// *** en realidad deber�a ser GetStoreProducts y storeId. ***
	
	public void getProducts (String retailerId, OnCompletedCallback callback) {
		Map<String, String> params = new HashMap <String, String>();
		params.put ("retailerId", retailerId);
    	GeoRedClient.GetAsync("/Retail/GetProducts", params, callback);
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
	public void purchaseNew (List <Product> products, HashMap <String, String> productPrices, String storeId) {
		_products = products;
        _productUnits = new HashMap <String, Integer> ();
        _productPrices = productPrices;
        _storeId = storeId;
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
		
		// devolver 1 (compra est�ndar)
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
	
	// realizar compra de productos.
	public double purchaseConfirm () {
		// *** LLAMARA AL SERVIDOR DE APLICACI�N ***
		
		return purchaseGetPricetotal ();
	}

	// realizar compra de productos.
	public double purchaseGetPricetotal () {
		double pricetotal = 0;
		
		// iterar en los items.
		Iterator <Entry <String, Integer>> iter = _productUnits.entrySet().iterator();
		
		int idx = 0;
		while (iter.hasNext()) {
			// obtener item
			Entry <String, Integer> item = iter.next();
			
			// multiplicar precio por unidades.
			pricetotal += Double.parseDouble (_productPrices.get (item.getKey ())) * (Integer) item.getValue();
			
			idx += 1;
		}

		return pricetotal;
	}
	
	public void getStoreById (String storeId, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		params.put ("id", storeId);
    	GeoRedClient.GetAsync("/Retail/GetStore", params, callback);
	}
	
	public void getStoresByPosition (int latitude, int longitude, OnCompletedCallback callback) {
		Map <String, String> params = new HashMap <String, String>();
		params.put ("latitude", Integer.toString(latitude));
		params.put ("longitude", Integer.toString(longitude));
		
    	GeoRedClient.GetAsync("/Retail/GetByLocation", params, callback);
	}
	
	// cachear los locales tra�dos por el mapa
	public void setStoresByPosition (List <RetailStore> stores) {
		_stores = stores;
	}

	// obtener los locales tra�dos por el mapa
	public List <RetailStore> getStoresByPosition () {
		return _stores;
	}

	// publicar evaluaci�n de un producto.
	public void publishProductReview (String productId, String review) {
	}
}
