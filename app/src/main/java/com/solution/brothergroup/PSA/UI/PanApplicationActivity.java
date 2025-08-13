package com.solution.brothergroup.PSA.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.solution.brothergroup.R;

public class PanApplicationActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_panLogin, btn_purchaseToken, btn_viewCredential;
    WebView t_n_c;
    Button okButton;
    TextView tv_psaId, tv_paspassword, tv_requestedId, tv_userid, tv_emailId, tv_mobileno;
    String PANID, psaRequestId, outletId, userId, emailId, mobileNo;
    String id, amount, panType;
    // List<ListPanCharge> ListPanCharge=new ArrayList<>();
   // String panList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_application);

        PANID = getIntent().getExtras().getString("PANID", "");
        psaRequestId = getIntent().getExtras().getString("psaRequestId", "");
        outletId = getIntent().getExtras().getString("outletId", "");
        userId = getIntent().getExtras().getString("userId", "");
        emailId = getIntent().getExtras().getString("emailId", "");
        mobileNo = getIntent().getExtras().getString("mobileNo", "");

        btn_panLogin =  findViewById(R.id.btn_panLogin);
        btn_purchaseToken = findViewById(R.id.btn_purchaseToken);
        btn_viewCredential = findViewById(R.id.btn_viewCredential);
        t_n_c = findViewById(R.id.t_n_c);
        t_n_c.loadData(getString(R.string.pan_tc),"text/html", "UTF-8");
       /* t_n_c.loadData("<body><strong>Terms &amp; Conditions</strong><br>\n" +
                "                PSA shall take written approval from UTI before organizing any PAN Card camp.</br>\n" +
                "                <ul class=\"anclnk\">\n" +
                "                    <li>\n" +
                "                        PSA shall not charge any extra fees/convenience charges for PAN card application.\n" +
                "                    </li>\n" +
                "\n" +
                "                    <li>\n" +
                "                        PSA Shall not issue any hand written/self-designed/created receipt to customer.\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        Proper process has to be followed while applying PAN card. (Refer manual)\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        PSA shall not be blacklisted by any bank, government, private organizations.\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        PSA should not have any criminal record.\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        PSA Is authorized to collect PAN applications at Rs.107 (inclusive of GST)\n" +
                "                        You may download any one of the following app (from google store) in your Smart Phone at your own discretion.\n" +
                "                        Cam Scanner\n" +
                "                        Smart Docs Scanner\n" +
                "                        Convert JPG to PDF &amp; Scanner\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        To merge Two or More .PDF pages use following links\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"https://www.pdfmerge.com\">https://www.pdfmerge.com</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"https://www.pdfjoiner.com\">http://pdfjoiner.com</a>\n" +
                "\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        Click the photo of following customer documents, using above app and send it to your own email in .PDF format\n" +
                "                        PAN Application form, signed by customer with his photograph\n" +
                "                        ID proof\n" +
                "                        Address Proof\n" +
                "                        Date of Birth document\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        Click photo of following documents in .JPEG/.JPG format with defined specification and send it to your email address\n" +
                "                        Photo Scanning 300 dpi,Colour,213 X 213 px (Size:less than 30 kb)\n" +
                "                        Signature scanning 600 dpi black and white (Size:less than 60 kb)\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        To convert the scanned image as per above specification you may use following links or any software like photoshop etc or link as per your wish\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"https://online-converting.com/image\">https://online-converting.com/image</a>\n" +
                "                    </li>\n" +
                "                    <li>\n" +
                "                        <a href=\"https://www.imgonline.com.ua/eng/resize-image.php\">https://www.imgonline.com.ua/eng/resize-image.php</a>\n" +
                "                    </li>\n" +
                "                    <li>Save all of the above documents in your computer and Upload on UTIISL website</li>\n" +
                "                </ul></body>", "text/html", "UTF-8");*/

        btn_viewCredential.setOnClickListener(this);
        btn_purchaseToken.setOnClickListener(this);
        btn_panLogin.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("PAN Service Agent(PSA)");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        ///get PSA details

        //pan list charge
        //id=getIntent().getExtras().getString("id");
        //amount=getIntent().getExtras().getString("amount");
        // panType=getIntent().getExtras().getString("panType");


      //  panList = getIntent().getExtras().getString("panList");





    }

    @Override
    public void onClick(View view) {

        if (view == btn_panLogin) {

            Intent intent = new Intent(PanApplicationActivity.this, UTILogin.class);
            startActivity(intent);
            finish();
        } else if (view == btn_viewCredential) {

            viewCredential();


        } else if (view == btn_purchaseToken) {


            Intent intent = new Intent(PanApplicationActivity.this, PurchaseToken.class);

           // intent.putExtra("panList", panList);
            intent.putExtra("PANID", PANID);
           /* intent.putExtra("id",id);
            intent.putExtra("amount",amount);
            intent.putExtra("panType",panType);*/
            startActivity(intent);
        }

    }

    private void viewCredential() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_view_credential, null);
        tv_psaId = viewMyLayout.findViewById(R.id.psaid);
        tv_paspassword = viewMyLayout.findViewById(R.id.paspassword);
       /* tv_requestedId=viewMyLayout.findViewById(R.id.requestedId);
        tv_userid=viewMyLayout.findViewById(R.id.userid);
        tv_emailId=viewMyLayout.findViewById(R.id.emailId);
        tv_mobileno=viewMyLayout.findViewById(R.id.mobileno);*/
        okButton = viewMyLayout.findViewById(R.id.okButton);
        TextView copyPsaId = viewMyLayout.findViewById(R.id.copyPsaId);
        TextView copyPass = viewMyLayout.findViewById(R.id.copyPass);

        tv_psaId.setText(PANID);
        tv_paspassword.setText(PANID);
       /* tv_requestedId.setText(psaRequestId);
        tv_userid.setText(userId);
        tv_emailId.setText(emailId);
        tv_mobileno.setText(mobileNo);*/

        final Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        dialog.setCancelable(false);
        dialog.setContentView(viewMyLayout);
       /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        copyPsaId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(tv_psaId.getText().toString());
            }
        });
        copyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(tv_paspassword.getText().toString());
            }
        });

        dialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    private void setClipboard(String text) {

        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Share Link", text);
                clipboard.setPrimaryClip(clip);
            }

            Toast.makeText(PanApplicationActivity.this, "Text Copied to clipboard", Toast.LENGTH_LONG).show();
        } catch (Exception e) {

        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_view) {

        }
        return super.onOptionsItemSelected(item);
    }*/


    private void getId() {

    }
}
