package org.wrl.spring.tx.dao.jdbc;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.wrl.spring.tx.dao.IUserDao;
import org.wrl.spring.tx.model.UserModel;


public class UserJdbcDaoImpl extends NamedParameterJdbcDaoSupport implements IUserDao {

    private final String INSERT_SQL = "insert into user(name) values(:name)";
    private final String COUNT_ALL_SQL = "select count(*) from user";

    
    @Override
    public void save(UserModel user) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new BeanPropertySqlParameterSource(user);
        getNamedParameterJdbcTemplate().update(INSERT_SQL, paramSource, generatedKeyHolder);
        user.setId(generatedKeyHolder.getKey().intValue());
    }

    @Override
    public int countAll() {
       return getJdbcTemplate().queryForObject(COUNT_ALL_SQL, Integer.class);
    }
    
}
