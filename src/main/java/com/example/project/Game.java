package com.example.project;
import java.util.ArrayList;


public class Game{
    public static String determineWinner(Player p1, Player p2,String p1Hand, String p2Hand,ArrayList<Card> communityCards)
    {
        int p1Rank = Utility.getHandRanking(p1Hand);
        int p2Rank = Utility.getHandRanking(p2Hand);
    
    
        if (p1Rank > p2Rank) 
        {
            return "Player 1 wins!";
        } 
        if (p2Rank > p1Rank) 
        {
            return "Player 2 wins!";
        }

    
        if (p1Rank == 5 && p2Rank == 5) 
        {  
            int p1ThreeOfAKindRank = getThreeHand(p1, communityCards);
            int p2ThreeOfAKindRank = getThreeHand(p2, communityCards);
        
            if (p1ThreeOfAKindRank > p2ThreeOfAKindRank) {
                return "Player 1 wins!";
            } 
            else if (p2ThreeOfAKindRank > p1ThreeOfAKindRank) 
            {
                return "Player 2 wins!";
            }
        }

  
        int p1HighCard = getHighCard(p1, communityCards);
        int p2HighCard = getHighCard(p2, communityCards);

        if (p1HighCard > p2HighCard) 
        {
            return "Player 1 wins!";
        }    
        if (p2HighCard > p1HighCard) 
        {
            return "Player 2 wins!";
        }
        int p1HighCardinHand = getHighCardInHand(p1);
        int p2HighCardinHand = getHighCardInHand(p2);

    
        if (p1HighCardinHand > p2HighCardinHand) 
        {
            return "Player 1 wins!";
        }
        if (p2HighCardinHand > p1HighCardinHand) 
        {
            return "Player 2 wins!";
        }

        return "Tie!";
    }

    public static void play()
    { 
        Deck deck = new Deck();
        deck.shuffleDeck();

        
        Player p1 = new Player();
        Player p2 = new Player();

       
        for (int i = 0; i < 2; i++) {
            p1.addCard(deck.drawCard());
            p2.addCard(deck.drawCard());
        }

        
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            communityCards.add(deck.drawCard());
        }

        
        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);

        
        String result = determineWinner(p1, p2, p1Hand, p2Hand, communityCards);

      
        System.out.println("Player 1 Hand: " + p1Hand);
        System.out.println("Player 2 Hand: " + p2Hand);
        System.out.println(result); 
    
    }

    private static int getHighCard(Player player, ArrayList<Card> communityCards) 
    {
        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.addAll(player.getHand());
        int highCardValue = -1; 
        for (Card card : allCards) 
        {
            int cardValue = Utility.getRankValue(card.getRank());
            if (cardValue > highCardValue) 
            {
                highCardValue = cardValue; 
            }
        }
    
        return highCardValue;
    }

    private static int getThreeHand(Player player, ArrayList<Card> communityCards) 
    {
        ArrayList<Card> allCards = new ArrayList<>(communityCards);
        allCards.addAll(player.getHand());
        ArrayList<Integer> values = new ArrayList<>();

        for (Card card : allCards) 
        {
            values.add(Utility.getRankValue(card.getRank()));
        }
    
        
        for (Integer value : values) 
        {
            int count = 0;
            for (int v : values) 
            {
            if (v == value) 
            {
                count++;
                }   
            }
            if (count == 3) 
            {
                return value;
            }
        }
    
        return -1;
    }

    private static int getHighCardInHand(Player player) 
    {
        int highCardValue = -1;  
        for (Card card : player.getHand()) 
        {
            int cardValue = Utility.getRankValue(card.getRank());
            if (cardValue > highCardValue) 
            {
                highCardValue = cardValue;
            }
        }
    
        return highCardValue;  
    }
}