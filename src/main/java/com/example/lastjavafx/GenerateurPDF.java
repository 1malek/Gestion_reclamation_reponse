package com.example.lastjavafx.utils;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Link;

import java.io.IOException;

public class GenerateurPDF {
    public static void creerPDF(String chemin, String clientName, String produit, double prix) throws IOException {
        // Initialiser le fichier PDF
        PdfWriter writer = new PdfWriter(chemin);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Ajouter du contenu au PDF
        document.add(new Paragraph("Bonjour " + clientName + ", voici votre facture."));
        document.add(new Paragraph("Client: " + clientName));
        document.add(new Paragraph("Produit: " + produit));
        document.add(new Paragraph("Prix: " + prix + " DT"));
        document.add(new Paragraph("Merci pour votre achat!"));

        // Ajouter un bouton interactif (lien)
        Rectangle rect = new Rectangle(100, 700, 200, 20); // Position et dimensions du bouton
        PdfLinkAnnotation linkAnnotation = new PdfLinkAnnotation(rect);
        linkAnnotation.setAction(com.itextpdf.kernel.pdf.action.PdfAction.createURI("http://www.example.com")); // Lien cliquable
        PdfCanvas canvas = new PdfCanvas(pdf.getFirstPage());
        canvas.rectangle(rect);
        canvas.stroke();
        pdf.getFirstPage().addAnnotation(linkAnnotation);

        // Ajouter un texte lié au bouton
        Link link = new Link("Cliquez ici pour visiter notre site", linkAnnotation);
        document.add(new Paragraph(link));

        // Fermer le document PDF
        document.close();
        System.out.println("PDF généré avec succès avec un bouton cliquable !");
    }
}
