package com.example.hp.test25.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.hp.test25.R;
import com.example.hp.test25.adapter.DealAdapter;
import com.example.hp.test25.object.Deal;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeExpensesFragment extends Fragment {

    private List<Deal> dealList = new ArrayList<>();

    private RadioGroup incomeExpensesRadio,incomeTypeRadio,expensesTypeRadio;
    private RadioButton incomeRadio,expensesRadio,clothesRadio,foodRadio,homeRadio,walkRadio,otherRadio,professionRadio,investRadio,othersRadio;
    private EditText moneyEdit,remarkEdit;
    private DatePicker datePicker;

    public IncomeExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_expenses, container, false);

        dealList = DataSupport.findAll(Deal.class);

        RecyclerView recyclerView = view.findViewById(R.id.income_expenses_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        final DealAdapter adapter = new DealAdapter(dealList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton addFab = (FloatingActionButton)view.findViewById(R.id.fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Deal deal = new Deal();
                if(dealList.isEmpty()){
                    LitePal.getDatabase();
                }
                showDealDialog(adapter);

            }
        });

        return view;
    }

    public void showDealDialog(final DealAdapter adapter){
        final Deal deal = new Deal();
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("交易")
                .customView(R.layout.dialog_deal , true)
                .positiveText("提交")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(TextUtils.isEmpty(moneyEdit.getText())){
                            Toast.makeText(getActivity(),"信息不全",Toast.LENGTH_SHORT).show();
                        }else {
                          //  Log.d("moneyEdit",moneyEdit.getText().toString());
                            deal.setMoney(Float.parseFloat(moneyEdit.getText().toString()));
                            if(incomeRadio.isChecked()||expensesRadio.isChecked()){
                                if(incomeRadio.isChecked()){
                                    deal.setDirection(Deal.INCOME);
                                }else {
                                    deal.setDirection(Deal.EXPENSES);
                                }

                                if(clothesRadio.isChecked()||foodRadio.isChecked()||homeRadio.isChecked()||walkRadio.isChecked()
                                        ||otherRadio.isChecked()||professionRadio.isChecked()||investRadio.isChecked()
                                        ||othersRadio.isChecked()){
                                    if(clothesRadio.isChecked()){
                                        deal.setType(Deal.CLOTHES);
                                    }else if(foodRadio.isChecked()) {
                                        deal.setType(Deal.FOOD);
                                    }else if(homeRadio.isChecked()){
                                        deal.setType(Deal.HOME);
                                    }else if(walkRadio.isChecked()){
                                        deal.setType(Deal.WALK);
                                    }else if(otherRadio.isChecked()){
                                        deal.setType(Deal.OTHER);
                                    }else if(professionRadio.isChecked()){
                                        deal.setType(Deal.PROFESSION);
                                    }else if(investRadio.isChecked()){
                                        deal.setType(Deal.INVEST);
                                    }else if(othersRadio.isChecked()){
                                        deal.setType(Deal.OTHERS);
                                    }

                                    deal.setRemark(remarkEdit.getText().toString());
                                    deal.setTime(datePicker.getYear()*10000+(datePicker.getMonth()+1)*100+datePicker.getDayOfMonth());

                                    //添加唯一主键
                                    SharedPreferences pref = getActivity().getSharedPreferences("deal_id", Context.MODE_PRIVATE);
                                    int dealId = pref.getInt("id",0);
                                    deal.setId(dealId);

                                    deal.save();
                                    dealList.add(deal);
                                    adapter.notifyDataSetChanged();

                                    SharedPreferences.Editor editor = pref.edit();  //主键加1
                                    editor.putInt("id",++dealId);
                                    editor.apply();

                                    BudgetFragment.adapter.notifyDataSetChanged(); //在添加收支情况后刷新预算
                                }else {
                                    Toast.makeText(getActivity(),"信息不全",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getActivity(),"信息不全",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }).build();
        incomeExpensesRadio = dialog.getCustomView().findViewById(R.id.income_expenses_radio);
        incomeTypeRadio = dialog.getCustomView().findViewById(R.id.income_type_Radio);
        expensesTypeRadio = dialog.getCustomView().findViewById(R.id.expenses_type_Radio);
        moneyEdit = dialog.getCustomView().findViewById(R.id.money);
        remarkEdit = dialog.getCustomView().findViewById(R.id.remark);
        incomeRadio = dialog.getCustomView().findViewById(R.id.income);
        expensesRadio = dialog.getCustomView().findViewById(R.id.expenses);
        clothesRadio = dialog.getCustomView().findViewById(R.id.clothes_radio);
        foodRadio = dialog.getCustomView().findViewById(R.id.food_radio);
        homeRadio = dialog.getCustomView().findViewById(R.id.home_radio);
        walkRadio = dialog.getCustomView().findViewById(R.id.walk_radio);
        otherRadio = dialog.getCustomView().findViewById(R.id.other_radio);
        professionRadio = dialog.getCustomView().findViewById(R.id.profession_radio);
        investRadio = dialog.getCustomView().findViewById(R.id.invest_radio);
        othersRadio = dialog.getCustomView().findViewById(R.id.others_radio);
        datePicker = dialog.getCustomView().findViewById(R.id.dataPicker);
        incomeExpensesRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.income:
                        incomeTypeRadio.setVisibility(View.VISIBLE);
                        expensesTypeRadio.setVisibility(View.GONE);
                        break;
                    case R.id.expenses:
                        incomeTypeRadio.setVisibility(View.GONE);
                        expensesTypeRadio.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }
        });
        dialog.show();

    }
}
