package com.vn.myhome;

import android.os.Bundle;

import com.vn.myhome.base.BaseActivity;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 21-August-2019
 * Time: 22:50
 * Version: 1.0
 */
public class Activity_Test_Calender extends BaseActivity {


    @Override
    public int setContentViewId() {
        return R.layout.fragment_home_search_ctv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 10);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, - 10);
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);

     //   calendar.deactivateDates(list);
        ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");

            String strdate = "22-4-2019";
            String strdate2 = "26-4-2019";

            Date newdate = dateformat.parse(strdate);
            Date newdate2 = dateformat.parse(strdate2);
            arrayList.add(newdate);
            arrayList.add(newdate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("MMMM, YYYY", Locale.getDefault())) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
               // .withDeactivateDates(list)

                .withHighlightedDates(arrayList);
        calendar.scrollToDate(new Date());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity_Test_Calender.this, "list "
                        + calendar.getSelectedDates().toString(), Toast.LENGTH_LONG).show();
            }
        });*/
    }

}
