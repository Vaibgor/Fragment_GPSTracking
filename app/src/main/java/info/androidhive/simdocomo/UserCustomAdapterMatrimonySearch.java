package info.androidhive.simdocomo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
 * Created by User on 1/16/2016.
 */
public class UserCustomAdapterMatrimonySearch extends ArrayAdapter<category> {

    Context context;
    int layoutResourceId;
    String agency_id;
    ArrayList<category> data = new ArrayList<category>();


    public UserCustomAdapterMatrimonySearch(Context context, int layoutResourceId,
                                            ArrayList<category> data,String agency_id) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.agency_id=agency_id;

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
            holder.area = (TextView) list.findViewById(R.id.textView3);

            list.setTag(holder);
        } else {
            holder = (UserHolder) list.getTag();
        }
        category member = data.get(position);
        final String temp = member.getUid();
        final String cust_name = member.getAge();
        final String accNo = member.getFirstName();
        final String name1 = member.getName1();
        final String code = member.getCode();

        //	 holder.uid.setText(member.getUid());

      /*  if (member.getImage() != null) {
            Log.d("In if ", "in if member.getImage()");
            //holder.image.setImageResource(R.drawable.gold);
            holder.image.setImageBitmap(member.getImage());
        } else {
            Log.d("In Else ", "in else member.getImage()");
            //holder.image.setImageResource(R.drawable.profilepic);
        }*/

        // holder.srno.setText(member.getSrno());
        holder.image.setImageResource(R.drawable.gold);
        holder.fname.setText(member.getFirstName());
        holder.age.setText(member.getAge());
        holder.area.setText(member.getArea());

        list.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //open(viewMemberInfo(temp));
                //     ((open) context).viewMemberInfo(temp);
                Intent op = new Intent(context, MainActivity.class);
                op.putExtra("cat_it", temp);
                op.putExtra("cust_name", cust_name);
                op.putExtra("name", name1);
                op.putExtra("code", code);
                op.putExtra("account_no", accNo);
                op.putExtra("agency_id",agency_id);
                context.startActivity(op);
            }
        });
        return list;
    }

    static class UserHolder {
        TextView textName;
        TextView area;
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
