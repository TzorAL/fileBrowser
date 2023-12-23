package FileBrowser;

/*
 * Implementation of ActionListener interface used in Edit option "Properties"
 * When properties option is selected, popup Window is going to appearing 
 * none other but the properties of the fie/directory selected as well as the option 
 * to make file readable,writable,executable or not in the form of checkboxes
 * By default, checkboxes are selected if the file selected is 
 * readable, writable or executable respectively
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JCheckBox;

/**
 *
 * @author altzortzis <altzortzis@uth.gr>
 */
public class CheckBoxActionListener implements ActionListener{
    private File propertiesFile;
    
    public CheckBoxActionListener(File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }
    
    public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        System.out.print("File \""+propertiesFile.getName()+"\" is now ");
        if(checkBox.getText().equals("Readable")){
            if(checkBox.isSelected()){
                System.out.println("\"Readable\"");
                propertiesFile.setReadable(true);
            }
            else{
                System.out.println("\"not Readable\"");
                propertiesFile.setReadable(false);
            }
        }
        else if(checkBox.getText().equals("Writable")){
            if(checkBox.isSelected()){
                System.out.println("\"Writable\"");
                propertiesFile.setWritable(true);
            }
            else{
                System.out.println("\"not Writable\"");
                propertiesFile.setWritable(false);
            }
        }
        else if(checkBox.getText().equals("Executable")){
            if(checkBox.isSelected()){
                System.out.println("\"Executable\"");
                propertiesFile.setExecutable(true);
            }
            else{
                System.out.println("\"not Executable\"");
                propertiesFile.setExecutable(false);
            }
        }
        else{
            System.out.println("Something went wrong with checkBox Action");
        }
    }
    
}
