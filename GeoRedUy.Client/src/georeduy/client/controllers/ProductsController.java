// ProductsController

// controlador para los casos de uso de productos:
// ver datos de productos, comprar producto, evaluar producto.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
	private HashMap <Integer, Integer> _productUnits;

	// precios de los productos=.
	// son parejas <productId, price>. 
	private HashMap <Integer, Integer> _productPrices;
	
	// id del local donde se realiza la compra.
	private int _storeId;
	
	// autoincrementador;
	//private int autitoIncrement;
	
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
	
	public void getProduct (int productId) {
	}

	// iniciar compra nueva.
	public void purchaseNew (HashMap <Integer, Integer> productPrices, int storeId) {
        _productUnits = new HashMap <Integer, Integer> ();
        _productPrices = productPrices;
        _storeId = storeId;
	}

	// agregar producto a la compra.
	public void purchaseAddItem (int productId, int units) {
		_productUnits.put (productId, units);
	}

	// obtener unidades de productos de la compra.
	public HashMap <Integer, Integer> purchaseGetUnits () {
		return _productUnits;
	}

	// obtener precios de productos de la compra.
	public HashMap <Integer, Integer> purchaseGetPrices () {
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
		Iterator <Entry <Integer, Integer>> iter = _productUnits.entrySet().iterator();
		while (iter.hasNext()) {
			// obtener item
			Entry <Integer, Integer> item = iter.next();
			
			// multiplicar precio por unidades.
			pricetotal += _productPrices.get (item.getKey()) * (Integer) item.getValue();
		}

		return pricetotal;
	}

	// publicar evaluación de un producto.
	public void publishProductReview (int productId, String review) {
	}
}
