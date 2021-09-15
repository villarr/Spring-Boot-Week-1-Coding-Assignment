package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.web.client.RestTemplate;
import com.promineotech.entity.Jeep;
import com.promineotech.entity.JeepModel;
import com.promineotech.jeep.controller.support.FetchJeepTestSupport;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
    "classpath:flyway/migrations/V1.0__Jeep_Schema.sql"}, 
    config = @SqlConfig(encoding = "utf-8"))
class FetchJeepTest extends FetchJeepTestSupport {

  @Test
  void testThatJeepsAreReturnedInAValidJeepModelAndTrim() {
    //given a valid model trim and URI
    JeepModel model = JeepModel.WRANGLER;
    String trim = "Sport";
      String uri = String.format("%s?model=%s&trim=%s",getBaseUri(),model,trim);
      
    //when a connection is made to the URI
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<Jeep> response = 
          restTemplate.getForEntity(uri, Jeep.class);
    
    //then a list of jeeps are returned - a status code
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}
