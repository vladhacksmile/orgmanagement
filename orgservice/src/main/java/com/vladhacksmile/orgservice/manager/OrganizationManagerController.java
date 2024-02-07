package com.vladhacksmile.orgservice.manager;

import com.vladhacksmile.orgservice.model.entity.Employee;
import com.vladhacksmile.orgservice.model.result.Result;
import com.vladhacksmile.orgservice.model.result.Status;
import com.vladhacksmile.orgservice.model.result.StatusDescription;
import com.vladhacksmile.orgservice.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orgmanager")
public class OrganizationManagerController {

    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/hire/{id}")
    public Result<?> hire(@PathVariable("id") Long organizationId, Employee employee) {
        if (organizationId == null) {
            return Result.createWithStatusAndDesc(Status.INCORRECT_PARAMS, StatusDescription.ORGANIZATION_ID_IS_NULL);
        }
        if (employee == null) {
            return Result.createWithStatusAndDesc(Status.INCORRECT_PARAMS, StatusDescription.EMPLOYEE_IS_NULL);
        }

        return organizationService.hire(organizationId, employee);
    }

    @PatchMapping("/acquise/{organizationId1}/{organizationId2}")
    public Result<?> acquise(@PathVariable("organizationId1") Long organizationId1, @PathVariable("organizationId2") Long organizationId2) {
        if (organizationId1 == null) {
            return Result.createWithStatusAndDesc(Status.INCORRECT_PARAMS, StatusDescription.ORGANIZATION_ID_IS_NULL);
        }

        if (organizationId2 == null) {
            return Result.createWithStatusAndDesc(Status.INCORRECT_PARAMS, StatusDescription.ORGANIZATION_ID_IS_NULL);
        }

        return organizationService.acquise(organizationId1, organizationId2);
    }
}
