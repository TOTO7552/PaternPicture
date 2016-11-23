package fr.studiochardon.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by philippechardon on 25/10/14.
 */
public class ImageMaker {

    private String soloPicturesFolderPath;
    private String groupePicturesFolderPath;
    private String backgroundPicturesFolderPath;
    private String targetFolder;

    public ImageMaker(String soloPicturesFolderPath, String groupePicturesFolderPath, String backgroundPicturesFolderPath, String targetFolder) {
        this.soloPicturesFolderPath = soloPicturesFolderPath;
        this.groupePicturesFolderPath = groupePicturesFolderPath;
        this.backgroundPicturesFolderPath = backgroundPicturesFolderPath;
        this.targetFolder = targetFolder;
    }

    public void createImages() throws IOException {

        List<File> pdfs = new PDFMaker(soloPicturesFolderPath, groupePicturesFolderPath,backgroundPicturesFolderPath , targetFolder).createPdfs();
        new PDFToJpeg(pdfs, targetFolder).createImageFromPdf();

        removePdf(pdfs);
    }


    private void removePdf(List<File> pdfs) {
        for (File pdf : pdfs) {
            pdf.delete();
        }
    }
}
