package org.jojen.wikistudy.service;


import antlr.StringUtils;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service
public class BackupService {
	@PersistenceContext
	private EntityManager em;

	@Inject
	private BlobService blobService;

	public File getBackup() {
		File dbBackupFile = generateDBBackup();
		File ret = null;
		try {
			ret = getZip();
		} catch (Exception e) {

		} finally {
			dbBackupFile.delete();
		}
		return ret;
	}

	private File generateDBBackup() {
		Connection connection = em.unwrap(SessionImpl.class).connection();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			String username = metaData.getUserName();
			String dbname = metaData.getURL().split(":")[2];
			// TODO check if psql
			File tempFile = new File(blobService.getBasePath() + File.separator + "database.sql");
			String pgHome = System.getProperty("PG_HOME");
			if(pgHome == null || pgHome.equals("")){
				pgHome = "/usr/lib/postgresql/9.1";
			}

			ProcessBuilder pb = new ProcessBuilder(pgHome + "/bin/pg_dump", "-f", tempFile.toString(), "-U", username, dbname);

			pb.redirectErrorStream(true);
			Process p = pb.start();

			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String ll;
			while ((ll = br.readLine()) != null) {
				System.out.println(ll);
			}
			return tempFile;


		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return null;
	}

	public File getZip() {
		File tempfile = null;
		try {
			byte[] buffer = new byte[1024];
			tempfile = File.createTempFile("backup.zip", null);


			String zipFile = tempfile.getAbsolutePath();
			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			List<String> fileList = generateFileList(new File(blobService.getBasePath()), new ArrayList<String>());


			for (String file : fileList) {
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(blobService.getBasePath() + File.separator + file);

				writeFile(buffer, zos, in);

				in.close();
			}

			zos.closeEntry();
			//remember close it
			zos.close();
		} catch (Exception e) {
			// bissle aufräumen wenns geht
			if (tempfile != null) {
				tempfile.delete();
			}
		}
		return tempfile;
	}

	private void writeFile(byte[] buffer, ZipOutputStream zos, FileInputStream in) throws IOException {
		int len;
		while ((len = in.read(buffer)) > 0) {
			zos.write(buffer, 0, len);
		}
	}

	private List<String> generateFileList(File node, List<String> fileList) {

		//add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename), fileList);
			}
		}
		return fileList;

	}

	private String generateZipEntry(String file) {
		return file.substring(blobService.getBasePath().length() + 1, file.length());
	}


}
