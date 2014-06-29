package com.mkr.hellgame.infrastructure.impl;

import com.mkr.hellgame.infrastructure.Configuration;
import com.mkr.hellgame.infrastructure.abstraction.ExecutorConfigurationFactory;
import com.mkr.hellgame.infrastructure.abstraction.Trigger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

public class ClassPathXmlExecutorConfigurationFactory implements ExecutorConfigurationFactory {
    private String configLocation;

    public ClassPathXmlExecutorConfigurationFactory(String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public Configuration getConfiguration() {
        Configuration result = new Configuration();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(configLocation)) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbFactory.newDocumentBuilder();
            Document doc = builder.parse(in);
            XPathFactory xpFactory = XPathFactory.newInstance();
            XPath xpath = xpFactory.newXPath();

            result.setExecutorGranularity(Integer.parseInt(xpath.evaluate("/executorConfig/granularity", doc)));

            Collection<Trigger> triggers = new ArrayList<>();
            NodeList triggerNodes = (NodeList)xpath.evaluate("/executorConfig/triggers/trigger", doc, XPathConstants.NODESET);
            for (int i=0; i<triggerNodes.getLength(); i++) {
                Node triggerNode = triggerNodes.item(i);
                if (triggerNode.getNodeType() == Node.ELEMENT_NODE) {
                    String className = triggerNode.getAttributes().getNamedItem("class").getNodeValue();
                    Class<?> type = Class.forName(className);
                    Trigger trigger = (Trigger)type.newInstance();
                    triggers.add(trigger);
                }
            }

            Node jobRunStrategyNode = (Node)xpath.evaluate("/executorConfig/jobRunStrategy", doc, XPathConstants.NODE);
            if (jobRunStrategyNode != null) {
                int asdasda = 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
