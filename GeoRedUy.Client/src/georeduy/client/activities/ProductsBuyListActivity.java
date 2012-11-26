// ProductsBuyListActivity

// actividad para el caso de uso Comprar productos.
// utiliza el layout products_buy_list_activity.

package georeduy.client.activities;

// imports
import com.paypal.android.MEP.CheckoutButton;
import com.paypal.android.MEP.PayPal;
import com.paypal.android.MEP.PayPalActivity;
import com.paypal.android.MEP.PayPalReceiverDetails;
import com.paypal.android.MEP.PayPalPayment;

import georeduy.client.controllers.ProductsController;
import georeduy.client.lists.ProductsBuyListAdapter;
import georeduy.client.model.Product;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;
import georeduy.client.util.ResultDelegate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.string;
import android.os.Message;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProductsBuyListActivity extends Activity implements android.view.View.OnClickListener {
	
	// instancia singleton
	private static ProductsBuyListActivity _instance;
	public PayPal ppObj;
	public ProgressDialog dialog; 
	protected static final int INITIALIZE_SUCCESS = 0;
	protected static final int INITIALIZE_FAILURE = 1;
	
	// This handler will allow us to properly update the UI. You cannot touch Views from a non-UI thread.
	Handler hRefresh = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
		    	case INITIALIZE_SUCCESS:
		    		initializeButtons();
		            break;
		    	case INITIALIZE_FAILURE:
		    		break;
			}
		}
	};
    // constructor

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.products_buy_list_activity);
        dialog = ProgressDialog.show(ProductsBuyListActivity.this, "", "Habilitando Paypal, espere por favor...", true);
     // Initialize the library. We'll do it in a separate thread because it requires communication with the server
     		// which may take some time depending on the connection strength/speed.
     		Thread libraryInitializationThread = new Thread() {
     			public void run() {
     				initLibrary();
     				
     				// The library is initialized so let's create our CheckoutButton and update the UI.
     				if (PayPal.getInstance().isLibraryInitialized()) {
     					hRefresh.sendEmptyMessage(INITIALIZE_SUCCESS);
     				}
     				else {
     					hRefresh.sendEmptyMessage(INITIALIZE_FAILURE);
     				}
     			}
     		};
       libraryInitializationThread.start();
     	        // inicializar singleton
        _instance = this;
        
        // poblar lista de ítems
        
        ArrayList <HashMap <String, String>> itemsStringList = new ArrayList <HashMap <String, String>> ();
        ArrayList <HashMap <String, Integer>> itemsIntList = new ArrayList <HashMap <String, Integer>> ();

        List <Product> products = ProductsController.getInstance ().purchaseGetProducts ();
        HashMap <String, Integer> productUnits = ProductsController.getInstance ().purchaseGetUnits ();
        HashMap <String, String> productPrices = ProductsController.getInstance ().purchaseGetPrices ();
        
        for (Product product : products) {
        	try {
        		// si se agregaron unidades, ...
        		int units = productUnits.get (product.getId ());
        		if (units >= 1) {

            		// sólo agregar productos con al menos 1 ítem
            		
                    // crear item
                    HashMap <String, String> itemStringMap = new HashMap <String, String> ();
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_ID, product.getId ());
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_NAME, product.getName ());
                    itemStringMap.put (ProductsListActivity.PRODUCT_ITEM_PRICE, product.getPrice ());
         
                    // adding HashList to ArrayList
                    itemsStringList.add (itemStringMap);

                    // crear item
                    HashMap <String, Integer> itemIntMap = new HashMap <String, Integer> ();
                    itemIntMap.put (ProductsListActivity.PRODUCT_ITEM_UNITS, units);
         
                    // adding HashList to ArrayList
                    itemsIntList.add (itemIntMap);
        		}
        	}
        	catch (NullPointerException ex) {
        	}
        	
        }
 
        // poblar lista de items
        ProductsBuyListAdapter adapter = new ProductsBuyListAdapter (this, itemsStringList, itemsIntList);
        ListView listView = (ListView) findViewById (R.id.listView_list);
        listView.setAdapter (adapter);
        
        // actualizar precio total
        updatePriceTotal ();
    }
    
    // actualizar el precio total. 
    
    public static ProductsBuyListActivity getInstance() {
        return _instance;        
    }
    
    // actualizar el precio total. 
    
    public void updatePriceTotal () {
        TextView viewPricetotal = (TextView) findViewById (R.id.pricetotal);
        viewPricetotal.setText (CommonUtilities.doubleToPrice (ProductsController.getInstance().purchaseGetPricetotal ()));
    }
    
    @Override
    public void onClick (View v) {
    	if (! ProductsController.getInstance ().purchaseIsValid ()) {
    		CommonUtilities.showAlertMessage (this, "Aviso", "Para realizar la compra, primero agregale productos.");
    	}
    	else
    	{
	    	PayPalPayment payment = new PayPalPayment();
	
	    	payment.setSubtotal(new BigDecimal(ProductsController.getInstance().purchaseGetPricetotal ()));
	
	    	payment.setCurrencyType("USD");
	    	
	    	String mailPayPal = ProductsController.getInstance().getStorePayPalMail();
	
	    	//payment.setRecipient("gandre_1353771273_biz@gmail.com");
	    	payment.setRecipient(mailPayPal);
	    	payment.setPaymentType(PayPal.PAYMENT_TYPE_GOODS);
	
	    	Intent checkoutIntent = PayPal.getInstance().checkout(payment, this, new ResultDelegate());
	
	    	startActivityForResult(checkoutIntent, 1);
    	}

    }
    
    
    public void initializeButtons()
    {
    	try
    	{
	    	// Adding paypal pay button 
	        CheckoutButton paypalButton = (CheckoutButton) ppObj.getCheckoutButton(this, PayPal.BUTTON_194x37, CheckoutButton.TEXT_PAY);
	    	
	        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        
	        paypalButton.setLeft(15);
	        paypalButton.setScaleX(0.8f);
	        paypalButton.setTranslationY(-13.0f);
	        //paypalButton.setLayoutParams(params);
	        paypalButton.setOnClickListener(this);
	        ViewGroup view = (ViewGroup)getWindow().getDecorView();
	        LinearLayout content = (LinearLayout)view.getChildAt(0);
	        content.addView(paypalButton);
	        //((RelativeLayout)findViewById(R.layout.products_buy_list_activity)).addView(paypalButton);
    	}
    	catch (Exception e)
    	{
    		String str = e.getMessage();
    		
    	}
    	dialog.dismiss();
    }
	
    
    
    
    // finalizar el caso de uso Comprar productos.
    
    public void returnMenu () {
    	setResult (ProductsListActivity.ACTIVITY_RESULT_FINISH);
		finish();		
    }
    
    // cliquear Cancelar -> salir del menú.
   
    public void button_product_cancel_onClick (View view) {
    	finish();
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
        	// button home
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask (this);
                return true;
        }
        return super.onOptionsItemSelected (item);
    }
    
    public void returnedFromPaypalAndServer()
    {
    	ProductsController.getInstance ().purchaseConfirm (new OnCompletedCallback() {			
			@Override
			public void onCompleted (String response, String error)
			{
				if (error == null) { 
					// 	mostrar confirmación
					final AlertDialog alertDialog = new AlertDialog.Builder (ProductsBuyListActivity.this).create ();

					alertDialog.setTitle ("Confirmación");
					alertDialog.setMessage ("Has comprado productos por un total de " + CommonUtilities.doubleToPrice (ProductsController.getInstance().purchaseGetPricetotal ()));
					
					alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
						public void onClick (DialogInterface dialog, int which) {
							// 	cerrar actividades.
							ProductsBuyListActivity.getInstance().returnMenu ();
						}
					});
					alertDialog.show();
				}
		
				else {
					CommonUtilities.showAlertMessage (ProductsBuyListActivity.this, "Error PBLA bpbo", "Hubo un error:\n" + error);
				}
			}
		});

    }

    public void onActivityResults(int requestCode, int resultCode, Intent data) {

       switch(resultCode) {

          case Activity.RESULT_OK:
        	          	  
              break;

           case Activity.RESULT_CANCELED:

               break;

           case PayPalActivity.RESULT_FAILURE:

      }

    }
    
    
    private void initLibrary() {
		ppObj = PayPal.getInstance();
		// If the library is already initialized, then we don't need to initialize it again.
		if(ppObj == null) {
			// This is the main initialization call that takes in your Context, the Application ID, and the server you would like to connect to.
			ppObj = PayPal.initWithAppID(this,  "APP-80W284485P519543T", PayPal.ENV_SANDBOX);
   			
			// -- These are required settings.
			ppObj.setLanguage("en_US"); // Sets the language for the library.
        	// --
        	
        	// -- These are a few of the optional settings.
        	// Sets the fees payer. If there are fees for the transaction, this person will pay for them. Possible values are FEEPAYER_SENDER,
        	// FEEPAYER_PRIMARYRECEIVER, FEEPAYER_EACHRECEIVER, and FEEPAYER_SECONDARYONLY.
			ppObj.setFeesPayer(PayPal.FEEPAYER_EACHRECEIVER); 
        	// Set to true if the transaction will require shipping.
			ppObj.setShippingEnabled(true);
        	// Dynamic Amount Calculation allows you to set tax and shipping amounts based on the user's shipping address. Shipping must be
        	// enabled for Dynamic Amount Calculation. This also requires you to create a class that implements PaymentAdjuster and Serializable.
			ppObj.setDynamicAmountCalculationEnabled(false);
        	// --
		}
	}



}
