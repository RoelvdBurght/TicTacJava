package rgburght.gmail.com.tictactoe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
//import android.support;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            game = (Game) savedInstanceState.getSerializable("game");
            String[] text = (String[]) savedInstanceState.getSerializable("array");

            Button b00 = (Button) findViewById(R.id.button00);
            Button b01 = (Button) findViewById(R.id.button01);
            Button b02 = (Button) findViewById(R.id.button02);
            Button b10 = (Button) findViewById(R.id.button10);
            Button b11 = (Button) findViewById(R.id.button11);
            Button b12 = (Button) findViewById(R.id.button12);
            Button b20 = (Button) findViewById(R.id.button20);
            Button b21 = (Button) findViewById(R.id.button21);
            Button b22 = (Button) findViewById(R.id.button22);
            Button[] butArray = {b00,b01,b02,b10,b11,b12,b20,b21,b22};

            for (int i = 0; i < butArray.length; i++) {
                Log.d("tag", butArray[i].toString() + " = " + text[i]);
                butArray[i].setText(text[i]);
            }
        }
        else {
            game = new Game();
        }
    }

    // Slaat de text op alle buttons op in een array en serialized dat, ook slaat deze functie het
    // Game object op en serialized dat.
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("game", game);
        Button b00 = (Button) findViewById(R.id.button00);
        Button b01 = (Button) findViewById(R.id.button01);
        Button b02 = (Button) findViewById(R.id.button02);
        Button b10 = (Button) findViewById(R.id.button10);
        Button b11 = (Button) findViewById(R.id.button11);
        Button b12 = (Button) findViewById(R.id.button12);
        Button b20 = (Button) findViewById(R.id.button20);
        Button b21 = (Button) findViewById(R.id.button21);
        Button b22 = (Button) findViewById(R.id.button22);

        Button[] butArray = {b00,b01,b02,b10,b11,b12,b20,b21,b22};
        String[] stateArray = new String[9];
        for (int i = 0; i < butArray.length; i++) {
            stateArray[i] = (String) butArray[i].getText();
        }
        outState.putSerializable("array", stateArray);
        super.onSaveInstanceState(outState);
    }

    // Vult een tile in als er op geklikt word.
    // Afhankelijk van de playerturn met kruis of cirkel, als al ingeuld gebeurt niks.
    public void tileClicked(View tile) {
        GameState finished = game.won();
        // Only run the method is the game is in progress.
        if (finished == GameState.IN_PROGRESS) {
            int intId = tile.getId();
            String id = tile.getResources().getResourceEntryName(intId);
            Button btn = findViewById(intId);

            String rowStr = id.substring(id.length() - 2);
            String colStr = id.substring(id.length() - 1);
            int row = Character.getNumericValue(rowStr.charAt(0));
            int col = Integer.parseInt(colStr);

            TileState entryState = game.choose(row, col);

            switch(entryState) {
                case CROSS:
                    btn.setText("X");
                    break;
                case CIRCLE:
                    btn.setText("O");
                    break;
                case INVALID:
                    break;
            }
            GameState state = game.won();
            if (game.won() != GameState.IN_PROGRESS) {
                String text = "";
                if (state == GameState.PLAYER_ONE) {
                    text = "Speler 1 heeft gewonnen!";
                }
                if (state == GameState.PLAYER_TWO) {
                    text = "Speler 2 heeft gewonnen!";
                }
                TextView winnerBox = findViewById(R.id.winnerBox);
                winnerBox.setText(text);
            }
        }
    }

    // Reset het speelveld. Verbetering door loopen over buttons mogelijk.
    public void resetClicked(View tile) {
        game = new Game();
        Button btn00 = findViewById(R.id.button00);
        btn00.setText(" ");
        Button btn01 = findViewById(R.id.button01);
        btn01.setText(" ");
        Button btn02 = findViewById(R.id.button02);
        btn02.setText(" ");
        Button btn10 = findViewById(R.id.button10);
        btn10.setText(" ");
        Button btn11 = findViewById(R.id.button11);
        btn11.setText(" ");
        Button btn12 = findViewById(R.id.button12);
        btn12.setText(" ");
        Button btn20 = findViewById(R.id.button20);
        btn20.setText(" ");
        Button btn21 = findViewById(R.id.button21);
        btn21.setText(" ");
        Button btn22 = findViewById(R.id.button22);
        btn22.setText(" ");
        TextView winnerBox = findViewById(R.id.winnerBox);
        winnerBox.setText("");
    }
}
