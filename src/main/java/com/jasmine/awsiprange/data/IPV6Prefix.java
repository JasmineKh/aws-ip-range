package com.jasmine.awsiprange.data;


public record IPV6Prefix(String ipv6_prefix,
                         String region,
                         String service,
                         String network_border_group) {
}
