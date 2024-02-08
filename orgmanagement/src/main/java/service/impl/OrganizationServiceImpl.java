package service.impl;

import dto.OrganizationDTO;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import lombok.Data;
import mapper.OrganizationsMapper;
import model.entity.Organization;
import model.result.Result;
import model.result.SearchResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.ejb3.annotation.Pool;
import repository.SearchCriteria;
import service.OrganizationService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static model.result.Result.*;
import static model.result.Status.INCORRECT_PARAMS;
import static model.result.Status.NOT_FOUND;
import static model.result.StatusDescription.*;

@Data
@Remote(OrganizationService.class)
@Stateless(name = "OrganizationService")
@Pool("slsb-strict-max-pool")
public class OrganizationServiceImpl implements OrganizationService {

    @PersistenceContext(unitName = "db_unit")
    private EntityManager entityManager;

    @Override
//    @Transactional
    public Result<OrganizationDTO> add(OrganizationDTO organizationDTO) {
        if (organizationDTO.getId() != null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_MUST_BE_NULL);
        }

        if (organizationDTO.getCoordinateX() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_X_IS_NULL);
        }

        if (organizationDTO.getCoordinateX() < -963) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_X_IS_INCORRECT);
        }

        if (organizationDTO.getCoordinateY() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, COORDINATE_Y_IS_NULL);
        }

        if (organizationDTO.getOfficialAddress() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ADDRESS_ID_IS_NULL);
        }

        if (organizationDTO.getName() == null || organizationDTO.getName().isEmpty()) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, NAME_IS_NULL);
        }

        if (organizationDTO.getType() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_TYPE_IS_NULL);
        }

        if (organizationDTO.getAnnualTurnover() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }

        if (organizationDTO.getAnnualTurnover() < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_INCORRECT);
        }

        Organization organization = new Organization(null, organizationDTO.getName(), organizationDTO.getCoordinateX(),
                organizationDTO.getCoordinateY(), ZonedDateTime.now(), organizationDTO.getAnnualTurnover(),
                organizationDTO.getType(), organizationDTO.getOfficialAddress());

        entityManager.persist(organization);

        return createWithOk(OrganizationsMapper.fromEntity(organization));
    }

    @Override
    public Result<OrganizationDTO> get(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = entityManager.find(Organization.class, id);
        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        return createWithOk(OrganizationsMapper.fromEntity(organization));
    }

    @Override
//    @Transactional
    public Result<OrganizationDTO> put(OrganizationDTO organizationDTO) {
        if (organizationDTO == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ORGANIZATION_IS_NULL);
        }

        if (organizationDTO.getId() == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = entityManager.find(Organization.class, organizationDTO.getId());

        if (organization == null) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        if (!Objects.equals(organization.getName(), organizationDTO.getName())) {
            organization.setName(organizationDTO.getName());
        }

        if (!Objects.equals(organization.getAnnualTurnover(), organizationDTO.getAnnualTurnover())) {
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        }

        if (!Objects.equals(organization.getType(), organizationDTO.getType())) {
            organization.setType(organizationDTO.getType());
        }

        if (!Objects.equals(organization.getAnnualTurnover(), organizationDTO.getAnnualTurnover())) {
            organization.setAnnualTurnover(organizationDTO.getAnnualTurnover());
        }

        if (!Objects.equals(organization.getCoordinateX(), organizationDTO.getCoordinateX())) {
            organization.setCoordinateX(organizationDTO.getCoordinateX());
        }

        if (!Objects.equals(organization.getOfficialAddress(), organizationDTO.getOfficialAddress())) {
            organization.setOfficialAddress(organizationDTO.getOfficialAddress());
        }

        entityManager.persist(organization);

        return createWithOk(OrganizationsMapper.fromEntity(organization));
    }

    @Override
//    @Transactional
    public Result<OrganizationDTO> delete(Long id) {
        if (id == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ID_IS_NULL);
        }

        Organization organization = entityManager.find(Organization.class, id);

        if (organization == null) {
            return createWithStatus(NOT_FOUND);
        }

        entityManager.remove(id);

        return createWithOk(OrganizationsMapper.fromEntity(organization));
    }

    @Override
    public Result<SearchResult<Organization>> getAll(int pageNum, int pageSize, String sortType, String sortColumn,
                                                     String filterOperation, String filterField, String filterValue) {
        if (pageNum < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_NUM_MUST_BE_POSITIVE);
        }

        if (pageSize < 1) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, PAGE_SIZE_MUST_BE_POSITIVE);
        }

        if (!sortType.equalsIgnoreCase("ASC") && !sortType.equalsIgnoreCase("DESC")) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_TYPE);
        }

        if (StringUtils.isNotEmpty(sortColumn) && !validateColumns(sortColumn)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_SORT_COLUMN);
        }

        if (StringUtils.isNotEmpty(filterField) && !validateColumns(filterField)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, INCORRECT_FILTER_FIELD);
        }

        SearchCriteria.SearchOperation searchOperation = null;
        if (StringUtils.isNotEmpty(filterField)) {
            searchOperation = SearchCriteria.SearchOperation.find(filterOperation);
            if (searchOperation == null) {
                return createWithStatusAndDesc(NOT_FOUND, FILTER_OPERATION_NOT_FOUND);
            }
        }

        boolean defaultSort = sortType.equalsIgnoreCase("ASC");
        String sql = "SELECT o FROM Organization o";
        boolean filterEnable = searchOperation != null && StringUtils.isNotEmpty(filterField) && StringUtils.isNotEmpty(filterValue);
        if (filterEnable) {
            sql += " WHERE o.:filter_field :search_operation :filter_value";
        }
        sql += " ORDER BY o.:sort_column :sort_direction";

        Query query = entityManager.createQuery(sql);
        if (filterEnable) {
            query.setParameter("filter_field", filterField);
            query.setParameter("search_operation", searchOperation.getSql());
            String preparedFilterValue = searchOperation == SearchCriteria.SearchOperation.LIKE ? ("%" + filterValue + "%") : filterValue;
            query.setParameter("filter_value", preparedFilterValue);
        }
        query.setParameter("sort_column", StringUtils.isNotEmpty(sortColumn) ? sortColumn : "ID");
        query.setParameter("sort_direction", defaultSort ? "ASC" : "DESC");
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNum * pageSize);

        List<Organization> organizations = query.getResultList();
        if (CollectionUtils.isEmpty(organizations)) {
            return createWithStatusAndDesc(NOT_FOUND, ORGANIZATION_NOT_FOUND);
        }

        return createWithOk(SearchResult.makeSearchResult(organizations, organizations.size(), query.getMaxResults() / pageSize, (long) query.getMaxResults()));
    }

    @Override
    public Result<SearchResult<Organization>> findSubstring(int pageNum, int pageSize, String sortType, String sortColumn, String substring) {
        if (StringUtils.isEmpty(substring)) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, SUBSTRING_IS_NULL);
        }
        return getAll(pageNum, pageSize, sortType, sortColumn, SearchCriteria.SearchOperation.LIKE.getName(), "name", substring);
    }

    @Override
    public Result<Integer> countLowerAnnualTurnover(Float annualTurnover) {
        if (annualTurnover == null) {
            return createWithStatusAndDesc(INCORRECT_PARAMS, ANNUAL_TURNOVER_IS_NULL);
        }

        Query query = entityManager.createQuery("SELECT o.annualTurnover FROM Organization o WHERE o.annualTurnover < :annualTurnover");
        query.setParameter("annualTurnover", annualTurnover);
        List<Float> values = query.getResultList();
        if (CollectionUtils.isEmpty(values)) {
            return createWithStatus(NOT_FOUND);
        }

        return createWithOk(values.size());
    }

    @Override
    public Result<List<Float>> findUniqueAnnualTurnover() {
        Query query = entityManager.createQuery("SELECT DISTINCT o.annualTurnover FROM Organization o");
        List<Float> values = query.getResultList();
        if (CollectionUtils.isEmpty(values)) {
            return createWithStatus(NOT_FOUND);
        }

        return createWithOk(values);
    }

    private boolean validateColumns(String column) {
        return column.equalsIgnoreCase("id") || column.equalsIgnoreCase("name") ||
                column.equalsIgnoreCase("coordinateX") || column.equalsIgnoreCase("coordinateY")
                || column.equalsIgnoreCase("annualTurnover")
                || column.equalsIgnoreCase("creationDate")
                || column.equalsIgnoreCase("type") || column.equalsIgnoreCase("officialAddress");
    }
}
