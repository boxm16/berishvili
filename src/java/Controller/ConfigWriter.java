/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Michail Sitmalidis
 */
public class ConfigWriter {

    public void saveConfigFile(int misconductTimeBound) {
        try {
            BasementController basementController = new BasementController();
            // create new `Document`
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document dom = builder.newDocument();

            // first create root element
            Element root = dom.createElement("storedConstant");
            dom.appendChild(root);

            // now create child elements (name, email, phone)
            Element storedConstant = dom.createElement("misconductTimeBound");
            storedConstant.setTextContent(String.valueOf(misconductTimeBound));

            // add child nodes to root node
            root.appendChild(storedConstant);

            // write DOM to XML file
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(dom), new StreamResult(new File(basementController.getBasementDirectory() + "/uploads/" + "config.xml")));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
