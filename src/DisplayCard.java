import javax.swing.*;

public class DisplayCard {


    private Card card;
    private JLabel label;

    public JLabel getLabel() {
        return label;
    }

    public Card getCard() {
        return card;
    }


    public DisplayCard(Card card) {
        this.card = card;
        this.label = new JLabel(new ImageIcon(card.getImageFace()));
        label.setVerticalTextPosition(JLabel.TOP);
        label.setHorizontalTextPosition(JLabel.CENTER);
    }
    public void setCard(Card card) {
        this.card = card;
        this.label.setIcon(new ImageIcon(card.getImageFace()));
        this.label.setText(""+card);
    }
}
