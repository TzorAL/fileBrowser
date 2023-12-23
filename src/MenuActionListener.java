package FileBrowser;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Implementation of ActionListener interface used to act as a response
 * for when user clicks on a File button (new Window,Exit) 
 * or an Edit button(cut,copy,paste,delete,rename,properties addition to favourites).
 * Edit actions on popMenu will also act based on this class
 */

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class MenuActionListener implements ActionListener{
    private String actionCode;
    private JFrame curFrame;
    private String cutPath;
    private static String copySrc;
    private static String cutSrc;
    private static String pasteDest;
    private static String curDir;
    private static String deleteSrc;
    private static int overwriteOption = 0;/*0: no action set yet
                                             1: overwrite file
                                             2: skip file*/

    /*Constructor used for Edit actions of the Menu Bars
      or popumenu*/    
    public MenuActionListener(String actionCode) {
        this.actionCode = actionCode;
    }

    /*Constructor used for File actions oof the Menu Bars*/
    public MenuActionListener(String actionCode,JFrame frame) {
        this.actionCode = actionCode;
        this.curFrame = frame;
    }
 
    /*Getters/Setters for specific static content from this class*/
    static void setCurDir(String newCurDir){
        curDir = newCurDir;
    }
    
    /*Setters/Getters so that other classes can retrieve static
      content from this classes*/
    static String getCurDir(){
        return curDir;
    }
    
    static void  setOverwrite(int option){
        overwriteOption = option;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(actionCode.equals("New Window")){
            System.out.println("\"New window\" option has been fired");
            javax.swing.SwingUtilities.invokeLater(() -> {
                Browser newBrowser = null;
                try {
                    newBrowser = new Browser();
                } catch (IOException ex) {
                    System.out.println("IOException in file \"MenuActionListener\"");
                    System.exit(-1);
                }
                newBrowser.setVisible(true);
            });
        }
        else if(actionCode.equals("Exit")){
            System.out.println("\"Exit\" option has been fired");
            curFrame.dispose();
        }
        else if(actionCode.equals("Cut")){
            System.out.println("Edit option \""+actionCode+"\" has been fired");
            cutSrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            System.out.println("Src: "+cutSrc);
            MenuBar.enablePaste(true);
            PopupEditMenu dummyPopupEditMenu = new PopupEditMenu();
            dummyPopupEditMenu.enablePaste(true);
            if(copySrc != null)
                copySrc = null;
        }
        else if(actionCode.equals("Copy")){
            System.out.println("Edit option \""+actionCode+"\" has been fired");
            copySrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            System.out.println("Src: "+copySrc);
            MenuBar.enablePaste(true);
            PopupEditMenu.enablePaste(true);
            if(cutSrc != null)
                cutSrc = null;
        }
        else if(actionCode.equals("Paste")){
            System.out.println("Edit option \""+actionCode+"\" has been fired");
            pasteDest = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            System.out.println("pasteDest: "+pasteDest);
            pasteToDest(pasteDest);
            MenuBar.enablePaste(false);
            PopupEditMenu.enablePaste(false);
        }
        else if(actionCode.equals("Delete")){
            System.out.println("Edit option \""+actionCode+"\" has been fired");
            deleteSrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            System.out.println("Src: "+deleteSrc);
            String str = "Are you sure you want to delete file \""+deleteSrc+"\"?";
            if(callOptionPane(deleteSrc, str) == false) return; // deletion rejected
            try {
                deleteDir(new File(deleteSrc));
            } catch (IOException ex) {
                System.out.println("IO Exception: "+ex.getMessage());
            }
        }
        else if(actionCode.equals("Rename")){
            String renameSrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            renameSrc(renameSrc);
        }
        else if(actionCode.equals("Properties")){
            String propertiesSrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            showProperties(propertiesSrc);            
        }
        else if(actionCode.equals("Add to Favourites")){
            String favouriteSrc = new String(curDir+File.separator+DirContentMouseListener.getBtnName());
            System.out.println("Add to Favourites file \""+favouriteSrc+"\"");
            if(new File(favouriteSrc).isFile()){
                MenuBar.enableFavourite(false);
            }
            else{
                MenuBar.enableFavourite(true);
                try {
                    FavouritesPanel.addFavourite(favouriteSrc);
                } catch (IOException ex) {
                    System.out.println("Error:IO Exception on \"Add2Fav\"");
                }
            }
        }
        else{
            System.out.println("Something went wrong in \""+actionCode+"\"");
        }
        JPanel dirCard = DirContentMouseListener.getDirCard();
        dirCard.removeAll();
        dirCard.add(new DirPanel(new File(curDir),dirCard));
        CardLayout cl =  (CardLayout) dirCard.getLayout();
        cl.addLayoutComponent(dirCard, curDir);
        cl.show(dirCard,curDir);  
    }
    
    static void copyFromDirToDir(File srcDir,File destDir) throws IOException{     
        
        /*Create destination directory*/
        if(srcDir.isFile()){
            System.out.println("copied \""+srcDir.getName()+"\" to \""+destDir.getAbsolutePath()+"\"");
            File newDestDir = new File(destDir.getAbsolutePath()+File.separator+srcDir.getName());
            Files.copy(srcDir.toPath(),newDestDir.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return;
        }
        String destPath = destDir.getAbsolutePath() + File.separator
                                                    + srcDir.getName();
        File newDestDir = new File(destPath);
        if(newDestDir.mkdir()){
            System.out.println("Directory \""+destPath+"\" was created");
        }
        else{
            System.out.println("Directory \""+destPath+"\" was NOT created");
        }
        
        /*Traverse source directory*/
        for(File file: srcDir.listFiles()){
            if(file.isDirectory()){//if there is a directory
                copyFromDirToDir(file,newDestDir);
            }
            else{
                System.out.println("Copy file \""+file.getName()+"\" to \""+newDestDir.getAbsolutePath()+"\"");
                String newDestPath = newDestDir.getAbsolutePath()+File.separator+file.getName();
                File destFilePath = new File(newDestPath);
                Files.copy(file.toPath(),destFilePath.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    
    static void deleteDir(File srcDir) throws IOException{

        if(srcDir.isFile() || srcDir.list().length == 0){//if directory is empty
            System.out.println("Deleted file \""+srcDir.getAbsolutePath()+"\"");
            Files.delete(srcDir.toPath());
            return;
        }
        File[] listFiles = srcDir.listFiles(); 
        if(listFiles != null){
            for(File file: listFiles){//for all files in directory
                if(file.isDirectory()){//if sub directory
                    deleteDir(file);//go to sub directory
                }
                System.out.println("Deleted file \""+file.getAbsolutePath()+"\"");
                Files.delete(file.toPath());//delete file or now empty directory
            }
        }
        if(srcDir.getAbsolutePath().equals(deleteSrc))
            Files.delete(srcDir.toPath());
    }
    
    static boolean callOptionPane(String fileName,String str){
        JDialog.setDefaultLookAndFeelDecorated(true);
       
        int response = JOptionPane.showConfirmDialog(null, str, "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.NO_OPTION) {
             System.out.println("No button clicked");
            MenuActionListener.setOverwrite(2);
            return false;
        } 
        else if(response == JOptionPane.YES_OPTION){
            System.out.println("Yes button clicked");
            MenuActionListener.setOverwrite(1);
            return true;
        }
        else if (response == JOptionPane.CLOSED_OPTION) {
          System.out.println("JOptionPane closed");
        }
        return false;
    }

    static void pasteToDest(String pasteDest){
        System.out.println("Dest: "+pasteDest);
        String str;
        boolean optionEnabled = false;
        if(copySrc != null){
            File srcDir = new File(copySrc);
            File destDir = new File(pasteDest);
            try {
                for(File f: destDir.listFiles()){
                    if(f.getName().equals(srcDir.getName())){
                        optionEnabled = true;
                        str = "\"There is already a file/directory with the name "
                        + "\" "+f.getName()+" \". Do you want to overwrite it?\"";
                        callOptionPane(f.getName(),str);
                        break;
                    }
                }
                while(overwriteOption == 0 && optionEnabled){}
                if(overwriteOption == 1 || !optionEnabled){
                    optionEnabled = false;
                    overwriteOption = 0;
                    copyFromDirToDir(srcDir,destDir);
                }
            } catch (IOException ex) {
                System.out.println("IO excpetion:"+ex.getMessage());
            }
        }
        else if(cutSrc != null){
            File srcDir = new File(cutSrc);
            File destDir = new File(pasteDest);
            try {
               for(File f: destDir.listFiles()){
                    if(f.getName().equals(srcDir.getName())){
                        optionEnabled = true;
                        str = "\"There is already a file/directory with the name "
                        + "\" "+f.getName()+" \". Do you want to overwrite it?\""; 
                        callOptionPane(f.getName(),str);
                        break;
                    }
                }
                while(overwriteOption == 0 && optionEnabled){}
                if(overwriteOption == 1 || !optionEnabled){
                    overwriteOption = 0;
                    optionEnabled = false;
                    copyFromDirToDir(srcDir,destDir);
                    deleteSrc = srcDir.getAbsolutePath();
                    deleteDir(srcDir);
                }
            } catch (IOException ex) {
                System.out.println("IO excpetion:"+ex.getMessage());
            }
        }
        else{
            System.out.println("Something bad happened in PASTE");
        }    
    }
    
    static void renameSrc(String renameSrc){
        String newName = new String();
        File newNameFile = new File(renameSrc);
        File oldNameFile = new File(renameSrc);
        System.out.println("Parent: "+ oldNameFile.getParent());

        boolean notValidName = true;
        while(notValidName){
            notValidName = false;//by default,name given will be valid
            String message = "Please enter new name for file: \""+renameSrc;
            
            String placeHolder = renameSrc.substring(renameSrc.lastIndexOf(File.separator)+1);
            newName = JOptionPane.showInputDialog(null,message,placeHolder);
            System.out.println("New name: "+newName);
            if(newName == null)//if exited pane without entering a new name
                return;
            
            newNameFile = new File(oldNameFile.getParent()+File.separator+newName);
            File renameParent = new File(oldNameFile.getParent());
            String str;
            for(File f: renameParent.listFiles()){
                if (newNameFile.exists()){
                    str = "\"There is already a file/directory with the name "
                        + "\" "+f.getName()+" \". Do you want to rename it?\"";
                    callOptionPane(renameSrc,str);
                    System.out.println("A file with that name already exists exists");
                    while(overwriteOption == 0){}
                    if(overwriteOption == 1){//if overwrite
                        try {
                            deleteDir(f);//delete problematic file
                        } catch (IOException ex) {
                            System.out.println("Error in delete in rename");
                        }
                        break;
                    }
                    else if(overwriteOption == 2){//if re-rename
                        notValidName = true;//set name to not valid
                        break;
                    }
                    else{
                        System.out.println("Error:Invalid option in Rename");
                    }
                }
            }
        }
        overwriteOption = 0;
        // Rename file (or directory)
        if (oldNameFile.renameTo(newNameFile)) {
            System.out.println("Renamed file \""+renameSrc+"\" to \""+newName+"\"");
        }
        else{
            System.out.println("Something went wrong in rename");
        }
    }
    
    static long findSize(File curDir){
        long size = 0;

        for(File file: curDir.listFiles()){
            if(file.isDirectory())
                size += findSize(file);
            size += file.length();
        }
        
        return size;
    }

    static void showProperties(String propertiesSrc){        
        File propertiesFile = new File(propertiesSrc);
        String message = "Name: "+propertiesFile.getName();
        message += "\nPath: "+propertiesFile.getAbsolutePath();

        if(propertiesFile.isDirectory())
        	message += "\nSize: "+findSize(propertiesFile)+" bytes";
        else
        	message += "\nSize: "+propertiesFile.length()+" bytes";

        JCheckBox readBox = new JCheckBox("Readable");
        if(propertiesFile.canRead()) 
            readBox.setSelected(true);
        readBox.addActionListener(new CheckBoxActionListener(propertiesFile));
        
        JCheckBox writeBox = new JCheckBox("Writable");
        if(propertiesFile.canWrite()) 
            writeBox.setSelected(true);
        writeBox.addActionListener(new CheckBoxActionListener(propertiesFile));
    
        JCheckBox execBox = new JCheckBox("Executable");
        if(propertiesFile.canExecute()) 
            execBox.setSelected(true);
        execBox.addActionListener(new CheckBoxActionListener(propertiesFile));
        
        Object[] params = {message,readBox,writeBox,execBox};
        JOptionPane.showMessageDialog(null,params,"Properties",JOptionPane.PLAIN_MESSAGE);
    }
}
