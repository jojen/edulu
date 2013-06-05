package org.jojen.wikistudy.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

@Service
public class BlobService {
    // TODO das noch aus den properties holen
    String blobstorePath = "/opt/wikistudy/blobstore";


    public String save(CommonsMultipartFile file) {
        File path = new File(blobstorePath);
        if (!path.exists()) {
            path.mkdirs();
        }
        try {
            String contentPath = getFilePath(System.currentTimeMillis());
            File f = new File(blobstorePath + File.separatorChar + contentPath);
            if(!f.exists()){
                f.mkdirs();
            }
            file.transferTo(f);
            return contentPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream get(String key) {
        try {
            return new FileInputStream(blobstorePath + File.separatorChar + key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    private String getFilePath(long id) {
        int bits = 10;
        int mask = (1 << bits) - 1;
        int padLen = (bits + 4) / 5;
        String pathInDir;
        for (pathInDir = (new StringBuilder()).append("Z").append(File.separatorChar).append(zeroPaddedBase32((int) (id & (long) mask), padLen)).toString(); (id >>>= bits) != 0L; pathInDir = (new StringBuilder()).append(zeroPaddedBase32((int) (id & (long) mask), padLen)).append(File.separatorChar).append(pathInDir).toString())
            ;
        return pathInDir;
    }

    private String zeroPaddedBase32(int i, int padLen) {
        String nString = Integer.toString(i, 32);
        return (new StringBuilder()).append("0000000".substring(0, padLen - nString.length())).append(nString).toString();
    }
}
