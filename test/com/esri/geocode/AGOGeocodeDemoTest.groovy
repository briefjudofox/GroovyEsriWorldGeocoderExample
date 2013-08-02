package com.esri.geocode

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AGOGeocodeDemoTest {
    AGOGeocodeDemo agoGeocoder;

    @Before
    void setUp() {
        agoGeocoder = new AGOGeocodeDemo();
    }

    @Test
    void testSimpleReverseGeocode() {
        String results = agoGeocoder.reverseGeocode(34,-118)
        agoGeocoder.printResult(results)
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
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocodeUS() {
        String results = agoGeocoder.geocodeSingleLine("380 New York St, Redlands, CA, 92373","US")
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocodeUS_POI() {
        String results = agoGeocoder.geocodeSingleLine("Golden Gate Bridge","US")
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_zhCN() {
        String results = agoGeocoder.geocodeSingleLine("上海市安远路170号 200060","CHN")
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_enCN() {
        String results = agoGeocoder.geocodeSingleLine("Beijing","CHN")
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }


    @Test
    void testSimpleSinglelineGeocode_esMX() {
        String results = agoGeocoder.geocodeSingleLine("Michoacán 30 Zona Centro, 37800","MEX")
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }

    @Test
    void testSimpleSinglelineGeocode_esMX_POI() {
        String results = agoGeocoder.geocodeSingleLine("Universidad Tecnologica de México","MEX")
        agoGeocoder.printResult(results)
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
        agoGeocoder.printResult(results)
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
        agoGeocoder.printResult(results)
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
        agoGeocoder.printResult(results)
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
        agoGeocoder.printResult(results)
        Assert.assertNotNull(results)
    }



}


