package info.androidhive.simdocomo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MoviesFragment extends Fragment {
    public MoviesFragment() {
    }

    private static final int CAMERA_REQUEST = 1888;


    Uri imageUri = null;

    public static ImageView imageView = null;
    MoviesFragment CameraActivity = null;
    String recid;

    public MoviesFragment(String mov) {
        this.recid = mov;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.imageView5);

        final Button photo = (Button) rootView.findViewById(R.id.button5);

        //Toast.makeText(getActivity(),"mov"+recid,Toast.LENGTH_SHORT).show();

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}