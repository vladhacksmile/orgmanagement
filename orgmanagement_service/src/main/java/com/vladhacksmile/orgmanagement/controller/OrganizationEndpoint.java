package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.mapper.OrganizationsMapper;
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
    public GetAllOrganizationsResponse getAll(@RequestPayload GetAllOrganizationsRequest getAllOrganizationsRequest) {
        return OrganizationsMapper.toGetAllOrganizationsResponse(organizationService.getAll(getAllOrganizationsRequest.getPageNum(),
                getAllOrganizationsRequest.getPageSize(), getAllOrganizationsRequest.getSortType(),
                getAllOrganizationsRequest.getSortColumn(), getAllOrganizationsRequest.getFilterOperation(),
                getAllOrganizationsRequest.getFilterField(), getAllOrganizationsRequest.getFilterValue()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrganizationByIdRequest")
    @ResponsePayload
    public GetOrganizationByIdResponse get(@RequestPayload GetOrganizationByIdRequest getOrganizationByIdRequest) {
        return OrganizationsMapper.toGetOrganizationByIdRequest(organizationService.get(getOrganizationByIdRequest.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddOrganizationRequest")
    @ResponsePayload
    public AddOrganizationResponse add(@RequestPayload AddOrganizationRequest addOrganizationRequest) {
        return OrganizationsMapper.toAddOrganizationResponse(organizationService.add(addOrganizationRequest.getOrganization()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutOrganizationRequest")
    @ResponsePayload
    public PutOrganizationResponse put(@RequestPayload PutOrganizationRequest putOrganizationRequest) {
        return OrganizationsMapper.toPutOrganizationResponse(organizationService.put(putOrganizationRequest.getOrganization()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrganizationByIdRequest")
    @ResponsePayload
    public DeleteOrganizationByIdResponse delete(@RequestPayload DeleteOrganizationByIdRequest deleteOrganizationByIdRequest) {
        return OrganizationsMapper.toDeleteOrganizationByIdResponse(organizationService.delete(deleteOrganizationByIdRequest.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CountLowerAnnualTurnoverRequest")
    @ResponsePayload
    public CountLowerAnnualTurnoverResponse countLowerAnnualTurnover(@RequestPayload CountLowerAnnualTurnoverRequest countLowerAnnualTurnoverRequest) {
        return OrganizationsMapper.toCountLowerAnnualTurnoverResponse(organizationService.countLowerAnnualTurnover(countLowerAnnualTurnoverRequest.getAnnualTurnover()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UniqueAnnualTurnoversRequest")
    @ResponsePayload
    public UniqueAnnualTurnoversResponse uniqueLowerAnnualTurnover(@RequestPayload UniqueAnnualTurnoversRequest uniqueAnnualTurnoversRequest) {
        return OrganizationsMapper.toUniqueAnnualTurnoversResponse(organizationService.findUniqueAnnualTurnover());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindSubstringRequest")
    @ResponsePayload
    public FindSubstringResponse findSubstring(@RequestPayload FindSubstringRequest findSubstringRequest) {
        return OrganizationsMapper.toFindSubstringResponse(organizationService.findSubstring(findSubstringRequest.getPageNum(), findSubstringRequest.getPageSize(),
                findSubstringRequest.getSortType(), findSubstringRequest.getSortColumn(), findSubstringRequest.getSubstring()));
    }

}
