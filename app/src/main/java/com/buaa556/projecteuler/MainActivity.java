package com.buaa556.projecteuler;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static Handler handler=new Handler();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static DataBaseHelper dbHelper;
    private static SQLiteDatabase db=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //数据库相关配置
        dbHelper=new DataBaseHelper();
        handler.post(new Thread() {
            public void run() {
                db =openDatabase();
            }
        });
    }


    public static int updateDatabase(final Context context) {

        Cursor c = db.rawQuery("select id from cache", null);
        int i = c.getCount() + 1;

        int count = dbHelper.generateFrom(i, db);
        return count;

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==R.id.action_settings) {
            Toast.makeText(this, "开始更新,请确认网络已连接", Toast.LENGTH_SHORT).show();
            int p=MainActivity.updateDatabase(MainActivity.this);
            String toast;
            if(p!=0)
                toast="成功添加了" + p + "道题目";
            else
                toast="题目没有更新";
            Toast.makeText(this,toast, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    //操作数据库相关代码
    private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/ProjectEuler";
    private final String DATABASE_FILENAME = "projecteuler.db";


    /**
     * return a database in the sdcard. first open the app, the database file will be copied to sdcard from res/raw
     * @return
     */
    private SQLiteDatabase openDatabase()
    {
        try
        {
            // 获得*.db文件的绝对路径
            String databaseFilename = DATABASE_PATH+ "/" + DATABASE_FILENAME;
            File dir = new File(DATABASE_PATH);

            if (!dir.exists())
                dir.mkdir();

            if (!(new File(databaseFilename)).exists())
            {
                // 获得封装ddb文件的InputStream对象
                InputStream is =getResources().openRawResource(R.raw.projecteuler);
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[7168];
                int count = 0;
                // 开始复制db文件
                while ((count = is.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
            return database;
        }
        catch (Exception e)
        {
            Log.e("exception", "Exception has been detected while loading database");

        }
        return null;
    }
}
