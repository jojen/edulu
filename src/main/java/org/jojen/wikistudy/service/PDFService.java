package org.jojen.wikistudy.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import org.jojen.wikistudy.entity.*;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PDFService {
	@Inject
	BlobService blobService;

	Font h1 = new Font(Font.BOLD, 26);
	Font h2 = new Font(Font.BOLD, 24);
	Font h3 = new Font(Font.BOLD, 18);
	Font h4 = new Font(Font.BOLD, 14);

	public ByteArrayOutputStream getPdf(Course c) {
		Document document = new Document(PageSize.A4, 36, 36, 54, 54);
		ByteArrayOutputStream ret = new ByteArrayOutputStream();
		try {

			PdfWriter writer = PdfWriter.getInstance(document, ret);
			writer.setStrictImageSequence(true);
			HeaderFooter event = new HeaderFooter();
			Rectangle rect = new Rectangle(36, 54, 559, 788);
			writer.setBoxSize("art", rect);
			writer.setPageEvent(event);
			document.open();



			document.add(new Paragraph(c.getName(), h1));
			document.addTitle(c.getName());

			ColumnText.showTextAligned(writer.getDirectContent(),
											  Element.ALIGN_LEFT, new Phrase(c.getName()),
											  rect.getLeft(), rect.getTop() + 18, 0);

			for (Element e : getRichText(c.getDescription())) {
				document.add(e);
			}
			Paragraph toctitle = new Paragraph("Table of content", h3);
			toctitle.setSpacingBefore(40);
			toctitle.setSpacingAfter(15);
			document.add(toctitle);
			com.lowagie.text.List lst1 = new com.lowagie.text.List(true, 15);
			// Inhaltsverzeichnis
			for (Lesson l : c.getLessons()) {
				lst1.add(l.getName());
			}
			document.add(lst1);

			int i = 0;
			for (Lesson l : c.getLessons()) {
				i++;
				Chapter chapter = new Chapter(new Paragraph(l.getName(),h2), i);

				Section currentSection = null;
				for (Content content : l.getContent()) {
					Paragraph title = new Paragraph();

					if (content instanceof Text) {
						if (((Text) content).getName() != null || currentSection == null) {
							title =  new Paragraph(((Text) content).getName(),h3);
							title.setSpacingAfter(7);
							title.setSpacingBefore(5);
							currentSection = chapter.addSection(title);
						}

						for (Element e : getRichText(((Text) content).getText())) {
							currentSection.add(e);
						}
					}
					if (currentSection == null) {
						currentSection = chapter.addSection(title);
					}
					if (content instanceof org.jojen.wikistudy.entity.Image) {
						File file = new File(blobService.get(content.getId()));
						BufferedImage bufferedImage = ImageIO.read(file);
						com.lowagie.text.Image pdfimg = com.lowagie.text.Image.getInstance(bufferedImage, null);
						pdfimg.scaleToFit(300, 580);
						Paragraph p = new Paragraph();
						p.add(pdfimg);
						currentSection.add(p);
					}
				}
				document.add(chapter);
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		return ret;
	}


	private Collection<Element> getRichText(String html) throws IOException, DocumentException {
		java.util.List<Element> ret = new ArrayList<Element>();
		for (Object o : HTMLWorker.parseToList(new StringReader(html), null)) {
			Paragraph p = new Paragraph();
			p.add(o);
			ret.add(p);

		}
		return ret;
	}
	/** Inner class to add a header and a footer. */
	class HeaderFooter extends PdfPageEventHelper {
		/** Alternating phrase for the header. */
		Phrase header = new Phrase("Edulu course");
		/** Current page number (will be reset for every chapter). */
		int pagenumber;





		/**
		 * Increase the page number.
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onStartPage(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onStartPage(PdfWriter writer, Document document) {
			pagenumber++;
		}

		/**
		 * Adds the header and the footer.
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {

			Rectangle rect = writer.getBoxSize("art");



			ColumnText.showTextAligned(writer.getDirectContent(),
											  Element.ALIGN_RIGHT, new Phrase(String.format("%d", pagenumber)),
											  rect.getRight(), rect.getBottom()-18, 0);
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");//dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			ColumnText.showTextAligned(writer.getDirectContent(),
											  Element.ALIGN_RIGHT, new Phrase(strDate),
											  rect.getRight(), rect.getTop()+18, 0);

		}
	}

}

