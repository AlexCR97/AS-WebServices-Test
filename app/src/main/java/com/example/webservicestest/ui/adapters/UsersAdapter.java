package com.example.webservicestest.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.webservicestest.R;
import com.example.webservicestest.entities.User;
import com.example.webservicestest.services.WebService;
import com.example.webservicestest.services.usecases.GetUserImageUC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersAdapter extends ArrayAdapter<User> {

    public static class Holder {
        ImageView ivImage;
        TextView tvId;
        TextView tvName;
        TextView tvBirthDate;
    }

    private Context context;
    private int resource;
    private List<User> users;

    private Map<Integer, Bitmap> cacheImages = new HashMap<>();

    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> users) {
        super(context, resource, users);

        this.context = context;
        this.resource = resource;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);

            holder = new Holder();
            holder.ivImage = convertView.findViewById(R.id.ivImage);
            holder.tvId = convertView.findViewById(R.id.tvId);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvBirthDate = convertView.findViewById(R.id.tvBirthDate);

            convertView.setTag(holder);
        }
        else
            holder = (Holder) convertView.getTag();

        final User user = users.get(position);

        holder.tvId.setText(String.valueOf(user.getId()));
        holder.tvName.setText(user.getName() + " " + user.getLastName());
        holder.tvBirthDate.setText(user.getBirthDate());

        if (!cacheImages.containsKey(user.getId())) {
            final Holder finalHolder = holder;
            new GetUserImageUC(context, new WebService.RequestAcceptedListener<Bitmap>() {
                @Override
                public void onRequestAccepted(Bitmap response) {
                    cacheImages.put(user.getId(), response);
                    finalHolder.ivImage.setImageBitmap(response);
                }
            }, new WebService.RequestRejectedListener() {
                @Override
                public void onRequestRejected() {
                    Log.d("JSON", "Could not obtain bitmap from GetUserImageUC");
                }
            }).sendRequest(user.getImage());
        }
        else {
            Bitmap userImage = cacheImages.get(user.getId());
            holder.ivImage.setImageBitmap(userImage);
        }

        return convertView;
    }
}
