package com.guarantee;

import com.guarantee.entity.Proxy;
import com.guarantee.mapper.ProxyMapper;
import com.guarantee.service.ProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author: wgf
 * @create: 2019-07-19 17:12
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GuaranteeApplication.class})
@ActiveProfiles("window")
public class ProxyTest {
    @Autowired
    private ProxyService proxyService;

    @Autowired
    private ProxyMapper proxyMapper;

    @Test
    public void test() {
        Proxy proxy = proxyService.getProxy();
        System.out.println(proxy);
    }

    @Test
    public void proxy() {
        String username = "adm567";
        String password = "123654";
        List<Proxy> proxyList = proxyMapper.selectAll();

        StringBuilder proxySb = new StringBuilder();
        StringBuilder chainSb = new StringBuilder();
        for (Proxy proxy : proxyList) {
            String arr[] = proxy.getProxyip().split(":");
            String ip = arr[0];
            String port = arr[1];

            String proxyStr = proxyTemplate.replace("[id]", proxy.getId().toString())
                    .replace("[ip]", ip)
                    .replace("[port]", port)
                    .replace("[user]", username)
                    .replace("[passwd]", password);

            String chainStr = chainTemplate.replace("[id]", proxy.getId().toString());

            proxySb.append(proxyStr);
            chainSb.append(chainStr);
        }

        String str = fileTemplate.replace("[proxyList]", proxySb.toString())
                .replace("[chainList]", chainSb.toString());

        System.out.println(str);
    }




    static String fileTemplate = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<ProxifierProfile version=\"101\" platform=\"Windows\" product_id=\"0\" product_minver=\"310\">\n" +
            "  <Options>\n" +
            "    <Resolve>\n" +
            "      <AutoModeDetection enabled=\"true\" />\n" +
            "      <ViaProxy enabled=\"false\">\n" +
            "        <TryLocalDnsFirst enabled=\"false\" />\n" +
            "      </ViaProxy>\n" +
            "      <ExclusionList>%ComputerName%; localhost; *.local</ExclusionList>\n" +
            "    </Resolve>\n" +
            "    <Encryption mode=\"disabled\" />\n" +
            "    <HttpProxiesSupport enabled=\"false\" />\n" +
            "    <HandleDirectConnections enabled=\"false\" />\n" +
            "    <ConnectionLoopDetection enabled=\"true\" />\n" +
            "    <ProcessServices enabled=\"false\" />\n" +
            "    <ProcessOtherUsers enabled=\"false\" />\n" +
            "  </Options>\n" +
            "  <ProxyList>\n" +
            "    [proxyList]\n" +
            "  </ProxyList>\n" +
            "  <ChainList>\n" +
            "    <Chain id=\"102\" type=\"load_balancing\">\n" +
            "      <Name>爬虫</Name>\n" +
            "     [chainList]\n" +
            "    </Chain>\n" +
            "  </ChainList>\n" +
            "  <RuleList>\n" +
            "    <Rule enabled=\"true\">\n" +
            "      <Name>保修</Name>\n" +
            "      <Applications>chrome.exe</Applications>\n" +
            "      <Targets>*.checkcoverage.apple.com</Targets>\n" +
            "      <Action type=\"Chain\">102</Action>\n" +
            "    </Rule>\n" +
            "    <Rule enabled=\"true\">\n" +
            "      <Name>Default</Name>\n" +
            "      <Action type=\"Direct\" />\n" +
            "    </Rule>\n" +
            "  </RuleList>\n" +
            "</ProxifierProfile>";

    static String proxyTemplate =
            "    <Proxy id=\"[id]\" type=\"SOCKS5\">\n" +
            "      <Address>[ip]</Address>\n" +
            "      <Port>[port]</Port>\n" +
            "      <Options>48</Options>\n" +
            "      <Authentication enabled=\"true\">\n" +
            "        <Username>[user]</Username>\n" +
            "        <Password>[passwd]</Password>\n" +
            "      </Authentication>\n" +
            "    </Proxy>\n";

    static String chainTemplate =
            "<Proxy enabled=\"true\">[id]</Proxy>\n";
}
