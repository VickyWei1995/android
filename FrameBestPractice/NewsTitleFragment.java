package com.example.framebestpractice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPage;

    @Override
    public View onCreateView(LayoutInflater infalte, ViewGroup container, Bundle saveInstanceState) {
        View view = infalte.inflate(R.layout.news_title_frame, container, false);
        RecyclerView newsRecyclerView = (RecyclerView) view.findViewById
                (R.id.news_title_frame);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsRecyclerView.setLayoutManager(layoutManager);
        List<news> myNewsList = initNewsList();
        NewsAdapter newsAdapter = new NewsAdapter(myNewsList);
        newsRecyclerView.setAdapter(newsAdapter);
        return view;
    }

    private List<news> initNewsList() {
        List<news> newList = new ArrayList<>();
        for (int i=0;i<50;i++) {
            news news1 = new news();
            news1.setTitle("This is a title" + i);
            news1.setContent(getRandomLengthContent("This is content"));
            newList.add(news1);
        }
        return newList;
    }

    private String getRandomLengthContent(String content) {
        Random randomNum = new Random();
        int length = randomNum.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<length;i++) {
            builder.append(content);
        }
        return builder.toString();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity().findViewById(R.id.news_content_layout) != null) {
            isTwoPage = true;
        } else {
            isTwoPage = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
        private List<news> myNewsList;
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;
            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView) view.findViewById(R.id.news_title);
            }
        }
        public NewsAdapter(List<news> iniList) {
            myNewsList = iniList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    news myNews = myNewsList.get(holder.getAdapterPosition());
                    if (isTwoPage) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(myNews.getTitle(), myNews.getContent());
                    } else {
                        NewsContentActivity.actionStart(getActivity(), myNews.getTitle(), myNews.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            news myNews = myNewsList.get(position);
            holder.newsTitleText.setText(myNews.getTitle());
        }

        @Override
        public int getItemCount() {
            return myNewsList.size();
        }
    }

}
