package com.vn.myhome.fragment.bottom_bar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.vn.myhome.App;
import com.vn.myhome.R;
import com.vn.myhome.activity.login.ActivityListHomestay;
import com.vn.myhome.base.BaseFragment;
import com.vn.myhome.config.Constants;
import com.vn.myhome.models.MessageEvent;
import com.vn.myhome.models.ObjHomeStay;
import com.vn.myhome.models.ObjReport;
import com.vn.myhome.network.response.ResponReport;
import com.vn.myhome.untils.SharedPrefs;
import com.vn.myhome.untils.StringUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class FragmentReportHost extends BaseFragment {
    private static final String TAG = "FragmentReportHost";
    public static FragmentReportHost fragment;

    public static FragmentReportHost getInstance() {
        if (fragment == null) {
            synchronized (FragmentReportHost.class) {
                if (fragment == null)
                    fragment = new FragmentReportHost();
            }
        }
        return fragment;
    }

    List<String> data_Home;
    @BindView(R.id.spinner_home)
    TextView txt_myhome;
    List<ObjHomeStay> mListHome;
    PresenterReportHost mPresenter;
    TextView txt_title_book;
    TextView txt_content_book;
    TextView txt_note_book;
    @BindView(R.id.txt_title_revenue)
    TextView txt_title_revenue;
    @BindView(R.id.txt_revenue_of_month)
    TextView txt_revenue_of_month;
    @BindView(R.id.txt_revenue_last_month)
    TextView txt_revenue_last_month;
    @BindView(R.id.txt_book_of_month)
    TextView txt_book_of_month;
    @BindView(R.id.ic_note_book)
    ImageView ic_up_book;
    @BindView(R.id.txt_book_last_month)
    TextView txt_book_last_month;
    @BindView(R.id.txt_title_cost)
    TextView txt_title_cost;
    @BindView(R.id.txt_cost_of_month)
    TextView txt_cost_of_month;
    @BindView(R.id.txt_cost_last_month)
    TextView txt_cost_last_month;
    @BindView(R.id.txt_duno_of_month)
    TextView txt_duno_of_month;
    @BindView(R.id.ic_revenue)
    ImageView ic_revenue;
    @BindView(R.id.ic_cost)
    ImageView ic_cost;
    @BindView(R.id.txt_duno_last_month)
    TextView txt_duno_last_month;
    @BindView(R.id.txt_loinhuan_of_month)
    TextView txt_loinhuan_of_month;
    @BindView(R.id.txt_loinhuan_last_month)
    TextView txt_loinhuan_last_month;
    @BindView(R.id.txt_title_book_last_month)
    TextView txt_title_book_last_month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report_host, container, false);
        ButterKnife.bind(this, view);
        txt_note_book = view.findViewById(R.id.txt_note_book);
        txt_content_book = view.findViewById(R.id.txt_content_book);
        txt_title_book = view.findViewById(R.id.txt_title_book);
        mListHome = new ArrayList<>();
        mPresenter = new PresenterReportHost(this);
        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.get_rp_revenue(sUsername);
        mPresenter.get_rp_cost(sUsername);
        mPresenter.get_rp_profit(sUsername);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initEvent();
        return view;
    }

    private void initEvent() {
        txt_myhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_city = new Intent(getActivity(), ActivityListHomestay.class);
                startActivityForResult(intent_city, Constants.RequestCode.GET_LIST_MYHOME);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.message.equals(Constants.EventBus.KEY_UPDATE_API_LIST_MYHOME)) {
            if (App.mListHomeStay != null && App.mListHomeStay.size() > 0) {
                mListHome.clear();
                mListHome.addAll(App.mListHomeStay);
                //  set_data_spinner();
                txt_myhome.setText(mListHome.get(0).getNAME());
                get_api_rp_book(mListHome.get(0).getGENLINK());

            }
        }
    }

    private void get_api_rp_book(String genlink) {
        String sUsername = SharedPrefs.getInstance().get(Constants.KEY_SAVE_USERNAME, String.class);
        mPresenter.getRpBook(sUsername, genlink);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void set_info_revene(ResponReport objRest_book) {
        txt_title_revenue.setText("cdj");
        txt_revenue_of_month.setText("abc");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RequestCode.GET_LIST_MYHOME:
                if (resultCode == RESULT_OK) {
                    txt_myhome.setText(App.mHomestay.getNAME());
                    get_api_rp_book(App.mHomestay.getGENLINK());
                }
                break;
        }
    }

    public void show_rp_venuue(ResponReport objReport) {
        if (objReport.getERROR().equals("0000")) {
            ObjReport objCurrent = objReport.getINFO().get(1);
            ObjReport objLate = objReport.getINFO().get(0);
            long current = Long.parseLong(objCurrent.getREVENUE());
            long last = Long.parseLong(objLate.getREVENUE());
            long revene = current - last;

            if (revene == 0) {
                ic_revenue.setImageResource(R.drawable.ic_ngang);
                txt_revenue_last_month.setTextColor(getResources().getColor(R.color.DarkOrange));
            } else if (revene > 0) {
                ic_revenue.setImageResource(R.drawable.ic_up_report);
                txt_revenue_last_month.setTextColor(getResources().getColor(R.color.ForestGreen));
            }
            if (revene < 0) {
                ic_revenue.setImageResource(R.drawable.ic_down_report);
                txt_revenue_last_month.setTextColor(getResources().getColor(R.color.Red));
            }
            txt_title_revenue.setText(Html.fromHtml("Doanh thu tháng này" + "<i> so với tháng trước nữa</i>"));
            txt_revenue_of_month.setText(StringUtil.conventMonney_Long(objCurrent.getREVENUE()));
            txt_revenue_last_month.setText(StringUtil.conventMonney_Long(revene + ""));

        }
    }

    public void show_rp_book(ResponReport objReport) {
        if (objReport.getERROR().equals("0000")) {
            ObjReport objReport_current = objReport.getINFO().get(1);
            ObjReport objReport_last = objReport.getINFO().get(0);
            long current = Long.parseLong(objReport_current.getTOTALDAY());
            long last = Long.parseLong(objReport_last.getTOTALDAY());
            long revene = current - last;
            if (revene == 0) {
                ic_up_book.setImageResource(R.drawable.ic_ngang);
                txt_note_book.setVisibility(View.INVISIBLE);
            } else if (revene > 0) {
                ic_up_book.setImageResource(R.drawable.ic_up_report);
                txt_note_book.setTextColor(getResources().getColor(R.color.ForestGreen));
            }
            if (revene < 0) {
                ic_up_book.setImageResource(R.drawable.ic_down_report);
                txt_note_book.setTextColor(getResources().getColor(R.color.Red));
            }
            txt_title_book.setText("Số ngày đặt phòng trong tháng " + objReport_current.getMONTH());
            txt_content_book.setText(objReport_current.getTOTALDAY() + "/" + objReport_current.getNUMBER_OF_DAYS() + " ngày");
            txt_note_book.setText(revene + " ngày so với tháng trước");

            //txt_title_book_last_month.setText("Số đơn trong tháng so với tháng trước nữa");

        }
    }

    public void show_rp_cost(ResponReport objReport) {
        if (objReport.getERROR().equals("0000")) {
            ObjReport objCurrent = objReport.getINFO().get(1);
            ObjReport objLate = objReport.getINFO().get(0);
            long current = Long.parseLong(objCurrent.getCOST());
            long last = Long.parseLong(objLate.getCOST());
            long revene = current - last;
            if (revene == 0) {
                ic_cost.setImageResource(R.drawable.ic_ngang);
                txt_cost_last_month.setVisibility(View.INVISIBLE);
            } else if (revene > 0) {
                ic_cost.setImageResource(R.drawable.ic_up_report);
                txt_cost_last_month.setTextColor(getResources().getColor(R.color.ForestGreen));
            }
            if (revene < 0) {
                ic_cost.setImageResource(R.drawable.ic_down_report);
                txt_cost_last_month.setTextColor(getResources().getColor(R.color.Red));
            }
            txt_title_cost.setText(Html.fromHtml("Chi phí tháng này" + "<i> so với tháng trước nữa</i>"));
            txt_cost_of_month.setText(StringUtil.conventMonney_Long(objCurrent.getCOST()));

        }
    }

    public void show_rp_profit(ResponReport objReport) {
        if (objReport.getERROR().equals("0000")) {
            ObjReport objCurrent = objReport.getINFO().get(0);
            txt_title_revenue.setText(Html.fromHtml("Doanh thu trọn đời"));
            long profit = Long.parseLong(objCurrent.getPROFIT());
            if (profit>=0){
                txt_loinhuan_of_month.setText(StringUtil.conventMonney_Long(objCurrent.getPROFIT()));
            }else
                txt_loinhuan_of_month.setText("-"+StringUtil.conventMonney_Long
                        (objCurrent.getPROFIT().substring(1, objCurrent.getPROFIT().length())));


            long balance = Long.parseLong(objCurrent.getBALANCE());
            if (balance >= 0) {
                txt_duno_of_month.setText(StringUtil.conventMonney_Long
                        (objCurrent.getBALANCE()));
            } else
                txt_duno_of_month.setText("-" + StringUtil.conventMonney_Long
                        (objCurrent.getBALANCE().substring(1, objCurrent.getBALANCE().length())));
        }
    }
}
