package com.jonathanvillafuerte.contactosuteq.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathanvillafuerte.contactosuteq.MapsActivity;
import com.jonathanvillafuerte.contactosuteq.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_ListarContactos extends RecyclerView.Adapter<Adapter_ListarContactos.RecViewHolder>{

    private List<dataContacts> list;
    Context context;
    public Adapter_ListarContactos(List<dataContacts> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.contacts, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecViewHolder holder, int position) {
        dataContacts dataContacts= list.get(position);

        holder.bind(dataContacts);
        Picasso.with(context).load(dataContacts.getUrl_Foto()).into(holder.fotoURl);


        holder.llamar.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_CALL);
            i.setData(Uri.parse("tel:"+dataContacts.getTelefono_Personal()));
            v.getContext().startActivity(i);
        });
        holder.ubicacion.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MapsActivity.class);
            i.putExtra("latitud",dataContacts.getLatitud());
            i.putExtra("longitud",dataContacts.getLongitud());
            i.putExtra("usuario",dataContacts.getNombres() + " "+ dataContacts.getApellidos());
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private Button llamar,ubicacion;
        private TextView nombres,apellidos,correo,telefonoHogar,telefonoPersonal,telefonoLaboral;
        private ImageView fotoURl;

        public RecViewHolder(View itemView) {
            super(itemView);

            llamar = itemView.findViewById(R.id.llamar);
            ubicacion = itemView.findViewById(R.id.ubication);
            nombres = itemView.findViewById(R.id.txtNombresContacts);
            apellidos = itemView.findViewById(R.id.txtApellidosContacts);
            correo = itemView.findViewById(R.id.txtcorreoelectronicoContacts);
            telefonoHogar = itemView.findViewById(R.id.txtTelefonoHogarContacts);
            telefonoPersonal = itemView.findViewById(R.id.txtTelefonoPersonalContacts);
            telefonoLaboral = itemView.findViewById(R.id.txtTelefonoLaboralContacts);
            fotoURl = itemView.findViewById(R.id.imgcontact);
        }

        private void bind(dataContacts datacontacts) {
            nombres.setText(datacontacts.getNombres());
            apellidos.setText(datacontacts.getApellidos());
            correo.setText(datacontacts.getCorreo_electronico());
            telefonoHogar.setText(datacontacts.getTelefono_Hogar());
            telefonoPersonal.setText(datacontacts.getTelefono_Personal());
            telefonoLaboral.setText(datacontacts.getTelefono_Laboral());
        }
    }
}