package me.marangon;

import java.io.*;
import java.net.ConnectException;
import java.util.Scanner;
import javax.swing.*;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

/**
 *
 * @author marangon.pietro
 */
public class DOMParser {

    public static void main(String[] args) throws IOException {
        
        Document html = null;
        PrintWriter writer = new PrintWriter("exception.log", "UTF-8");
        Scanner url = new Scanner(System.in);
        
        System.out.print("URL:");
        try{
            html = Jsoup.connect(url.nextLine()).get();
        }catch(ConnectException e){
            System.out.println("Impossibile stabilire la connesione");
            writer.println(e.toString());
            System.exit(404);
        }
        
        html.normalise();
        
        Graph frame = new Graph(html.title());
                                
        html.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int depth) {
                if(!node.nodeName().equals("#text") && 
                   !node.nodeName().equals("#comment")){ 
                   //!node.nodeName().equals("#data")){
                        for(int i = 0;i<depth;i++)
                            System.out.print("-");
                    System.out.println(node.nodeName());
                    frame.add(node.nodeName(),depth);
                }
            }
            @Override
            public void tail(Node node, int depth) { /* Nope */ }
        });       
        
        frame.out();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 320);
        frame.toFront();
        frame.setVisible(true);
        writer.close();
    }
}
