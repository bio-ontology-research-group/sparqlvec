package bio2vec.jena;

import org.apache.jena.graph.Node;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.ExprEvalException;
import org.apache.jena.sparql.function.FunctionBase3;
import org.apache.jena.sparql.util.FmtUtils;
import org.apache.jena.ext.xerces.util.URI;
import java.util.Map;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.util.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;

import org.json.*;

import bio2vec.Functions;

public class similarity extends FunctionBase3 {

    public similarity() {
	super();
    }

    
    public NodeValue exec(NodeValue nv1, NodeValue nv2, NodeValue nv3) {
	Node n1 = nv1.asNode();
	if (!n1.isURI())
            throw new ExprEvalException(
		"Not a URI: " + nv1.asString());
        Node n2 = nv2.asNode();
        if (!n2.isURI())
            throw new ExprEvalException(
		"Not a URI: " + nv2.asString());
	Node n3 = nv3.asNode();
        if (!n2.isURI())
            throw new ExprEvalException(
		"Not a URI: " + nv3.asString());
	String d = null;
	try {
	    d = new URI(nv1.asString()).getFragment();
	} catch (URI.MalformedURIException  e) {
	    throw new ExprEvalException("Not a URI: " + nv1.asString());
	}
	String v1 = nv2.asString();
	String v2 = nv3.asString();
	double sim = Functions.cosineSimilarity(d, v1, v2);
	return NodeValue.makeNodeDouble(sim);
    }
}
