/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package georeduy.server.util;

import georeduy.server.logic.model.Roles;
import georeduy.server.logic.model.UserData;

import java.util.ArrayList;
import java.util.List;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

/**
 *
 * @author micalb
 */
public class FacebookUtils {

    FacebookClient facebookClient = null;

    public boolean isTokenValid(String accessToken) {
        try {
            facebookClient = new DefaultFacebookClient(accessToken);
        } catch (FacebookOAuthException e) {
            // El token no es valido
            return false;
        }
        // el token es valido
        return true;
    }

    // Hay que haber llamado antes al validate token
    public georeduy.server.logic.model.User fetchUser() {

    	georeduy.server.logic.model.User georedUser = null;

        if (facebookClient != null) {

            User facebookUser = facebookClient.fetchObject("me", User.class);

            // Retorno nuestro User con los datos de Facebook
            georedUser = new georeduy.server.logic.model.User();
            georedUser.setUserName(facebookUser.getUsername());
            georedUser.setExternalId(facebookUser.getId());

            UserData userData = new UserData();
            userData.setEmail(facebookUser.getEmail());
            userData.setLastName(facebookUser.getLastName());
            userData.setName(facebookUser.getName());
            
            List<String> roles = new ArrayList<String>();
            roles.add(Roles.REG_USER);
            georedUser.setRoles(roles);

            georedUser.setUserData(userData);

        }
        return georedUser;

    }
}
