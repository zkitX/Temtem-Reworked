import Game.CommandHandler.swapTemtem;
import Game.Commands;
import Login.LoginEventHandler;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class MyExtension extends SFSExtension {
    @Override
    public void init()
    {
        trace("Hello world this is my first extension.");
        addRequestHandler(Commands.swapTem, swapTemtem.class);

        addEventHandler(SFSEventType.USER_LOGIN, LoginEventHandler.class);

    }

    @Override
    public void destroy() {
        trace("Temtem-Reworked destroy().");

        super.destroy();

        removeRequestHandler(Commands.swapTem);
    }
}
