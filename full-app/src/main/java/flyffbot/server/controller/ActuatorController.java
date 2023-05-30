package flyffbot.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ActuatorController {

    @RequestMapping("/server/health")
    @ResponseBody
    public ResponseEntity<Boolean> healthCheck() {
        return ResponseEntity.ok().build();
    }
}