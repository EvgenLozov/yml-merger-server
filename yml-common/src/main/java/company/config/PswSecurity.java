package company.config;

import java.util.Base64;

public class PswSecurity {

    String randomString = "qwrr2rf-312";

    public void encodePsw(Config config)
    {
        byte[] pswBytes = Base64.getEncoder().encode((config.getPsw() + randomString).getBytes());
        config.setPsw(new String(pswBytes));
    }

    public void decodePsw(Config config)
    {
        byte[] pswBytes = Base64.getDecoder().decode(config.getPsw());

        String psw = new String(pswBytes).replace(randomString, "");

        config.setPsw(psw);
    }


}
