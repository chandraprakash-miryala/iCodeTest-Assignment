package iCodeTest.technical.assignment.organization.controller;

import iCodeTest.technical.assignment.organization.OrgTableNameMap;
import iCodeTest.technical.assignment.organization.Organization;
import iCodeTest.technical.assignment.organization.exception.OrgExistsException;
import iCodeTest.technical.assignment.organization.repository.OrgDetailsJdbcRepository;
import iCodeTest.technical.assignment.organization.repository.OrgJpaRepository;
import iCodeTest.technical.assignment.organization.repository.OrgTableNameMapJpaRepository;
import iCodeTest.technical.assignment.organization.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orgs")
public class OrgController {

    @Autowired
    private OrgJpaRepository orgJpaRepository;

    @Autowired
    private OrgDetailsJdbcRepository orgDetailsJdbcRepository;

    @Autowired
    private OrgTableNameMapJpaRepository orgTableNameMapJpaRepository;

    @Autowired
    private OrgService orgService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("")
    public List<Organization> getAllOrgs() {
        return orgJpaRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("")
    @SuppressWarnings("unchecked")
    public Organization createOrg(@RequestBody Object[] orgDetails) throws Exception {
        Organization org = new Organization("", ((HashMap<String, String>) orgDetails[0]).get("name"));
        org.setId(UUID.randomUUID().toString());
        List<HashMap<String, String>> fields = (List<HashMap<String, String>>) orgDetails[1];
        if(orgJpaRepository.findByName(org.getName()).isEmpty()) {
            String tableName = orgDetailsJdbcRepository.createTable(fields.size());
            orgDetailsJdbcRepository.addRow(tableName, orgService.getFieldsNameArray(fields));
            orgTableNameMapJpaRepository.save(new OrgTableNameMap(org.getId(), tableName));
            return orgJpaRepository.save(org);
        }
        throw new OrgExistsException("Organization already exists");
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/{orgId}/data")
    public int[] addOrgRowsData(@PathVariable String orgId, @RequestBody List<Object[]> orgDetailRows) {
        Optional<OrgTableNameMap> orgTableNameMap = orgTableNameMapJpaRepository.findById(orgId);
        if(orgTableNameMap.isPresent()) {
            return orgDetailsJdbcRepository.addMultipleRow(orgTableNameMap.get().getTableName(), orgDetailRows);
        }
        throw new RuntimeException("Organization Does Not Exists");
    }

    @ResponseStatus(
            value = HttpStatus.BAD_REQUEST,
            reason = "Organization already exists")
    @ExceptionHandler(OrgExistsException.class)
    public void handleException(OrgExistsException e) {
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/{orgId}")
    public boolean deleteOrgById(@PathVariable String orgId) {
        orgTableNameMapJpaRepository.findById(orgId).ifPresent(orgTableNameMap -> {
            orgDetailsJdbcRepository.deleteTable(orgTableNameMap.getTableName());
            orgTableNameMapJpaRepository.deleteById(orgId);
        });
        orgJpaRepository.deleteById(orgId);
        return true;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{orgId}")
    public Organization getOrgById(@PathVariable String orgId) {
        return orgJpaRepository.findById(orgId).get();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{orgId}/fields")
    public List<String> getFieldsByOrgId(@PathVariable String orgId) {
        List<String> fieldsList = new ArrayList<>();
        Optional<OrgTableNameMap> orgTableNameMap = orgTableNameMapJpaRepository.findById(orgId);
        if(orgTableNameMap.isPresent()) {
            fieldsList = orgDetailsJdbcRepository.getFirstRowFromTable(orgTableNameMap.get().getTableName());
        }
        return fieldsList;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{orgId}/data")
    public List<List<String>> getOrgDataByOrgId(@PathVariable String orgId) {
        List<List<String>> fieldsList = new ArrayList<>();
        Optional<OrgTableNameMap> orgTableNameMap = orgTableNameMapJpaRepository.findById(orgId);
        if(orgTableNameMap.isPresent()) {
            fieldsList = orgDetailsJdbcRepository.getOrgDataRowsFromTable(orgTableNameMap.get().getTableName());
        }
        return fieldsList;
    }
}
