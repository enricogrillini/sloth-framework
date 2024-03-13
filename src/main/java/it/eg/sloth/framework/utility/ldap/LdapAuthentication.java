package it.eg.sloth.framework.utility.ldap;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
public class LdapAuthentication {

    private LdapAuthentication() {
        // NOP
    }

    private static String dnFromUser(String serverUrl, String searchName, String username) throws NamingException {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, serverUrl);
            props.put(Context.REFERRAL, "ignore");
            InitialDirContext context = new InitialDirContext(props);

            SearchControls ctrls = new SearchControls();
            ctrls.setReturningAttributes(new String[]{"givenName", "cn"});
            ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<SearchResult> answers = context.search(searchName, "(uid=" + username + ")", ctrls);
            SearchResult result = answers.next();

            return result.getNameInNamespace();
        } catch (Exception e) {
            log.error("dnFromUser", e);
        }

        return null;
    }

    /**
     * Esegue l'autenticazione LDAP
     *
     * @param serverUrl
     * @param searchName
     * @param username
     * @param password
     * @return
     */
    public static final boolean authenticate(String serverUrl, String searchName, String username, String password) {
        try {
            Properties props = new Properties();
            props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            props.put(Context.PROVIDER_URL, serverUrl);
            props.put(Context.REFERRAL, "ignore");
            props.put(Context.SECURITY_PRINCIPAL, dnFromUser(serverUrl, searchName, username));
            props.put(Context.SECURITY_CREDENTIALS, password);

            // InitialDirContext context =
            new InitialDirContext(props);

            return true;
        } catch (NamingException e) {
            return false;
        }
    }

}
