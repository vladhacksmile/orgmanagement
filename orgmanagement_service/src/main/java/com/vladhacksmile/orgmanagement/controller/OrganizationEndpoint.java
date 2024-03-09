package com.vladhacksmile.orgmanagement.controller;

import _8080.api.v1.orgservice.*;
import com.vladhacksmile.orgmanagement.mapper.OrganizationsMapper;
import com.vladhacksmile.orgmanagement.model.ResultHelper;
import com.vladhacksmile.orgmanagement.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class OrganizationEndpoint {

    private static final String NAMESPACE_URI = "8080/api/v1/orgservice";

    @Autowired
    private OrganizationService organizationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllOrganizationsRequest")
    @ResponsePayload
    public GetAllOrganizationsResponse getAll(@RequestPayload GetAllOrganizationsRequest getAllOrganizationsRequest) {
        try {
            return OrganizationsMapper.toGetAllOrganizationsResponse(organizationService.getAll(getAllOrganizationsRequest.getPageNum(),
                    getAllOrganizationsRequest.getPageSize(), getAllOrganizationsRequest.getSortType(),
                    getAllOrganizationsRequest.getSortColumn(), getAllOrganizationsRequest.getFilterOperation(),
                    getAllOrganizationsRequest.getFilterField(), getAllOrganizationsRequest.getFilterValue()));
        } catch (Exception e) {
            return OrganizationsMapper.toGetAllOrganizationsResponse(ResultHelper.createSearchResultOrganizationWithStatusAndDesc(Status.INTERNAL_ERROR, null));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrganizationByIdRequest")
    @ResponsePayload
    public GetOrganizationByIdResponse get(@RequestPayload GetOrganizationByIdRequest getOrganizationByIdRequest) {
        try {
            return OrganizationsMapper.toGetOrganizationByIdRequest(organizationService.get(getOrganizationByIdRequest.getId()));
        } catch (Exception e) {
            return OrganizationsMapper.toGetOrganizationByIdRequest(ResultHelper.createOrganizationWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddOrganizationRequest")
    @ResponsePayload
    public AddOrganizationResponse add(@RequestPayload AddOrganizationRequest addOrganizationRequest) {
        try {
            return OrganizationsMapper.toAddOrganizationResponse(organizationService.add(addOrganizationRequest.getOrganization()));
        } catch (Exception e) {
            return OrganizationsMapper.toAddOrganizationResponse(ResultHelper.createOrganizationWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "PutOrganizationRequest")
    @ResponsePayload
    public PutOrganizationResponse put(@RequestPayload PutOrganizationRequest putOrganizationRequest) {
        try {
            return OrganizationsMapper.toPutOrganizationResponse(organizationService.put(putOrganizationRequest.getOrganization()));
        } catch (Exception e) {
            return OrganizationsMapper.toPutOrganizationResponse(ResultHelper.createOrganizationWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrganizationByIdRequest")
    @ResponsePayload
    public DeleteOrganizationByIdResponse delete(@RequestPayload DeleteOrganizationByIdRequest deleteOrganizationByIdRequest) {
        try {
            return OrganizationsMapper.toDeleteOrganizationByIdResponse(organizationService.delete(deleteOrganizationByIdRequest.getId()));
        } catch (Exception e) {
            return OrganizationsMapper.toDeleteOrganizationByIdResponse(ResultHelper.createOrganizationWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CountLowerAnnualTurnoverRequest")
    @ResponsePayload
    public CountLowerAnnualTurnoverResponse countLowerAnnualTurnover(@RequestPayload CountLowerAnnualTurnoverRequest countLowerAnnualTurnoverRequest) {
        try {
            return OrganizationsMapper.toCountLowerAnnualTurnoverResponse(organizationService.countLowerAnnualTurnover(countLowerAnnualTurnoverRequest.getAnnualTurnover()));
        } catch (Exception e) {
            return OrganizationsMapper.toCountLowerAnnualTurnoverResponse(ResultHelper.createIntegerWithStatusAndDesc(Status.INTERNAL_ERROR, null));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UniqueAnnualTurnoversRequest")
    @ResponsePayload
    public UniqueAnnualTurnoversResponse uniqueLowerAnnualTurnover(@RequestPayload UniqueAnnualTurnoversRequest uniqueAnnualTurnoversRequest) {
        try {
            return OrganizationsMapper.toUniqueAnnualTurnoversResponse(organizationService.findUniqueAnnualTurnover());
        } catch (Exception e) {
            return OrganizationsMapper.toUniqueAnnualTurnoversResponse(ResultHelper.createListFloatWithStatus(Status.INTERNAL_ERROR));
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindSubstringRequest")
    @ResponsePayload
    public FindSubstringResponse findSubstring(@RequestPayload FindSubstringRequest findSubstringRequest) {
        try {
            return OrganizationsMapper.toFindSubstringResponse(organizationService.findSubstring(findSubstringRequest.getPageNum(), findSubstringRequest.getPageSize(),
                    findSubstringRequest.getSortType(), findSubstringRequest.getSortColumn(), findSubstringRequest.getSubstring()));
        } catch (Exception e) {
            return OrganizationsMapper.toFindSubstringResponse(ResultHelper.createSearchResultOrganizationWithStatusAndDesc(Status.INTERNAL_ERROR, null));
        }
    }

}
