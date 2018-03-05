package com.gasmanager.viacheslav.gasmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class AdvicePager extends Fragment {

    static final String TAG = "myLogs";


    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";


    int pageNumber;

    static AdvicePager newInstance(int page) {
        AdvicePager pageFragment = new AdvicePager();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        Log.d(TAG, "onCreate: " + pageNumber);

        int savedPageNumber = -1;
        if (savedInstanceState != null) {
            savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER);
        }
        Log.d(TAG, "savedPageNumber = " + savedPageNumber);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advice_pager, null);

        ImageView illust = view.findViewById(R.id.illust);
        TextView advicetitle = view.findViewById(R.id.advicetitle);
        TextView advicetext = view.findViewById(R.id.advice);

        int[] images = {R.drawable.car, R.drawable.speedometerillustration, R.drawable.heavy, R.drawable.charging, R.drawable.pump};
        int[] titles = {R.string.advicetitle1, R.string.advicetitle2, R.string.advicetitle3, R.string.advicetitle4, R.string.advicetitle5};
        int[] advice = {R.string.advice1, R.string.advice2, R.string.advice3, R.string.advice4, R.string.advice5};

        illust.setImageResource(images[pageNumber]);
        advicetitle.setText(titles[pageNumber]);
        advicetext.setText(advice[pageNumber]);

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + pageNumber);
    }


    public int getIndexOf(int toSearch, int[] tab) {
        for (int i = 0; i < tab.length; i++)
            if (tab[i] == toSearch)
                return i;

        return -1;
    }

}

