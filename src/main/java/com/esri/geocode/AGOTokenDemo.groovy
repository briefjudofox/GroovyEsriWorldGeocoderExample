package com.esri.geocode

import groovy.transform.Field

@Field
def TOKEN_BASE_URL = 'https://$baseurl/sharing/rest/generateToken?'
@Field
def OAUTH_TOKEN_BASE_URL = 'https://$baseurl/sharing/oauth2/token?'


/**
 * Get general access token.  See here:
 * http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#//02r3000000m5000000
 * params e.g. [username: user, password: pass,expiration:"100", referer:"domain.com",
 * clientid:"ref",f:"pjson"]
 *
 * @param baseURL
 * @param params
 * @return
 */
def getToken(baseURL,params){
    url = RequestHelper.populateUrlTemplate(TOKEN_BASE_URL,["baseurl":baseURL])
    parmeterizedURL = RequestHelper.parameterizeURL(url,params)
    return responseToToken(RequestHelper.postURL(parmeterizedURL,params.referer))
}

/**
 * Get OAuth2 token.  See doc here:
 * http://resources.arcgis.com/en/help/arcgis-rest-api/index.html#/Token/02r300000213000000/
 * params e.g. [client_id: "FOO", client_secret: "FOO_PANTS",grant_type:"client_credentials"]
 *
 * @param baseURL
 * @param username
 * @param password
 * @return
 */
def getOAuthToken(baseURL,params){
    url = RequestHelper.populateUrlTemplate(OAUTH_TOKEN_BASE_URL,["baseurl":baseURL])
    parmeterizedURL = RequestHelper.parameterizeURL(url,params)
    return responseToOAuthToken(RequestHelper.postURL(parmeterizedURL))
}

/**
 * Parse json response and return only the token string
 * @param response
 * @return
 */
def responseToToken(response){
    responseMap = RequestHelper.jsonToMap(response)
    return responseMap.token
}

/**
 * Parse json response and return only the oauth token string
 * {"access_token":"h9bHI-H1F1e7sOHEgFLpRLLRA...","expires_in":7200}
 * @param response
 */
def responseToOAuthToken(response){
    responseMap = RequestHelper.jsonToMap(response)
    return responseMap.access_token
}


