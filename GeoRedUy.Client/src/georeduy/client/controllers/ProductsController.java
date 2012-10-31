// ProductsController

// controlador para los casos de uso de productos:
// ver datos de productos, comprar producto, evaluar producto.

package georeduy.client.controllers;

// imports

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

	// items de la compra.
	// son parejas <productId, units>. 
	private HashMap <String, Integer> _productUnits;

	// precios de los productos=.
	// son parejas <productId, price>. 
	private HashMap <String, Integer> _productPrices;
	
	// id del local donde se realiza la compra.
	private String _storeId;
	
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

	// *** hay que ver cuáles se precisan y cuáles no. ***
	
	// obtener datos de los productos.
	// *** en realidad devuelve Collection <User> o algo por el estilo. ***
	
	public void getProducts () {
	}
	
	// obtener datos del producto.
	// *** en realidad devuelve Product o algo por el estilo. ***
	
	public void getProduct (String productId) {
	}

	// iniciar compra nueva.
	public void purchaseNew (HashMap <String, Integer> productPrices, String storeId) {
        _productUnits = new HashMap <String, Integer> ();
        _productPrices = productPrices;
        _storeId = storeId;
	}

	// agregar producto a la compra.
	public void purchaseAddItem (String productId, int units) {
		_productUnits.put (productId, units);
	}

	// obtener unidades de productos de la compra.
	public HashMap <String, Integer> purchaseGetUnits () {
		return _productUnits;
	}

	// obtener precios de productos de la compra.
	public HashMap <String, Integer> purchaseGetPrices () {
		return _productPrices;
	}
	
	// realizar compra de productos.
	public int purchaseConfirm () {
		// *** LLAMARA AL SERVIDOR DE APLICACIÓN ***
		
		return purchaseGetPricetotal ();
	}

	// realizar compra de productos.
	public int purchaseGetPricetotal () {
		int pricetotal = 0;
		
		// iterar en los items.
		Iterator <Entry <String, Integer>> iter = _productUnits.entrySet().iterator();
		
		int idx = 0;
		while (iter.hasNext()) {
			// obtener item
			Entry <String, Integer> item = iter.next();
			
			// multiplicar precio por unidades.
			pricetotal += _productPrices.get (idx) * (Integer) item.getValue();
			
			idx += 1;
		}

		return pricetotal;
	}
	
	public void getStoresByPosition (String storeId, OnCompletedCallback callback) {
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
	
	// cachear los locales traídos por el mapa
	public void setStoresByPosition (List <RetailStore> stores) {
		_stores = stores;
	}

	// obtener los locales traídos por el mapa
	public List <RetailStore> getStoresByPosition () {
		return _stores;
	}

	// publicar evaluación de un producto.
	public void publishProductReview (String productId, String review) {
	}
}
