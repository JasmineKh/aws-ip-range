package com.jasmine.awsiprange.data;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record IPInfo(@JsonProperty("ip_prefix")
                     @JsonAlias("ipv6_prefix")
                     String ip_prefix,
                     String region,
                     String service,
                     String network_border_group) {


}
