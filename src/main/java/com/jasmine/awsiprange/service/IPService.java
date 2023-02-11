package com.jasmine.awsiprange.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jasmine.awsiprange.config.RegionEnum;
import com.jasmine.awsiprange.data.IPInfo;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.*;

@Service
public class IPService {



    public String loadAndFindIPsByRegion(String region) throws Exception {
        Map<String, List<IPInfo>> ipInfoMap = readJsonAndLoadIPs();
        List<IPInfo> ipInfoV4s = ipInfoMap.get("ipv4");
        List<IPInfo> ipInfoV6s = ipInfoMap.get("ipv6");
        if(region.equalsIgnoreCase(RegionEnum.ALL.name())) {
             return prepareIps(ipInfoV4s, ipInfoV6s);
        } else {
            List<IPInfo> ipInfoV4ByRegionList = ipInfoV4s.stream().filter(IPInfo -> IPInfo.region().startsWith(region.toLowerCase() + "-")).toList();
            List<IPInfo> ipInfoV6ByRegionList = ipInfoV6s.stream().filter(ipv6Prefix -> ipv6Prefix.region().startsWith(region.toLowerCase() + "-")).toList();
            return prepareIps(ipInfoV4ByRegionList,ipInfoV6ByRegionList);
        }
    }


    private String prepareIps(List<IPInfo> ipv4Infos, List<IPInfo> ipv6Infos){
        String ips = "";
        String ip4s = "";
        String ip6s = "";

        for(IPInfo ipInfoV4 : ipv4Infos){
            ip4s = ip4s.concat(ipInfoV4.ip_prefix()+"\n");
        }

        for(IPInfo ipInfoV6 : ipv6Infos){
            ip6s = ip6s.concat(ipInfoV6.ip_prefix()+"\n");
        }

        ips = "#".repeat(8) + "ipv4s" + "#".repeat(8) + "\n" + ip4s + "\n" + "#".repeat(8) + "ipv6s" + "#".repeat(8) + "\n" + ip6s;
        return ips;
    }

    private Map<String, List<IPInfo>> readJsonAndLoadIPs() throws Exception {
        final Map<String , List<IPInfo>> ipInfoMap = new HashMap<>();

            URL url = new URL("https://ip-ranges.amazonaws.com/ip-ranges.json");
            ObjectMapper mapper = new JsonMapper();
            JsonNode jsonNode = mapper.readTree(url);

            ipInfoMap.put("ipv4", new ArrayList<>(Arrays.asList(mapper.treeToValue(jsonNode.get("prefixes"), IPInfo[].class))));
            ipInfoMap.put("ipv6", new ArrayList<>(Arrays.asList(mapper.treeToValue(jsonNode.get("ipv6_prefixes"), IPInfo[].class))));


        return ipInfoMap;
    }

}
