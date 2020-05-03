public class Pravila {
    private PrizCombination[] prizCombinations;

    public Pravila(DeckOfCard deckOfCard){
        prizCombinations = new PrizCombination[10];
        for (int i = 0; i <10 ; i++) {
            prizCombinations[i] = new PrizCombination(0.4);
        }

        //Флэш Рояль
        prizCombinations[0].SetCardsPriz(1,deckOfCard.GetCard(8,0));
        prizCombinations[0].SetCardsPriz(2,deckOfCard.GetCard(9,0));
        prizCombinations[0].SetCardsPriz(3,deckOfCard.GetCard(10,0));
        prizCombinations[0].SetCardsPriz(4,deckOfCard.GetCard(11,0));
        prizCombinations[0].SetCardsPriz(5,deckOfCard.GetCard(12,0));
        prizCombinations[0].priz = deckOfCard.RoyalFlush;

        //Стрит Флэш
        prizCombinations[1].SetCardsPriz(1,deckOfCard.GetCard(0,0));
        prizCombinations[1].SetCardsPriz(2,deckOfCard.GetCard(1,0));
        prizCombinations[1].SetCardsPriz(3,deckOfCard.GetCard(2,0));
        prizCombinations[1].SetCardsPriz(4,deckOfCard.GetCard(3,0));
        prizCombinations[1].SetCardsPriz(5,deckOfCard.GetCard(4,0));
        prizCombinations[1].priz = deckOfCard.StraightFlush;

        //Каре
        prizCombinations[2].SetCardsPriz(1,deckOfCard.GetCard(0,0));
        prizCombinations[2].SetCardsPriz(2,deckOfCard.GetCard(0,1));
        prizCombinations[2].SetCardsPriz(3,deckOfCard.GetCard(0,2));
        prizCombinations[2].SetCardsPriz(4,deckOfCard.GetCard(0,3));
        prizCombinations[2].SetCardsPriz(5,deckOfCard.GetCard(4,0));
        prizCombinations[2].priz = deckOfCard.Four;

        //Фулл Хауз
        prizCombinations[3].SetCardsPriz(1,deckOfCard.GetCard(0,0));
        prizCombinations[3].SetCardsPriz(2,deckOfCard.GetCard(0,1));
        prizCombinations[3].SetCardsPriz(3,deckOfCard.GetCard(0,2));
        prizCombinations[3].SetCardsPriz(4,deckOfCard.GetCard(1,0));
        prizCombinations[3].SetCardsPriz(5,deckOfCard.GetCard(1,1));
        prizCombinations[3].priz = deckOfCard.FullHouse;

        //Флеш
        prizCombinations[4].SetCardsPriz(1,deckOfCard.GetCard(7,1));
        prizCombinations[4].SetCardsPriz(2,deckOfCard.GetCard(5,1));
        prizCombinations[4].SetCardsPriz(3,deckOfCard.GetCard(2,1));
        prizCombinations[4].SetCardsPriz(4,deckOfCard.GetCard(0,1));
        prizCombinations[4].SetCardsPriz(5,deckOfCard.GetCard(11,1));
        prizCombinations[4].priz = deckOfCard.Flush;

        //Стрит
        prizCombinations[5].SetCardsPriz(1,deckOfCard.GetCard(1,3));
        prizCombinations[5].SetCardsPriz(2,deckOfCard.GetCard(2,1));
        prizCombinations[5].SetCardsPriz(3,deckOfCard.GetCard(3,2));
        prizCombinations[5].SetCardsPriz(4,deckOfCard.GetCard(4,0));
        prizCombinations[5].SetCardsPriz(5,deckOfCard.GetCard(5,1));
        prizCombinations[5].priz = deckOfCard.Straight;

        //Тройка
        prizCombinations[6].SetCardsPriz(1,deckOfCard.GetCard(4,3));
        prizCombinations[6].SetCardsPriz(2,deckOfCard.GetCard(4,1));
        prizCombinations[6].SetCardsPriz(3,deckOfCard.GetCard(4,2));
        prizCombinations[6].SetCardsPriz(4,deckOfCard.GetCard(9,0));
        prizCombinations[6].SetCardsPriz(5,deckOfCard.GetCard(6,1));
        prizCombinations[6].priz = deckOfCard.Three;

        //Две пары
        prizCombinations[7].SetCardsPriz(1,deckOfCard.GetCard(4,3));
        prizCombinations[7].SetCardsPriz(2,deckOfCard.GetCard(4,1));
        prizCombinations[7].SetCardsPriz(3,deckOfCard.GetCard(5,2));
        prizCombinations[7].SetCardsPriz(4,deckOfCard.GetCard(5,0));
        prizCombinations[7].SetCardsPriz(5,deckOfCard.GetCard(6,1));
        prizCombinations[7].priz = deckOfCard.TwoPairs;

        //Пара
        prizCombinations[8].SetCardsPriz(1,deckOfCard.GetCard(4,3));
        prizCombinations[8].SetCardsPriz(2,deckOfCard.GetCard(4,1));
        prizCombinations[8].SetCardsPriz(3,deckOfCard.GetCard(7,2));
        prizCombinations[8].SetCardsPriz(4,deckOfCard.GetCard(5,0));
        prizCombinations[8].SetCardsPriz(5,deckOfCard.GetCard(6,1));
        prizCombinations[8].priz = deckOfCard.Pair;

        //
        prizCombinations[9].SetCardsPriz(1,deckOfCard.GetCard(8,3));
        prizCombinations[9].SetCardsPriz(2,deckOfCard.GetCard(4,1));
        prizCombinations[9].SetCardsPriz(3,deckOfCard.GetCard(7,2));
        prizCombinations[9].SetCardsPriz(4,deckOfCard.GetCard(3,0));
        prizCombinations[9].SetCardsPriz(5,deckOfCard.GetCard(2,1));
        prizCombinations[9].priz = deckOfCard.Blank;

    }
    public PrizCombination GetPriz(int num) {
        return prizCombinations[num];
    }
}
