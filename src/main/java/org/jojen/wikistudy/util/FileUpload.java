package org.jojen.wikistudy.util;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * User: jochen
 * Date: 6/3/13
 * Time: 3:35 PM
 */
public class FileUpload {
    private String name;
    private CommonsMultipartFile fileData;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public CommonsMultipartFile getFileData()
    {
        return fileData;
    }

    public void setFileData(CommonsMultipartFile fileData)
    {
        this.fileData = fileData;
    }
}
