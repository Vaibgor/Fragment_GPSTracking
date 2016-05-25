package info.androidhive.simdocomo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 3/2/2016.
 */
public class UserCustomAdapterMatrimonySearch2 extends ArrayAdapter<category2> {

    Context context;
    int layoutResourceId;
    ArrayList<category2> data = new ArrayList<category2>();


    public UserCustomAdapterMatrimonySearch2(Context context, int layoutResourceId,
                                            ArrayList<category2> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    //Read more: http://www.androidhub4you.com/2013/02/muftitouch-listview-multi-click.html#ixzz3VqRFUlXo

    public View getView(final int position, View convertView, ViewGroup parent) {
        View list = convertView;
        UserHolder holder = null;
        if (list == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            list = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.image = (ImageView) list.findViewById(R.id.imageView4);

            holder.fname = (TextView) list.findViewById(R.id.textView1);
            holder.age = (TextView) list.findViewById(R.id.textView2);


            list.setTag(holder);
        } else {
            holder = (UserHolder) list.getTag();
        }
        category2 member = data.get(position);
        final String temp = member.getUid();
        final  String name=member.getFirstName();
        //	 holder.uid.setText(member.getUid());
        if (member.getImage() != null) {
            Log.d("In if ", "in if member.getImage()");
           // holder.image.setImageBitmap(member.getImage());
            holder.image.setImageResource(R.drawable.gold);
        } else {
            Log.d("In Else ", "in else member.getImage()");
            //holder.image.setImageResource(R.drawable.profilepic);
        }

        // holder.srno.setText(member.getSrno());
        holder.fname.setText(member.getFirstName());
        holder.age.setText(member.getAge());
        //	 holder.lname.setText(member.getLastName());

//        holder.fname.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//
//
//                ((open) context).viewMemberInfo(temp);
//
//            }
//        });
//
//
//        holder.image.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//



        // ((open)info.androidhive.tabsswipe).viewMemberInfo(temp);
//
//            }
//        });
        list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open(viewMemberInfo(temp));
                //     ((open) context).viewMemberInfo(temp);
//                Intent op=new Intent(context,MainActivity.class);
//                op.putExtra("cat_it", temp);
//                op.putExtra("cus_name", name);
//
//                //  intent.putExtra("vendor_code", code);
//                context.startActivity(op);
//                Toast.makeText(context, name, Toast.LENGTH_LONG).show();
            }
        });
        return list;
    }

    static class UserHolder {
        TextView textName;
        TextView srno;
        // TextView textLocation;
        ImageView image;
        Button btnView;
        TextView uid;
        TextView age;
        TextView fname;
        TextView lname;
        // TextView textLocation;

    }

}

