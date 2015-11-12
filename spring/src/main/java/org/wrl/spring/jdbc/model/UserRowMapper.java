package org.wrl.spring.jdbc.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-11-12 15:33
 */
public class UserRowMapper implements RowMapper<UserModel> {

    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserModel model = new UserModel();
        model.setId(rs.getInt("id"));
        model.setMyName(rs.getString("name"));
        return model;
    }
}
