package pe.com.jdmm21.app.loginmodule;

import java.io.Serializable;
import java.security.Principal;

public class JaasPrincipal implements Principal, Serializable {
    private String username;

    public JaasPrincipal(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return "Authenticated: " + this.username;
    }

}
