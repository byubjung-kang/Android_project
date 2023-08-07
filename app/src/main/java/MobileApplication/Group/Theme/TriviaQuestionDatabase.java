package MobileApplication.Group.Theme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import MobileApplication.Group.R;
import MobileApplication.Group.Theme.BearImageGenerator.BearImageGenerator;
import MobileApplication.Group.databinding.ActivityTriviaQuestionDatabaseBinding;

/**
 * An activity that represents the trivia question database main screen.
 * @author byubjung kang
 * @version 1.0
 */
public class TriviaQuestionDatabase extends AppCompatActivity {

    /**
     * The binding object for the activity layout.
     */
    protected ActivityTriviaQuestionDatabaseBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaQuestionDatabaseBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

        setSupportActionBar(binding.toolBar);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String userName = prefs.getString("UserName", "");
        binding.uNameText.setText(userName);

        binding.playButton.setOnClickListener(clk -> {

            String inputUName = binding.uNameText.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("UserName", inputUName);
            editor.apply();

            if(checkUNameComplexity(inputUName)) {

                AlertDialog.Builder builder = new AlertDialog.Builder( TriviaQuestionDatabase.this );



                builder.setMessage(getString(R.string.warnMessage))
                        .setTitle(getString(R.string.warn))
                        .setNegativeButton(getString(R.string.no), (dialog, cl) -> {})
                        .setPositiveButton(getString(R.string.yes), (dialog, cl) ->{


                            Intent nextPage = new Intent( TriviaQuestionDatabase.this, TriviaQuestionDatabase2.class);
                            nextPage.putExtra("UserName", inputUName);

                            startActivity( nextPage );
                        })
                .create().show();

            }else {

            }
        });

        binding.rankButton.setOnClickListener(clk -> {

            Intent nextPage = new Intent( TriviaQuestionDatabase.this, TriviaQuestionRank.class);
            startActivity( nextPage );
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if( item.getItemId() == R.id.t_item1 ) {
            Intent intent = new Intent(TriviaQuestionDatabase.this, AviationStackFlightTracker.class);
            startActivity( intent );


        }else if( item.getItemId() == R.id.t_item2 ) {
            Intent intent = new Intent(TriviaQuestionDatabase.this, CurrencyConverterActivity.class);
            startActivity( intent );

        } else if ( item.getItemId() == R.id.t_item3 ) {
            Intent intent = new Intent(TriviaQuestionDatabase.this, BearImageGenerator.class);
            startActivity( intent );

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder( TriviaQuestionDatabase.this );
            builder.setTitle(getString(R.string.tuto))
                    .setMessage(getString(R.string.tutoMsg) )
                    .setPositiveButton(getString(R.string.okay), (dialog, clk) -> {

                    })
                    .create().show();

        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu2, menu);
        return true;
    }

    /**
     * Checks the complexity of the user name by validating if it contains at least one uppercase letter,
     * one lowercase letter, and no numbers or special characters.
     *
     * @param inputUName The user name to be checked.
     * @return true if the user name is complex, false otherwise.
     */
    boolean checkUNameComplexity(String inputUName) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase  = foundLowerCase = foundNumber = foundSpecial  = false;

        for (int i = 0; i <inputUName.length(); i++) {
            char c = inputUName.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(this, getString(R.string.valiUpper), Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, getString(R.string.valiLower), Toast.LENGTH_SHORT).show();
            return false;
        }else if (foundNumber || foundSpecial){
            Toast.makeText(this, getString(R.string.valiLetter), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * Checks if the given character is a special character.
     *
     * @param c The character to be checked.
     * @return true if the character is a special character, false otherwise.
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}