package iCodeTest.technical.assignment.organization.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrgService {

    public List<String> getFieldsNameArray(List<HashMap<String,String>> fields) {
        List<String> fieldsNameList = new ArrayList<>();
        fields.forEach(field -> fieldsNameList.add(field.get("field")));
        return fieldsNameList;
    }
}
