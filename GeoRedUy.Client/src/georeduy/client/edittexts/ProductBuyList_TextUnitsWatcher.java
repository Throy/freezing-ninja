// ProductBuyList_UnitTextWatcher

// watcher para los EditText de unidades
// del caso de uso Comprar productos.
// lo usa la actividad ProductBuyListActivity a través de ProductsBuyListAdapter.

package georeduy.client.edittexts;

import georeduy.client.controllers.ProductsController;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class ProductBuyList_TextUnitsWatcher implements TextWatcher
{
	// id del producto
	private int _productId;
	
	// edittext
    private EditText _textUnits;

    public ProductBuyList_TextUnitsWatcher (int productId, EditText e) {
    	_productId = productId;
    	_textUnits = e;
    }

    public void beforeTextChanged (CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged (CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged (Editable s) {
		int units = 0;
		
		// actualizar cantidad de unidades, o asignar 0
		try {
			units = Integer.parseInt (_textUnits.getText ().toString ());
		}
		catch (NumberFormatException exception) {
		}

		ProductsController.getInstance().purchaseAddItem (_productId, units);
		
		// *** actualizar precio total ***
    }
}
