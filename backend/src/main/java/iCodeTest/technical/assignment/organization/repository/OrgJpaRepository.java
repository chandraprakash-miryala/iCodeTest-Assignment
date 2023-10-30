package iCodeTest.technical.assignment.organization.repository;

import iCodeTest.technical.assignment.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgJpaRepository extends JpaRepository <Organization, String> {
    List<Organization> findByName(String name);

//    @Autowired
//    private JdbcTemplate springJdbcTemplate;
//
//    private static final String INSERT_QUERY =
//            """
//                insert into organization (id, name)
//                values (?,?);
//            """;
//
//    private static final String DELETE_QUERY =
//            """
//                delete from organization
//                where id = ?;
//            """;
//
//    public void insert(Organization organization) {
//        UUID uuId = UUID.randomUUID();
//        springJdbcTemplate.update(INSERT_QUERY, uuId.toString(), organization.getName());
//    }
//
//    public void deleteById(String id) {
//        springJdbcTemplate.update(DELETE_QUERY, id);
//    }
}

