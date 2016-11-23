package fr.studiochardon.service;


import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFMaker {

    private String soloPicturesFolderPath;
    private String groupePicturesFolderPath;
    private String backgroundPicturesFolderPath;
    private String targetFolder;

    public PDFMaker(String soloPicturesFolderPath, String groupePicturesFolderPath, String backgroundPicturesFolderPath,String targetFolder) {
        this.backgroundPicturesFolderPath = backgroundPicturesFolderPath;
        this.soloPicturesFolderPath = soloPicturesFolderPath;
        this.groupePicturesFolderPath = groupePicturesFolderPath;
        this.targetFolder = targetFolder;
    }

    public List<File> createPdfs() throws IOException {
        List<File> resultsAsPdf = new ArrayList<File>();
        File[] files = listFilesForFolder(new File(soloPicturesFolderPath));

        for (File studentPicture : files) {

            if(studentPicture.getName().equals(".DS_Store") ==false)
            try {
                resultsAsPdf.add(createPdf(studentPicture));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        return resultsAsPdf;
    }

    public File createPdf(File studentPicture) throws DocumentException, IOException {
        if (studentPicture.isFile()) {
            System.out.println("file:" + studentPicture.getName());
            String filename = studentPicture.getName() + ".pdf";
            // step 1
            Document document = new Document();
            // step 2filename


            Rectangle pageSize=new Rectangle(602,1182);
           document.setPageSize(pageSize);

            FileOutputStream fileOutputStream = new FileOutputStream(filename);
            PdfWriter.getInstance(document, fileOutputStream);

            // step 3
            document.open();
            // step 4

            //document.add(new Paragraph("MDL du Lycée Diderot"));
            //document.add(new Paragraph("Année 2015-2016"));

            Paragraph fileNameParagraph = new Paragraph(studentPicture.getName());
            fileNameParagraph.setAlignment(Element.ALIGN_CENTER);


            addScaledImageToPdf(document, groupePicturesFolderPath);
            addImageToPdf(document, studentPicture.getAbsolutePath(), 50, 780, 200*2/3, 200);
            addImageToPdf(document, backgroundPicturesFolderPath, 240, 780, 300,200);
            document.add(fileNameParagraph);
            document.close();

            return new File(filename);
        } else {
            return null;
        }
    }

    private void addScaledImageToPdf(Document document, String imagePath) throws DocumentException, IOException {
        Image image = getImage(imagePath);

        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / image.getWidth()) * 100;

        image.scalePercent(scaler);

        document.add(image);
    }


    private void addImageToPdf(Document document, String imagePath, int posx, int posy, int scaleX,int scaleY ) throws DocumentException, IOException {
        Image image = getImage(imagePath);
        image.scaleAbsolute(scaleX, scaleY);
        image.setAbsolutePosition(posx, posy);

        document.add(image);
    }

    private Image getImage(String imagePath) throws BadElementException, IOException {
        return Image.getInstance(imagePath);
    }


    static File addTextWatermark(String text, File sourceImageFile, File destImageFile) {
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();

            // initializes necessary graphic properties
            AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);
            g2d.setComposite(alphaChannel);
            g2d.setColor(Color.BLUE);
            g2d.setFont(new Font("Arial", Font.BOLD, 64));
            FontMetrics fontMetrics = g2d.getFontMetrics();
            Rectangle2D rect = fontMetrics.getStringBounds(text, g2d);

            // calculates the coordinate where the String is painted
            int centerX = (sourceImage.getWidth() - (int) rect.getWidth()) / 2;
            int centerY = sourceImage.getHeight() / 2;

            // paints the textual watermark
            g2d.drawString(text, centerX, centerY);


            File image = new File("");
            ImageIO.write(sourceImage, "png", image);
            g2d.dispose();


            System.out.println("The tex watermark is added to the image.");
            return image;

        } catch (IOException ex) {
            System.err.println(ex);
        }
        return sourceImageFile;
    }


    public File[] listFilesForFolder(final File folder) {
        return folder.listFiles();
    }
}
