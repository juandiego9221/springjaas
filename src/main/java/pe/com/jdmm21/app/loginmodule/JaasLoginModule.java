package pe.com.jdmm21.app.loginmodule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;

public class JaasLoginModule implements LoginModule {
    private static Logger logger = LoggerFactory.getLogger(JaasLoginModule.class);
    private Subject subject;
    private String username;
    private String password;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;
        NameCallback nameCallback = new NameCallback("Username: ");
        PasswordCallback passwordCallback = new PasswordCallback("Password: ", false);

        try {
            callbackHandler.handle(new Callback[]{nameCallback, passwordCallback});
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedCallbackException e) {
            e.printStackTrace();
        }
        username = nameCallback.getName();
        password = new String(passwordCallback.getPassword());
    }

    @Override
    public boolean login() throws LoginException {
//        if (username==null || (username.equalsIgnoreCase(""))
//        || password==null || password.equalsIgnoreCase("")){
//            throw new LoginException("Username and password is mandatory");
        logger.info("XXXX");
        if (username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("password")) {
            logger.info("XXXX1");
//    }else if (username.equalsIgnoreCase("admin")&& password.equalsIgnoreCase("password")){
            subject.getPrincipals().add(new JaasPrincipal(username));
            return true;
        } else if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("password")) {
            subject.getPrincipals().add(new JaasPrincipal(username));
            return true;
        }
        return false;
    }

    @Override
    public boolean commit() throws LoginException {
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
