package MobileApplication.Group.Theme.BearImageGenerator;

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

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import Data.BearImageGeneratorData.BearImage;
import Data.BearImageGeneratorData.BearImageDAO;
import Data.BearImageGeneratorData.BearImageDatabase;
import MobileApplication.Group.R;
import MobileApplication.Group.databinding.BearImageCardBinding;
import MobileApplication.Group.databinding.ActivityBearImageRoomBinding;

public class BearImageGenerator extends AppCompatActivity {

    @NonNull
    ActivityBearImageRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<BearImage> images;
    int positionDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "database-name").build();
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
//            TextView timeText;
            ImageView imageView;

            public MyRowHolder(@NonNull View itemView) {
                super(itemView);

                AlertDialog.Builder builder = new AlertDialog.Builder( BearImageGenerator.this );

                itemView.setOnLongClickListener(clk -> {
                    int position = getAbsoluteAdapterPosition();
                    builder.setMessage( "Do you want to delete the iamge: " + messageText.getText() )
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

        //this loads the toolbar, in onCreateOptionsMenu
        setSupportActionBar(binding.myToolbar);

        binding.generateButton.setOnClickListener(click -> {
            String input = binding.textInput.getText().toString();
            String height = binding.textInput2.getText().toString();
            boolean type = false;

            BearImage newImage = new BearImage(input, height, type);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute( () -> {
                newImage.id = mDAO.insertImage(newImage);
            });

            images.add(newImage);
            myAdapter.notifyItemInserted(images.size()-1);
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
                if (obj.getIsSentButton() == true)
                    return 1;
                else
                    return 2;
            }


        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        BearImageDatabase db = Room.databaseBuilder(getApplicationContext(), BearImageDatabase.class, "database-name").build();
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

            builder.setMessage( "Please input the Width and Height to generate the bear image! " )
                    .setTitle( "Help:" )
                    .create().show();

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //inflate the menu
        getMenuInflater().inflate( R.menu.my_menu, menu);

        return true;
    }


}