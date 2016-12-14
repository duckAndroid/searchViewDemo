package com.pythoncat;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pythoncat.utils.ToastHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        // searchItem.expandActionView();
        android.support.v7.widget.SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        ComponentName componentName = getComponentName();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setQueryHint("想要搜索什么呢？");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 点击输入法的搜索按键触发的回调！
                ToastHelper.show(getApplicationContext(), "我主动搜索了！！！");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s == null || s.length() <= 0)
                    return false;
                // 输入框内容改变时的回调
                ToastHelper.show(getApplicationContext(), "文本框改变了.....");
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Toast.makeText(getApplicationContext(), "add=== ", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.menu_remove) {
            Toast.makeText(getApplicationContext(), "remove=== ", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
