package georeduy.client.util;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import georeduy.client.activities.ContactAddActivity;
import georeduy.client.activities.R;
import georeduy.client.model.Contact;
import georeduy.client.util.SlideMenu.SlideMenuAdapter.MenuDesc;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SlideMenu {
	//just a simple adapter
	public static class SlideMenuAdapter extends ArrayAdapter<SlideMenu.SlideMenuAdapter.MenuDesc> {
		Activity act;
		ArrayList<SlideMenu.SlideMenuAdapter.MenuDesc> items = new ArrayList<SlideMenu.SlideMenuAdapter.MenuDesc>();

		class MenuItem {
			public TextView label;
			public ImageView icon;
		}

		public static class MenuDesc {
			public int id;
			public int icon;
			public String label;
		}

		public SlideMenuAdapter(Activity act, ArrayList<MenuDesc> items) {
			super(act, R.id.menu_label, items);
			this.act = act;
			this.items = items;
		}
		
		@Override
        public MenuDesc getItem(int position) {
	        return items.get(position);
        }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = act.getLayoutInflater();
				rowView = inflater.inflate(R.layout.side_menu_listitem, null);
				MenuItem viewHolder = new MenuItem();
				viewHolder.label = (TextView) rowView.findViewById(R.id.menu_label);
				viewHolder.icon = (ImageView) rowView.findViewById(R.id.menu_icon);
				rowView.setTag(viewHolder);
			}

			MenuItem holder = (MenuItem) rowView.getTag();
			MenuDesc item = items.get(position);
			String s = item.label;
			holder.label.setText(s);
			holder.icon.setImageResource(item.icon);

			return rowView;
		}
	}

	private boolean menuShown = false;
	private boolean addMenuShown = false;
	private View menu = null;
	private ListView list = null;
	private LinearLayout content;
	private FrameLayout parent;
	private int statusHeight = 0;
	private int menuSize;
	private Activity act;
	private boolean alignRight;
	private ListAdapter adapter = null;
	private OnItemClickListener onClick = null;
	
	public SlideMenu(Activity act, boolean alignRight) {
		this.act = act;
		this.alignRight = alignRight;
	}
	
	//call this in your onCreate() for screen rotation
	public void checkEnabled() {
		if(menuShown)
			this.show(false);
	}
	
	public void show() {
		//get the height of the status bar
		if(statusHeight == 0) {
			Rect rectgle = new Rect();
			Window window = act.getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
			statusHeight = rectgle.top;
		}
		this.show(true);
	}
	
	public void show(boolean animate) {
		menuSize = CommonUtilities.dpToPx(250, act);
		if (alignRight)
			menuSize = -menuSize;
		
		content = ((LinearLayout) act.findViewById(android.R.id.content).getParent());
		FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
		parm.setMargins(menuSize, 0, -menuSize, 0);
		content.setLayoutParams(parm);
		
		parent = (FrameLayout) content.getParent();
		if (menu == null) {
			LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			menu = inflater.inflate((alignRight)?R.layout.side_menu_right:R.layout.side_menu_left, null);
			
			if (alignRight) {
				Button addContact = (Button)menu.findViewById(R.id.overlay).findViewById(R.id.add_contact_button);
				addContact.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(act, ContactAddActivity.class);
						act.startActivity(myIntent);
						/*addMenuShown = true;
						parent.removeView(menu);
						LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
						menu = inflater.inflate(R.layout.contact_add_activity, null);
						parent.addView(menu);*/
					}
				});
			}
		}
		
		FrameLayout.LayoutParams lays = new FrameLayout.LayoutParams(-1, -1, (alignRight)?Gravity.RIGHT:Gravity.LEFT);
		lays.setMargins(0, statusHeight, 0, 0);
		
		menu.setLayoutParams(lays);
		
		parent.addView(menu);
		list = (ListView) act.findViewById(R.id.menu_listview);
		
		if (onClick != null) 
			list.setOnItemClickListener(onClick);
		
		//animation for smooth slide-out
		TranslateAnimation ta = new TranslateAnimation(-menuSize, 0, 0, 0);
		ta.setDuration(500);
		if (animate) {
			content.startAnimation(ta);
			menu.startAnimation(ta);
		}
		
		menu.findViewById(R.id.overlay).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SlideMenu.this.hide();
			}
		});
		CommonUtilities.enableDisableViewGroup((LinearLayout) parent.findViewById(android.R.id.content).getParent(), false);
		//((ExtendedViewPager) act.findViewById(R.id.viewpager)).setPagingEnabled(false);
		//((ExtendedPagerTabStrip) act.findViewById(R.id.viewpager_tabs)).setNavEnabled(false);
		menuShown = true;
		this.fill();
	}
	
	public void fill() {
		if (list != null) {
			if (adapter != null) {
				list.setAdapter(adapter);
			}
			/*else {
				SlideMenuAdapter.MenuDesc[] items = new SlideMenuAdapter.MenuDesc[5];
				//fill the menu-items here
				SlideMenuAdapter adap = new SlideMenuAdapter(act, items);
				list.setAdapter(adap);
			}*/
		}
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.onClick = listener;
	}
	
	public void setAdapter(ListAdapter adapter) {
		this.adapter = adapter;
		if (list != null)
			list.setAdapter(adapter);
	}
	
	public void hide() {
		/*if (addMenuShown) {
			parent.removeView(menu);
			LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			menu = inflater.inflate((alignRight)?R.layout.side_menu_right:R.layout.side_menu_left, null);
			parent.addView(menu);
			
			addMenuShown = false;
		} else {*/
			TranslateAnimation ta = new TranslateAnimation(0, -menuSize, 0, 0);
			ta.setDuration(500);
			menu.startAnimation(ta);
			parent.removeView(menu);
	
			TranslateAnimation tra = new TranslateAnimation(menuSize, 0, 0, 0);
			tra.setDuration(500);
			content.startAnimation(tra);
			FrameLayout.LayoutParams parm = (FrameLayout.LayoutParams) content.getLayoutParams();
			parm.setMargins(0, 0, 0, 0);
			content.setLayoutParams(parm);
			CommonUtilities.enableDisableViewGroup((LinearLayout) parent.findViewById(android.R.id.content).getParent(), true);
			//((ExtendedViewPager) act.findViewById(R.id.viewpager)).setPagingEnabled(true);
			//((ExtendedPagerTabStrip) act.findViewById(R.id.viewpager_tabs)).setNavEnabled(true);
			menuShown = false;
		//}
	}
	
	public boolean isMenuActive() {
		return menuShown | addMenuShown;
	}
	
	/**
	 * Parse XML describe menu.
	 * @param menu - resource ID
	 */
	public void parseXml(int menu) {
		ArrayList<MenuDesc> menuItems = new ArrayList<MenuDesc>();
		try {
			XmlResourceParser xrp = act.getResources().getXml(menu);
			xrp.next();
			int eventType = xrp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String elemName = xrp.getName();
					if (elemName.equals("item")) {
						String textId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"title");
						String iconId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"icon");
						String resId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"id");
						MenuDesc item = new MenuDesc();
						item.id = Integer.valueOf(resId.replace("@", ""));
						item.label = resourceIdToString(textId);
						if (iconId != null)
							item.icon = Integer.valueOf(iconId.replace("@", ""));
						menuItems.add(item);
					}
				}
				eventType = xrp.next();
			}
			
			adapter = new SlideMenuAdapter(act, menuItems);
			fill();
			
		} catch (Exception e) {
			Log.w("Menu_xml", e);
		}
	}
	
	/**
	 * Convert resource ID to String.
	 * @param text
	 * @return
	 */
	private String resourceIdToString(String resId) {
		if (!resId.contains("@")) {
			return resId;
		} else {
			String id = resId.replace("@", "");
			return act.getResources().getString(Integer.valueOf(id));
		}
	}

	public Object get(int position) {
	    return adapter.getItem(position);
    }
}