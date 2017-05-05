package com.housekeeper.ar.healthhousekeeper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PassAuthenticator extends Authenticator
{
    public PasswordAuthentication getPasswordAuthentication()
    {
        /**
         *
         */
        String username = "zhangruitest0113@sina.com";
        String pwd = "123456789Abc";
        return new PasswordAuthentication(username, pwd);
    }
}  
