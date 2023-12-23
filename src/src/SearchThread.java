package FileBrowser;

/*
 *  Class that implements Runnable interface for concurrent search
 *  along with other File Browser actions
 */

import java.awt.CardLayout;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */

public class SearchThread implements Runnable{
    private String startDir;                //  starting directory for search
    private SearchResultsPanel resultsPanel;//  panel which contains list of buttons/search results
    private String text;
    private String type;
    private JPanel dirCard;                 // panel to properly swap with results panel
    
    public SearchThread(SearchResultsPanel resultsPanel, String text,String type) {
        this.startDir = MenuActionListener.getCurDir();
        this.resultsPanel = resultsPanel;
        this.text = text;
        this.type = type;
    }

    public SearchThread(SearchResultsPanel resultsPanel, String text) {
        this.startDir = MenuActionListener.getCurDir();
        this.resultsPanel = resultsPanel;
        this.text = text;
        this.type = null;
    }
    
    /*Method called when thread starts*/
    public void run(){
        if(type == null){
            search(text, new File(startDir)); 
            SearchBar.swap2Search();
            dirCard = DirContentMouseListener.getDirCard();
            dirCard.removeAll();
            dirCard.add(new JScrollPane(resultsPanel));
            CardLayout cl =  (CardLayout) dirCard.getLayout();
            cl.addLayoutComponent(dirCard, "321");
            cl.show(dirCard,"321"); 
        }
        else{
            parameterisedSearch(text, type, new File(startDir));
            SearchBar.swap2Search();
            dirCard = DirContentMouseListener.getDirCard();
            dirCard.removeAll();
            dirCard.add(new JScrollPane(resultsPanel));
            CardLayout cl =  (CardLayout) dirCard.getLayout();
            cl.addLayoutComponent(dirCard, "642");
            cl.show(dirCard,"642");  
        }
    }
    
    /*Search functions used for searching a specific string/fileName in the file
      system with or without setting a specific type of file for our search*/
    void search(String text,File startDir){
//        listFiles method sometimes returns null

        File[] listFiles = startDir.listFiles();
        if(listFiles != null){
            for(File file: listFiles){
                if(SearchBar.stopSearch) {
                    return;
                }
                /*  convert both strings to lower case(could be upper case)
                    in order to have a case-insensitive comparation*/
                if(file.getName().toLowerCase().contains(text.toLowerCase())){
                    if( !SearchBar.stopSearch ){
                        System.out.println("Found: "+file.getAbsolutePath());
                        resultsPanel.addSearchResult(file.getAbsolutePath());                        
                    }
                }
                if(file.isDirectory() && !SearchBar.stopSearch){
                    search(text, file);
                }
            }
        }
    }
    
    void parameterisedSearch(String text,String type, File startDir){
//        listFiles method sometimes returns null

        File[] listFiles = startDir.listFiles(); 
        if(listFiles != null){
            for(File file: listFiles){
                if(SearchBar.stopSearch) {
                    return;
                }
                if(file.isDirectory() && type.equals("dir") && !SearchBar.stopSearch){
                    if(file.getName().toLowerCase().contains(text.toLowerCase())){
                        System.out.println("Found: "+file.getAbsolutePath());
                        resultsPanel.addSearchResult(file.getAbsolutePath());
                    }
                }

                if(!type.equals("dir") && !SearchBar.stopSearch){
                    if(file.getName().toLowerCase().contains(text.toLowerCase())){
                        String fileExtension = file.getName().substring(file.getName().lastIndexOf(".")+1);
                        if(fileExtension.equals(type)){
                            resultsPanel.addSearchResult(file.getAbsolutePath());
                            System.out.println("Found: "+file.getAbsolutePath());
                        }
                    }
                }

                if(file.isDirectory() && !SearchBar.stopSearch){
                    parameterisedSearch(text, type, file);
                }
            }
        }
    }
}
