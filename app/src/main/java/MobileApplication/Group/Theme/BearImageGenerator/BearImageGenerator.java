package MobileApplication.Group.Theme.BearImageGenerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Data.BearImageGeneratorData.BearImage;
import Data.BearImageGeneratorData.BearImageDAO;
import Data.BearImageGeneratorData.BearImageDatabase;
import MobileApplication.Group.R;
import MobileApplication.Group.Theme.AviationStackFlightTracker;

import MobileApplication.Group.Theme.CurrencyConverterActivity;
import MobileApplication.Group.Theme.MainActivity;
import MobileApplication.Group.Theme.TriviaQuestionDatabase;
import MobileApplication.Group.databinding.BearImageCardBinding;
import MobileApplication.Group.databinding.ActivityBearImageRoomBinding;
/**
 * @author Jeonghyeon Lee
 * @version 1.0
 */
/**
 * This class represents the main activity of the BearImageGenerator app.
 * Users can generate and display bear images with different width and height combinations.
 */
public class BearImageGenerator extends AppCompatActivity {

    @NonNull
    ActivityBearImageRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<BearImage> images;
    int positionDel;
    RequestQueue queue = null;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);

        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(),
                BearImageDatabase.class, "BearImaage-database")
                .build();
        BearImageDAO mDAO = db.cmDAO();

        //        ArrayList<String> messages = new ArrayList<>();
        BearImageViewModel imageModel ;
        imageModel = new ViewModelProvider(this).get(BearImageViewModel.class);
        imageModel.selectedImage.observe(this, newMessage -> {

            imageModel.selectedImage.observe(this, (newValue) -> {
                BearImageDetailsFragment chatFragment = new BearImageDetailsFragment(newValue);
                FragmentManager fMgr = getSupportFragmentManager();

                FragmentTransaction tx = fMgr.beginTransaction();

                tx.addToBackStack("");
                tx.replace(R.id.fragmentLocation, chatFragment);
                tx.commit();
            });

        });

        images = imageModel.images.getValue();

        if(images == null)
        {
            imageModel.images.postValue( images = new ArrayList<BearImage>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                images.addAll( mDAO.getAllImages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }


        class MyRowHolder extends RecyclerView.ViewHolder {
            TextView messageText;
            ImageView imageView;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                AlertDialog.Builder builder = new AlertDialog.Builder( BearImageGenerator.this );

                itemView.setOnLongClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    builder.setMessage( "Do you want to delete the image: " + messageText.getText() )
                    .setTitle( "Question:" )
                    .setNegativeButton("No", (dialog, cl) -> { })
                    .setPositiveButton("Yes", (dialog, cl) -> {

                        BearImage m = images.get(position);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute( () -> {
                            mDAO.deleteMessage( m );
                            images.remove(position);
                            runOnUiThread(() -> { myAdapter.notifyItemRemoved(position); });
                        });
                        Snackbar.make(messageText, "You deleted message #"+ position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", c -> {
                                    images.add(position, m);
                                    myAdapter.notifyItemInserted(position);
                                })
                                .show();
                })
                            .create().show();
                    return false;
                });

                itemView.setOnClickListener(clk -> {
                    positionDel = getAbsoluteAdapterPosition();
                    BearImage selectedMessage = images.get(positionDel);

                    imageModel.selectedImage.postValue( images.get(positionDel) );

                });

                messageText = itemView.findViewById(R.id.message);
                imageView = itemView.findViewById(R.id.imageView2);
            }
        }


        binding = ActivityBearImageRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.textInput.setText(prefs.getString("width", ""));
        binding.textInput2.setText(prefs.getString("height", ""));

        SharedPreferences.Editor edit = prefs.edit();

        //this loads the toolbar, in onCreateOptionsMenu
        setSupportActionBar(binding.myToolbar);

        binding.generateButton.setOnClickListener(click -> {
            SharedPreferences.Editor editor = prefs.edit();


            String input = binding.textInput.getText().toString();
            editor.putString("width", input);
            editor.commit();

            String height = binding.textInput2.getText().toString();
            editor.putString("height", height);
            editor.commit();


            BearImage newImage = new BearImage(input, height);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                newImage.id = mDAO.insertImage(newImage);
            });

            images.add(newImage);
            myAdapter.notifyItemInserted(images.size()-1);

            String pathname = getFilesDir() + "/" + input + "X" + height + ".png";
            File file = new File(pathname);
            if (file.exists()) {
                image = BitmapFactory.decodeFile(pathname);
            } else {
                String imageUrl = "https://placebear.com/" + input + "/" + height;
                ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        try {
                            image = bitmap;
                            image.compress(Bitmap.CompressFormat.PNG, 100, BearImageGenerator.this.openFileOutput(input + "X" + height+".png", BearImageGenerator.MODE_PRIVATE));
                            runOnUiThread( (  )  -> {
//                                binding.imageView2.setImageBitmap(bitmap);
//                                binding.imageView.setVisibility(View.VISIBLE);
                            });
                            int i = 10;
                        } catch (Exception e) {
                            int i = 0;
                            e.printStackTrace();
                        }
                    }
                }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                        (error) -> {
                        });
                queue.add(imgReq);
            }


            binding.textInput.setText("");
            binding.textInput2.setText("");

            CharSequence text = "Image created!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this /* MyActivity */, text, duration);
            toast.show();
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                BearImageCardBinding binding =
                        BearImageCardBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder( binding.getRoot() );

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                holder.messageText.setText("");
                BearImage obj = images.get(position);
                holder.messageText.setText(obj.getWidth() +"X"+ obj.getHeight() + " Bear Imaage");
                String url = obj.getUrl();
                Picasso.get().load(url).into(holder.imageView);

            }

            @Override
            public int getItemCount() {
                return images.size();
            }

            @Override
            public int getItemViewType(int position){
                BearImage obj = images.get(position);
                    return 2;
            }


        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "BearImaage-database").build();
        BearImageDAO mDAO = db.cmDAO();
        BearImageViewModel chatModel ;
        chatModel = new ViewModelProvider(this).get(BearImageViewModel.class);


        if( item.getItemId() == R.id.item_1 ) {



            AlertDialog.Builder builder = new AlertDialog.Builder( BearImageGenerator.this );

            builder.setMessage( "Do you want to delete the image: " )
                    .setTitle( "Question:" )
                    .setNegativeButton("No", (dialog, cl) -> { })
                    .setPositiveButton("Yes", (dialog, cl) -> {

                        BearImage m = images.get(positionDel);

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute( () -> {
                            mDAO.deleteMessage( m );
                            images.remove(positionDel);
                            runOnUiThread(() -> { myAdapter.notifyItemRemoved(positionDel); });
                        });
//                        Snackbar.make(messageText, "You deleted message #"+ positionDel, Snackbar.LENGTH_LONG)
//                                .setAction("Undo", c -> {
//                                    messages.add(positionDel, m);
//                                    myAdapter.notifyItemInserted(positionDel);
//                                })
//                                .show();

                    })
                    .create().show();

        } else if( item.getItemId() == R.id.item_2 ) {
            AlertDialog.Builder builder = new AlertDialog.Builder( BearImageGenerator.this );

            builder.setMessage( "Please input the Width and Height to generate the bear image!" +
                            " Insert the Width and Height for your bear image that you want to create!" +
                            " It will automatically generate the bear image once you click on the generate button" +
                            " after you set the parameters  " )
                    .setTitle( "Help:" )
                    .create().show();

        } else if( item.getItemId() == R.id.currency ) {
            Intent intent = new Intent(BearImageGenerator.this, CurrencyConverterActivity.class);
            startActivity(intent);
        } else if( item.getItemId() == R.id.question ) {
            Intent intent = new Intent(BearImageGenerator.this, TriviaQuestionDatabase.class);
            startActivity(intent);
        } else if( item.getItemId() == R.id.flight ) {
            Intent intent = new Intent(BearImageGenerator.this, AviationStackFlightTracker.class);
            startActivity(intent);
        }

        return true;
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false, it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate the menu
        getMenuInflater().inflate( R.menu.my_menu4, menu);

        return true;
    }


}