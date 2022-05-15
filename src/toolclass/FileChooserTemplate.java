/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolclass;

import java.io.File;
import java.util.List;

/**
 *
 * @author cimpde1
 */
public class FileChooserTemplate {
    public List<File> files;

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public FileChooserTemplate(List<File> files) {
        this.files = files;
    }

   

   public boolean checkFileNull()
    {
        List<File> result =getFiles();
        if (result != null) {
            // success
            return false;
        }
        else{
            // failure
            return true;
         }
    }
    
    
}
