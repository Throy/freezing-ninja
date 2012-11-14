// ContactInviteActivity

// actividad para el caso de uso Invitar a usuario.
// utiliza el layout contact_invite_activity.

package georeduy.client.activities;

// imports

import georeduy.client.controllers.ClientsController;
import georeduy.client.controllers.SitesController;
import georeduy.client.util.CommonUtilities;
import georeduy.client.util.OnCompletedCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ContactInviteActivity extends Activity {
	
	// inicializadores
	
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.contact_invite_activity);
    }
    
    // funciones del programador
    
    // cliquear Send -> enviar la invitación
    
    public void button_send_onClick (View view) {

    	// obtener el id de la visita y el comentario
    	String username = ((EditText) findViewById (R.id.edittext_username)).getText ().toString ();
    	String email = ((EditText) findViewById (R.id.edittext_email)).getText ().toString ();
    	String message = ((EditText) findViewById (R.id.edittext_message)).getText ().toString ();
    	
		// intentar enviar la invitación
    	ClientsController.getInstance ().sendInvitation (email, username, message, new OnCompletedCallback() {
					
					@Override
					public void onCompleted (String response, String error)
					{
						if (error == null) {

							// mostrar confirmación
							AlertDialog alertDialog = new AlertDialog.Builder (ContactInviteActivity.this).create ();

							alertDialog.setTitle ("Confirmación");
							alertDialog.setMessage ("Enviaste la invitación.");

							alertDialog.setButton (DialogInterface.BUTTON_NEGATIVE, "Ok", new DialogInterface.OnClickListener() {
								public void onClick (DialogInterface dialog, int which) {
									// cerrar la actividad.
									finish ();
								}
							});
							
							alertDialog.show();
						}
						
						else {
							CommonUtilities.showAlertMessage (ContactInviteActivity.this, "Error CIA bio", "Hubo un error:\n" + error);
						}
					}
				});
    	
    }
}
