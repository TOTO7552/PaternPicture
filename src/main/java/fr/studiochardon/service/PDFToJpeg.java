package fr.studiochardon.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFToJpeg {

    private List<File> pdfs;

    private String targetFolder;

    public PDFToJpeg(List<File> pdfs, String targetFolder) {
        this.pdfs = pdfs;
        this.targetFolder= targetFolder;
    }

    public void createImageFromPdf() {
        int test = 0;
        for (File pdf : pdfs) {
            try {
                PDDocument document = PDDocument.load(pdf);
                List<PDPage> list = document.getDocumentCatalog().getAllPages();
                //int pageNumber = 1;

                for (PDPage page : list) {
                    BufferedImage image = page.convertToImage();
                    //File outputfile = new File(test++ + "_" + pageNumber + ".jpg");

                    System.out.println(targetFolder);
                    String outPutFilePath = new String(targetFolder+"/"+ pdf.getName());

                    if (outPutFilePath.length() > 0 )
                        outPutFilePath = outPutFilePath.substring(0, outPutFilePath.length()-4);


                    File outputfile = new File(outPutFilePath);

                    System.out.println("Image Created -> " + outPutFilePath);
                    ImageIO.write(image, "jpg", outputfile);
                   // pageNumber++;

                   // if(outputfile.renameTo(new File(targetFolder+ outputfile.getName()))){
                      //  System.out.println("File is moved successful!");
                    //}else{
                     //   System.out.println("File is failed to move!");
                   // }
                }
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
