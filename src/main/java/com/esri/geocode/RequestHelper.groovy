package com.esri.geocode

import groovy.json.JsonSlurper
import groovy.text.SimpleTemplateEngine
import groovyx.net.http.HTTPBuilder


/**
 * Append URL params to baseURL.  URL params will be URLEncoded for unicode
 * @param baseURL
 * @param params
 * @return encoded URL with parameters
 */
def static parameterizeURL(baseURL, params){
    return baseURL + params.collect { k,v -> "$k=${URLEncoder.encode(v,"UTF-8")}" }.join('&')
}


/**
 * Convert json String to parameter Map
 * @param json
 * @return
 */
def static jsonToMap(json){
    return new JsonSlurper().parseText(json)
}

/**
 * Populates the given parameterized template URL with the corresponding
 * parameters, params.  For example, a template URL might look like:
 *
 * https://$baseurl/sharing/rest/generateToken?
 *
 * where the "$baseurl" parameter is replaced with params.baseurl
 *
 * @param templateUrl
 * @param params
 * @return
 */
def static populateUrlTemplate(templateUrl,params){
    def engine = new SimpleTemplateEngine()
    def template = engine.createTemplate(templateUrl).make(params)
    return template.toString()
}

/**
 * Get URL return contents as String
 * @param url
 * @return
 */
def static postURL(url){
    def http = new HTTPBuilder(url)
    def response;
    http.post( headers:[Pragma:"no-cache"]) { resp , reader->
        response = reader.text
    }
    return response
}

/**
 * Get URL return contents as String
 * @param url
 * @return
 */
def static postURL(url,referer){
    def http = new HTTPBuilder(url)
    def response;
    http.post( headers:[Referer:referer,Pragma:"no-cache"]) { resp , reader->
        response = reader.text
    }
    return response
}


/**
 * Get URL return contents as String
 * @param url
 * @return
 */
def static getURL(url){
    return new URL(url).getText()
}