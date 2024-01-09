package org.pah_monitoring.main.controllers.rest;

import org.pah_monitoring.main.controllers.mvc.TestEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
public class TestRestController {

    @PostMapping("/test/rest/processing")
    public TestEntity test(@RequestBody TestEntity entity) {
        //throw new RuntimeException("Нельзя просто, и всё тут");
        System.out.println(entity);
        return entity;
    }

}
