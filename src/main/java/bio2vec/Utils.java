package bio2vec;

import java.io.*;
import java.util.*;
import org.json.*;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;

import org.apache.jena.query.ARQ;
import org.apache.jena.sparql.util.*;


public class Utils {
    
    public static final String ELASTIC_INDEX_URI = "http://10.127.4.79:9200/";

    public static JSONObject queryIndex(String dataset, String query) {
	CloseableHttpClient client = HttpClients.createDefault();
	JSONObject result = null;
	try {
	    try {
		HttpPost post = new HttpPost(ELASTIC_INDEX_URI +
					     dataset + "/_search");
		StringEntity requestEntity = new StringEntity(query,
							      ContentType.APPLICATION_JSON);
		post.setEntity(requestEntity);
		CloseableHttpResponse response = client.execute(post);
		try {
		    // Execute the method.
		    int statusCode = response.getStatusLine().getStatusCode();
		    HttpEntity entity = response.getEntity();
			
		    if (statusCode < 200 || statusCode >= 300) {
			System.err.println("Method failed: " + response.getStatusLine());
		    } else {
			// Read the response body.
			String responseBody = EntityUtils.toString(entity, "UTF-8");
			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary data
			result = new JSONObject(responseBody);
		    }
		    EntityUtils.consume(entity);
		} finally {
		    // Release the connection.
		    response.close();
		}
	    } finally {
		client.close();
	    }
	} catch (IOException ex) {
	    ex.printStackTrace();
	}
	return result;
    }
}
