package com.gasmanager.viacheslav.gasmanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class EntryActivity extends Activity {

    Realm mRealm;
    Context mContext;
    ListView mListView;
    myListAdapter mAdapter;

    int ADD_ACTIVITY = 0;
    int UPDATE_ACTIVITY = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        mContext = this;

        Realm.init(mContext);
        Realm mRealm = Realm.getDefaultInstance();
        ArrayList<MyData> list = new ArrayList<>();
        RealmResults<MyData> results = mRealm.where(MyData.class).findAll();
        list.addAll(mRealm.copyFromRealm(results));

        mListView = findViewById(R.id.list);
        mAdapter = new myListAdapter(mContext, list);
        mListView.setAdapter(mAdapter);

        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        mContext = this;
        Realm.init(mContext);
        Realm mRealm = Realm.getDefaultInstance();
        ArrayList<MyData> list = new ArrayList<>();
        RealmResults<MyData> results = mRealm.where(MyData.class).findAll();
        list.addAll(mRealm.copyFromRealm(results));
        switch (item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(mContext, EditActivity.class);

                MyData md = list.get((int) info.id);
                i.putExtra("MyData", md);
                startActivityForResult(i, UPDATE_ACTIVITY);
                updateList();
                return true;
            case R.id.delete:
                MyData md0 = mRealm.where(MyData.class).findAll().where().equalTo("id", info.id).findFirst();
                if (!mRealm.isInTransaction()) {
                    mRealm.beginTransaction();
                }

                md0.deleteFromRealm();

                mRealm.commitTransaction();
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item1 = menu.findItem(R.id.add);
        MenuItem item2 = menu.findItem(R.id.deleteAll);
        MenuItem item3 = menu.findItem(R.id.exit);
        SpannableString s1 = new SpannableString(getResources().getText(R.string.add));
        SpannableString s2 = new SpannableString(getResources().getText(R.string.deleteAll));
        SpannableString s3 = new SpannableString(getResources().getText(R.string.exit));
        s1.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s1.length(), 0);
        s2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s2.length(), 0);
        s3.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s3.length(), 0);
        item1.setTitle(s1);
        item2.setTitle(s2);
        item3.setTitle(s3);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mContext = this;
        Realm.init(mContext);
        Realm mRealm = Realm.getDefaultInstance();
        switch (item.getItemId()) {
            case R.id.add:
                if (mRealm.isEmpty()) {
                    Intent i = new Intent(mContext, EditActivity.class);
                    startActivityForResult(i, 1);
                    updateList();
                } else {
                    Toast.makeText(this, "Use main screen to add new entry", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.deleteAll:
                if (!mRealm.isInTransaction()) {
                    mRealm.beginTransaction();
                }
                mRealm.deleteAll();
                mRealm.commitTransaction();
                updateList();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mContext = this;
        Realm.init(mContext);
        Realm mRealm = Realm.getDefaultInstance();
        if (resultCode == Activity.RESULT_OK) {
            MyData md = (MyData) data.getExtras().getSerializable("MyData");
            if (requestCode == UPDATE_ACTIVITY) {
                mRealm.beginTransaction();
                mRealm.insertOrUpdate(md);
                mRealm.commitTransaction();
            } else {
                mRealm.beginTransaction();
                mRealm.insert(md);
                mRealm.commitTransaction();
            }

            updateList();
        }
    }

    private void updateList() {
        Realm.init(mContext);
        Realm mRealm = Realm.getDefaultInstance();
        ArrayList<MyData> list = new ArrayList<>();
        RealmResults<MyData> results = mRealm.where(MyData.class).findAll();
        list.addAll(mRealm.copyFromRealm(results));
        mAdapter.setArrayMyData(list);
        mAdapter.notifyDataSetChanged();
    }

    class myListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private ArrayList<MyData> arrayMyData;

        private myListAdapter(Context ctx, ArrayList<MyData> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        public ArrayList<MyData> getArrayMyData() {
            return arrayMyData;
        }

        private void setArrayMyData(ArrayList<MyData> arrayMyData) {
            this.arrayMyData = arrayMyData;
        }

        public int getCount() {
            return arrayMyData.size();
        }

        public Object getItem(int position) {

            return position;
        }

        public long getItemId(int position) {
            MyData md = arrayMyData.get(position);
            if (md != null) {
                return md.getID();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);


            TextView vDate = convertView.findViewById(R.id.Date);
            TextView vOdo = convertView.findViewById(R.id.odo);
            TextView vLiters = convertView.findViewById(R.id.liters);
            TextView vPrice = convertView.findViewById(R.id.price);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            MyData md = arrayMyData.get(position);
            vDate.setText(dateFormat.format(md.getDate()));
            vOdo.setText(getResources().getString(R.string.odoedit) + ": " + String.valueOf(md.getOdometer()) + getResources().getString(R.string.km));
            vLiters.setText(getResources().getString(R.string.volume) + ": " + String.valueOf(md.getLiters()) + getResources().getString(R.string.l));
            vPrice.setText(getResources().getString(R.string.price) + ":" + getResources().getText(R.string.currency).toString() + String.valueOf(md.getPrice()) + getResources().getString(R.string.currencyru));

            return convertView;
        }
    } // end myAdapter
}