package org.example;

import java.util.Scanner;

public class Program {
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY = '-';
    private static final int SIZE = 3;
    private char[][] board;

    public Program() {
        board = new char[SIZE][SIZE];
        initializeBoard();
    }

    public static void main(String[] args) {
        Program game = new Program();
        game.playGame();
    }

    public void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean makeMove(int row, int col, char player) {
        if (board[row][col] == EMPTY) {
            board[row][col] = player;
            return true;
        } else {
            return false;
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isMovesLeft() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    public int evaluate() {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == PLAYER_X) return +10;
                else if (board[row][0] == PLAYER_O) return -10;
            }
        }

        for (int col = 0; col < SIZE; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == PLAYER_X) return +10;
                else if (board[0][col] == PLAYER_O) return -10;
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == PLAYER_X) return +10;
            else if (board[0][0] == PLAYER_O) return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == PLAYER_X) return +10;
            else if (board[0][2] == PLAYER_O) return -10;
        }

        return 0;
    }

    public int minimax(int depth, boolean isMax) {
        int score = evaluate();

        if (score == 10) return score;

        if (score == -10) return score;

        if (!isMovesLeft()) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_X;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j] == EMPTY) {
                        board[i][j] = PLAYER_O;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = EMPTY;
                    }
                }
            }
            return best;
        }
    }

    public Move findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_X;
                    int moveVal = minimax(0, false);
                    board[i][j] = EMPTY;

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }
        return bestMove;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Игра Крестики-Нолики: Вы играете за 'O'.");

        while (true) {
            printBoard();
            System.out.println("Ваш ход! Введите строку и столбец (0-2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (makeMove(row, col, PLAYER_O)) {
                if (evaluate() == -10) {
                    printBoard();
                    System.out.println("Вы победили!");
                    break;
                } else if (!isMovesLeft()) {
                    printBoard();
                    System.out.println("Ничья!");
                    break;
                }

                Move bestMove = findBestMove();
                makeMove(bestMove.row, bestMove.col, PLAYER_X);

                if (evaluate() == 10) {
                    printBoard();
                    System.out.println("Компьютер победил!");
                    break;
                } else if (!isMovesLeft()) {
                    printBoard();
                    System.out.println("Ничья!");
                    break;
                }
            } else {
                System.out.println("Недопустимый ход, попробуйте еще раз.");
            }
        }
    }

    static class Move {
        int row, col;

        Move(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
