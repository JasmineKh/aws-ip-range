package com.jasmine.awsiprange.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {IPService.class})
public class IPServiceTest {


    @Autowired
    private IPService ipService;


    @Test
    void loadAndFindIPsByRegion_returnsIps_whenRegionIsValid() throws Exception {

        String ips = ipService.loadAndFindIPsByRegion("cn");

        Assertions.assertNotNull(ips);
        Assertions.assertTrue(ips.contains("\n"));

    }


    @Test
    void loadAndFindIPsByRegion_returnsIps_whenRegionIsALL() throws Exception {

        String ips = ipService.loadAndFindIPsByRegion("all");

        Assertions.assertNotNull(ips);
        Assertions.assertTrue(ips.contains("\n"));

    }

}
