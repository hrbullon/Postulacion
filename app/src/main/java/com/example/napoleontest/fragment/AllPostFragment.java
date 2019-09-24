package com.example.napoleontest.fragment;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.actitivies.DetailPostActivity;
import com.example.napoleontest.adapter.PostAllAdapter;
import com.example.napoleontest.adapter.PostUserAdapter;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;
import com.example.napoleontest.utilities.RecyclerItemTouchHelper;
import com.yarolegovich.lovelydialog.LovelyDialogCompat;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class AllPostFragment extends Fragment implements AllPostView, PostAllAdapter.ItemClickListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private static View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String idUser;
    List mData;

    ProgressDialog pd;
    PostAllAdapter adapter;

    //private OnFragmentInteractionListener mListener;

    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;

    AllPostPresenter mAllPostPresenter;

    public AllPostFragment() {

    }

    public static AllPostFragment newInstance(String param1, String param2) {
        AllPostFragment fragment = new AllPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_post,container,false);
        MasterApp.getAppComponent().inject(this);

        Bundle bundle= getActivity().getIntent().getExtras();
        idUser = bundle.getString("idUser");

        initDataAllPost();



        return view;
    }

    private void initDataAllPost() {
        mAllPostPresenter = new AllPostPresenter(this,getActivity(),mRetrofit,mSecurityRepository);
        mAllPostPresenter.initDataPostAllRecyclerview(idUser);
    }

    @Override
    public void getDataPost(List<PostUser> mListPost) {
        List<PostUser> mListClean = new ArrayList<PostUser>();

        //busca si tiene registros en la tabla post de sqlite, sino guarda automaticamente todos los post en sqlite
        if(!mAllPostPresenter.checkExistDataTablePost()){
            mAllPostPresenter.savePostSqlite(mListPost);
        }

        for(int i = 0; i< mListPost.size();i++){

            if(!mListPost.get(i).getUserId().equals(idUser)){
                mListClean.add(mListPost.get(i));
            }
        }

        mData = new ArrayList(mListClean);
        RecyclerView recyclerView = view.findViewById(R.id.rv_list_allpost);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostAllAdapter(getActivity(),mListClean,this);
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    @Override
    public void showProgressDialog() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Cargando todos los post");
        pd.show();
    }

    @Override
    public void hideProgressDialog() {
        pd.dismiss();
    }

    @Override
    public void showSaveSqlite() {
        new LovelyStandardDialog(getActivity(), LovelyStandardDialog.ButtonLayout.VERTICAL)
                .setTopColorRes(R.color.background_alert_dialog)
                .setButtonsColorRes(R.color.text_alert_dialog)
                .setIcon(R.drawable.alert_updated)
                .setTitle(R.string.sqlite_title)
                .setMessage(R.string.sqlite_message)
                .setPositiveButton("CONTINUAR", LovelyDialogCompat.wrap(
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //nada..!!
                            }
                        }))
                .show();
    }

    @Override
    public void onItemClick(View view, int position) {
        callToPostDetail(((PostUser) mData.get(position)).getUserId(),((PostUser) mData.get(position)).getBody(), ((PostUser) mData.get(position)).getId());
    }

    private void callToPostDetail(String userId, String body, String idPost) {
        Intent intent = new Intent(getActivity(), DetailPostActivity.class);
        intent.putExtra("iduserlog", idUser);
        intent.putExtra("iduser", userId);
        intent.putExtra("idpost", idPost);
        intent.putExtra("body", body);
        startActivity(intent);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PostAllAdapter.ViewHolder) {

        //    String name = mData.get(viewHolder.getAdapterPosition()).getName();


        /*    final ClipData.Item deletedItem = mData.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();


            mAdapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }
    }
}
