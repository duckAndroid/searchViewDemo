# searchViewDemo
android searchView在标题栏menu菜单 的使用

..............................
 
 SearchView 在标题栏经常有使用，这个东西用起来也不难，但是会有一些坑的存在。
 
 下面来看代码：
 
 1. 要在res目录下面添加一个menu目录，然后在menu目录里面添加一个给menu使用的.xml。比如`main.xml`
 
 ```java
 <?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto">

    <!--
    SearchView 获取不到的BUG 原因：
    属性 actionViewClass 使用的命名空间是android 就为null
    使用的命名空间为app 就可以正常获取了！
    app:actionViewClass="android.widget.SearchView"
    -->
    <!--
    为什么 使用 android.support.v7.widget.SearchView
    而不是 android.widget.SearchView：因为v7的这个效果更直接！
    -->
    <item
        android:id="@+id/menu_search"
        android:icon="@android:drawable/ic_menu_search"
        android:title="@string/search"
        app:actionViewClass="android.support.v7.widget.SearchView"
        app:showAsAction="ifRoom|collapseActionView"/>

    <item
        android:id="@+id/menu_add"
        android:title="@string/add"/>
    <item
        android:id="@+id/menu_remove"
        android:title="@string/remove"/>
</menu>
 ```
 
  比如上面这样的。我是标题栏放了一个`SearchView`和两个其他的按钮。<b>重点关注里面的注释内容</b>，注释也就是我踩坑的过程。
 
2. 然后 看一下Activity中的代码，也很简单

```java

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
        android.support.v7.widget.SearchView searchView = 
                (SearchView) MenuItemCompat.getActionView(searchItem);
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
                // 输入框里面的内容发生改变时的回调
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

```
 
 <b>重点关注`onCreateOptionsMenu(Menu menu)`方法里面的代码即可。
 这里面的代码就描述了SearchView的常见使用场景，以及必要的回调监听的含义。</b>
