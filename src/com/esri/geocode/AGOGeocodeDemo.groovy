package com.esri.geocode

import groovy.json.JsonSlurper
import groovy.transform.Field

@Field
def SINGLE_GEOCODE_BASE_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/find?"
@Field
def MULTILINE_GEOCODE_BASE_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/findAddressCandidates?"
@Field
def REVERSE_GEOCODE_BASE_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer/reverseGeocode?"
@Field
def OUT_SR = "4326"
@Field
def OUT_FORMAT = "pjson"

/**
 * Geocode individual component address
 * Possible parameters: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r30000001p000000
 * Service coverage: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000018000000
 * @param addressParams
 * @return
 */
def geocodeMultiLine(addressParams){
    return getURL(parameterizeURL(MULTILINE_GEOCODE_BASE_URL,addressParams))
}

/**
 * Convenience method for individual single line geocode.
 * Doc: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000015000000
 * Service coverage: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000018000000
 * @param searchText
 * @param country
 * @return
 */
def geocodeSingleLine(searchText,country){
    params = [text:searchText,sourceCountry:country,outFields:"*",maxLocations:"3",outSR:OUT_SR,f:OUT_FORMAT];
    return geocodeSingleLine(params)
}

/**
 * Full support method for individual single line geocode.
 * Doc: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000015000000
 * Service coverage: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000018000000
 * @param params
 * @param country
 * @return
 */
def geocodeSingleLine(params){
    return getURL(parameterizeURL(SINGLE_GEOCODE_BASE_URL,params))
}

/**
 * Convenience method for reverse geocode
 * Doc: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r30000000n000000
 * Service coverage: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000018000000
 * @param lat
 * @param lon
 * @return
 */
def reverseGeocode(lat,lon){
  params = [location:"${lon},${lat}",outSR:OUT_SR,f:OUT_FORMAT];
  return reverseGeocode(params)
}

/**
 * Full support method for reverse geocode
 * Doc: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r30000000n000000
 * Service coverage: http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r300000018000000
 * @param params
 * @return
 */
def reverseGeocode(params){
  return getURL(parameterizeURL(REVERSE_GEOCODE_BASE_URL,params))
}

/**
 * Append URL params to baseURL.  URL params will be URLEncoded for unicode
 * @param baseURL
 * @param params
 * @return encoded URL with parameters
 */
def parameterizeURL(baseURL, params){
    return baseURL + params.collect { k,v -> "$k=${URLEncoder.encode(v,"UTF-8")}" }.join('&')
}

/**
 * Get URL return contents as String
 * @param url
 * @return
 */
def getURL(url){
    return new URL(url).getText()
}

/**
 * Convert json String to parameter Map
 * @param json
 * @return
 */
def jsonToMap(json){
    return new JsonSlurper().parseText(json)
}

def printResult(result){
  println(result)
}


