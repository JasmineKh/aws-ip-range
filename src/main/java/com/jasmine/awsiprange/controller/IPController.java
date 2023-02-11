package com.jasmine.awsiprange.controller;

import com.jasmine.awsiprange.config.RegionType;
import com.jasmine.awsiprange.data.IPRange;
import com.jasmine.awsiprange.service.IPService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping("/ips")
public class IPController {

    private final IPService ipService;

    public IPController(IPService ipService) {
        this.ipService = ipService;
    }

    @GetMapping("/list")
    public ResponseEntity<IPRange> list(@RequestParam @RegionType String region) {

        IPRange ipRange = ipService.loadAndFindIPsByRegion(region);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(ipRange, responseHeaders, HttpStatus.OK);
    }


}
