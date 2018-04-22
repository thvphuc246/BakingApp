package com.example.vinhphuc.bakingapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.example.vinhphuc.bakingapp.R;

import java.util.Locale;

public final class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    private CommonUtils() {}

    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static String formatIngredient(Context context, String name, double quantity, String measure) {
        String line = context.getResources().getString(R.string.ingredient_line);
        String quantityString = String.format(Locale.UK, "%s", quantity);
        if (quantity == (long) quantity) {
            quantityString = String.format(Locale.UK, "%d", (long) quantity);
        }
        return String.format(Locale.UK, line, name, quantityString, measure);
    }

    /*
    Make part of the text Bold in android at runtime
    Source: https://stackoverflow.com/questions/10979821/
     */

    public static void setTextWithSpan(TextView textView, String fullText, String styledText, StyleSpan style) {
        SpannableStringBuilder sb = new SpannableStringBuilder(fullText);
        int start = fullText.indexOf(styledText);
        int end = start + styledText.length();
        sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(sb);
    }

    public static SpannableStringBuilder setTextWithSpan(String fullText, String styledText, StyleSpan style) {
        SpannableStringBuilder sb = new SpannableStringBuilder(fullText);
        int start = fullText.indexOf(styledText);
        int end = start + styledText.length();
        sb.setSpan(style, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return sb;
    }
}
