package com.fei.netty.springmvc.http;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

public class DefaultRequestDispatcher implements RequestDispatcher{
	
	private final Log logger = LogFactory.getLog(getClass());

	private final String resource;


	/**
	 * Create a new DefaultRequestDispatcher for the given resource.
	 * @param resource the server resource to dispatch to, located at a
	 * particular path or given by a particular name
	 */
	public DefaultRequestDispatcher(String resource) {
		Assert.notNull(resource, "resource must not be null");
		this.resource = resource;
	}


	@Override
	public void forward(ServletRequest request, ServletResponse response) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(response, "Response must not be null");
		if (response.isCommitted()) {
			throw new IllegalStateException("Cannot perform forward - response is already committed");
		}
		getDefaultHttpServletResponse(response).setForwardedUrl(this.resource);
		if (logger.isDebugEnabled()) {
			logger.debug("DefaultRequestDispatcher: forwarding to [" + this.resource + "]");
		}
	}

	@Override
	public void include(ServletRequest request, ServletResponse response) {
		Assert.notNull(request, "Request must not be null");
		Assert.notNull(response, "Response must not be null");
		getDefaultHttpServletResponse(response).addIncludedUrl(this.resource);
		if (logger.isDebugEnabled()) {
			logger.debug("DefaultRequestDispatcher: including [" + this.resource + "]");
		}
	}

	/**
	 * Obtain the underlying {@link DefaultHttpServletResponse}, unwrapping
	 * {@link HttpServletResponseWrapper} decorators if necessary.
	 */
	protected DefaultHttpServletResponse getDefaultHttpServletResponse(ServletResponse response) {
		if (response instanceof DefaultHttpServletResponse) {
			return (DefaultHttpServletResponse) response;
		}
		if (response instanceof HttpServletResponseWrapper) {
			return getDefaultHttpServletResponse(((HttpServletResponseWrapper) response).getResponse());
		}
		throw new IllegalArgumentException("DefaultRequestDispatcher requires DefaultHttpServletResponse");
	}

	
}
