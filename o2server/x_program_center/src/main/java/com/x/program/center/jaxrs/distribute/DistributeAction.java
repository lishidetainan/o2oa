package com.x.program.center.jaxrs.distribute;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.x.base.core.project.annotation.JaxrsDescribe;
import com.x.base.core.project.annotation.JaxrsMethodDescribe;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.HttpMediaType;
import com.x.base.core.project.jaxrs.ResponseFactory;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;

@Path("distribute")
@JaxrsDescribe("分配模块")
public class DistributeAction extends StandardJaxrsAction {

	@GET
	@Path("assemble/source/{source}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	@JaxrsMethodDescribe(value = "为用户分派应用主机。", action = ActionAssemble.class)
	public void assemble(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@PathParam("source") String source) {
		ActionResult<ActionAssemble.Wo> result = new ActionResult<>();
		try {
			result = new ActionAssemble().execute(request, source);
		} catch (Throwable th) {
			th.printStackTrace();
			result.error(th);
		}
		asyncResponse.resume(ResponseFactory.getDefaultActionResultResponse(result));
	}

	@GET
	@Path("webserver/assemble/source/{source}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	@JaxrsMethodDescribe(value = "为用户分派应用主机和Web主机。", action = ActionAssembleWithWebServer.class)
	public void assembleWithWebServer(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@PathParam("source") String source) {
		ActionResult<ActionAssembleWithWebServer.Wo> result = new ActionResult<>();
		try {
			result = new ActionAssembleWithWebServer().execute(request, source);
		} catch (Throwable th) {
			th.printStackTrace();
			result.error(th);
		}
		asyncResponse.resume(ResponseFactory.getDefaultActionResultResponse(result));
	}
}