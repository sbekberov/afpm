package spd.trello.service;

import spd.trello.domain.Card;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardService extends AbstractService<Card>{
   static List<Card> storage = new ArrayList<>();
    @Override
    public Card create(){
        Card card = new Card();
        Scanner sc = new Scanner(System.in);
        System.out.println("Input your card name : ");
        String name = sc.nextLine();
        card.setName(name);
        storage.add(card);
        return card;
    }
    @Override
    public void update(int index, Card card){
        Card card1 = storage.get(index);
        card1.setName(card.getName());
        card1.setUpdatedDate(LocalDateTime.now());
    }

}
