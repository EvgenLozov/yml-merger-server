package company;

import java.io.File;

/**
 * Created by Naya on 16.04.2016.
 */
public class DeleteOldPrices {

    public void delete(String Dir){
        File dir = new File(Dir);
        if (dir.isDirectory()){
            File first = new File(Dir+"/output0.xml");
            long lastModifyDate = first.lastModified();
            if (dir.list().length>0) {
                for (File file : dir.listFiles()) {
                    if (file.lastModified() < lastModifyDate)
                        file.delete();
                }
            }
        }

    }
}
