package org.wrl.spring.jdbc.dao.jdbc;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.wrl.spring.jdbc.dao.IUserDao;
import org.wrl.spring.jdbc.model.UserModel;

/**
 * 定义Dao实现，此处是使用Spring JDBC实现
 * 放在dao.jdbc包里，如果有hibernate实现就放在dao.hibernate包里
 */
public class UserJdbcDaoImpl extends JdbcDaoSupport implements IUserDao {

    private static final String INSERT_SQL = "insert into test(name) values(:myName)";
    private static final String COUNT_ALL_SQL = "select count(*) from test";
    
    
    @Override
    public void save(UserModel model) {
        getJdbcTemplate().update(INSERT_SQL, new BeanPropertySqlParameterSource(model));
    }

    @Override
    public int countAll() {
        return getJdbcTemplate().queryForObject(COUNT_ALL_SQL, Integer.class);
    }
    
    
}
