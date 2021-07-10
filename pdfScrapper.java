package sample;

import java.io.File;
import java.io.IOException;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class pdfScrapper {

    public static void main(String[] args) throws IOException {


        File file = new File("C:\\Users\\harsh\\Downloads\\Test file.pdf");
        FileInputStream fileStream = new FileInputStream(file);
        InputStreamReader input = new InputStreamReader(fileStream);
        BufferedReader reader = new BufferedReader(input);
        PDDocument document = PDDocument.load(file);


        PDFTextStripper pdfStripper = new PDFTextStripper();

        String text = pdfStripper.getText(document);
        System.out.println(text);

        String line;
        // Finding out the number of sentences for storing it in the string array
        // Initializing counters
        int sentenceCount = 0;

        // Reading line by line from the
        // file until a null is returned
        while((line = reader.readLine()) != null)
        {
            if(line.equals(""))
            {
            } else {

                // \\s+ is the space delimiter in java

                // [!?.:]+ is the sentence delimiter in java
                String[] sentenceList = line.split("[!?.:]+");

                sentenceCount += sentenceList.length;
            }
        }
        System.out.println("Total number of sentences = " + sentenceCount);
        document.close();
    }
}