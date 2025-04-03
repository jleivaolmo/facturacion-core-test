package com.echevarne.sap.cloud.facturacion.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataRequest;
import org.apache.olingo.odata2.api.uri.PathSegment;
import org.apache.olingo.odata2.api.uri.UriSyntaxException;
import org.apache.olingo.odata2.core.ODataPathSegmentImpl;
import org.apache.olingo.odata2.core.PathInfoImpl;
import org.apache.olingo.odata2.core.commons.Decoder;

public class ODataUriUtils {

	private static final Pattern INITIAL_SEGMENT_PATTERN = Pattern.compile("(?:([^.()]+)\\.)?([^.()]+)(?:\\((.+)\\)|(\\(\\)))?");
	public static final String PREFIX_ID_TEMPORAL = "id-";

	public static String percentDecode(final String value) throws UriSyntaxException {
		try {
			return Decoder.decode(value);
		} catch (RuntimeException e) {
			throw new UriSyntaxException(UriSyntaxException.URISYNTAX, e);
		}
	}

	public static List<String> copyPathSegmentList(final List<PathSegment> source) {
		List<String> copy = new ArrayList<String>();

		for (final PathSegment segment : source) {
			copy.add(segment.getPath());
		}

		return copy;
	}

	public static String getEntityFromURI(List<PathSegment> pathSegment) throws UriSyntaxException {
		List<String> segments = copyPathSegmentList(pathSegment);
		preparePathSegments(segments);
		String currentPathSegment = segments.remove(0);
		Matcher matcher = INITIAL_SEGMENT_PATTERN.matcher(currentPathSegment);
		if (matcher.matches()) {
			return percentDecode(matcher.group(2));
		} else
			return null;
	}

	private static void preparePathSegments(List<String> pathSegments) throws UriSyntaxException {
		// Remove an empty path segment at the start of the OData part of the resource
		// path.
		if (!pathSegments.isEmpty() && "".equals(pathSegments.get(0))) {
			pathSegments.remove(0);
		}

		// Remove an empty path segment at the end of the resource path,
		// although there is nothing in the OData specification that would allow that.
		if (!pathSegments.isEmpty() && "".equals(pathSegments.get(pathSegments.size() - 1))) {
			pathSegments.remove(pathSegments.size() - 1);
		}

		// Intermediate empty path segments are an error, however.
		for (String pathSegment : pathSegments) {
			if ("".equals(pathSegment)) {
				throw new UriSyntaxException(UriSyntaxException.EMPTYSEGMENT);
			}
		}
	}

	public static String getBaseUri(final ODataRequest request) {
		String baseUri = request.getPathInfo().getServiceRoot().toASCIIString();
		if (baseUri.endsWith("/")) {
			baseUri = baseUri.substring(0, baseUri.length() - 1);
		}
		return baseUri;
	}
	
	public static PathInfoImpl createPathInfo(ODataRequest request, String entityId, List<PathSegment> odataSegments) throws ODataException {
		
		PathInfoImpl pathInfo = new PathInfoImpl();
		try {
			List<PathSegment> modifiedODataSegments = new ArrayList<PathSegment>();
			String[] segments = entityId.split("/");
			for (String segment : segments) {
				modifiedODataSegments.add(new ODataPathSegmentImpl(segment, null));
			}
			String newRequestUri = ODataUriUtils.getBaseUri(request);
			newRequestUri += "/" + entityId;
			for (int i = 1; i < odataSegments.size(); i++) {
				newRequestUri += "/" + odataSegments.get(i).getPath();
				modifiedODataSegments.add(odataSegments.get(i));
			}
			for (Map.Entry<String, String> entry : request.getQueryParameters().entrySet()) {
				newRequestUri += "/" + entry;
			}
			pathInfo.setServiceRoot(request.getPathInfo().getServiceRoot());
			pathInfo.setPrecedingPathSegment(request.getPathInfo().getPrecedingSegments());
			pathInfo.setRequestUri(new URI(newRequestUri));
			pathInfo.setODataPathSegment(modifiedODataSegments);
		} catch (URISyntaxException e) {
			throw new ODataException(e);
		}
		
		return pathInfo;
	}

}
