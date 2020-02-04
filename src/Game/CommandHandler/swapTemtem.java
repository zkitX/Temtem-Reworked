package Game.CommandHandler;

import com.smartfoxserver.v2.api.ISFSMMOApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.mmo.MMORoom;

public class swapTemtem extends BaseClientRequestHandler {
    ISFSObject response = new SFSObject();

    @Override
    public void handleClientRequest(User user, ISFSObject params) {

        // Get the parameters for the two temtem we want to swap
        short temtem1 = params.getShort("s1");
        short temtem2 = params.getShort("s2");


    }
}
