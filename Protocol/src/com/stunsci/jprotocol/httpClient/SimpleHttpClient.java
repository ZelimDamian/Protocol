package com.stunsci.jprotocol.httpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Logger;

public class SimpleHttpClient {
	public SimpleHttpClient() {}
	
	public String getStringFromURL(String urlString, String data, String method)
	{
		try {
	        URL url = new URL(urlString);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod(method);
	        
//	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
//	        writer.write(data);
//	        writer.close();

        	InputStream _is;  
        	if (connection.getResponseCode() < 400) {  
        	    _is = connection.getInputStream();  
        	} else {  
        	     /* error from server */  
        	    _is = connection.getErrorStream();  
        	}
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(_is)); 
        	String response = "";
        	String strLine;
        	
        	while ((strLine = in.readLine()) != null) {
                response += strLine;
            }
        	
        	return response;
        	
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return "Error";
	}
	
	public String getJsonStringFromURL(String urlString, String data, String method)
	{
	    try {
	        URL url = new URL(urlString);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoOutput(true);
	        connection.setRequestMethod(method);
	        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
	        
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	        writer.write(data);
	        writer.close();

        	InputStream _is;  
        	if (connection.getResponseCode() < 400) {  
        	    _is = connection.getInputStream();  
        	} else {  
        	     /* error from server */  
        	    _is = connection.getErrorStream();  
        	}
        	
        	BufferedReader in = new BufferedReader(new InputStreamReader(_is)); 
        	String response = "";
        	String strLine;
        	
        	while ((strLine = in.readLine()) != null) {
                response += strLine;
            }
        	
        	return response;
        	
	    } catch (Exception e) {
	    	Logger.getLogger(SimpleHttpClient.class.getName()).severe(e.getMessage());
	        e.printStackTrace();
	    }
		
		return "SOMETHING WENT WRONG";
	}
}
