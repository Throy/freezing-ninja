// ProductsController

// controlador para los casos de uso de productos:
// ver datos de productos, comprar producto, evaluar producto.

package georeduy.client.controllers;

// imports

import georeduy.client.model.User;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class ProductsController
{
	// *******************
	// instancia singleton
	// *******************
	
	private static ProductsController _instance = null;
	
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
	// *** en realidad devuelve Collection <User> o algo por el estilo. ***
	
	public void getProducts () {
	}
	
	// obtener datos del producto.
	// *** en realidad devuelve Product o algo por el estilo. ***
	
	public void getProduct (int productId) {
	}

	// iniciar compra nueva.
	public void purchaseNew () {
	}

	// agregar producto a la compra.
	public void purchaseAddItem (int productId, int units) {
	}
	
	// realizar compra de productos.
	public void purchaseConfirm () {
	}

	// publicar evaluaci�n de un producto.
	public void publishProductReview (int productId, String review) {
	}
}
