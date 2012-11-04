package georeduy.client.lists;

import georeduy.client.activities.R;
import georeduy.client.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

	private TextView content;
	private List<ChatMessage> messages = new ArrayList<ChatMessage>();
	private LinearLayout wrapper;

	@Override
	public synchronized void add(ChatMessage object) {
		messages.add(object);
	}

	public ChatAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public int getCount() {
		return this.messages.size();
	}

	public ChatMessage getItem(int index) {
		return this.messages.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.chat_bubble, parent, false);
		}
		
		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);
		content = (TextView) row.findViewById(R.id.comment);

		ChatMessage message = getItem(position);

		content.setText(message.getMessage());

		content.setBackgroundResource(message.isLeft() ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		wrapper.setGravity(message.isLeft() ? Gravity.LEFT : Gravity.RIGHT);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	public void setMessages(List<ChatMessage> messages) {
		if (messages != null)
			this.messages = messages;
	}
}