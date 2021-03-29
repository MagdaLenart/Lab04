package pollub.ism.lab04;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button [] buttons = new Button[9];

    private boolean player1Turn = true;

    private int roundCount=0;

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    int [][] winningPosition ={
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0; i<9; i++){
            String buttonID="button_"+i;
            int resourceID=getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i]=(Button) findViewById(resourceID);
        }
    }

    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        String buttonID=v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (player1Turn) {
            ((Button) v).setText("X");
            gameState[gameStatePointer] =0;
        } else {
            ((Button) v).setText("O");
            gameState[gameStatePointer] =1;
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {

                Toast.makeText(this, "Wygrały X!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Wygrały O!", Toast.LENGTH_SHORT).show();
            }
            playAgain();
        } else if (roundCount == 9) {
            Toast.makeText(this, "Remis!", Toast.LENGTH_SHORT).show();
            playAgain();
        } else {
            player1Turn = !player1Turn;
        }
    }
    private boolean checkForWin()
    {
        boolean winnerResult =false;

        for(int [] winningPosition :winningPosition){
            if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]]!=2){
                winnerResult=true;
            }
        }
        return winnerResult;
    }

    public void playAgain()
    {
        roundCount=0;
        player1Turn=true;
        for(int i=0; i<buttons.length; i++) {
        gameState[i]=2;
        buttons[i].setText("");
        }
    }

    @Override
    protected void onSaveInstanceState( Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putBoolean("player1Turn",player1Turn);
        for(int i=0; i<buttons.length; i++) {
            outState.putIntArray("gameState",gameState);

        }

    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount=savedInstanceState.getInt("roundCount");
        player1Turn=savedInstanceState.getBoolean("player1Turn");
        for(int i=0; i<buttons.length; i++) {
            gameState=savedInstanceState.getIntArray("gameState");

        }
    }
}