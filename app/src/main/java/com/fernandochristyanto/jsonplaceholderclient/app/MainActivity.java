package com.fernandochristyanto.jsonplaceholderclient.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.fernandochristyanto.jsonplaceholderclient.R;
import com.fernandochristyanto.jsonplaceholderclient.model.Post;
import com.fernandochristyanto.jsonplaceholderclient.proxy.PostClientImpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    @BindView (R.id.recyclerView)
    RecyclerView recyclerView;
    
    private MainContract.Presenter presenter = new MainPresenter(this, new PostClientImpl());
    private PostsAdapter postsAdapter;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        
        // Initialize recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsAdapter = new PostsAdapter();
        recyclerView.setAdapter(postsAdapter);
        
        // Trigger a request to fetch posts
        presenter.getPosts();
    }
  
    @Override
    public void showPosts (List<Post> posts) {
        postsAdapter.updatePosts(posts);
    }
    
    @Override
    public void showError (String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
