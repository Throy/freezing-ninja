// UserDetailActivity

// actividad para el caso de uso Ver datos de un usuario.
// utiliza el layout user_detail_activity.

package georeduy.client.activities;

// imports

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserDetailActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.user_detail_activity);
        
        // obtener datos del usuario a partir del id.
        String userId = getIntent().getStringExtra (UsersListActivity.EXTRA_USER_ID);
        int idx = Integer.parseInt (userId);
        
        // traer datatype de la base de datos
        // DTUser user = getUser (idx);

        // inventar nombre
        Character aLower = 'a';
        Character aUpper = 'A';
        int aLowerValue = aLower + idx;
        int aUpperValue = aUpper + idx;
        char charLower = (char) aLowerValue;
        char charUpper = (char) aUpperValue;

        TextView viewUserId = (TextView) findViewById (R.id.textview_user_id);
        TextView viewUsername = (TextView) findViewById (R.id.textview_username);
        TextView viewFullname = (TextView) findViewById (R.id.textview_fullname);
        
        viewUserId.setText (userId);
        viewUsername.setText ("" + charUpper + charLower + charLower);
        viewFullname.setText ("" + charUpper + charLower + " " + charUpper + charLower);
    }
    
    // funciones del programador
    
    // cliquear botón -> agregar contacto 
    
    public void button_user_item_add_onClick (View view) {
		// nada
		AlertDialog alertDialog = new AlertDialog.Builder (UserDetailActivity.this).create ();
		
		alertDialog.setTitle ("Cliqueaste en agregar id " + ((TextView) findViewById (R.id.textview_user_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
    }
    
    // cliquear botón -> quitar contacto 
    
    public void button_user_item_rem_onClick (View view) {
		// nada
		AlertDialog alertDialog = new AlertDialog.Builder (UserDetailActivity.this).create ();
		
		alertDialog.setTitle ("Cliqueaste en quitar id " + ((TextView) findViewById (R.id.textview_user_id)).getText ().toString ());
		alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick (DialogInterface dialog, int which) {
			}
		});
		alertDialog.show();
    }
}
