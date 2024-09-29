package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {
    private Program game;

    @BeforeEach
    void setUp() {
        game = new Program();
    }

    @Test
    void testInitializeBoard() {
        char[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals('-', board[i][j], "Все клетки должны быть пустыми после инициализации.");
            }
        }
    }

    @Test
    void testMakeMoveValid() {
        boolean move = game.makeMove(0, 0, 'X');
        assertTrue(move, "Должно быть возможным сделать ход на пустую клетку.");
        assertEquals('X', game.getBoard()[0][0], "Клетка должна быть занята символом игрока.");
    }

    @Test
    void testMakeMoveInvalid() {
        game.makeMove(0, 0, 'X');
        boolean move = game.makeMove(0, 0, 'O');
        assertFalse(move, "Невозможно сделать ход на занятую клетку.");
    }

    @Test
    void testEvaluateWinForX() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        int score = game.evaluate();
        assertEquals(10, score, "X должен выиграть, и результат должен быть +10.");
    }

    @Test
    void testEvaluateWinForO() {
        game.makeMove(0, 0, 'O');
        game.makeMove(0, 1, 'O');
        game.makeMove(0, 2, 'O');
        int score = game.evaluate();
        assertEquals(-10, score, "O должен выиграть, и результат должен быть -10.");
    }

    @Test
    void testEvaluateDraw() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'O');
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 0, 'O');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'O');
        game.makeMove(2, 0, 'O');
        game.makeMove(2, 1, 'X');
        game.makeMove(2, 2, 'O');
        assertFalse(game.isMovesLeft(), "Не должно оставаться свободных ходов.");
        int score = game.evaluate();
        assertEquals(0, score, "Игра должна закончиться вничью.");
    }

    @Test
    public void testFindBestMove() {
        Program game = new Program();
        game.getBoard()[0][0] = 'X';
        game.getBoard()[0][1] = 'O';
        game.getBoard()[0][2] = 'X';
        game.getBoard()[1][0] = 'O';
        game.getBoard()[1][1] = 'X';
        game.getBoard()[1][2] = 'O';
        game.getBoard()[2][0] = '-';
        game.getBoard()[2][1] = '-';
        game.getBoard()[2][2] = '-';

        Program.Move bestMove = game.findBestMove();
        assertEquals(2, bestMove.row);
        assertEquals(0, bestMove.col);
    }

    @Test
    void testMinimaxWinForX() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'X');
        game.makeMove(0, 2, 'X');
        int result = game.minimax(0, false);
        assertEquals(10, result, "Ожидается, что минимакс вернет 10 для победы X.");
    }

    @Test
    void testMinimaxDraw() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'O');
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 0, 'O');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'O');
        game.makeMove(2, 0, 'O');
        game.makeMove(2, 1, 'X');
        game.makeMove(2, 2, 'O');
        int result = game.minimax(0, true);
        assertEquals(0, result, "Ожидается, что минимакс вернет 0 для ничьей.");
    }

    @Test
    void testIsMovesLeftTrue() {
        assertTrue(game.isMovesLeft(), "Должны оставаться доступные ходы на пустой доске.");
    }

    @Test
    void testIsMovesLeftFalse() {
        game.makeMove(0, 0, 'X');
        game.makeMove(0, 1, 'O');
        game.makeMove(0, 2, 'X');
        game.makeMove(1, 0, 'O');
        game.makeMove(1, 1, 'X');
        game.makeMove(1, 2, 'O');
        game.makeMove(2, 0, 'O');
        game.makeMove(2, 1, 'X');
        game.makeMove(2, 2, 'O');
        assertFalse(game.isMovesLeft(), "Не должно оставаться доступных ходов на заполненной доске.");
    }
}

