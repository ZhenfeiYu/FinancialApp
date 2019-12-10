package ng.com.obkm.finance;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ng.com.obkm.finance.R;
public class AccountInfoFragment extends Fragment {

    private TextView name,email,share_link;
    private ImageView img;
    private Button sign_out;
    private FirebaseAuth.AuthStateListener mAuthStateListner;

    public AccountInfoFragment(){

    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account_info, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String personName = user.getDisplayName();
            String personEmail = user.getEmail();
            Uri personPhoto = user.getPhotoUrl();
            name = root.findViewById(R.id.account_info_name_value);
            name.setText(personName);
            email = root.findViewById(R.id.account_info_email_value);
            email.setText(personEmail);
            img  = root.findViewById(R.id.user_photo);
            Glide.with(this).load(String.valueOf(personPhoto)).into(img);

        }

        share_link = root.findViewById(R.id.share_link);
        share_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharelink();
            }
        });

        sign_out = root.findViewById(R.id.sign_out);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void sharelink(){
        String link = "Hey! Check out our personalized finance application for free at:  " +
                "https://drive.google.com/open?id=1M0irCGgUrzF6CR2zYgmWwaU8SClxGAAh";
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, link);
        intent.setType("text/plain");
        startActivity(intent);
    }

}
