package Login;

import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class ExtensionLogin extends SFSExtension{
    @Override
    public void init()
    {
        trace("Database Login Extension -- started");
        addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);

    }

    @Override
    public void destroy() {
    }
}
