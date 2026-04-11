package ac.id.unri.platform.aktivitas;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private View location, phone, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactus);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        location = findViewById(R.id.layout_location);
        phone = findViewById(R.id.layout_phone);
        email = findViewById(R.id.layout_email);

        location.setOnClickListener(this);
        phone.setOnClickListener(this);
        email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.layout_location) {
            TextView tvLoc = findViewById(R.id.tv_location);
            String alamat = tvLoc.getText().toString();

            Uri IntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(alamat));

            Intent loc = new Intent(Intent.ACTION_VIEW, IntentUri);
            loc.setPackage("com.google.android.apps.maps");
            startActivity(loc);
        } else if (id == R.id.layout_email) {
            String targetEmail = "afdhalfidi.ansori015@gmail.com";
            String subject = Uri.encode("Subjek");
            String body = Uri.encode("Isi Pesan");
            String mailUri = "mailto:" + targetEmail + "?subject=" + subject + "&body=" + body;

            Intent mail = new Intent(Intent.ACTION_SENDTO);
            mail.setData(Uri.parse(mailUri));

            startActivity(mail);
        } else if (id == R.id.layout_phone) {
            TextView tvPhone = findViewById(R.id.tv_phone);
            String nomor = tvPhone.getText().toString().trim();

            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + nomor));
            startActivity(call);
        }
    }

}