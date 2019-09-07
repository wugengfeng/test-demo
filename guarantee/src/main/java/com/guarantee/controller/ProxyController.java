package com.guarantee.controller;

import com.guarantee.entity.Proxy;
import com.guarantee.mapper.ProxyMapper;
import com.guarantee.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by acer on 2019/9/7.
 */
@RestController
public class ProxyController {

    @Autowired
    private ProxyService proxyService;

    @Autowired
    private ProxyMapper proxyMapper;

    @GetMapping("getProxy")
    public String getProxy(String user, String passwd) {

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
                    .replace("[user]", user)
                    .replace("[passwd]", passwd);

            String chainStr = chainTemplate.replace("[id]", proxy.getId().toString());

            proxySb.append(proxyStr);
            chainSb.append(chainStr);
        }

        String str = fileTemplate.replace("[proxyList]", proxySb.toString())
                .replace("[chainList]", chainSb.toString());

        this.saveAsFileWriter(str);

        return "C:\\java\\Default.ppx 文件生成完毕";
    }


    private void saveAsFileWriter(String content) {

        String filePath = "C:\\java\\Default.ppx";

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
