package FileBrowser;
/*
 * Extension JPanel class,used to implement a panel for user to access his 
 * favourite directories
 */

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class FavouritesPanel extends javax.swing.JPanel{
    private static File home;
    private static JPanel thisPanel;
    private static JPanel dirCard;
    private static JPanel favCard;
    private static  File propertiesFile;
    static JButton btnPressed;
    
    public FavouritesPanel(JPanel dirCard,JPanel favCard) throws IOException {
        super();
        this.dirCard = dirCard;
        this.favCard = favCard;
        thisPanel = this;
        home = new File(System.getProperty("user.home"));
        this.setBackground(new Color(63,63,63));
        this.setBorder(new LineBorder(new Color(35,35,35), 15));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        addFavourite(home.getAbsolutePath());
        
        boolean dirFound = false;
        for(File f: home.listFiles()){
            if(f.getName().equals(".java-file-browser")){
                dirFound = true;
                break;
            }
        }
        
        if(dirFound){
            connectToXml(home.getAbsolutePath());
            Scanner sc = new Scanner(propertiesFile);
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                if(nextLine.contains("Directory")){
                    int pathIndex = nextLine.indexOf("path=");
                    pathIndex += 6; //goto end of " path=" "
                    String path = nextLine.substring(pathIndex, nextLine.lastIndexOf("\""));
                    addFavourite(path);
                }
            }
        }

    }

    /*  Function used to check if hidden folder ".java-file-browser" already exists
        in user's home directory,and if not create it along with 
        properties.xml file
    */
    static void connectToXml(String path) throws IOException{
        boolean dirFound = false;
        for(File f: home.listFiles()){
            if(f.getName().equals(".java-file-browser")){
                dirFound = true;
                break;
            }
        }

        String propertiesString = path+File.separator+".java-file-browser"
                                 +File.separator+"properties.xml";
        propertiesFile = new File(propertiesString);
        
        if(!dirFound){
            File propertiesDir = new File(path+File.separator+".java-file-browser");
            if (propertiesDir.mkdir()){
                System.out.print("Dir \""+propertiesDir.getName()+"\" ");
                System.out.println("was created in \""+home.getAbsolutePath()+"\"");
                if(propertiesFile.createNewFile()){
                    System.out.print("File \""+propertiesFile.getName()+"\" ");
                    System.out.println("was created in \""+propertiesDir.getName()+"\"");
                    StringBuilder fileContents = new StringBuilder();
                    fileContents.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                    fileContents.append("<Favourites>\n</Favourites>");
                    try(FileWriter fWriter = new FileWriter(propertiesFile)){
                        fWriter.write(fileContents.toString());
                    }
                }
                else{
                    System.out.println("Could not create \""+propertiesFile.getName()+"\"");    
                }
            }
            else{
                System.out.println("Could not create \""+propertiesDir.getName()+"\"");
            }
        }
    }
    
    /*  Method used to add new favourite folder to the list of favourite directories
        The code needs to add new entry to properties.xml file along with adding 
        a new button to the panel and of course updating the entire panel
    */
    static void addFavourite(String path) throws IOException{            
        JPopupMenu favPopupMenu = new JPopupMenu();
        JMenuItem miRemove = new JMenuItem("Remove");
        miRemove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    /*Remove it from properties.xml*/
                    removeFromXML(new File(path).getName());
                } catch (IOException ex) {
                    System.out.println("IO Exception during removeFromXML");
                }
                
                thisPanel.remove(btnPressed);
                
                favCard.removeAll();
                try {
                    favCard.add(new FavouritesPanel(dirCard,favCard));
                } catch (IOException ex) {
                    System.out.println("IO Excetion in card swap of favourites panel");
                }
                CardLayout cl =  (CardLayout) favCard.getLayout();
                cl.addLayoutComponent(favCard, "favCard");
                cl.show(favCard,"favCard");  
                
                /*Delete from favourite bar*/
                System.out.println("Remove file \""+new File(path).getAbsolutePath()+"\""
                        + " from favourites bar");
            }
        });
        favPopupMenu.add(miRemove);

        JButton newFavourite = new JButton(new File(path).getName());
        newFavourite.addMouseListener(new FavouritesMouseListener(path,favPopupMenu,dirCard));
        newFavourite.setBackground(new Color(248,238,246));
        thisPanel.add(newFavourite);
        
        if(!path.equals(home.getAbsolutePath()))//not store home directory to XML
            addToXML(path);
    }
    
    /*Function used to add new favourite directory to properties.xml file*/
    static void addToXML(String path) throws IOException{
        connectToXml(home.getAbsolutePath());

        try {
            Scanner sc = new Scanner(propertiesFile);
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                if(nextLine.contains(path))
                    return;
            }
            /*  Append it at the end of properties.xml file
            right after last directory entry(one line before last line of file)
        */
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            /* parse existing file to DOM */
            Document document = documentBuilder.parse(propertiesFile);
            Element root = document.getDocumentElement();

            Element directory = document.createElement("Directory");
            String propertiesEnrty = "name=\""+new File(path).getName()
                                     +"\" path=\""+path+"\"";  

            directory.setTextContent(propertiesEnrty);

            System.out.println("directory:"+directory.getTextContent());
            root.appendChild(directory);
            root.appendChild(document.createTextNode("\n"));

            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();
            aTransformer.setOutputProperty("http://www.oracle.com/xml/is-standalone", "yes");
            Source src = new DOMSource(document);
            Result dest = new StreamResult(propertiesFile);
            
            aTransformer.transform(src, dest);

        } catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException in file \"FavouritesPanel\"");
            System.exit(-1);   
        } catch (SAXException ex) {
            System.out.println("SAXException in file \"FavouritesPanel\"");
            System.exit(-1);   
        } catch (TransformerConfigurationException ex) {
            System.out.println("TransformerConfigurationException in file \"FavouritesPanel\"");
            System.exit(-1);   
        } catch (TransformerException ex) {
            System.out.println("TransformerException in file \"FavouritesPanel\"");
            System.exit(-1);   
        }
    }
    
    /*  Function used to remove a favourite directory from properties.xml file
        Basically we rewrite the entire file and ignore the entry we want to remove
    */
    static void removeFromXML(String name) throws FileNotFoundException, IOException{
        Scanner sc = new Scanner(propertiesFile);
        StringBuilder refreshedXML = new StringBuilder();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(propertiesFile);
        } 
        catch (ParserConfigurationException ex) {
            System.out.println("ParserConfigurationException in file \"FavouritesPanel\"");
            System.exit(-1);   
        }
        catch (SAXException ex) {
            System.out.println("SAXException in file \"FavouritesPanel\"");
            System.exit(-1);   
        }
        
        NodeList nodes = document.getElementsByTagName("Directory");        
        for(int i = 0; i < nodes.getLength(); i++){
            Node nNode = nodes.item(i);
            
            if(nNode.getTextContent().contains((name))){
                nNode.getParentNode().removeChild(nNode);
            }
        }
        
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer;
        try {
            aTransformer = tranFactory.newTransformer();
            Source src = new DOMSource(document);
            Result dest = new StreamResult(propertiesFile);
            aTransformer.transform(src, dest);
        } catch (TransformerConfigurationException ex) {
            System.out.println("TransformerConfigurationException in file \"FavouritesPanel\"");
            System.exit(-1);   
        } catch (TransformerException ex) {
            System.out.println("TransformerException in file \"FavouritesPanel\"");
            System.exit(-1);   
        }
    }
}
