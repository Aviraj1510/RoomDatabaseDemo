package com.example.roomdatabasedemo;

import static java.util.Locale.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabasedemo.adapter.RVFormViewAdapter;
import com.example.roomdatabasedemo.modal.DetailsModal;
import com.example.roomdatabasedemo.modal.ViewModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView formRV;
    private static final int ADD_FORM_REQUEST = 1;
    private static final int EDIT_FORM_REQUEST = 2;
    private ViewModal viewmodal;
    TextView clickText;
    ImageView clickImg;
    SearchView searchView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        formRV = findViewById(R.id.idRVForm);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);
        clickText = findViewById(R.id.clickImage);
        clickImg = findViewById(R.id.imageView);
        searchView = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final RVFormViewAdapter adapter = new RVFormViewAdapter();
        formRV.setAdapter(adapter);

        viewmodal = new ViewModelProvider(this).get(ViewModal.class);
        viewmodal.getAllForm().observe(this, new Observer<List<DetailsModal>>() {
            @Override
            public void onChanged(List<DetailsModal> models) {
                adapter.submitList(models);
                updateHintVisibility(models);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewFormActivity.class);
                startActivityForResult(intent, ADD_FORM_REQUEST);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update the list based on search query
                viewmodal.searchDetails(newText).observe(MainActivity.this, new Observer<List<DetailsModal>>() {
                    @Override
                    public void onChanged(List<DetailsModal> models) {
                        adapter.submitList(models);
                    }
                });
                return true;
            }
        });



        formRV.setLayoutManager(new LinearLayoutManager(this));
        formRV.setHasFixedSize(true);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);
        viewmodal.getAllForm().observe(this, models -> {
            // when the data is changed in our models we are
            // adding that list to our adapter class.
            adapter.submitList(models);
            updateHintVisibility(models);
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.Delete(adapter.getFormAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Form deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(formRV);
        adapter.setOnItemClickListener(new RVFormViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onItemClick(DetailsModal model) {
                // after clicking on item of recycler view
                // we are opening a new activity and passing
                // a data to our activity.
                Intent intent = new Intent(MainActivity.this, NewFormActivity.class);
                intent.putExtra(NewFormActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewFormActivity.EXTRA_NAME, model.getName());
                intent.putExtra(NewFormActivity.EXTRA_EMAIL, model.getEmail());
                intent.putExtra(NewFormActivity.EXTRA_PHONENUMBER, model.getPhoneNumber());

                // below line is to start a new activity and
                // adding a edit course constant.
                startActivityForResult(intent, EDIT_FORM_REQUEST);
            }
        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginRegister.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_FORM_REQUEST && resultCode == RESULT_OK) {
            String Name = data.getStringExtra(NewFormActivity.EXTRA_NAME);
            String Email = data.getStringExtra(NewFormActivity.EXTRA_EMAIL);
            String PhoneNumber = data.getStringExtra(NewFormActivity.EXTRA_PHONENUMBER);
            DetailsModal model = new DetailsModal(Name, Email, PhoneNumber);
            viewmodal.insert(model);
            Toast.makeText(this, "Form saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_FORM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewFormActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Form can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String Name = data.getStringExtra(NewFormActivity.EXTRA_NAME);
            String Email = data.getStringExtra(NewFormActivity.EXTRA_EMAIL);
            String PhoneNumber = data.getStringExtra(NewFormActivity.EXTRA_PHONENUMBER);
            DetailsModal model = new DetailsModal(Name, Email, PhoneNumber);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Form updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Form not saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateHintVisibility(List<DetailsModal> models) {
        if (models.isEmpty()) {
            clickText.setVisibility(View.VISIBLE);
            clickImg.setVisibility(View.VISIBLE);
        } else {
            clickText.setVisibility(View.GONE);
            clickImg.setVisibility(View.GONE);
        }
    }
}