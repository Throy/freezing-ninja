package georeduy.client.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import georeduy.client.activities.ChatActivity;
import georeduy.client.activities.ConfigureNotificationsTagsActivity;
import georeduy.client.activities.ConfigureNotificationsTypesActivity;
import georeduy.client.activities.ContactAddActivity;
import georeduy.client.activities.ContactListActivity;
import georeduy.client.activities.ProductsListActivity;
import georeduy.client.activities.PurchasesListActivity;
import georeduy.client.activities.R;
import georeduy.client.activities.SitesListActivity;
import georeduy.client.activities.StoresListActivity;
import georeduy.client.activities.VisitsListActivity;
import georeduy.client.controllers.ClientsController;
import georeduy.client.lists.UsersListAdapter;
import georeduy.client.lists.UsersListAdapter.UserListMode;
import georeduy.client.model.Contact;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockMapActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class MenuHandler {
	private Activity _activity;
	private SherlockActivity _sherlockActivity;
	private SherlockMapActivity _sherlockMapActivity;
	private SlideMenu slideMenu;
	private SlideMenu slideContacts;
	
	public static final String EXTRA_USER_ID = "georeduy.client.user_id";
    public static final String EXTRA_USER_NAME = "georeduy.client.user_name";
	
	public MenuHandler(SherlockMapActivity activity) {
		_sherlockActivity = null;
		_sherlockMapActivity = activity;
		_activity = activity;
		
		ActionBar actionBar = activity.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.ic_button_menu);
		
		actionBar.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.action_bar));
		
		initMenus();
	}
	
	public MenuHandler(SherlockActivity activity) {
		_sherlockActivity = activity;
		_sherlockMapActivity = null;
		_activity = activity;
		
		ActionBar actionBar = activity.getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.ic_button_menu);
		
		actionBar.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.action_bar));
		
		initMenus();
	}
	
	private void initMenus() {
		slideMenu = new SlideMenu(_activity, false);
		slideMenu.checkEnabled();
		slideMenu.parseXml(R.menu.main_menu);
		
		slideMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onMenuItemClick(((SlideMenu.SlideMenuAdapter.MenuDesc)slideMenu.get(position)).id);
				hideAll();	            
            }
		});
	    
		slideContacts = new SlideMenu(_activity, true);
		slideContacts.checkEnabled();
		
		UsersListAdapter adapter = new UsersListAdapter (_activity, null, UserListMode.LIST_CONTACTS);
		slideContacts.setAdapter(adapter);
		
		// Abre el chat con ese usuario
		slideContacts.setOnItemClickListener (new OnItemClickListener() {

        	public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
            	// agregar id del usuario al intent
            	String userId = ((TextView) view.findViewById (R.id.user_id)).getText().toString();
            	if (userId != null && !userId.equals("-1")) {
            		String userName = ((TextView) view.findViewById (R.id.username)).getText().toString();
            		// crear intent de la actividad chat.
            		Intent intent_user_detail = new Intent (parent.getContext (), ChatActivity.class);
	            	intent_user_detail.putExtra (EXTRA_USER_ID, userId);	 
	            	intent_user_detail.putExtra (EXTRA_USER_NAME, userName);
	            	// ejecutar intent.
	            	_activity.startActivity (intent_user_detail);
            	}
        	}
        });
		
		refreshContactList();
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			slideMenu.show();
			break;
		case R.id.show_contacts_button:
			slideContacts.show();
			break;
		case R.id.add_contact_button:
        	Intent myIntent = new Intent(_activity, ContactAddActivity.class);
        	_activity.startActivity(myIntent);
		default:
			return false;
		}
		return true;
	}
	
	public void onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater;
		if (_sherlockActivity != null)
			inflater = _sherlockActivity.getSupportMenuInflater();
		else 
			inflater = _sherlockMapActivity.getSupportMenuInflater();
		
	    inflater.inflate(R.menu.actionbar_menu, menu);
	}
	
	public void refreshContactList() {
    	ClientsController.getInstance().GetContacts(new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error == null) {
					Gson gson = new Gson();
		        	Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();				    		
		    		List<Contact> contacts = gson.fromJson(response, listType);
		    		if (contacts != null) {
		    			UsersListAdapter adapter = new UsersListAdapter (_activity, contacts, UserListMode.LIST_CONTACTS);
		    			slideContacts.setAdapter(adapter);
		    		}
				}
			}
		});
    }

	public boolean hideAll() {
		if (slideMenu.isMenuActive()) {
			slideMenu.hide();
			return true;
		}
		
		if (slideContacts.isMenuActive()) {
			slideContacts.hide();
			return true;
		}
		
	    return false;
    }

	public void onRemoveContact(String userId) {
		ClientsController.getInstance().removeContact(userId, new OnCompletedCallback() {
			
			@Override
			public void onCompleted(String response, String error) {
				if (error == null) {
					refreshContactList();
	        	}
			}
		});	
    }
	
	public void onMenuItemClick(int itemId) {
		Intent intent;
		switch (itemId) {
		case R.id.main_menu_sites:
			intent = new Intent(_activity, SitesListActivity.class);
			_activity.startActivity(intent);
			break;

		case R.id.main_menu_visits:
			intent = new Intent(_activity, VisitsListActivity.class);
			_activity.startActivity(intent);
			break;

		case R.id.main_menu_stores:
			intent = new Intent(_activity, StoresListActivity.class);
			_activity.startActivity(intent);
			break;

		case R.id.main_menu_purchases:
			intent = new Intent(_activity, PurchasesListActivity.class);
			_activity.startActivity(intent);
			break;

		case R.id.main_menu_notitags:
			intent = new Intent(_activity, ConfigureNotificationsTagsActivity.class);
			_activity.startActivity(intent);
			break;
			
		case R.id.main_menu_notitypes:
			intent = new Intent(_activity, ConfigureNotificationsTypesActivity.class);
			_activity.startActivity(intent);
			break;

		default:
			return;
		}
	}	
}
