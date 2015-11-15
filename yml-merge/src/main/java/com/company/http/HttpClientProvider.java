package com.company.http;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by user50 on 21.06.2015.
 */
public class HttpClientProvider {

    private String user;
    private String psw;

    public HttpClientProvider(String user, String psw) {
        this.user = user;
        this.psw = psw;
    }

    public CloseableHttpClient get()
    {
        WebDriver driver = new HtmlUnitDriver();

        //Open gmail
        driver.get("http://www.apishops.com/login.jsp");

        // Enter userd id
        WebElement login = driver.findElement(By.name("login"));
        login.sendKeys(user);

        //wait 3 secs for  userid to be entered
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        //Enter Password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(psw);

        //wait 3 secs for  userid to be entered
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebElement button = driver.findElements(By.tagName("input")).get(4);

        //Submit button
        button.submit();

        CookieStore cookieStore = seleniumCookiesToCookieStore(driver);

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);

        return HttpClients.custom().setConnectionManager(cm).setDefaultCookieStore(cookieStore).build();
    }

    private CookieStore seleniumCookiesToCookieStore(WebDriver driver) {

        Set<Cookie> seleniumCookies = driver.manage().getCookies();
        CookieStore cookieStore = new BasicCookieStore();

        for(Cookie seleniumCookie : seleniumCookies){
            BasicClientCookie basicClientCookie =
                    new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            basicClientCookie.setDomain(seleniumCookie.getDomain());
            basicClientCookie.setExpiryDate(seleniumCookie.getExpiry());
            basicClientCookie.setPath(seleniumCookie.getPath());
            cookieStore.addCookie(basicClientCookie);
        }

        return cookieStore;
    }
}
