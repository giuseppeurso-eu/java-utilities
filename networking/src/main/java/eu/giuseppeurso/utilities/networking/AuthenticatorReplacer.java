package eu.giuseppeurso.utilities.networking;

import java.net.PasswordAuthentication;

import org.apache.cxf.Bus;

public class AuthenticatorReplacer {

    public AuthenticatorReplacer(Bus bus) {
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("username", "pasword".toCharArray());
            }
        });
    }

}