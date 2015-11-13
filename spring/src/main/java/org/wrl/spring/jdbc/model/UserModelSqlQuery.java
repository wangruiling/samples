package org.wrl.spring.jdbc.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlQuery;

import java.sql.Types;
import java.util.Map;

public class UserModelSqlQuery extends SqlQuery<UserModel> {
    
    public UserModelSqlQuery(JdbcTemplate jdbcTemplate) {
        //1.设置数据源或JdbcTemplate
        //super.setDataSource(jdbcTemplate.getDataSource());
        super.setJdbcTemplate(jdbcTemplate);
        //2.注入sql语句
        super.setSql("select * from test where name=?");
        //3.对PreparedStatement参数描述，如命名参数、占位符参数，用于描述参数类型
        super.declareParameter(new SqlParameter(Types.VARCHAR));
        //可选的编译步骤，当执行查询方法时自动编译，对于编译的SqlQuery不能再对参数进行修改
        compile();
        //以上步骤是不可变的，必须按顺序执行
    }

    /**
     * SqlQuery：需要覆盖如下方法来定义一个RowMapper，其中parameters参数表示命名参数或占位符参数值列表，而context是由用户传入的上下文数据
     * @param parameters
     * @param context
     * @return
     */
    @Override
    protected RowMapper<UserModel> newRowMapper(Object[] parameters, Map context) {
        return new UserRowMapper();
    }
}
