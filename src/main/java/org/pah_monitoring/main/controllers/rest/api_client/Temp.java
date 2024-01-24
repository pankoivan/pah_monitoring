package org.pah_monitoring.main.controllers.rest.api_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/byb")
public class Temp {

    @GetMapping
    public List<Byb> get() {
        return IntStream.range(0, 10).mapToObj(i -> new Byb("Hello" + i)).toList();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Byb {
        String bybString;
    }

}
