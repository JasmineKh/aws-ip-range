package com.jasmine.awsiprange.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jasmine.awsiprange.config.RegionEnum;
import com.jasmine.awsiprange.data.IPPrefix;
import com.jasmine.awsiprange.data.IPRange;
import com.jasmine.awsiprange.data.IPV6Prefix;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IPService {



    public IPRange loadAndFindIPsByRegion(String region) {

        IPRange ipRange = readJsonAndLoadIPs();
        if(region.equalsIgnoreCase(RegionEnum.ALL.name())) {
            return ipRange;
        } else {
            List<IPPrefix> ipPrefixes = ipRange.ipPrefixes().stream().filter(ipPrefix -> ipPrefix.region().startsWith(region.toLowerCase() + "-")).toList();
            List<IPV6Prefix> ipv6Prefixes = ipRange.ipv6Prefixes().stream().filter(ipv6Prefix -> ipv6Prefix.region().startsWith(region.toLowerCase() + "-")).toList();
            return new IPRange(ipPrefixes, ipv6Prefixes);
        }

    }


    private IPRange readJsonAndLoadIPs() {
        List<IPPrefix> ipPrefixList = new ArrayList<>();
        List<IPV6Prefix> ipv6PrefixList = new ArrayList<>();

        try {
            URL url = new URL("https://ip-ranges.amazonaws.com/ip-ranges.json");
            ObjectMapper mapper = new JsonMapper();
            JsonNode jsonNode = mapper.readTree(url);

            ipPrefixList = new ArrayList<>(Arrays.asList(mapper.treeToValue(jsonNode.get("prefixes"), IPPrefix[].class)));
            ipv6PrefixList = new ArrayList<>(Arrays.asList(mapper.treeToValue(jsonNode.get("ipv6_prefixes"), IPV6Prefix[].class)));

        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }

        return new IPRange(ipPrefixList,ipv6PrefixList);
    }

}
