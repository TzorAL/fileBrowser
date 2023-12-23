package FileBrowser;

/*
 * Extension of JPanel class and represents an object used as Search Bar for
 * File Browser
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class SearchBar extends javax.swing.JPanel{
    private static JTextField searchText;       
    private static JButton searchBtn;
    private String startDir;
    private JPanel dirCard;
    private SearchResultsPanel resultsPanel;
    private static ActionListener searchActionListener;
    private static ActionListener stopSearchActionListener;
    public static boolean stopSearch = false; //variable used to manually stop search
    
    public SearchBar() {
        super();
        this.startDir = MenuActionListener.getCurDir();
        GridBagConstraints textConstraints = new GridBagConstraints();
        this.searchText = new JTextField();
        this.searchBtn = new JButton("Search");
        this.searchText.setBackground(new Color(248,238,246));
        this.searchBtn.setBackground(new Color(178,203,220));
        this.setLayout(new GridBagLayout());
        this.setVisible(false);
        textConstraints.fill = GridBagConstraints.BOTH;
        textConstraints.weightx = 0.5;
        
        /*Listens for search button*/
        this.searchActionListener = new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                startDir = MenuActionListener.getCurDir();
                swap2Stop();
                parseSearchText(searchText.getText());
            }
        };
        
        /*Listens for stop button*/
        this.stopSearchActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSearch = true;
            }
        };
        
        this.searchBtn.addActionListener(searchActionListener);
        this.add(searchText,textConstraints);
        this.add(searchBtn);
    }
    
    /*Swap from search button to stop button at the start of search*/
    static void swap2Stop(){
        searchBtn.setText("Stop");
        /*Swap listeners*/
        searchBtn.removeActionListener(searchActionListener);
        searchBtn.addActionListener(stopSearchActionListener);
        System.out.println("Search button -> Stop Button");
    }
    
    /*  Swap from search button to stop button at the end of search or
        in case stop button was pressed*/
    static void swap2Search(){
        searchBtn.setText("Search");
        /*Swap listeners*/
        searchBtn.removeActionListener(stopSearchActionListener);
        searchBtn.addActionListener(searchActionListener);
        stopSearch = false;
        System.out.println("Stop button -> Search Button");        
    }
    
    /*  break String given as input to its funamental components
        and based on those create thread to initiate search*/
    void parseSearchText(String text){
        resultsPanel = new SearchResultsPanel();

        if(text.contains("type")){
            String text4Search = text.substring(0,text.indexOf(" "));
            System.out.println("text: "+ text4Search);
            int index = text.lastIndexOf("type:");
            index += 5; //goto start of type
            String type = text.substring(index, text.length());
            System.out.println("type: "+type);
            System.out.println("StartDir: "+startDir);
         
            new Thread(new SearchThread(resultsPanel,text4Search,type)).start();    
        }
        else{
            System.out.println("text: "+ text);
            System.out.println("type: None");
            System.out.println("StartDir: "+startDir);
            
            new Thread(new SearchThread(resultsPanel,text)).start();  
        }
    }
}
