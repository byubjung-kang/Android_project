package MobileApplication.Group.Theme;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import MobileApplication.Group.databinding.ActivityTriviaQuestionDatabaseBinding;


public class TriviaQuestionDatabase extends AppCompatActivity {

    protected ActivityTriviaQuestionDatabaseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTriviaQuestionDatabaseBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );

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
                builder.setMessage("Are you old enough to play game?" )
                        .setTitle("Question:")
                        .setNegativeButton("No", (dialog, cl) -> {})
                        .setPositiveButton("Yes", (dialog, cl) ->{


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
            Toast.makeText(this, "User Name must contain an uppercase letter", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "User Name must contain an lowercase letter", Toast.LENGTH_SHORT).show();
            return false;
        }else if (foundNumber || foundSpecial){
            Toast.makeText(this, "Letter Only", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
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