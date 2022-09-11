package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    @BeforeEach
    void setUp() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        //스키마 sql스크립트 파일을 ClassPathResource에서 읽어옴
        populator.addScript(new ClassPathResource("db_schema.sql"));//테스트 수행전 테이블을 생성하기 위해서.
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    void createTest() throws SQLException {
        UserDao userDao = new UserDao();

        //userDao에 해당 정보를 DB에 저장해 달라고 요청
        userDao.create(new User("wizard", "password", "name", "email"));

        //user 정보 조회
        User user = userDao.findByUserId("wizard");
        assertThat(user).isEqualTo(new User("wizard", "password", "name", "email"));
    }
}
