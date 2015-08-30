/*
 * Copyright (C) 2014 Dmitry Kotlyarov.
 * All Rights Reserved.
 *
 * CONFIDENTIAL
 *
 * All information contained herein is, and remains the property
 * of copyright holders. The intellectual and technical concepts
 * contained herein are proprietary to copyright holders and may
 * be covered by U.S. and Foreign Patents, patents in process,
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission
 * is obtained from copyright holders.
 */

package apphub.service.ldap;

import org.forgerock.opendj.ldap.DN;
import org.forgerock.opendj.ldap.Entry;
import org.forgerock.opendj.ldap.IntermediateResponseHandler;
import org.forgerock.opendj.ldap.LinkedHashMapEntry;
import org.forgerock.opendj.ldap.RequestContext;
import org.forgerock.opendj.ldap.RequestHandler;
import org.forgerock.opendj.ldap.ResultCode;
import org.forgerock.opendj.ldap.ResultHandler;
import org.forgerock.opendj.ldap.SearchResultHandler;
import org.forgerock.opendj.ldap.requests.AddRequest;
import org.forgerock.opendj.ldap.requests.BindRequest;
import org.forgerock.opendj.ldap.requests.CompareRequest;
import org.forgerock.opendj.ldap.requests.DeleteRequest;
import org.forgerock.opendj.ldap.requests.ExtendedRequest;
import org.forgerock.opendj.ldap.requests.ModifyDNRequest;
import org.forgerock.opendj.ldap.requests.ModifyRequest;
import org.forgerock.opendj.ldap.requests.SearchRequest;
import org.forgerock.opendj.ldap.responses.BindResult;
import org.forgerock.opendj.ldap.responses.CompareResult;
import org.forgerock.opendj.ldap.responses.ExtendedResult;
import org.forgerock.opendj.ldap.responses.Result;

import static org.forgerock.opendj.ldap.responses.Responses.newResult;
import static org.forgerock.opendj.ldap.responses.Responses.newSearchResultEntry;

/**
 * @author Dmitry Kotlyarov
 * @since 1.0
 */
public class LdapRequestHandler implements RequestHandler<RequestContext> {
    public LdapRequestHandler() {
    }

    @Override
    public void handleAdd(RequestContext requestContext, AddRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<Result> resultHandler) {
    }

    @Override
    public void handleBind(RequestContext requestContext, int version, BindRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<BindResult> resultHandler) {
    }

    @Override
    public void handleCompare(RequestContext requestContext, CompareRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<CompareResult> resultHandler) {
    }

    @Override
    public void handleDelete(RequestContext requestContext, DeleteRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<Result> resultHandler) {
    }

    @Override
    public <R extends ExtendedResult> void handleExtendedRequest(RequestContext requestContext, ExtendedRequest<R> request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<R> resultHandler) {
    }

    @Override
    public void handleModify(RequestContext requestContext, ModifyRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<Result> resultHandler) {
    }

    @Override
    public void handleModifyDN(RequestContext requestContext, ModifyDNRequest request, IntermediateResponseHandler intermediateResponseHandler, ResultHandler<Result> resultHandler) {
    }

    @Override
    public void handleSearch(RequestContext requestContext, SearchRequest request, IntermediateResponseHandler intermediateResponseHandler, SearchResultHandler resultHandler) {
        DN dn = request.getName();
        Entry entry = new LinkedHashMapEntry("uid=dmktv,dc=apphub");
        entry.addAttribute("uid", "dmktv");
        resultHandler.handleEntry(newSearchResultEntry(entry));
        resultHandler.handleResult(newResult(ResultCode.SUCCESS));
    }
}
