package com.jasmine.awsiprange.controller;

import com.jasmine.awsiprange.config.RegionType;
import com.jasmine.awsiprange.service.IPService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Validated
@RestController
@RequestMapping("/ip")
public class IPController {

    private final IPService ipService;

    public IPController(IPService ipService) {
        this.ipService = ipService;
    }

    @GetMapping(value = "/list", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getIpsByRegion(@RequestParam @RegionType String region) throws Exception {

        String ips =  ipService.loadAndFindIPsByRegion(region);
        return new ResponseEntity<>(ips, HttpStatus.OK);

    }


}
