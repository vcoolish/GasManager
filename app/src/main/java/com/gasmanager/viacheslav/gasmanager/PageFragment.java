package com.gasmanager.viacheslav.gasmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

import static java.lang.Math.round;

public class PageFragment extends Fragment {
    static final String TAG = "myLogs";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";
    int pageNumber;
    double[] maxValues = {0.0, 0.0, 0.0, 0.0};
    int[] dates = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    int currentmonth;
    double[] values1;
    double[] values2;
    double[] values3;
    double[] values4;
    ArrayList<double[]> barValues;
    int[] bgcolor = {R.drawable.progresscolor1, R.drawable.progresscolor2, R.drawable.progresscolor3, R.drawable.progresscolor4};
    private Context context;

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    public static int find(int[] a, int target) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == target)
                return i;

        return -1;
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
        View view = inflater.inflate(R.layout.fragment, null);
        context = container.getContext();

        CharSequence[] titles1 = {getResources().getText(R.string.tab1), getResources().getText(R.string.tab2), getResources().getText(R.string.tab3), getResources().getText(R.string.tab4)};
        CharSequence[] titles2 = {getResources().getText(R.string.title1), getResources().getText(R.string.title2), getResources().getText(R.string.title3), getResources().getText(R.string.title4)};
        CharSequence[] monthname = {getResources().getText(R.string.mon1), getResources().getText(R.string.mon2), getResources().getText(R.string.mon3), getResources().getText(R.string.mon4), getResources().getText(R.string.mon5), getResources().getText(R.string.mon6), getResources().getText(R.string.mon7), getResources().getText(R.string.mon8), getResources().getText(R.string.mon9), getResources().getText(R.string.mon10), getResources().getText(R.string.mon11), getResources().getText(R.string.mon12)};
        double[] data1 = {0.0, 0.0, 0.0, 0.0};
        double[] data2 = {0.0, 0.0, 0.0, 0.0};
        String[] infos1 = {data1[pageNumber] + getResources().getText(R.string.lkm).toString(), data1[pageNumber] + getResources().getText(R.string.km).toString(), data1[pageNumber] + "", getResources().getText(R.string.currency).toString() + data1[pageNumber]};
        String[] infos2 = {data2[pageNumber] + getResources().getText(R.string.lkm).toString(), data2[pageNumber] + getResources().getText(R.string.km).toString(), data2[pageNumber] + "", getResources().getText(R.string.currency).toString() + data2[pageNumber] + getResources().getText(R.string.currencyru).toString()};
        int[] images1 = {R.drawable.jerrycangray, R.drawable.distance, R.drawable.jerrycangray, R.drawable.money};
        int[] images2 = {R.drawable.fuelinggray, R.drawable.month, R.drawable.month, R.drawable.coins};

        Realm.init(context);
        Realm mRealm = Realm.getDefaultInstance();
        currentmonth = getCurrentMonth(new Date(new Date().getTime()).getMonth()) + 1;

        RealmResults<MyData> mdarr = mRealm.where(MyData.class).findAll();


        TextView info1 = (TextView) view.findViewById(R.id.info1);
        TextView title1 = (TextView) view.findViewById(R.id.title1);
        TextView info2 = (TextView) view.findViewById(R.id.info2);
        TextView title2 = (TextView) view.findViewById(R.id.title2);
        TextView indata = view.findViewById(R.id.indata);

        TextView month1 = (TextView) view.findViewById(R.id.month1);
        TextView month2 = (TextView) view.findViewById(R.id.month2);
        TextView month3 = (TextView) view.findViewById(R.id.month3);
        TextView month4 = (TextView) view.findViewById(R.id.month4);
        TextView month5 = (TextView) view.findViewById(R.id.month5);
        TextView month6 = (TextView) view.findViewById(R.id.month6);
        TextView month7 = (TextView) view.findViewById(R.id.month7);
        TextView month8 = (TextView) view.findViewById(R.id.month8);
        TextView month9 = (TextView) view.findViewById(R.id.month9);
        TextView month10 = (TextView) view.findViewById(R.id.month10);
        TextView month11 = (TextView) view.findViewById(R.id.month11);
        TextView month12 = (TextView) view.findViewById(R.id.month12);

        View bar1 = view.findViewById(R.id.bar1);
        View bar2 = view.findViewById(R.id.bar2);
        View bar3 = view.findViewById(R.id.bar3);
        View bar4 = view.findViewById(R.id.bar4);
        View bar5 = view.findViewById(R.id.bar5);
        View bar6 = view.findViewById(R.id.bar6);
        View bar7 = view.findViewById(R.id.bar7);
        View bar8 = view.findViewById(R.id.bar8);
        View bar9 = view.findViewById(R.id.bar9);
        View bar10 = view.findViewById(R.id.bar10);
        View bar11 = view.findViewById(R.id.bar11);
        View bar12 = view.findViewById(R.id.bar12);

        for (int i = 0; i < 12; i++) {
            if (currentmonth == 0) {
                currentmonth = 12;
            }
            dates[i] = currentmonth;
            currentmonth -= 1;
        }

        month1.setText(monthname[dates[11] - 1]);
        month2.setText(monthname[dates[10] - 1]);
        month3.setText(monthname[dates[9] - 1]);
        month4.setText(monthname[dates[8] - 1]);
        month5.setText(monthname[dates[7] - 1]);
        month6.setText(monthname[dates[6] - 1]);
        month7.setText(monthname[dates[5] - 1]);
        month8.setText(monthname[dates[4] - 1]);
        month9.setText(monthname[dates[3] - 1]);
        month10.setText(monthname[dates[2] - 1]);
        month11.setText(monthname[dates[1] - 1]);
        month12.setText(monthname[dates[0] - 1]);


        ImageView img1 = view.findViewById(R.id.img1);
        ImageView img2 = view.findViewById(R.id.img2);

        title1.setText(titles1[pageNumber]);
        title2.setText(titles2[pageNumber]);

        int count = 1;
        img1.setImageResource(images1[pageNumber]);
        img2.setImageResource(images2[pageNumber]);
        int size = mdarr.size();
        if (size < 4) {
            indata.setText(R.string.indata);
        }
        if (size > 3) {
            currentmonth = (new Date(new Date().getTime()).getMonth());
            int monthFuel;
            int number;


            values1 = new double[12];
            values2 = new double[12];
            values3 = new double[12];
            values4 = new double[12];
            for (int i = 0; i < 12; i++) {
                values1[i] = 0.0;
                values2[i] = 0.0;
                values3[i] = 0.0;
                values4[i] = 0.0;
            }


            for (int i = size - 2; i > 2; i--) {
                monthFuel = new Date(mdarr.get(i).getDate()).getMonth() + 1;
                System.out.println(monthFuel);
                System.out.println(dates[0]);
                number = find(dates, monthFuel);
                System.out.println(number);
                if (i < size - 2) {
                    if (monthFuel != currentmonth) {
                        int prev = find(dates, currentmonth);
                        values1[prev] = round(values1[prev] / count * 100.0) / 100;
                        values1[number] = (mdarr.get(i).getLiters() * 100) / (mdarr.get(i).getDistance());
                        currentmonth = monthFuel;
                        count = 1;
                    } else {
                        values1[number] += (mdarr.get(i).getLiters() * 100) / (mdarr.get(i).getDistance());
                        count += 1;
                    }
                } else {
                    values1[number] = (mdarr.get(i).getLiters() * 100) / (mdarr.get(i).getDistance());
                    currentmonth = monthFuel;
                    count = 1;
                }
            }
            monthFuel = new Date(mdarr.get(1).getDate()).getMonth() + 1;
            number = getIndexOf(monthFuel, dates);
            values1[number] = values1[number] / count;


            for (int i = size - 1; i > 1; i--) {
                monthFuel = new Date(mdarr.get(i).getDate()).getMonth() + 1;
                number = getIndexOf(monthFuel, dates);

                if (monthFuel != currentmonth) {
                    values2[number] = mdarr.get(i).getDistance();
                    currentmonth = monthFuel;
                    count = 1;
                } else {
                    values2[number] += mdarr.get(i).getDistance();
                    count += 1;
                }
            }
            for (int i = size - 1; i > 1; i--) {
                monthFuel = new Date(mdarr.get(i).getDate()).getMonth() + 1;
                number = getIndexOf(monthFuel, dates);

                if (monthFuel != currentmonth) {
                    values3[number] = mdarr.get(i).getLiters();
                    values4[number] = mdarr.get(i).getPaid();
                    currentmonth = monthFuel;
                    count = 1;
                } else {
                    values3[number] += mdarr.get(i).getLiters();
                    values4[number] += mdarr.get(i).getPaid();
                    count += 1;
                }
            }


            for (int i = 0; i < 12; i++) {
                if (values1[i] > maxValues[0]) {
                    maxValues[0] = values1[i];
                }
                if (values2[i] > maxValues[1]) {
                    maxValues[1] = values2[i];
                }
                if (values3[i] > maxValues[2]) {
                    maxValues[2] = values3[i];
                }
                if (values4[i] > maxValues[3]) {
                    maxValues[3] = values4[i];
                }
            }
            barValues = new ArrayList<>();
            barValues.add(values1);
            barValues.add(values2);
            barValues.add(values3);
            barValues.add(values4);
            System.out.println(barValues.get(pageNumber)[0] / maxValues[pageNumber]);
            System.out.println(barValues.get(pageNumber)[1] / maxValues[pageNumber]);
            onMeasure(barValues.get(pageNumber)[11] / maxValues[pageNumber], bar1);
            onMeasure(barValues.get(pageNumber)[10] / maxValues[pageNumber], bar2);
            onMeasure(barValues.get(pageNumber)[9] / maxValues[pageNumber], bar3);
            onMeasure(barValues.get(pageNumber)[8] / maxValues[pageNumber], bar4);
            onMeasure(barValues.get(pageNumber)[7] / maxValues[pageNumber], bar5);
            onMeasure(barValues.get(pageNumber)[6] / maxValues[pageNumber], bar6);
            onMeasure(barValues.get(pageNumber)[5] / maxValues[pageNumber], bar7);
            onMeasure(barValues.get(pageNumber)[4] / maxValues[pageNumber], bar8);
            onMeasure(barValues.get(pageNumber)[3] / maxValues[pageNumber], bar9);
            onMeasure(barValues.get(pageNumber)[2] / maxValues[pageNumber], bar10);
            onMeasure(barValues.get(pageNumber)[1] / maxValues[pageNumber], bar11);
            onMeasure(barValues.get(pageNumber)[0] / maxValues[pageNumber], bar12);

        }
        double allTimeValue = 0.0;
        double volume = 0.0;
        double everyMonth = 0.0;
        long countMonth = 0;
        double lastExp = 0.0;
        double dailyDistance = 0.0;
        double allDistance = 0.0;
        double allCost = 0.0;
        double dayCost = 0.0;
        long lastDate = 0;
        long firstDate = 0;
        long countDays = 0;

        if (size > 3) {

            lastDate = mdarr.get(size - 1).getDate();
            firstDate = mdarr.get(0).getDate();
            countDays = (lastDate - firstDate) / (24 * 60 * 60 * 1000) + 1;
            double distance = mdarr.get(size - 1).getOdometer() - mdarr.get(1).getOdometer();
            double volumeOil = 0.0;
            for (int i = 2; i <= size - 2; i++) {
                volumeOil += mdarr.get(i).getLiters();
            }
            allTimeValue = volumeOil * 100 / distance;

            double lastDistance = mdarr.get(size - 1).getDistance();
            double lastVolume = mdarr.get(size - 2).getLiters();

            lastExp = lastVolume * 100 / lastDistance;

            allDistance = mdarr.get(size - 1).getOdometer() - mdarr.get(1).getOdometer();

            dailyDistance = allDistance / countDays;

            for (int i = 1; i < size; i++) {
                int monthFuel = new Date(mdarr.get(i).getDate()).getMonth() + 1;
                int number = getIndexOf(monthFuel, dates);

                values1[number] += mdarr.get(i).getLiters();
            }
            for (int i = 0; i < 12; i++) {
                if (values3[i] != 0) {
                    volume += values3[i];
                    countMonth += 1;
                }
            }
            everyMonth = volume / countMonth;

        }
        if (size > 2) {
            for (int i = 0; i < size; i++) {
                allCost += mdarr.get(i).getPaid();
            }
            dayCost = allCost / countDays;
        }
        if (size > 3) {
            data1[0] = Double.parseDouble(new DecimalFormat("#0.00").format(allTimeValue).replace(",", "."));
            data2[0] = Double.parseDouble(new DecimalFormat("#0.00").format(lastExp).replace(",", "."));
            data1[1] = Double.parseDouble(new DecimalFormat("#0.00").format(allDistance).replace(",", "."));
            ;
            data2[1] = Double.parseDouble(new DecimalFormat("#0.00").format(dailyDistance).replace(",", "."));
            data1[2] = Double.parseDouble(new DecimalFormat("#0.00").format(volume).replace(",", "."));
            ;
            data2[2] = Double.parseDouble(new DecimalFormat("#0.00").format(everyMonth).replace(",", "."));
            data1[3] = Double.parseDouble(new DecimalFormat("#0.00").format(allCost).replace(",", "."));
            data2[3] = Double.parseDouble(new DecimalFormat("#0.00").format(dayCost).replace(",", "."));
        }

        infos1[0] = data1[pageNumber] + getResources().getText(R.string.lkm).toString();
        infos2[0] = data2[pageNumber] + getResources().getText(R.string.lkm).toString();
        infos1[1] = data1[pageNumber] + getResources().getText(R.string.km).toString();
        infos2[1] = data2[pageNumber] + getResources().getText(R.string.km).toString();
        infos1[2] = data1[pageNumber] + getResources().getText(R.string.l).toString();
        infos2[2] = data2[pageNumber] + getResources().getText(R.string.l).toString();
        infos1[3] = getResources().getText(R.string.currency).toString() + data1[pageNumber] + getResources().getText(R.string.currencyru).toString();
        infos2[3] = getResources().getText(R.string.currency).toString() + data2[pageNumber] + getResources().getText(R.string.currencyru).toString();

        info1.setText(String.valueOf(infos1[pageNumber]));
        info2.setText(String.valueOf(infos2[pageNumber]));
        mRealm.close();
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

    private int getCurrentMonth(int currentmonth) {
        for (int i = 0; i < 12; i++) {
            if (currentmonth == 0) {
                currentmonth = 12;
            }
            dates[i] = currentmonth;
            currentmonth -= 1;
        }
        return currentmonth;
    }

    private void onMeasure(double height, View view) {

        if (height > 0) {
            final float scale = getResources().getDisplayMetrics().density;
            int height0 = (int) (height * (200 * scale + 0.5f));
            view.getLayoutParams().height = (int) (height * (200 * scale + 0.5f));
            view.setBackgroundResource(bgcolor[pageNumber]);
        }
    }
}

