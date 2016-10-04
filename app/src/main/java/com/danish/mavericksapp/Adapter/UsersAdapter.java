package com.danish.mavericksapp.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.danish.mavericksapp.Model.User;
import com.danish.mavericksapp.R;

import java.util.List;

/**
 * Created by DaNii on 29/09/2016.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private List<User> mUserItems;
    private LayoutInflater mLayoutInflater;
    FragmentManager mFragmentManager;
    Context context;

    public UsersAdapter(List<User> userItems, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.mUserItems = userItems;
        mFragmentManager = fragmentManager;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = mLayoutInflater.inflate(R.layout.custom_user_name_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(customView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        User user = mUserItems.get(position);
        holder.setData(user, position);
    }

    @Override
    public int getItemCount() {
        return mUserItems.size();
    }

    public void addItem(int position, User userItem) {
        mUserItems.add(position, userItem);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mUserItems.size());
        //notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mUserItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mUserItems.size());
        //notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        /*EditText mFirstNameET, mLastNameET;
        Button mCancelBtn, mAddBtn;*/
        TextView mUserNameTV;

        int position;
        User userItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            mUserNameTV = (TextView) itemView.findViewById(R.id.userNameTv);
        }

        public void setData(User user, int position) {

            String firstName = user.getFirstName();
            String lastName = user.getLastName();


            String firstUpperString = firstName.substring(0,1).toUpperCase() + firstName.substring(1);
            String lastUpperString = lastName.substring(0,1).toUpperCase() + lastName.substring(1);

            String fullName = lastUpperString + " , " + firstUpperString;
            mUserNameTV.setText(fullName);

            this.position = position;
            this.userItem = user;


        }


    }
}
