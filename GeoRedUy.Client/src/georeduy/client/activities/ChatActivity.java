package georeduy.client.activities;

import static georeduy.client.util.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static georeduy.client.util.CommonUtilities.EXTRA_MESSAGE;
import georeduy.client.controllers.NotificationsController;
import georeduy.client.lists.ChatAdapter;
import georeduy.client.model.ChatMessage;
import georeduy.client.util.CommonUtilities;

import java.util.Random;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;

public class ChatActivity extends Activity {
	private ChatAdapter adapter;
	private ListView lv;
	private EditText messageText;
	String partnerUserId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_activity);

		partnerUserId = getIntent().getStringExtra (ContactListActivity.EXTRA_USER_ID);
		
		String userName = getIntent().getStringExtra (ContactListActivity.EXTRA_USER_NAME);
		
		setTitle(userName);
		
		lv = (ListView) findViewById(R.id.chat_messages);

		adapter = new ChatAdapter(getApplicationContext(), R.layout.chat_activity);

		adapter.setMessages(NotificationsController.getInstance().getChatRoom(partnerUserId).getMessages());
		
		lv.setAdapter(adapter);
		lv.setDivider(null);
		lv.setDividerHeight(0);
		
		lv.smoothScrollToPosition(adapter.getCount() - 1);

		messageText = (EditText) findViewById(R.id.messageText);
		messageText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					sendMessage();
				}
				return false;
			}
		});
		
		registerReceiver(mHandleMessageReceiver,
                new IntentFilter(CommonUtilities.DISPLAY_CHAT_MESSAGE_ACTION));
	}
	
	public void button_send_onClick(View view) {
		sendMessage();
	}
	
	private void sendMessage() {
		ChatMessage message = new ChatMessage();
		message.setLeft(true);
		message.setMessage(messageText.getText().toString());
		message.setToUserId(partnerUserId);

		NotificationsController.getInstance().sendMessage(message, null);
		adapter.notifyDataSetChanged();
		lv.smoothScrollToPosition(adapter.getCount() - 1);
		
		messageText.setText("");
	}
	
	private final BroadcastReceiver mHandleMessageReceiver =
            new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String messageText = intent.getExtras().getString(CommonUtilities.EXTRA_CHAT_MESSAGE);
            String userId = intent.getExtras().getString(CommonUtilities.EXTRA_CHAT_USER_ID);
            
            if (partnerUserId.equals(userId)) {
	    		adapter.notifyDataSetChanged();
	    		lv.smoothScrollToPosition(adapter.getCount() - 1);
            }
        }
    };

	@Override
    protected void onResume() {
		
		lv.smoothScrollToPosition(adapter.getCount() - 1);
		
	    super.onResume();
    }
    
    /*@Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }*/
    
    
}