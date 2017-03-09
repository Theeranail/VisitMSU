package com.example.visitmsu.visitmsu.model;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitmsu.visitmsu.DetailSubActivity;
import com.example.visitmsu.visitmsu.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class CreateFuction {

    public Context context;
    public static RelativeLayout progressber;

    public CreateFuction(Context context) {
        this.context = context;
    }

    public void AlertDialogOK(String msg) {

        AlertDialog.Builder dbuilder = new AlertDialog.Builder(context);
        dbuilder.setIcon(R.drawable.ic_error_outline_black_24dp);
        dbuilder.setMessage(msg);
        dbuilder.setPositiveButton("ตกลง", null);
        dbuilder.show();

    }

    public static void Showloading(Context context, View v) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v =  inflater.inflate(R.layout.content_register,null,true);
        progressber = (RelativeLayout) v.findViewById(R.id.contaiberprogressbar);
        progressber.setVisibility(View.VISIBLE);
    }

    public static void Dissmissloading(Context context,View v) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v =  inflater.inflate(R.layout.content_register,null,true);
        progressber = (RelativeLayout) v.findViewById(R.id.contaiberprogressbar);
        if (progressber.getVisibility() == View.VISIBLE) {
            progressber.setVisibility(View.GONE);
        }
    }

    public static int splitpositionmarkger(String position) {
        int i = 0;
        String[] str = position.split("m");
        i = Integer.valueOf(str[1]).intValue();
        return i;
    }

    public static int splitsnippet(String snippet) {
        int i = 0;

        String[] str = snippet.split(",");
        i = Integer.valueOf(splitSTRtoInt(str[1])).intValue();
        return i;
    }

    public static int splitSTRtoInt(String str) {
        int i = 0;
        String[] s = str.split("M");

        i = Integer.valueOf(s[s.length - 1]).intValue();
        return i;
    }

    public class ColorOptionsView extends LinearLayout {

        public ColorOptionsView(Context context) {
            super(context);
        }
    }

    public void DialogAlertPhone(final String numberphone) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("เเจ้งเตือน อาจมีค่าใช้จ่าย");
        alertDialogBuilder.setMessage("โทรออก:" + numberphone);
        alertDialogBuilder.setPositiveButton("โทร.",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intentCall = new Intent(Intent.ACTION_CALL);
                        intentCall.setData(Uri.parse("tel:" + numberphone));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(intentCall);
                    }
                });

        alertDialogBuilder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void DialogAlertSendmail(final String Tomail, final  String nameTo) {

        final AlertDialog.Builder dai_builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_sendmail, null);

        final TextView textViewMail = (TextView) view.findViewById(R.id.text_tomail);
        final TextView textViewName = (TextView) view.findViewById(R.id.text_tomailname);
        final TextView TextViewdetail = (TextView) view.findViewById(R.id.edittext_detailmail);
        final EditText edittext_subject = (EditText) view.findViewById(R.id.edittext_subject);
        Button btnSendmail = (Button)view.findViewById(R.id.btn_sendmail);

        textViewMail.setText("ถึง: "+ Tomail);
        textViewName.setText(nameTo);

        dai_builder.setTitle("Send Mail");
        dai_builder.setView(view);

        dai_builder.setNegativeButton("ปิด", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        btnSendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {Tomail};
                String[] CC = {""};
                Intent intentGotomail = new Intent(Intent.ACTION_SEND);
                intentGotomail.setData(Uri.parse("mailto:"));
                intentGotomail.setType("message/rfc822");
                intentGotomail.putExtra(Intent.EXTRA_EMAIL,TO);
                intentGotomail.putExtra(Intent.EXTRA_SUBJECT, edittext_subject.getText().toString());
                intentGotomail.putExtra(Intent.EXTRA_TEXT, TextViewdetail.getText().toString());

                try{

                    context.startActivity(Intent.createChooser(intentGotomail,"Choose an Email client"));

                }catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        final AlertDialog ad = dai_builder.show();

    }

    public Bitmap textBitmap(String text, int textWidth, int textSize) {
        TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

// Create bitmap and canvas to draw to
        Bitmap b = Bitmap.createBitmap(textWidth, mTextLayout.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);

// Draw background
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.LINEAR_TEXT_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        c.drawPaint(paint);

// Draw text
        c.save();
        c.translate(0, 0);
        mTextLayout.draw(c);
        c.restore();

        return b;
    }
}
