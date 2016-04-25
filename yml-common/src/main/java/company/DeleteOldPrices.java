package company;

import java.io.File;

/**
 * Created by Naya on 16.04.2016.
 */
public class DeleteOldPrices {

    public void delete(String firstFileName){
        File firstFile = new File(firstFileName);
        File dir = firstFile.getParentFile();
        if (dir!=null){
            long lastModifyDate = firstFile.lastModified();
            if (dir.list().length>0) {
                for (File file : dir.listFiles()) {
                    if (file.lastModified() < lastModifyDate)
                        file.delete();
                }
            }
        }

    }
}
