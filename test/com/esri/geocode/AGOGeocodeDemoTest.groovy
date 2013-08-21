package com.esri.geocode

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AGOGeocodeDemoTest {
    AGOGeocodeDemo agoGeocoder;
    AGOTokenDemo agoTokenDemo;
    String token;
    String oAuthTokenApp1;
    String oAuthTokenApp2;

    @Before
    void setUp() {
        agoGeocoder = new AGOGeocodeDemo();
        agoTokenDemo = new AGOTokenDemo()
        Map config = new ConfigSlurper().parse(new File("src/main/resources/account_config.groovy").toURI().toURL()).flatten()


        token = agoTokenDemo.getToken(config.get("organization.baseURL"),
                [username: config.get("organization.namedUsers.mainUser.username"),
                        password: config.get("organization.namedUsers.mainUser.password"),
                        expiration:"100",
                        referer:config.get("organization.namedUsers.mainUser.referer"),
                        clientid:"ref",
                        f:"pjson"])


        oAuthTokenApp1 = agoTokenDemo.getOAuthToken(config.get("organization.baseURL"),
                [client_id: config.get("organization.apps.app1.clientId"),
                 client_secret: config.get("organization.apps.app1.clientSecret"),
                 grant_type:"client_credentials"])

        oAuthTokenApp2= agoTokenDemo.getOAuthToken(config.get("organization.baseURL"),
                [client_id: config.get("organization.apps.app2.clientId"),
                 client_secret: config.get("organization.apps.app2.clientSecret"),
                 grant_type:"client_credentials"])


    }

    @Test
    void testSimpleReverseGeocode() {
        String results = agoGeocoder.reverseGeocode(34,-118)
        println(results)
        Assert.assertNotNull(results)
    }


    @Test
    void testAllParamsReverseGeocode() {
        Map params = [location: "-118,34",
                distance: "50",
                outSR: agoGeocoder.OUT_SR,
                f: agoGeocoder.OUT_FORMAT,
                forStorage: "false"];
        String results = agoGeocoder.reverseGeocode(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testAllParamsReverseGeocodeWithToken20X() {
        for(i in 1..20){testAllParamsReverseGeocodeWithToken()}
    }

    @Test
    void testAllParamsReverseGeocodeWithToken() {
        Map params = [location: "-118,34",
                distance: "50",
                outSR: agoGeocoder.OUT_SR,
                f: agoGeocoder.OUT_FORMAT,
                forStorage: "false",
                token:token];
        String results = agoGeocoder.reverseGeocode(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocodeUS() {
        String results = agoGeocoder.geocodeSingleLine("380 New York St, Redlands, CA, 92373","US")
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocodeUS_POI() {
        String results = agoGeocoder.geocodeSingleLine("Golden Gate Bridge","US")
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_zhCN() {
        String results = agoGeocoder.geocodeSingleLine("上海市安远路170号 200060","CHN")
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_enCN() {
        String results = agoGeocoder.geocodeSingleLine("Beijing","CHN")
        println(results)
        Assert.assertNotNull(results)
    }


    @Test
    void testSimpleSinglelineGeocode_esMX() {
        String results = agoGeocoder.geocodeSingleLine("Michoacán 30 Zona Centro, 37800","MEX")
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_esMX_POI() {
        String results = agoGeocoder.geocodeSingleLine("Universidad Tecnologica de México","MEX")
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsSinglelineGeocodeBboxFilter() {
        Map params = [text: "14461 Oak Canyon Dr., 91745",
                sourceCountry: "USA",
                bbox:"-120,32,-115,40",
                outSR: agoGeocoder.OUT_SR,
                f: agoGeocoder.OUT_FORMAT,
                outFields:"*",
                maxLocations:"6",
                forStorage: "false"];
        String results = agoGeocoder.geocodeSingleLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsSinglelineGeocodeLocationFilter() {
        Map params = [text: "14461 Oak Canyon Dr., 91745",
                sourceCountry: "USA",
                location:"-118,34",
                distance:"3000",
                outSR: agoGeocoder.OUT_SR,
                f: agoGeocoder.OUT_FORMAT,
                outFields:"*",
                maxLocations:"6",
                forStorage: "false"];
        String results = agoGeocoder.geocodeSingleLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsMultilineGeocode_US() {
        Map params = [Address:"380 New York Street",
                Neighborhood:"",
                City:"Redlands",
                Subregion:"",
                Region:"CA",
                Postal:"92373",
                PostalExt:"",
                CountryCode:"USA",
                searchExtent:"",
                location:"",
                distance:"",
                outFields:"*",
                outSR:agoGeocoder.OUT_SR,
                f:agoGeocoder.OUT_FORMAT];
        String results = agoGeocoder.geocodeMultiLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsMultilineGeocode_USWithToken1000X() {
        for(i in 1..1000){println(i);testParamsMultilineGeocode_USWithToken();}
    }

    @Test
    void testParamsMultilineGeocode_USWithToken() {
        Map params = [Address:"380 New York Street",
                Neighborhood:"",
                City:"Redlands",
                Subregion:"",
                Region:"CA",
                Postal:"92373",
                PostalExt:"",
                CountryCode:"USA",
                searchExtent:"",
                location:"",
                distance:"",
                outFields:"*",
                outSR:agoGeocoder.OUT_SR,
                f:agoGeocoder.OUT_FORMAT,
                forStorage:"true",
                token:token];
        String results = agoGeocoder.geocodeMultiLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsMultilineGeocode_USWithOauthTokenApp1_500X() {
        for(i in 1..500){println(i);testParamsMultilineGeocode_USWithOauthTokenApp1();}
    }

    @Test
    void testParamsMultilineGeocode_USWithOauthTokenApp1() {
        Map params = [Address:"380 New York Street",
                Neighborhood:"",
                City:"Redlands",
                Subregion:"",
                Region:"CA",
                Postal:"92373",
                PostalExt:"",
                CountryCode:"USA",
                searchExtent:"",
                location:"",
                distance:"",
                outFields:"*",
                outSR:agoGeocoder.OUT_SR,
                f:agoGeocoder.OUT_FORMAT,
                forStorage:"true",
                token:oAuthTokenApp1];
        String results = agoGeocoder.geocodeMultiLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsMultilineGeocode_USWithOauthTokenApp21000X() {
        for(i in 1..1000){println(i);testParamsMultilineGeocode_USWithOauthTokenApp2();}
    }

    @Test
    void testParamsMultilineGeocode_USWithOauthTokenApp2() {
        Map params = [Address:"380 New York Street",
                Neighborhood:"",
                City:"Redlands",
                Subregion:"",
                Region:"CA",
                Postal:"92373",
                PostalExt:"",
                CountryCode:"USA",
                searchExtent:"",
                location:"",
                distance:"",
                outFields:"*",
                outSR:agoGeocoder.OUT_SR,
                f:agoGeocoder.OUT_FORMAT,
                forStorage:"true",
                token:oAuthTokenApp2];
        String results = agoGeocoder.geocodeMultiLine(params)
        println(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testParamsMultilineGeocode_MX() {
        Map params = [Address:"Avenida Revolucion 8208",
                Neighborhood:"Herrera",
                City:"Tijuana",
                Subregion:"",
                Region:"Baja California",
                Postal:"",
                PostalExt:"",
                CountryCode:"MEX",
                searchExtent:"",
                location:"",
                distance:"",
                outFields:"*",
                outSR:agoGeocoder.OUT_SR,
                f:agoGeocoder.OUT_FORMAT];
        String results = agoGeocoder.geocodeMultiLine(params)
        println(results)
        Assert.assertNotNull(results)
    }



}


