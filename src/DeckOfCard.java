import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DeckOfCard {

    private Card[] deck;

    public int CARD_2 = 0;
    public int CARD_3 = 1;
    public int CARD_4 = 2;
    public int CARD_5 = 3;
    public int CARD_6 = 4;
    public int CARD_7 = 5;
    public int CARD_8 = 6;
    public int CARD_9 = 7;
    public int CARD_10 = 8;
    public int CARD_B = 9;
    public int CARD_D = 10;
    public int CARD_K = 11;
    public int CARD_T = 12;

    private Card   rubashka;
    protected PrizCombination prizCombination;

    private  String suits[] = {"Треф","Бубей","Червей","Пик"};
    private  String faces[] = {"2","3","4","5","6","7","8","9","10","Валет","Дама","Король","Туз"};

    final int width = 73;
    final int height = 115;
    final int cols = 13;
    final int rows = 4;

    final int count = cols*rows;

    BufferedImage bigImage;

    protected int RoyalFlush    = 0;
    protected int StraightFlush = 1;
    protected int Four          = 2;
    protected int FullHouse     = 3;
    protected int Flush         = 4;
    protected int Straight      = 5;
    protected int Three         = 6;
    protected int TwoPairs      = 7;
    protected int Pair          = 8;
    protected int Blank         = 9;



    public Card GetCard(int num,int suit){

        for (int i = 0; i < deck.length ; i++) {
            if ((deck[i].getNum() == num ) && (deck[i].getSuit() == suit ))
                return deck[i];
        }
        return null;
    }

    public Card getRubashka() {
        return rubashka;
    }


    public DeckOfCard(String filename) {

        this.deck = new Card[count];
        this.bigImage = getBufferedImage(filename);

        for (int suit = 0; suit <rows ; suit++) {
            for (int num = 0; num <cols; num++) {

            BufferedImage image = bigImage.getSubimage(
                    getCoordinateX(num) ,
                    getCoordinateY(suit) ,
                    getWidth(num),
                    height );

                int  num1 = (num == 0) ? 12 : num-1;
                String name = faces[num1]+" "+ suits[suit];
                deck[suit*cols+num1] = new Card(suit,num1,name,image);
            }
        }
        BufferedImage image2 = bigImage.getSubimage(
                getCoordinateX(2) ,
                getCoordinateY(4) ,
                getWidth(2),
                height );

        this.rubashka = new Card(4,2,"Рубашка",image2);
        prizCombination = new PrizCombination(0.6);



    }
    public ImageIcon GetPrizCombination(){
        return prizCombination.GetCardsPriz();
    }
    public String GetNamePrizCombination(){
        return prizCombination.getNameCombinations();
    }

    public void ClearCardsPriz(){
        prizCombination.ClearCardsPriz();
    }


    private int getCoordinateX(int num){

        if (num == 0) return 0;

        int ret = num*width+num;
        if (num > 5) ret--;
        if (num == 12) ret --;
        return ret;
    }
    private int getWidth(int num){
        int ret = width;
        if (num == 0) ret++;
        if (num == 12) ret++;
        return ret;
    }

    private int getCoordinateY(int num){

        if (num == 0) return 0;
        return num*height;
    }

    private BufferedImage getBufferedImage (String name){
        String filename = "img/" + name + ".png";

        try {
            return ImageIO.read(getClass().getResource(filename));
        } catch (IOException e) {

        }
        return null;
    }

    public Card getRandomCard(int rnd1,int rnd2,int rnd3,int rnd4,int rnd5){

        int rnd  ;

        while (true) {

            rnd = (int) Math.ceil(count * Math.random())-1 ;


            if ((rnd != rnd1) && (rnd != rnd2) &&
                (rnd != rnd3) && (rnd != rnd4) &&  (rnd != rnd5))
            break;

        }

        return deck[rnd];
    }

    public void SetCardPriz(Card[] cards,boolean [] NumCard,int num){

        prizCombination.priz = num;
        int index = -1;

        //Ищем 1 пару
        if (num == this.Pair)
        {
            for (int i = 1; i <=4 ; i++) {
             if (NumCard[i]) { index = i; break;}
            };

            prizCombination.SetCardsPriz(1 , cards[index-1]);
            prizCombination.SetCardsPriz(2 , cards[index]);
            return;
        }

        //Ищем 2 пары
        if (num == this.TwoPairs)
        {
            for (int i = 1; i <=4 ; i++) {
                if (NumCard[i]) { index = i; break;}
            };

            prizCombination.SetCardsPriz(1 , cards[index-1]);
            prizCombination.SetCardsPriz(2 , cards[index]);

            for (int i = index+1; i <=4 ; i++) {
                if (NumCard[i]) { index = i; break;}
            };

            prizCombination.SetCardsPriz(3 , cards[index-1]);
            prizCombination.SetCardsPriz(4 , cards[index]);

            return;
        }

        //Ищем тройку
        if (num == this.Three)
        {
            for (int i = 1; i <=4 ; i++) {
                if (NumCard[i]) { index = i; break;}
            };


            prizCombination.SetCardsPriz(1 , cards[index-1]);
            prizCombination.SetCardsPriz(2 , cards[index]);
            prizCombination.SetCardsPriz(3 , cards[index+1]);
            return;
        }

        //Ищем каре
        if (num == this.Four)
        {
            for (int i = 1; i <=4 ; i++) {
                if (NumCard[i]) { index = i; break;}
            };

            prizCombination.SetCardsPriz(1 , cards[index-1]);
            prizCombination.SetCardsPriz(2 , cards[index]);
            prizCombination.SetCardsPriz(3 , cards[index+1]);
            prizCombination.SetCardsPriz(4 , cards[index+2]);
            return;
        }


        //Выводим все 5 карт
        for (int i = 0; i < cards.length; i++) {
            prizCombination.SetCardsPriz(i + 1, cards[i]);
        }


    }

}
