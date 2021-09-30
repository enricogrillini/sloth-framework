package it.eg.sloth.framework.utility.ssl;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public class SSLUtil {

    private SSLUtil() {
        // NOP
    }

    public static void turnOffSslChecking(String[] admittedHostname) throws NoSuchAlgorithmException, KeyManagementException {
        // SSLSocketFactory
        X509TrustManager trustManager = new CustomX509TrustManager();
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{trustManager}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        // HostnameVerifier
        CustomHostnameVerifier customHostnameVerifier = new CustomHostnameVerifier(HttpsURLConnection.getDefaultHostnameVerifier(), admittedHostname);
        HttpsURLConnection.setDefaultHostnameVerifier(customHostnameVerifier);
    }


    /**
     * Assume come verificati tutti gli admittedHostname, per gli altri hostname utilizza la verifca di default
     */
    private static class CustomHostnameVerifier implements HostnameVerifier {

        HostnameVerifier originalVerifier;
        Set<String> admittedHostname;

        public CustomHostnameVerifier(HostnameVerifier originalVerifier, String[] admittedHostname) {
            this.originalVerifier = originalVerifier;
            this.admittedHostname = new HashSet<>(Arrays.asList(admittedHostname));
        }

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            if (admittedHostname.contains(s)) {
                return true;
            }

            return originalVerifier.verify(s, sslSession);
        }
    }

    private static class CustomX509TrustManager implements X509TrustManager {

        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            log.info("checkClientTrusted", chain.length, chain[0].getIssuerDN());
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            log.info("checkServerTrusted {} {}", chain.length, chain[0].getIssuerDN());
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

}