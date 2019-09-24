package com.example.napoleontest.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.napoleontest.R;
import com.example.napoleontest.adapter.PostUserAdapter;
import com.example.napoleontest.aplicattion.MasterApp;
import com.example.napoleontest.domain.model.PostUser;
import com.example.napoleontest.domain.repository.interfaces.SecurityRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MyPostFragment extends Fragment implements MyPostView, PostUserAdapter.ItemClickListener {
    private static View view;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;
    String idUser;
    ProgressDialog pd;
    MyPostPresenter myPostPresenter;
    PostUserAdapter adapter;


    @Inject
    public Retrofit mRetrofit;

    @Inject
    public SecurityRepository mSecurityRepository;



    public MyPostFragment() {
        // Required empty public constructor
    }

    public static MyPostFragment newInstance(String param1, String param2) {
        MyPostFragment fragment = new MyPostFragment();
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
        view = inflater.inflate(R.layout.fragment_my_post,container,false);
        MasterApp.getAppComponent().inject(this);

        Bundle bundle= getActivity().getIntent().getExtras();
        idUser = bundle.getString("idUser");
        
        initDataPostUser(idUser);

        return view;
    }

    private void initDataPostUser(String idUser) {
        myPostPresenter = new MyPostPresenter(this,getActivity(),mRetrofit,mSecurityRepository);
        myPostPresenter.initDataPostUserRecyclerview(idUser);

    }

    @Override
    public void getDataUser(List<PostUser> mListPostUser) {
        RecyclerView recyclerView = view.findViewById(R.id.rv_list_postuser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PostUserAdapter(getActivity(),mListPostUser,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgressDialog() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Cargando mis Post");
        pd.show();
    }

    @Override
    public void hideProgressDialog() {
        pd.dismiss();
    }

    @Override
    public void onItemClick(View view, int position) {
       // callToGaleryProduct(((Categories) mData.get(position)).getNombre(), ((Categories) mData.get(position)).getDescripcion());
    }


}
