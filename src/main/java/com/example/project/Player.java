package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Player{
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; 
    String[] suits  = Utility.getSuits();
    String[] ranks = Utility.getRanks();
    
    public Player(){
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand(){return hand;}
    public ArrayList<Card> getAllCards(){return allCards;}

    public void addCard(Card c)
    {
        hand.add(c);
    }

    public String playHand(ArrayList<Card> communityCards){      
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();

        ArrayList<Integer> rankFreq = findRankingFrequency();
        ArrayList<Integer> suitFreq = findSuitFrequency();

        int consecutive = 0;
        int previousRank = -1;
        boolean isStraight = false;
        for (Card card : allCards) 
        {
            int currentRank = Utility.getRankValue(card.getRank());
            if (previousRank != -1 && currentRank == previousRank + 1) 
            {
                consecutive++;
                if (consecutive == 4) 
                {
                    isStraight = true;
                    break;
                }
            }
            else if (previousRank != currentRank) 
            {
                consecutive = 0;
            }
            previousRank = currentRank;
        }

        boolean hasFlush = false;
        for (int count : suitFreq) {
            if (count == 5) {
                hasFlush = true;
                break;
            }
        }

        boolean hasFourOfAKind = false;
        boolean hasThreeOfAKind = false;
        boolean hasPair = false;
        int pairCount = 0;
        for (int count : rankFreq) 
        {
            if (count == 4) 
            {
                hasFourOfAKind = true;
            }
            
            if (count == 3)
            {
                hasThreeOfAKind = true;
            } 
            if (count == 2) 
            {
                hasPair = true;
                pairCount++;
            }
        }

        if (hasFlush && isStraight && allCards.get(allCards.size() - 1).getRank().equals("A")) 
        {
            return "Royal Flush";
        }

        if (hasFlush && isStraight) 
        {
            return "Straight Flush";
        }

        if (hasFourOfAKind) 
        {
            return "Four of a Kind";
        }

        if (hasThreeOfAKind && hasPair) 
        {
            return "Full House";
        }

        if (hasFlush) 
        {
            return "Flush";  
        }

        if (isStraight) 
        {
            return "Straight";
        }

        if (hasThreeOfAKind) 
        {
            return "Three of a Kind";
        }
        if (pairCount == 2) 
        {
            return "Two Pair";
        }
        if (hasPair) 
        {
            return "A Pair";
        }

        boolean isHighCard = false;
        for (int i = 0; i < allCards.size(); i++) 
        {
            
            if (hand.get(0) != allCards.get(i) && hand.get(1) != allCards.get(i)) 
            {
                isHighCard = false;
                if (getRankInt(hand.get(0)) > getRankInt(allCards.get(i)) || getRankInt(hand.get(1)) > getRankInt(allCards.get(i)))
                {
                    isHighCard = true;
                    
                }
                
            }
        }

        if (isHighCard) {
            return "High Card"; 
        }

        return "Nothing"; 
    }

    public static void main(String[] args)
    {
        Player player1 = new Player();
        Player player2 = new Player();
        
        player1.addCard(new Card("A", "♠"));
        player1.addCard(new Card("A", "♣"));
        System.out.println(player1);
        
        player2.addCard(new Card("K", "♠"));
        player2.addCard(new Card("K", "♣"));
        
        ArrayList<Card> communityCards = new ArrayList<>();
        communityCards.add(new Card("A", "♦"));
        communityCards.add(new Card("K", "♥"));
        communityCards.add(new Card("9", "♠"));
        
        String p1Result = player1.playHand(communityCards);
        String p2Result = player2.playHand(communityCards);
        
        String winner = Game.determineWinner(player1, player2, p1Result, p2Result, communityCards);
        System.out.println(winner);

        
    }

    public void sortAllCards()
    {
        for (int i = 1; i < allCards.size(); i++) 
        {
            Card key = allCards.get(i);
            int keyValue = Utility.getRankValue(key.getRank());
            int j = i - 1;
    
            while (j >= 0 && Utility.getRankValue(allCards.get(j).getRank()) > keyValue) 
            {
                allCards.set(j + 1, allCards.get(j));
                j--;
            }
            allCards.set(j + 1, key);
        }
   
       
    } 

    public ArrayList<Integer> findRankingFrequency()
    {
        ArrayList<Integer> rankFreq = new ArrayList<>(Collections.nCopies(13, 0));

        for (Card card : allCards) 
        {
            int index = Utility.getRankValue(card.getRank()) - 2; 
            rankFreq.set(index, rankFreq.get(index) + 1);
        }
        return rankFreq;
    }

    public ArrayList<Integer> findSuitFrequency()
    {
        ArrayList<Integer> suitFreq = new ArrayList<>(Collections.nCopies(4, 0));

        for (Card card : allCards) 
        {
            int suitIndex = getSuitIndex(card.getSuit());
            suitFreq.set(suitIndex, suitFreq.get(suitIndex) + 1);
        }
        return suitFreq;
    }

    private int getSuitIndex(String suit) {
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit)) return i;
        }
        return -1;
    }

    private int getRankInt(Card rank)
    {
        return Utility.getRankValue(rank.getRank());
    }


   
    @Override
    public String toString()
    {
        return hand.toString();
    }




}
