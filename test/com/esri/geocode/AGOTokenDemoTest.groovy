package com.esri.geocode

import org.junit.Assert
import org.junit.Before
import org.junit.Test


class AGOTokenDemoTest {
    String baseURL
    AGOTokenDemo agoTokenDemo
    Map config

    @Before
    void setUp() {
        config = new ConfigSlurper().parse(new File("src/main/resources/account_config.groovy").toURI().toURL()).flatten()
        baseURL = config.get("organization.baseURL")
        agoTokenDemo = new AGOTokenDemo()
    }

    @Test
    void testGetToken() {
        Map params = [username: config.get("organization.namedUsers.mainUser.username"),
                password: config.get("organization.namedUsers.mainUser.password"),
                expiration:"100",
                referer:config.get("organization.namedUsers.mainUser.referer"),
                clientid:"ref",
                f:"pjson"];
        String token = agoTokenDemo.getToken(baseURL,params)
        println(token)
        Assert.assertNotNull(token)
    }

    @Test
    void testGetApp1OAuthToken() {
        Map params = [client_id: config.get("organization.apps.app1.clientId"),
                client_secret: config.get("organization.apps.app1.clientSecret"),
                grant_type:"client_credentials"];
        String token = agoTokenDemo.getOAuthToken(baseURL,params)
        println(token)
        Assert.assertNotNull(token)
    }

    @Test
    void testGetApp2OAuthToken() {
        Map params = [client_id: config.get("organization.apps.app2.clientId"),
                client_secret: config.get("organization.apps.app2.clientSecret"),
                grant_type:"client_credentials"];
        String token = agoTokenDemo.getOAuthToken(baseURL,params)
        println(token)
        Assert.assertNotNull(token)
    }
}
