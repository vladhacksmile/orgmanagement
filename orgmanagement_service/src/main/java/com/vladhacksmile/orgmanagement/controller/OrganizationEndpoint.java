package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class OrganizationEndpoint {

    private static final String NAMESPACE_URI = "8080/api/v1/orgservice";

    @Autowired
    private OrganizationService organizationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllOrganizationsRequest")
    @ResponsePayload
    public SearchResultOrganization getAll(@RequestPayload GetAllOrganizationsRequest getAllOrganizationsRequest) {
        return organizationService.getAll(getAllOrganizationsRequest.getPageNum(),
                getAllOrganizationsRequest.getPageSize(), getAllOrganizationsRequest.getSortType(),
                getAllOrganizationsRequest.getSortColumn(), getAllOrganizationsRequest.getFilterOperation(),
                getAllOrganizationsRequest.getFilterField(), getAllOrganizationsRequest.getFilterValue());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrganizationByIdRequest")
    @ResponsePayload
    public ResultOrganization get(@RequestPayload GetOrganizationByIdRequest getOrganizationByIdRequest) {
        return organizationService.get(getOrganizationByIdRequest.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddOrganizationRequest")
    @ResponsePayload
    public ResultOrganization add(@RequestPayload AddOrganizationRequest addOrganizationRequest) {
        return organizationService.add(addOrganizationRequest.getOrganization());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutOrganizationRequest")
    @ResponsePayload
    public ResultOrganization put(@RequestPayload PutOrganizationRequest putOrganizationRequest) {
        return organizationService.put(putOrganizationRequest.getOrganization());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrganizationByIdRequest")
    @ResponsePayload
    public ResultOrganization delete(@RequestPayload DeleteOrganizationByIdRequest deleteOrganizationByIdRequest) {
        return organizationService.delete(deleteOrganizationByIdRequest.getId());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CountLowerAnnualTurnoverRequest")
    @ResponsePayload
    public ResultInteger countLowerAnnualTurnover(@RequestPayload CountLowerAnnualTurnoverRequest countLowerAnnualTurnoverRequest) {
        return organizationService.countLowerAnnualTurnover(countLowerAnnualTurnoverRequest.getAnnualTurnover());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UniqueAnnualTurnoversRequest")
    @ResponsePayload
    public ResultListFloat uniqueLowerAnnualTurnover(@RequestPayload UniqueAnnualTurnoversRequest uniqueAnnualTurnoversRequest) {
        return organizationService.findUniqueAnnualTurnover();
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindSubstringRequest")
    @ResponsePayload
    public SearchResultOrganization findSubstring(@RequestPayload FindSubstringRequest findSubstringRequest) {
        return organizationService.findSubstring(findSubstringRequest.getPageNum(), findSubstringRequest.getPageSize(),
                findSubstringRequest.getSortType(), findSubstringRequest.getSortColumn(), findSubstringRequest.getSubstring());
    }

}
