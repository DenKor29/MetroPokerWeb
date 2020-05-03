import java.awt.image.BufferedImage;

public class Card implements Comparable<Card>{

    public int getNum() {
        return num;
    }

    private int num;

    public int getSuit() {
        return suit;
    }

    private int suit;

    public String getName() {
        return name;
    }

    private String name;
    private BufferedImage image;
    private int index;




    public void setTypeCompare(int typecompare) {
        this.typecompare = typecompare;
    }

    private int typecompare;


    public Card(int suit, int num, String name,BufferedImage image) {
		

        this.num = num ;
        this.suit = suit;
        this.image = image;
        this.name = name;
        this.index = this.suit*13+this.num;
        this.typecompare = 0;
    }

    public BufferedImage getImageFace() {
        return image;
    }
    public int getIndex(){
        return index;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card == false) {
            return false;
        }
        Card other = (Card) o;
        if (other.suit == this.suit) {
            if (other.num == this.num) {
                return true;
            }
        }
        return false;
    }



    @Override
    public int compareTo(Card o) {
        if (typecompare == 0) return compareToNum(o);
        if (typecompare == 1) return compareToFaceNum(o);

        return 0;
    }

    private int compareToNum(Card o) {

        if (this.num > o.num) return 1;
        if (this.num < o.num) return -1;

        return 0;
    }

    private int compareToFaceNum(Card o) {

        if (this.suit > o.suit) return 1;
        if (this.suit < o.suit) return -1;

        if (this.num > o.num) return 1;
        if (this.num < o.num) return -1;

        return 0;
    }
}
