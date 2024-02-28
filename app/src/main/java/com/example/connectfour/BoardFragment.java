package com.example.connectfour;

import static com.example.connectfour.R.*;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

/*board class that redirects to BoardFragment activity when clicked*/
public class BoardFragment extends Fragment {

    final String GAME_STATE = "gameState";
    ConnectFourGame mGame;
    GridLayout mGrid;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gLayout = inflater.inflate(layout.fragment_board, container, false);

        mGrid = gLayout.findViewById(id.grid_layout);

        mGame = new ConnectFourGame();

        if(savedInstanceState == null){
            startGame();
        }
        else{
            String gameState = savedInstanceState.getString(GAME_STATE);
            mGame.setState(GAME_STATE);
            setDisc();
        }

        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button button = (Button) mGrid.getChildAt(i);
            button.setOnClickListener(this::onButtonClick);
        }

        return gLayout;
    }

    private void onButtonClick(View view) {
        // Find the button's row and col
        // Declare local variable, data type integer i.e buttonIndex....
        int buttonIndex = mGrid.indexOfChild(view);
        int row = buttonIndex / ConnectFourGame.ROW;
        int col = buttonIndex % ConnectFourGame.COL;
        // call method selectDisc

        mGame.selectDisc(row, col);
        setDisc();
        // congratulate the player if they won
        if (mGame.isGameOver()) {
            Toast.makeText(this.requireActivity(), "Congratulations! You won!", Toast.LENGTH_SHORT).show();
            // Call method newGame in classConnectFourGame
            // delay for 5 seconds to show congratulations
            new Handler(Looper.getMainLooper()).postDelayed(() ->{

                mGame.newGame();
                setDisc();
            }, 5000);

        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(GAME_STATE, mGame.getState());
    }

    // Method startGame
    public void startGame() {
        mGame.newGame();
        setDisc();

    }

    public void setDisc() {
        for (int i = 0; i < mGrid.getChildCount(); i++) {
            Button gridButton = (Button) mGrid.getChildAt(i);
            // Find the button's row and col that is being selected by the player
            int row = i / ConnectFourGame.COL;
            int col = i % ConnectFourGame.COL;

            // Instantiate Drawable objects for the discs
            Drawable whiteDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_white);
            Drawable blueDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_blue);
            Drawable redDisc = ContextCompat.getDrawable(getActivity(), R.drawable.circle_red);

            // wrapped drawables
            Drawable wrappedWhiteDisc = DrawableCompat.wrap(whiteDisc);
            Drawable wrappedBlueDisc = DrawableCompat.wrap(blueDisc);
            Drawable wrappedRedDisc = DrawableCompat.wrap(redDisc);

            // Get the value of the element stored in the current row and column...
            int discColor = mGame.getDisc(row, col);
            Drawable discDrawable;
            switch (discColor) {
                case ConnectFourGame.BLUE:
                    discDrawable = wrappedBlueDisc; // Use the wrapped Drawable for blue disc
                    break;
                case ConnectFourGame.RED:
                    discDrawable = wrappedRedDisc; // Use the wrapped Drawable for red disc
                    break;
                case ConnectFourGame.EMPTY:
                default:
                    discDrawable = wrappedWhiteDisc; // Use the wrapped Drawable for empty disc (or any default)
                    break;

            }
            // set Background
            gridButton.setBackground(discDrawable);
        }
    }


}
