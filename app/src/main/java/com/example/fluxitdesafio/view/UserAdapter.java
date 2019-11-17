package com.example.fluxitdesafio.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fluxitdesafio.R;
import com.example.fluxitdesafio.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter {

    private List<User> userList;
    private UserAdapterListener userAdapterListener;

    public UserAdapter(List<User> userList, UserAdapterListener listener) {
        this.userList = userList;
        this.userAdapterListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cell_main, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        User user = this.userList.get(position);
        UserViewHolder userViewHolder = (UserViewHolder) holder;
        userViewHolder.bindUser(user);
    }

    @Override
    public int getItemCount() {
        return this.userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewThumbnailMain)
        ImageView imageViewThumbnailMain;
        @BindView(R.id.textViewUsernameMain)
        TextView textViewUsernameMain;
        private User userCell;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer adapterPosition = getAdapterPosition();
                    User user = userList.get(adapterPosition);
                    userAdapterListener.listenerSelectionUser(adapterPosition, user);
                }
            });
        }

        public void bindUser(User userCell) {
            this.userCell = userCell;
            Glide.with(itemView)
                    .load(this.userCell.getPicture().getThumbnail())
                    .into(imageViewThumbnailMain);
            textViewUsernameMain.setText(this.userCell.getName().getTitle() + " " + this.userCell.getName().getFirst() + " " + this.userCell.getName().getLast());

        }
    }

    public interface UserAdapterListener {
        public void listenerSelectionUser(Integer position, User user);
    }
}
