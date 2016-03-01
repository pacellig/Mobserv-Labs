package eurecom.fr.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import eurecom.fr.mycontactlist.Contact;
import eurecom.fr.mycontactlist.R;

class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    private final int id;
    private final List<Contact> contacts;
    private Contact contact;

    public ContactAdapter(Context context, int id, List<Contact> contacts)
    {
        super(context,id, contacts);
        this.context = context;
        this.id = id;
        this.contacts = contacts;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(id, parent, false);

        contact = contacts.get(position);
        TextView contactName= (TextView) rowView.findViewById(R.id.input_name);
        TextView contactEmail= (TextView) rowView.findViewById(R.id.input_email);
        TextView contactNumber= (TextView) rowView.findViewById(R.id.input_number);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.user_pic);

        contactName.setText(contact.getName());
        contactEmail.setText(contact.getEmail());
        contactNumber.setText(contact.getPhone());
        imageView.setImageBitmap(contact.getPicture());
        
        return rowView;
    }


}
