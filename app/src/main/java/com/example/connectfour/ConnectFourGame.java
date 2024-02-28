package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame {
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int DISCS = 42;
    public static final int YELLOW = 1;
    public static final int RED = 2;
    private int[][] boardGrid;
    private int currentPlayer;

    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
    }

    public void newGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
    }
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }

    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    // Function to determine if any player won or the board is full
    public boolean isGameOver() {
        return isBoardFull() || isWin();
    }

    // Function to check if board is full
    private boolean isBoardFull() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (boardGrid[i][j] == EMPTY) {
                    //game is not over
                    return false;
                }
            }
        }
        //If all the slots on the board are filled, return true
        return true;
    }

    // Function to check if one of the players won
    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    // helper functions that check if there are any 4 chips either in a horizontal, vertical or diagonal matter
    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                int discColor = boardGrid[row][col];

                if (discColor != EMPTY) {

                    if (col + 3 < COL &&
                            boardGrid[row][col + 1] == discColor &&
                            boardGrid[row][col + 2] == discColor &&
                            boardGrid[row][col + 3] == discColor) {
                        return true;
                    }
                    if (col - 3 >= 0 &&
                            boardGrid[row][col - 1] == discColor &&
                            boardGrid[row][col - 2] == discColor &&
                            boardGrid[row][col - 3] == discColor) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col < COL; col++) {
                int discColor = boardGrid[row][col];


                if (discColor != EMPTY) {
                    boolean win = true;
                    for (int i = 1; i < 4; i++) {
                        if (row + i >= ROW || row + i < 0) {
                            win = false;
                            break;
                        }

                        if (boardGrid[row + i][col] != discColor) {
                            win = false;
                            break;
                        }
                    }
                    if (win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private boolean checkDiagonal() {
        //check from top-left to bottom-right
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                int discColor = boardGrid[row][col];

                if (discColor != EMPTY) {
                    boolean win = true;
                    for (int i = 1; i < 4; i++) {
                        // check row and column indices are within bounds
                        if (row + i >= ROW || col + i >= COL || row + i < 0 || col + i < 0) {
                            win = false;
                            break;
                        }

                        // Check if next diagonal position has the same color
                        if (boardGrid[row + i][col + i] != discColor) {
                            win = false;
                            break;
                        }
                    }
                    if (win) {
                        return true;  //found
                    }
                }
            }
        }

        // Check from top-right to bottom-left
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = COL - 1; col >= 3; col--) {
                int discColor = boardGrid[row][col];


                if (discColor != EMPTY) {
                    boolean win = true;
                    for (int i = 1; i < 4; i++) {

                        if (row + i >= ROW || col - i >= COL || row + i < 0 || col - i < 0) {
                            win = false;
                            break;
                        }


                        if (boardGrid[row + i][col - i] != discColor) {
                            win = false;
                            break;
                        }
                    }
                    if (win) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void setState(String gameState){
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {

                boardGrid[row][col] = Character.getNumericValue(gameState.charAt(index));
                index++;
            }
        }
    }
    public void selectDisc(int row, int col) {
        if (col < 0 || col >= COL) {
            System.out.println("Invalid column selection.");
            return;
        }


        for (int r = ROW - 1; r >= 0; r--) {
            if (boardGrid[r][col] == EMPTY) {
                // set element in the array to the current player
                //int currentPlayer = 0;

                boardGrid[r][col] = currentPlayer;

                //switch player
                currentPlayer = (currentPlayer == BLUE) ? RED : BLUE;

                //out of loop
                break;
            }
        }
    }

}