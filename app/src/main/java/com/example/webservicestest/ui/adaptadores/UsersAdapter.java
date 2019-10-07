package com.example.webservicestest.ui.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.webservicestest.R;
import com.example.webservicestest.entidades.Usuario;
import com.example.webservicestest.servicios.ServicioWeb;
import com.example.webservicestest.servicios.implementaciones.SWImagenUsuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersAdapter extends ArrayAdapter<Usuario> {

    public static class Holder {
        ImageView ivImage;
        TextView tvId;
        TextView tvName;
        TextView tvBirthDate;
    }

    private Context context;
    private int resource;
    private List<Usuario> users;

    private Map<Integer, Bitmap> cacheImages = new HashMap<>();

    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<Usuario> users) {
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

        final Usuario user = users.get(position);

        holder.tvId.setText(String.valueOf(user.getId()));
        holder.tvName.setText(user.getNombre() + " " + user.getApellido());
        holder.tvBirthDate.setText(user.getFechaNacimiento());

        if (!cacheImages.containsKey(user.getId())) {
            final Holder finalHolder = holder;
            new SWImagenUsuario(context, new ServicioWeb.EventoPeticionAceptada<Bitmap>() {
                @Override
                public void alAceptarPeticion(Bitmap respuesta) {
                    cacheImages.put(user.getId(), respuesta);
                    finalHolder.ivImage.setImageBitmap(respuesta);
                }
            }, new ServicioWeb.EventoPeticionRechazada() {
                @Override
                public void alRechazarPeticion() {
                    Log.d("JSON", "Could not obtain bitmap from SWImagenUsuario");
                }
            }).enviarPeticion(user.getRutaImagen());
        }
        else {
            Bitmap userImage = cacheImages.get(user.getId());
            holder.ivImage.setImageBitmap(userImage);
        }

        return convertView;
    }
}
