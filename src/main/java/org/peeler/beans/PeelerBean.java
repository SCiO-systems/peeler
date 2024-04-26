package org.peeler.beans;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.PropertiesComponent;
import org.peeler.models.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PeelerBean {

    private static PropertiesComponent properties;

    private static String model = "all-mpnet-base-v2";
    private CamelContext ctx;
    public void peel(Exchange exchange) throws Exception{


    }

    public void printResult(Result result) {
        System.out.println("Metadata: " + result.getMetadata());
        System.out.println("Page Count: " + result.getPageCount());
        System.out.println("Title: " + result.getTitle());
        System.out.println("Abstract: " + result.getAbstrct());
        System.out.println("Keywords: " + result.getKeywords());
        System.out.println("Chapters: " + result.getChapters());
        System.out.println("Figures: " + result.getFigures());
        System.out.println("Siever ID: " + result.getSieverID());
    }

    public List<Peel> peelResult(Exchange exchange,Result result) throws Exception{
        this.ctx = new DefaultCamelContext();

        PropertiesComponent properties = ctx.getPropertiesComponent();
        //properties.setLocation("file:env.properties");
        properties.setLocation("classpath:/env.properties");
        InputJob metadata = result.getMetadata();
        List<Peel> peeledDocument = new ArrayList<>();
        //Get the models word limit from the env file
        String modelEnv = model.toUpperCase();
        modelEnv = modelEnv.replace("-", "_");
        String modelLimit =  properties.loadPropertiesAsMap().get(modelEnv).toString();

        //Peel the abstract of the file
        Peel abstrct;
        Peel peel;
        if(textSizeChecker(result.getAbstrct(), Integer.parseInt(modelLimit)) > 1)
        {
            //System.out.println("O boi we showered");
            List<String> chunks = chunkText(result.getAbstrct(), Integer.parseInt(modelLimit));
            int part = 1;
            for (String chunk : chunks)
            {
                abstrct =  new Peel(metadata.getId(), chunk, part, "ABSTRACT", tokenCounter(chunk), "eng", model);
                part++;
                peeledDocument.add(abstrct);
            }
        }
        else
        {
            abstrct = new Peel(metadata.getId(), result.getAbstrct(), 1, "ABSTRACT", tokenCounter(result.getAbstrct()), "eng", model);
            //System.out.println("We peeled the abstract boi ");
            peeledDocument.add(abstrct);
        }

        //Peel the chapters
        for(Chapter chapter : result.getChapters())
        {
            int part = 1;
            for(Paragraph paragraph: chapter.getParagraphs())
            {
                if(textSizeChecker(paragraph.getText(), Integer.parseInt(modelLimit)) > 1)
                {
                    //System.out.println("O boi we showered");
                    List<String> chunks = chunkText(paragraph.getText(), Integer.parseInt(modelLimit));
                    for (String chunk : chunks)
                    {
                        peel =  new Peel(metadata.getId(), chunk, part, chapter.getHead(), tokenCounter(chunk), "eng", model);
                        part++;
                        peeledDocument.add(peel);
                    }
                }
                else
                {
                    peel = new Peel(metadata.getId(), paragraph.getText(), part, chapter.getHead(), tokenCounter(paragraph.getText()), "eng", model);
                    //System.out.println("We peeled the abstract boi ");
                    peeledDocument.add(peel);
                    part++;
                }
            }
        }



        System.out.println("We peeled the document: " + metadata.getId());
//        for (Peel singlePeel: peeledDocument)
//        {
//            System.out.println(singlePeel);
//        }

        return peeledDocument;
        //exchange.getIn().setBody(result, Result.class);


    }

    public int textSizeChecker(String text, int modelLimit) //Every model has a different word count, check the current text and determine if more than one parts are needed
    {
        int partsNeeded = 1;
        if(tokenCounter(text) > modelLimit)
        {
            partsNeeded = (text.length()/modelLimit) + (text.length()%modelLimit);
        }

        return partsNeeded;
    }

    public int tokenCounter(String text)
    {
        // Split the input string into tokens using space as delimiter
        String[] tokens = text.split("\\s+");
        // Count the number of tokens and return the result
        return tokens.length;
    }

    public static List<String> chunkText(String text, int wordLimit) {
        List<String> chunks = new ArrayList<>();
        if (text == null || text.isEmpty() || wordLimit <= 0) {
            return chunks;
        }

        String[] words = text.split("\\s+");
        StringBuilder chunkBuilder = new StringBuilder();
        int wordCount = 0;

        for (String word : words) {
            if (wordCount + 1 > wordLimit) {
                chunks.add(chunkBuilder.toString());
                chunkBuilder.setLength(0); // Clear StringBuilder
                wordCount = 0;
            }
            chunkBuilder.append(word).append(" ");
            wordCount++;
        }

        // Add the last chunk if there's any content left
        if (chunkBuilder.length() > 0) {
            chunks.add(chunkBuilder.toString().trim());
        }

        return chunks;
    }

}
