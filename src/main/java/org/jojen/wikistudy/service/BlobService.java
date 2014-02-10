package org.jojen.wikistudy.service;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class BlobService {
	public static final int MAX_IMAGE_WIDTH = 770;
	// TODO das noch aus den properties holen
	private ServletContext context;

	@Autowired
	public BlobService(ServletContext context) {
		this.context = context;
	}

	public String getBasePath() {
		return context.getRealPath("../../../edulu/store");
	}

	public void delete(Integer id) {
		File f = getFile(id);
		if (f.exists()) {
			f.delete();
		}
	}


	public void save(CommonsMultipartFile file, Integer id) {
		File path = new File(getBasePath());
		if (!path.exists()) {
			path.mkdirs();
		}
		try {
			File f = getFile(id);
			if (file.getContentType().startsWith("image")) {
				BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
				if (bufferedImage.getWidth() > MAX_IMAGE_WIDTH) {
					ImageIO.write(Scalr.resize(bufferedImage, MAX_IMAGE_WIDTH), FilenameUtils.getExtension(file.getFileItem().getName()), f);
				} else {
					file.transferTo(f);
				}

			} else {
				file.transferTo(f);
			}


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getFile(Integer id) {
		String contentPath = getFilePath(id);
		File f = new File(getBasePath() + File.separatorChar + contentPath);
		if (!f.exists()) {
			if (!f.mkdirs()) {
				f = new File(System.getProperty("user.home") + File.separatorChar + contentPath);
				if (!f.exists()) {
					f.mkdirs();
				}
			}
		}
		return f;
	}

	public String get(Integer id) {
		return getBasePath() + File.separatorChar + getFilePath(id);
	}

	public void copy(Integer originalId, Integer id) {
		try {
			FileUtils.copyFile(new File(get(originalId)), new File(get(id)));
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private String getFilePath(Integer id) {
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

	public String readableFileSize(long size) {
		if (size <= 0) return "0";
		final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}



}
