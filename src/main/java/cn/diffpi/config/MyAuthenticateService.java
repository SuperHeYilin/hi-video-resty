package cn.diffpi.config;

import java.util.HashSet;
import java.util.Set;

import cn.diffpi.resource.platform.user.model.PtUser;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.security.AuthenticateService;
import cn.dreampie.security.PasswordService;
import cn.dreampie.security.Principal;
import cn.dreampie.security.credential.Credential;

public class MyAuthenticateService extends AuthenticateService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public Principal getPrincipal(String username) {
        PasswordService passwordService = getPasswordService();

        PtUser u = PtUser.dao.findFirstBy(" username=?", username);

        if (u == null) {
            throw new HttpException(HttpStatus.UNAUTHORIZED, "");
        }

        Principal<PtUser> principal = new Principal<PtUser>(u.<String>get("username"), passwordService.crypto(u.<String>get("pwd"), "x"), "x", new HashSet<String>() {{
            add("*");
        }}, u);
        return principal;
    }

    public Set<Credential> getAllCredentials() {
        Set<Credential> credentials = new HashSet<Credential>();
        credentials.add(new Credential("*", "/api/b/v1.0/**", "*"));
        return credentials;
    }
}
