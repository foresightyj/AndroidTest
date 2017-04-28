package com.fht360.com.helloandroid;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PostsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MESSAGE = "message";
    private static final String TAG = "PostsFragment";
    // TODO: Rename and change types of parameters
    private String message;

    private RecyclerView mPostRecyclerView;
    private PostAdapter mPostAdapter;

    private OnFragmentInteractionListener mListener;

    public PostsFragment() {
        // Required empty public constructor
    }

    private void updateUI() {
        mPostAdapter = new PostAdapter();
        mPostRecyclerView.setAdapter(mPostAdapter);
    }

    public void updatePosts(PostModel[] posts)
    {
        mPostAdapter.updatePosts(posts);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param message Parameter 1.
     * @return A new instance of fragment PostsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostsFragment newInstance(String message) {
        PostsFragment fragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MESSAGE, message);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            message = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_posts, container, false);
        mPostRecyclerView = (RecyclerView)v.findViewById(R.id.posts_recycler_view);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        Bundle bundle = this.getArguments();
        String myValue = bundle.getString(ARG_MESSAGE);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class PostHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mContentTextView;

        public PostHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.list_item_post_title);
            mContentTextView = (TextView) itemView.findViewById(R.id.list_item_post_content);
        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {
        private static final String TAG = "PostAdapter";
        private List<PostModel> mPosts;
        public PostAdapter() {
            List<PostModel> posts = new ArrayList<>();
            this.mPosts = posts;
        }

        public void updatePosts(PostModel[] posts)
        {
            this.mPosts = Arrays.asList(posts);
            this.notifyDataSetChanged();
        }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(TAG, "onCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_post, parent, false);
            return new PostHolder(view);
        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder, position = " + position);
            PostModel post = mPosts.get(position);
            holder.mTitleTextView.setText(post.title);
            holder.mContentTextView.setText(post.content);
        }

        @Override
        public int getItemCount() {
            Log.i(TAG, "getItemCount = " + mPosts.size());
            return mPosts.size();
        }
    }
}
