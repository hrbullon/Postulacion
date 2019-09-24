package com.example.napoleontest.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.napoleontest.R;
import com.example.napoleontest.adapter.PostAllAdapter;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class FavoritesFragment extends Fragment implements FavoriteView, PostAllAdapter.ItemClickListener {
    private static View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String idUser;
    FavoritePresenter mFavoritePresenter;
    ProgressDialog pd;
    PostAllAdapter adapter;

    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;

    AllPostPresenter mAllPostPresenter;

    //private OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        view = inflater.inflate(R.layout.fragment_favorites,container,false);
        MasterApp.getAppComponent().inject(this);

        Bundle bundle= getActivity().getIntent().getExtras();
        idUser = bundle.getString("idUser");


        initDataFavorite();

        return view;
    }

    private void initDataFavorite() {
        mFavoritePresenter = new FavoritePresenter(this,getActivity(), mRetrofit,mSecurityRepository, idUser);
        mFavoritePresenter.initDataFavorite();

    }

    @Override
    public void showProgressDialog() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Cargando mis favoritos");
        pd.show();
    }

    @Override
    public void hideProgressDialog() {
        pd.dismiss();
    }

    @Override
    public void getAllDataPost(List<PostUser> mListPostUser) {
        List<PostUser> mListClean = new ArrayList<PostUser>();

        for(int i = 0; i< mListPostUser.size();i++){

            if(!mListPostUser.get(i).getUserId().equals(idUser)){
                mListClean.add(mListPostUser.get(i));
            }
        }
        mFavoritePresenter.selectMyFavoritePost(mListClean);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataFavoritePost(ArrayList<PostUser> mListFavorite) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_list_allpost);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostAllAdapter(getActivity(),mListFavorite,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

}
