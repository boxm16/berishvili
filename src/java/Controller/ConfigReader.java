package Controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConfigReader {

    int misconductTimeBound;

    public ConfigReader() {
        BasementController basementController = new BasementController();

        try {

            // parse XML file to build DOM
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document dom = builder.parse(new File(basementController.getBasementDirectory() + "/uploads/" + "config.xml"));

            // normalize XML structure
            dom.normalizeDocument();

            // get root element
            Element root = dom.getDocumentElement();

            // print elements
            misconductTimeBound = Integer.valueOf(root.getElementsByTagName("misconductTimeBound").item(0).getTextContent());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getMisconductTimeBound() {
        return misconductTimeBound;
    }

}
