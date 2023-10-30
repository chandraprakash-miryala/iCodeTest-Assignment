package iCodeTest.technical.assignment.organization.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrgDetailsRowMapper implements RowMapper<List<String>> {
    @Override
    public List<String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<String> rowMap = new ArrayList<>();
        for(int i=0; i< rs.getMetaData().getColumnCount(); i++) {
            rowMap.add(rs.getString(i+1));
        }
        return rowMap;
    }
}
