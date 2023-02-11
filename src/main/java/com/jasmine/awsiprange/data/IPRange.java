package com.jasmine.awsiprange.data;


import java.util.List;

public record IPRange(List<IPPrefix> ipPrefixes, List<IPV6Prefix> ipv6Prefixes) {
}
