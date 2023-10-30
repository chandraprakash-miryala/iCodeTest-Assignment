package iCodeTest.technical.assignment.organization.repository;

import iCodeTest.technical.assignment.organization.mapper.OrgDetailsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrgDetailsJdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public static Integer TABLE_NUMBER = 0;
    public String createTable(Integer noOfColumns) {
        jdbcTemplate.execute(getCreateQuery(noOfColumns));
        return "table_org_"+(TABLE_NUMBER++);
    }

    private static String getCreateQuery(Integer noOfColumns) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table table_org_").append(TABLE_NUMBER).append(" (");
        for(int i=0; i< noOfColumns; i++) {
            sb.append("field_").append(i).append(" varchar(255) not null");
            if ((i != (noOfColumns - 1))) {
                sb.append(", ");
            }
        }
        sb.append(");");
        return sb.toString();
    }

    public void addRow(String tableName, List<String> fieldsName) {
        PreparedStatementSetter pss = new ArgumentPreparedStatementSetter(fieldsName.toArray());
        jdbcTemplate.update(getInsertQuery(tableName, fieldsName.size()), pss);
    }

    public int[] addMultipleRow(String tableName, List<Object[]> dataRows) {
        return jdbcTemplate.batchUpdate(getInsertQuery(tableName, dataRows.get(0).length), dataRows);
    }

    private static String getInsertQuery(String tableName, Integer noOfColumns) {
        StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(tableName).append(" (");
        for(int i=0; i< noOfColumns; i++) {
            sb.append("field_").append(i);
            if ((i != (noOfColumns - 1))) {
                sb.append(", ");
            }
        }
        sb.append(") values(");
        for(int i=0; i< noOfColumns; i++) {
            sb.append("?");
            if ((i != (noOfColumns - 1))) {
                sb.append(", ");
            }
        }
        sb.append(");");
        return sb.toString();
    }

    public List<String> getFirstRowFromTable(String tableName) {
        return jdbcTemplate.queryForObject(getFirstRowFetchQuery(tableName), new OrgDetailsRowMapper());
    }

    private static String getFirstRowFetchQuery(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from ").append(tableName).append(" fetch first 1 rows only");
        return sb.toString();
    }

    public void deleteTable(String tableName) {
        jdbcTemplate.execute(getDeleteTableQuery(tableName));
    }

    private static String getDeleteTableQuery(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("drop table if exists ").append(tableName);
        return sb.toString();
    }

    public List<List<String>> getOrgDataRowsFromTable(String tableName) {
        return jdbcTemplate.query(getOrgDataFetchQuery(tableName), new OrgDetailsRowMapper());
    }

    private static String getOrgDataFetchQuery(String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("select * from ").append(tableName).append(" offset 1");
        return sb.toString();
    }
}
