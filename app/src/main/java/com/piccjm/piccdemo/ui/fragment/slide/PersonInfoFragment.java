package com.piccjm.piccdemo.ui.fragment.slide;

import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.piccjm.piccdemo.R;
import com.piccjm.piccdemo.bean.UserBean;
import com.piccjm.piccdemo.presenter.slide.PersonInfoPresenter;
import com.piccjm.piccdemo.presenter.slide.PersonInfoPresenterImpl;
import com.piccjm.piccdemo.ui.fragment.base.BaseFragment;
import com.piccjm.piccdemo.ui.view.LoadingDialog;
import com.piccjm.piccdemo.ui.view.SexDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by mangowangwang on 2017/12/12.
 */

public class PersonInfoFragment extends BaseFragment<PersonInfoPresenterImpl> implements PersonInfoPresenter.View {

    private UserBean mPersonInfo;

    @BindView(R.id.linear_nickname)
    TextView tv_nickName;
    @BindView(R.id.linear_name)
    TextView tv_name;
    @BindView(R.id.linear_password)
    TextView tv_password;
    @BindView(R.id.linear_birthday)
    TextView tv_birthday;
    @BindView(R.id.linear_tv_sex)
    TextView tv_sex;


    SexDialog sexDialog;
    LoadingDialog loadingDialog;

    String selected_sex;

    @OnClick(R.id.linear_sex)
    public void upDataSex() {
         sexDialog = new SexDialog(getContext(),tv_sex.getText().toString());
        sexDialog.setTitle("性别选择");
        //selfDialog.setMessage("确定退出应用?");
        sexDialog.setYesOnclickListener("确定", new SexDialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                RadioButton radioButton = (RadioButton)sexDialog.findViewById(sexDialog.sexGroup.getCheckedRadioButtonId());
                selected_sex = radioButton.getText().toString();
                if (selected_sex.equals(tv_sex.getText().toString()))
                {
                    sexDialog.dismiss();
                }else
                {
                    mPresenter.updatePersonInfo("sex",selected_sex);
                    sexDialog.dismiss();
                    LoadingDialog.Builder loadBuilder=new LoadingDialog.Builder(getContext())
                            .setMessage("正在提交...")
                            .setCancelable(false)
                            .setCancelOutside(true);
                    loadingDialog=loadBuilder.create();
                    loadingDialog.show();
                }


            }
        });
//                selfDialog.setNoOnclickListener("取消", new SexDialog.onNoOnclickListener() {
//                    @Override
//                    public void onNoClick() {
//                        Toast.makeText(MainActivity.this, "点击了--取消--按钮", Toast.LENGTH_LONG).show();
//                        selfDialog.dismiss();
//                    }
//                });
        sexDialog.show();
    }



    @Override
    protected void loadData() {
        mPresenter.fetchPersonInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personinfo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }


    @Override
    public void refresh() {

        mPersonInfo = mPresenter.getPersonInfo();
        tv_nickName.setText(mPersonInfo.getNickname());
        tv_birthday.setText(mPersonInfo.getBirthday());
        tv_name.setText(mPersonInfo.getName());
        tv_password.setText(mPersonInfo.getPassword());
        tv_sex.setText(mPersonInfo.getSex());
    }

    @Override
    public void resultOfSexUpdate(String result)
    {
        if (result.equals("succeed"))
        {
            tv_sex.setText(selected_sex);
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();

        }else if(result.equals("fail"))
        {
            loadingDialog.dismiss();
            Toast.makeText(getContext(), "修改失败,请重试!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultOfError() {
        loadingDialog.dismiss();
    }
}
