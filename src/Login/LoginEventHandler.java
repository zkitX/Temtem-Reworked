package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.db.IDBManager;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class LoginEventHandler extends BaseServerEventHandler
{

    @Override
    public void handleServerEvent(ISFSEvent isfsEvent) throws SFSException {
        // Grab the parameters from client request
        String userName = (String) isfsEvent.getParameter(SFSEventParam.LOGIN_NAME);
        String cryptedPass = (String) isfsEvent.getParameter(SFSEventParam.LOGIN_PASSWORD);
        ISession session = (ISession) isfsEvent.getParameter(SFSEventParam.SESSION);

        IDBManager dbManager = getParentExtension().getParentZone().getDBManager();
        Connection connection = null;

        try {
            connection = dbManager.getConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT `password` FROM `user` WHERE `name` = ?");
            stmt.setString(1, userName);

            ResultSet res = stmt.executeQuery();

            if (!res.first()){
                SFSErrorData errData = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
                errData.addParameter(userName);

                throw new SFSLoginException("Bad user name: " + userName, errData);
            }

            String dbPswd = res.getString("password");

            // Verify password
            if (!getApi().checkSecurePassword(session, dbPswd, cryptedPass)){
                SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                data.addParameter(userName);
                throw new SFSLoginException("Login failed for user: " + userName, data);
            }

        }
        catch(SQLException e) {
            SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
            errData.addParameter("A SQL Error occured: " + e.getMessage());

            throw new SFSLoginException("A SQL Error occured: " + e.getMessage(), errData);

        }
        finally {
            if (connection != null)
            {
                try {
                    connection.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }

    }
}
