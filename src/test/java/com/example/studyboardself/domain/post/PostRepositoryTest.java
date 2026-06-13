package com.example.studyboardself.domain.post;

import com.example.studyboardself.global.config.JpaAuditingConfig;
import com.example.studyboardself.global.config.QueryDslConfig;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({JpaAuditingConfig.class, QueryDslConfig.class})
@ActiveProfiles("test")
public class PostRepositoryTest {
}
