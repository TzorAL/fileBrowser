package FileBrowser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */

public class DirContentPanel extends javax.swing.JPanel{
    private File curDir;
    private JPanel dirCard;
    
    public DirContentPanel(File curDir,JPanel dirCard) {
        super();
        
        this.curDir = curDir;
        this.dirCard = dirCard;
        setDirContent(this);
        this.setBackground(new Color(248,238,246));
        this.setLayout(new WrapLayout());
    }
    
     private void addContentBtn(ImageIcon imgIcon,JPanel contentPanel,String name){
        /*Create button based on name and image icon given by params*/
        JButton fileButton;        
        fileButton = new JButton(name,imgIcon);
        
        /*Properly customise it*/
        fileButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        fileButton.setHorizontalTextPosition(SwingConstants.CENTER);
        fileButton.setPreferredSize(new Dimension(100,100));
        fileButton.setBorderPainted(false);
        fileButton.setBackground(new Color(248,238,246));
        String fullPath = curDir.getAbsolutePath();
        
        /*Add listener as well as add it to parent panel itself*/
        fileButton.addMouseListener(new DirContentMouseListener(fullPath,dirCard));
        contentPanel.add(fileButton);
    }

    /*  Function used to sort directories and files of current folder to their
        respected lists
     */
    private void sortContent(ArrayList<File> sortedDirs, ArrayList<File> sortedFiles){
        /*  First,traverse current directory and add files/directories
            to their respected lists (unordered for the time being)
            There is a chance that listFiles method returns null
        */
        for(File file: curDir.listFiles()){
            if(file.isDirectory()){
                sortedDirs.add(file);
            }
            else{
                sortedFiles.add(file);
            }
        }
        /*Create a comparator for comparing File objects based on their name*/
        Comparator fileComparator = new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return file1.getName().compareTo(file2.getName());
            }  
        };
                
        /*Sort the two lists,based on that comparator*/
        Collections.sort(sortedDirs,fileComparator);
        Collections.sort(sortedFiles,fileComparator);
    }
    
    /*Function used to set content of current directory to the panel*/
    private void setDirContent(JPanel contentPanel){
        Icon imgIcon;               
        String fExtension;          
        String fName; int index;
        ArrayList<File> sortedDirs = new ArrayList<File>();
        ArrayList<File> sortedFiles = new ArrayList<File>();
        Iterator listIterator;
        Iterator imageIterator;
        
        /*sort contents of current directory into two lists:
            sortedDirs: list containing sub-directories stored lexicographically
            sortedFiles: list containing sub-files stored lexicographically
        */
        sortContent(sortedDirs,sortedFiles);
        
        listIterator = sortedDirs.iterator(); //iterator for directory arraylist
        File file;
        /*  Iterate dirList and add buttons to content_panel with a specific
            picture (imgIcon)*/
        while(listIterator.hasNext()){
            file = (File) listIterator.next();
            if(!file.getName().startsWith(".")){                                
                imgIcon = new ImageIcon("./icons/folder.png",file.getName());
                addContentBtn((ImageIcon) imgIcon,contentPanel,file.getName());                  
            }
        }
        
        /*  Iterate fileList and add buttons to content_panel with a specific
            picture (imgIcon)*/
        listIterator = sortedFiles.iterator();//iterator for file arraylist
        File imgDir = new File("./icons/");//directory where images are stored
        while (listIterator.hasNext()) {//for each file in fileList
            file = (File) listIterator.next();
            index = file.getName().lastIndexOf(".");
            if(index > 0 && !file.getName().startsWith(".")){//if file has .extension
                fExtension = file.getName().substring(index+1);//store that extension
                /*if there is an image which has that extension as its name,
                  then there is a match.Match is set to false by default */
                boolean matched = false;
                File[] listFiles = imgDir.listFiles();
                if(listFiles != null){
                    for(File imgFile: listFiles){
                        if(imgFile.getName().equals(fExtension+".png")){
                            matched = true; break; 
                        }
                    }    
                }
                if(matched){//if there is a match
                    /*create an ImageIcon based on that image*/
                    imgIcon = new ImageIcon("./icons/"+
                            file.getName().substring(index+1)+
                            ".png",file.getName());
                }
                else{//create default ImageIcon
                    imgIcon = new ImageIcon("./icons/question.png",file.getName());
                }
                //create button based on selected ImageIcon
                addContentBtn((ImageIcon) imgIcon,contentPanel,file.getName());
            }
        }
    }
}
