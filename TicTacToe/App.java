import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        TicTacToe ticTacToe = new TicTacToe();
    }
    public static class TicTacToe {
        int bw=650;
        int bh=650;

        JFrame frame=new  JFrame("Tic-Tac-Toe");
        JLabel textLabel=new JLabel();
        JPanel textPanel= new JPanel();
        JPanel boardPanel=new JPanel();

        JButton[][] board=new JButton[3][3];
        String playerX="X";
        String playerO="O";
        String currentPlayer=playerX;

        JButton restart = new JButton("Restart");
        JButton matchesSummary = new JButton("Matches Summary");
        boolean gameover=false;
        int turns=0,matches=0,tied=0;
        int xWins = 0;
        int oWins = 0;
        int xSum=0,oSum=0;
        TicTacToe(){
            frame.setVisible(true);
            frame.setSize(bw,bh);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            textLabel.setBackground(Color.darkGray);
            textLabel.setForeground(Color.white);
            textLabel.setFont(new Font("Consolas",Font.BOLD,50));
            textLabel.setHorizontalAlignment(JLabel.CENTER);
            textLabel.setText("TicTacToe");
            textLabel.setOpaque(true);



            textPanel.setLayout(new BorderLayout());
            textPanel.add(textLabel);
            frame.add(textPanel, BorderLayout.NORTH);

            boardPanel.setLayout(new GridLayout(3,3));
            boardPanel.setBackground(Color.DARK_GRAY);
            frame.add(boardPanel);

            for(int r=0;r<3;r++){
                for(int c=0;c<3;c++){
                    JButton tile=new JButton();
                    board[r][c]= tile;
                    boardPanel.add(tile);

                    tile.setBackground(Color.darkGray);
                    tile.setForeground(Color.white);
                    tile.setFont(new Font("Consolas",Font.BOLD,120));
                    tile.setFocusable(false);

                    tile.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e){
                            if (gameover) {
                                return;
                            }
                            JButton tile=(JButton) e.getSource();
                            if (tile.getText()=="") {
                                tile.setText(currentPlayer);
                                turns++;
                                checkWinner();
                                if (!gameover) {
                                    currentPlayer = currentPlayer==playerX? playerO:playerX;
                                    textLabel.setText(currentPlayer + "'s turn ");
                                }

                            }
                        }
                    });
                }
            }
            restart.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    restartGame();
                }
            });
            restart.setFont(new Font("Consolas", Font.BOLD, 20));
            restart.setForeground(Color.white);
            restart.setBackground(Color.DARK_GRAY);
            matchesSummary.setFont(new Font("Consolas", Font.BOLD, 20));
            matchesSummary.setForeground(Color.white);
            matchesSummary.setBackground(Color.DARK_GRAY);
            matchesSummary.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    matchesSummary();
                }
            });


            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(1,2));
            buttonPanel.add(restart);
            buttonPanel.add(matchesSummary);
            frame.add(buttonPanel, BorderLayout.SOUTH);


        }
        void restartGame() {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    board[r][c].setText("");
                    board[r][c].setBackground(Color.darkGray);
                    board[r][c].setForeground(Color.white);
                }
            }
            gameover = false;
            currentPlayer = playerX;
            turns = 0;
            textLabel.setText(currentPlayer + "'s turn");
        }

        void checkWinner(){
            for(int r=0;r<3;r++){
                if (board[r][0].getText()=="") continue;
                if (board[r][0].getText()== board[r][1].getText() &&
                        board[r][1].getText()== board[r][2].getText()){
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            if(i==r){
                                setWinner(board[i][j]);
                            }
                            else{
                                setRed(board[i][j]);
                            }
                        }
                    }
                    gameover=true;
                    matches++;
                    return;
                }
            }
            for(int c=0;c<3;c++){
                if (board[0][c].getText()=="") continue;
                if (board[0][c].getText()== board[1][c].getText() &&
                        board[1][c].getText()== board[2][c].getText()){
                    for(int i=0;i<3;i++){
                        for(int j=0;j<3;j++){
                            if(j==c){
                                setWinner(board[i][j]);
                            }
                            else{
                                setRed(board[i][j]);
                            }
                        }
                    }
                    gameover=true;
                    matches++;
                    return;
                }
            }

            if (board[0][0].getText()==board[1][1].getText() &&
                    board[1][1].getText()==board[2][2].getText() &&
                    board[0][0].getText()!="") {
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if(i==j){
                            setWinner(board[i][i]);
                        }
                        else{
                            setRed(board[i][j]);
                        }
                    }

                }
                gameover=true;
                matches++;
                return;
            }

            if (board[0][2].getText()==board[1][1].getText() &&
                    board[1][1].getText()==board[2][0].getText() &&
                    board[0][2].getText()!="") {
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if((i==0 && j==2)||(i==1 && j==1)||(i==2 && j==0)){
                            setWinner(board[i][j]);
                        }
                        else{
                            setRed(board[i][j]);
                        }
                    }
                }
                gameover=true;
                matches++;
                return;
            }
            if(turns==9){
                for(int r=0;r<3;r++){
                    for (int c = 0; c <3; c++) {
                        setTie(board[r][c]);
                    }
                }
                gameover=true;
                matches++;
            }
        }
        void matchesSummary() {
            xWins=xWins/3;
            xSum+=xWins;
            oWins=oWins/3;
            oSum+=oWins;
            JPanel messagePanel = new JPanel();
            messagePanel.setLayout(new GridLayout(4, 4));
            JLabel xWinsLabel = new JLabel("X Wins: " + xSum);
            JLabel oWinsLabel = new JLabel("O Wins: " + oSum);
            tied=matches-(xSum+oSum);
            JLabel tiedLabel = new JLabel("Tied: "+tied);
            JLabel matchespLabel = new JLabel("Total Games Played: "+ matches);
            Font customFont = new Font("Consolas", Font.BOLD, 20);
            tiedLabel.setFont(customFont);
            xWinsLabel.setFont(customFont);
            oWinsLabel.setFont(customFont);
            matchespLabel.setFont(customFont);
            messagePanel.add(xWinsLabel);
            messagePanel.add(oWinsLabel);
            messagePanel.add(tiedLabel);
            messagePanel.add(matchespLabel);

            JOptionPane.showMessageDialog(frame, messagePanel, "Summary", JOptionPane.INFORMATION_MESSAGE);    }
        void setWinner(JButton tile){
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
            if (currentPlayer.equals(playerX)) {
                xWins++;
            } else {
                oWins++;
            }
            textLabel.setText(currentPlayer + "is the winner");
        }
        void setRed(JButton tile){
            tile.setForeground(Color.red);
            tile.setBackground(Color.gray);
        }
        void setTie(JButton tile){
            tile.setForeground(Color.red);
            tile.setBackground(Color.gray);
            textLabel.setText("TIE !");
        }
    }

}
