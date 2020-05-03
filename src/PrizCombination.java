import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class PrizCombination {
    private int count;
    public  int priz;
    private double scale;

    private Card[] cards;

    public String getCombinations(int number) {
        return combinations[number];
    }
    public String getNameCombinations() {
        if (priz == -1) return "Не определена";
        return combinations[priz];
    }

    private  String combinations[] = {"Флэш Рояль","Стрит Флэш","Каре","Фулл Хаус","Флеш","Стрит","Сет (тройка)","Две пары","Пара","Старшая карта"};


    public PrizCombination(double scale) {
        cards = new Card[5];
        count = 0;
        priz = 0;
        this.scale = scale;
    }
    public void SetCardsPriz(int num,Card card){
        if (num > count) count = num;
        cards[num-1] = card;
    }
    public ImageIcon GetCardsPriz(){

        if (count == 0) return null;
        int width = cards[0].getImageFace().getWidth();
        int height = cards[0].getImageFace().getHeight();

        BufferedImage combined = new BufferedImage(width*count, height, BufferedImage.TYPE_INT_RGB);
        Graphics g2d = combined.getGraphics();
        for (int i = 0; i < count ; i++) {
            g2d.drawImage(cards[i].getImageFace(), width*i, 0, null);
        }
        g2d.dispose();


        int width_new = (int) Math.round(count*width*scale);
        int height_new = (int) Math.round(height*scale);

        BufferedImage after = new BufferedImage(width_new, height_new, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(combined, after);
        return new ImageIcon(after);
    }
    public void ClearCardsPriz(){
        for (int i = 0; i < cards.length ; i++) {
            cards[i] = null;

        }
        count = 0;
    }

}
