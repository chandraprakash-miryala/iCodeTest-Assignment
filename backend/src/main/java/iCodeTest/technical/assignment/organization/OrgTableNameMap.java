package iCodeTest.technical.assignment.organization;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "org_table_name_map")
public class OrgTableNameMap {
    @Id
    private String id;
    private String tableName;

    public OrgTableNameMap() {

    }

    public OrgTableNameMap(String id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "OrgTableNameMap{" +
                "id='" + id + '\'' +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
