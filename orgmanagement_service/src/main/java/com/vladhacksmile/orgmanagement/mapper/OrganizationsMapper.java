package com.vladhacksmile.orgmanagement.mapper;

import _8080.api.v1.orgservice.*;


public class OrganizationsMapper {

    public static GetAllOrganizationsResponse toGetAllOrganizationsResponse(SearchResultOrganization searchResultOrganization) {
        GetAllOrganizationsResponse result = new GetAllOrganizationsResponse();
        result.setDescription(searchResultOrganization.getDescription());
        result.setPageNum(searchResultOrganization.getPageNum());
        result.setPageSize(searchResultOrganization.getPageSize());
        result.setStatus(searchResultOrganization.getStatus());
        result.setPageTotal(searchResultOrganization.getPageTotal());
        result.setTotalElements(searchResultOrganization.getTotalElements());
        result.getObjects().addAll(searchResultOrganization.getObjects());

        return result;
    }

    public static GetOrganizationByIdResponse toGetOrganizationByIdRequest(ResultOrganization resultOrganization) {
        GetOrganizationByIdResponse result = new GetOrganizationByIdResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static AddOrganizationResponse toAddOrganizationResponse(ResultOrganization resultOrganization) {
        AddOrganizationResponse result = new AddOrganizationResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static PutOrganizationResponse toPutOrganizationResponse(ResultOrganization resultOrganization) {
        PutOrganizationResponse result = new PutOrganizationResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static DeleteOrganizationByIdResponse toDeleteOrganizationByIdResponse(ResultOrganization resultOrganization) {
        DeleteOrganizationByIdResponse result = new DeleteOrganizationByIdResponse();
        result.setDescription(resultOrganization.getDescription());
        result.setStatus(resultOrganization.getStatus());
        result.setObject(resultOrganization.getObject());
        return result;
    }

    public static CountLowerAnnualTurnoverResponse toCountLowerAnnualTurnoverResponse(ResultInteger resultInteger) {
        CountLowerAnnualTurnoverResponse result = new CountLowerAnnualTurnoverResponse();
        result.setDescription(resultInteger.getDescription());
        result.setStatus(resultInteger.getStatus());
        result.setObject(resultInteger.getObject());
        return result;
    }

    public static UniqueAnnualTurnoversResponse toUniqueAnnualTurnoversResponse(ResultListFloat resultListFloat) {
        UniqueAnnualTurnoversResponse result = new UniqueAnnualTurnoversResponse();
        result.setDescription(resultListFloat.getDescription());
        result.setStatus(resultListFloat.getStatus());
        result.getObject().addAll(resultListFloat.getObject());
        return result;
    }

    public static FindSubstringResponse toFindSubstringResponse(SearchResultOrganization searchResultOrganization) {
        FindSubstringResponse result = new FindSubstringResponse();
        result.setDescription(searchResultOrganization.getDescription());
        result.setPageNum(searchResultOrganization.getPageNum());
        result.setPageSize(searchResultOrganization.getPageSize());
        result.setStatus(searchResultOrganization.getStatus());
        result.setPageTotal(searchResultOrganization.getPageTotal());
        result.setTotalElements(searchResultOrganization.getTotalElements());
        result.getObjects().addAll(searchResultOrganization.getObjects());

        return result;
    }

}