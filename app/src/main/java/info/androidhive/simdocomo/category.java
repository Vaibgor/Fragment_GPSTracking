package info.androidhive.simdocomo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

public class category {
    String name1;
    String address;
    String location;
    String imageurl;
    String age;
    int srno;
    String u_id;
    String first_name;
    String last_name;
    String code;
    String name;
    private Bitmap image;
    private UserCustomAdapterMatrimonySearch userAdapter;


    public int getSrno() {
        return srno;
    }

    public void setSrno(int srno) {
        this.srno = srno;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imageurl;
    }

    public void setImgUrl(String imageurl) {
        this.imageurl = imageurl;
    }


    public Bitmap getImage() {
        Log.d("image", "get image");
        return image;
    }

    public String getUid() {
        return u_id;
    }

    public void setUid(String u_id) {
        this.u_id = u_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }


    public String getArea() {
        return location;
    }

    public void setArea(String location) {
        this.location = location;
    }
    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String age) {
        this.code = code;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public UserCustomAdapterMatrimonySearch getAdapter() {
        return userAdapter;
    }

    public void setAdapter(UserCustomAdapterMatrimonySearch userAdapter) {
        this.userAdapter = userAdapter;
    }

    public void loadImage(UserCustomAdapterMatrimonySearch userAdapter) {
        // HOLD A REFERENCE TO THE ADAPTER
        this.userAdapter = userAdapter;
        if (imageurl != null && !imageurl.equals("")) {
            new ImageLoadTask().execute(imageurl);
        }
    }

    public category(String name, String address) {
        super();
        Log.d("User ", "member constructor is called");
        this.name = name;
        this.address = address;
        //this.location = location;
    }

    public category(String u_id, String first_name, String last_name) {
        // TODO Auto-generated constructor stub
        super();
        Log.d("user", "member constructor is called");

        this.image = null;
        this.u_id = u_id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public category(String u_id, int i, String first_name, String last_name) {
        // TODO Auto-generated constructor stub
        super();
        this.u_id = u_id;
        this.srno = i;
        this.first_name = first_name;
        this.last_name = last_name;

    }


    public category(String pro, String ac_no, String dob,String area,
                    String code,String name) {
        // TODO Auto-generated constructor stub
        //super();

        // Log.d("In constructor", filename);
        this.u_id = pro;

        this.first_name = ac_no;

        this.age = dob;
        //this.imageurl = img;
        this.location=area;
        this.code = code;
        this.name1=name;
        // Log.d("In constructor imageurl", this.imageurl);
    }

    private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            Log.d("ImageLoadTask", "Loading image...");
        }

        // param[0] is img url
        protected Bitmap doInBackground(String... param) {
            //   Log.d("ImageLoadTask", "Attempting to load image URL: " + param[0]);
            try {
                Bitmap b = BitmapFactory.decodeStream((InputStream) new URL(param[0]).getContent());
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onProgressUpdate(String... progress) {
            // NO OP
        }

        protected void onPostExecute(Bitmap ret) {
            if (ret != null) {
                //    Log.d("ImageLoadTask", "Successfully loaded ");
                image = ret;
                if (userAdapter != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    userAdapter.notifyDataSetChanged();
                }
            } else {
                //       Log.e("ImageLoadTask", "Failed to load ");
            }
        }
    }
}