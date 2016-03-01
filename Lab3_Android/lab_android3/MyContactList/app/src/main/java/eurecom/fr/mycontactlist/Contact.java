package eurecom.fr.mycontactlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by pacellig on 17/11/2015.
 */
class Contact implements Serializable {

    private  String id;
    private  String name;
    private  String email;
    private  String phone;
    private  String picture;
    private  Bitmap pict;

    public Contact(JSONObject jsonObject, Bitmap photo) throws JSONException {

        id = jsonObject.getString("id");
        phone = jsonObject.getString("phone");
        email = jsonObject.getString("email");
        name = jsonObject.getString("name");
        picture = jsonObject.getString("pict");
        pict = photo;
    }
    @Override
    public String toString(){
        return String.format("%s - %s",name,email);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPictureURL() { return picture; }

    public Bitmap getPicture(){
        return pict;
    }

    private void writeObject(java.io.ObjectOutputStream out ) throws IOException{
        out.writeObject(id);
        out.writeObject(name);
        out.writeObject(email);
        out.writeObject(phone);
        out.writeObject(picture);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        pict.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes,0,bitmapBytes.length);

    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.id = (String)in.readObject();
        this.name = (String)in.readObject();
        this.email = (String)in.readObject();
        this.phone = (String)in.readObject();
        this.picture = (String)in.readObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        pict = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }
}

