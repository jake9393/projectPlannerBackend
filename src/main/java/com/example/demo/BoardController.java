package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class BoardController {
    public BoardController() {

        Board board = new Board("Board", "Beschreibung", nextBoardId++);
        List list = new List("Liste", "Erklärung", nextListId++);
        board.lists.add(list);
        list.cards.add(new Card("Karte", "", nextCardId++,"blue"));
        boardsByID.put(board.id, board);
    }

    @GetMapping("/board/{boardId}")
    public Board getBoard(@PathVariable("boardId") int id) {

        Board board = boardsByID.get(id);

        return board;
    }

    @GetMapping("/board/{boardId}/lists/{listId}")
    public List getList(@PathVariable("boardId") int boardId, @PathVariable("listId") int listId) {

        Board board = boardsByID.get(boardId);
        var boardListMaybe = board.lists.stream().filter(l -> l.id == listId).findFirst();
        if (boardListMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        List list = boardListMaybe.get();

        return list;
    }

    @GetMapping("/board/{boardId}/lists/{listId}/cards/{cardId}")
    public Card getCard(@PathVariable("boardId") int boardId, @PathVariable("listId") int listId, @PathVariable("cardId") int cardId) {

        Board board = boardsByID.get(boardId);
        var boardListMaybe = board.lists.stream().filter(l -> l.id == listId).findFirst();
        if (boardListMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        List list = boardListMaybe.get();

        var cardMaybe = list.cards.stream().filter(c -> c.id == cardId).findFirst();
        if (cardMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        Card card = cardMaybe.get();
        return card;
    }

    @PostMapping("/board/{boardId}/lists")
    public List createList(@PathVariable("boardId") int id) {

        List list = new List("newList", "", nextListId++);

        Board board = boardsByID.get(id);
        board.lists.add(list);

        return list;
    }

    @PostMapping("/board/{boardId}/lists/{listId}/cards")
    public Card createCard(@PathVariable("boardId") int boardId, @PathVariable("listId") int listId) {
        Card card = new Card("newCard", "", nextCardId++, "blue");

        Board board = boardsByID.get(boardId);
        var boardListMaybe = board.lists.stream().filter(l -> l.id == listId).findFirst();
        if (boardListMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        List list = boardListMaybe.get();
        list.cards.add(card);

        return card;
    }

    @PutMapping("/board/{boardId}/lists/{listId}")
    public List updateList(@PathVariable("boardId") int boardId, @PathVariable("listId") int listId, @RequestBody List listData) {

        Board board = boardsByID.get(boardId);
        var boardListMaybe = board.lists.stream().filter(l -> l.id == listId).findFirst();
        if (boardListMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        List boardList = boardListMaybe.get();
        boardList.title = listData.title;
        boardList.description = listData.description;

        return boardList;
    }

    @PutMapping("/board/{boardId}/lists/{listId}/cards/{cardId}")
    public Card updateCard(@PathVariable("boardId") int boardId, @PathVariable("listId") int listId, @PathVariable("cardId") int cardId, @RequestBody Card cardData) {

        Board board = boardsByID.get(boardId);
        var boardListMaybe = board.lists.stream().filter(l -> l.id == listId).findFirst();
        if (boardListMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        List list = boardListMaybe.get();

        var cardMaybe = list.cards.stream().filter(c -> c.id == cardId).findFirst();
        if (cardMaybe.isEmpty()) {
            throw new RuntimeException();
        }
        Card card = cardMaybe.get();
        card.title = cardData.title;
        card.description = cardData.description;
        card.color = cardData.color;

        return card;
    }

    static int nextBoardId = 1;
    static int nextListId = 1;
    static int nextCardId = 1;
    static final Map<Integer, Board> boardsByID = new HashMap<>();


}
