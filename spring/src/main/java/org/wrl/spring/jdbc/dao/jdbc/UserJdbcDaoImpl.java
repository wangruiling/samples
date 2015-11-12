package org.wrl.spring.jdbc.dao.jdbc;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.wrl.spring.jdbc.dao.IUserDao;
import org.wrl.spring.jdbc.model.UserModel;


public class UserJdbcDaoImpl extends SimpleJdbcDaoSupport implements IUserDao {

    private static final String INSERT_SQL = "insert into test(name) values(:myName)";
    private static final String COUNT_ALL_SQL = "select count(*) from test";
    
    
    @Override
    public void save(UserModel model) {
        getSimpleJdbcTemplate().update(INSERT_SQL, new BeanPropertySqlParameterSource(model));
    }

    @Override
    public int countAll() {
        return getJdbcTemplate().queryForInt(COUNT_ALL_SQL);
    }
    
    
}
