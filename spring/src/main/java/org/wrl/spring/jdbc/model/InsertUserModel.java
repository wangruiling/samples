package org.wrl.spring.jdbc.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

import java.sql.Types;

public class InsertUserModel extends SqlUpdate {
    
    public InsertUserModel(JdbcTemplate jdbcTemplate) {
        super.setJdbcTemplate(jdbcTemplate);
        super.setSql("insert into test(name) values(?)");
        super.declareParameter(new SqlParameter(Types.VARCHAR));
        compile();
    }
}
