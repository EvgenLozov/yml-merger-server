package com.company.repository;

import com.company.config.MergerConfig;

public class PswSecurity {

    String randomString = "qwrr2rf-312";

    public void encodePsw(MergerConfig config)
    {
//        byte[] pswBytes = Base64.getEncoder().encode((config.getPsw() + randomString).getBytes());
//        config.setPsw(new String(pswBytes));
    }

    public void decodePsw(MergerConfig config)
    {
//        byte[] pswBytes = Base64.getDecoder().decode(config.getPsw());
//
//        String psw = new String(pswBytes).replace(randomString, "");
//
//        config.setPsw(psw);
    }

}
