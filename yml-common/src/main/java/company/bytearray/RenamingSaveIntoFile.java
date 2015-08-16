package company.bytearray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by user50 on 15.08.2015.
 */
public class RenamingSaveIntoFile implements ByteArrayPostProcessor {

    String filePath;

    public RenamingSaveIntoFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void process(byte[] bytes) throws IOException {

        String tempFile = getTmpFileInSameFolder(filePath);

        try(OutputStream outputStream = new FileOutputStream(tempFile))
        {
            outputStream.write(bytes);
        }

        deleteOldFile(filePath);
        rename(tempFile, filePath);
    }

    private String getTmpFileInSameFolder(String filePath)
    {
        return new File(filePath).getParentFile()+"/tmp"+ UUID.randomUUID().toString();
    }

    private void deleteOldFile(String oldFile)
    {
        if (new File(oldFile).exists() && !new File(oldFile).delete())
            throw new RuntimeException("Unable to delete "+oldFile);
    }

    private void rename(String fileToRename, String newName )
    {
        if (!new File(fileToRename).renameTo(new File(newName )))
            throw new RuntimeException("Unable to rename "+fileToRename+" to "+newName );
    }
}
