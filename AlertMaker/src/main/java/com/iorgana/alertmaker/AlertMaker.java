package com.iorgana.alertmaker;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

public class AlertMaker {
    public enum AlertType {
        Primary, Secondary, Info, Success, Warning, Danger
    }

    private final Context context;
    private AlertMaker.AlertType alertType = AlertMaker.AlertType.Primary;
    private String title;
    private String content;

    // By default, alert is not cancelable
    private boolean isCancelable = false;
    private String lastError;

    // Alert Props
    private int marginTop=0;
    private int marginBottom=0;
    private int marginStart=0;
    private int marginEnd=0;



    public AlertMaker(Context context){
        this.context = context;
    }

    /**
     * Set Alert AlertType
     */
    public AlertMaker setType(AlertMaker.AlertType alertType) {
        this.alertType = alertType;
        return this;
    }

    /**
     * Set Label
     */
    public AlertMaker setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Set Content
     */
    public AlertMaker setContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Set Is Cancelable
     */
    public AlertMaker setCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    /**
     * Set Alert Margins
     */
    public AlertMaker setMargins(int marginTop, int marginBottom, int marginStart, int marginEnd){
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.marginStart = marginStart;
        this.marginEnd = marginEnd;
        return this;
    }

    /**
     * Set Margin Top
     */
    public AlertMaker setMarginTop(int marginTop){
        this.marginTop = marginTop;
        return this;
    }
    /**
     * Set Margin Bottom
     */
    public AlertMaker setMarginBottom(int marginBottom){
        this.marginBottom = marginBottom;
        return this;
    }

    /**
     * Get Last Error
     */
    public String getLastError(){
        return this.lastError;
    }

    /**
     * Show Alert
     */
    public LinearLayout build(){
        if(this.title==null || this.content==null){
            this.lastError = "No title or content is setup, You must at least provide title or content";
            return null;
        }


        // Get Layout Views
        LinearLayout alertLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_alert, null);
        TextView alertTitle = alertLayout.findViewById(R.id.alertTitle);
        TextView alertContent = alertLayout.findViewById(R.id.alertContent);
        ImageButton closeBtn = alertLayout.findViewById(R.id.closeBtn);
        LinearLayout closeBtnContainer = alertLayout.findViewById(R.id.closeBtnContainer);

        // Get Alert Styles
        Drawable alertBackground;
        int textColor;

        // Set layout style
        switch (this.alertType){
            case Secondary:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_secondary, null);
                textColor = context.getResources().getColor(R.color.color_secondary);
                break;
            case Info:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_info, null);
                textColor = context.getResources().getColor(R.color.color_info);
                break;
            case Success:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_success, null);
                textColor = context.getResources().getColor(R.color.color_success);
                break;
            case Warning:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_warning, null);
                textColor = context.getResources().getColor(R.color.color_warning);
                break;
            case Danger:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_danger, null);
                textColor = context.getResources().getColor(R.color.color_danger);
                break;
            default:
                alertBackground = ResourcesCompat.getDrawable(context.getResources(), R.drawable.selector_alert_primary, null);
                textColor = context.getResources().getColor(R.color.color_primary);
                break;

        }

        // Set Alert Style
        alertLayout.setBackground(alertBackground);
        alertTitle.setTextColor(textColor);
        alertContent.setTextColor(textColor);

        // Set layout margins
        LinearLayout.LayoutParams layoutParamsNew = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsNew.setMarginEnd(marginEnd);
        layoutParamsNew.setMarginStart(marginStart);
        layoutParamsNew.topMargin = marginTop;
        layoutParamsNew.bottomMargin = marginBottom;

        alertLayout.setLayoutParams(layoutParamsNew);



        // Set Alert Data
        if(this.title!=null){
            alertTitle.setText(this.title);
        }else{
            alertTitle.setVisibility(View.GONE);
        }
        if(this.content!=null){
            alertContent.setText(this.content);
        }else{
            alertContent.setVisibility(View.GONE);
        }

        if(this.isCancelable){
            closeBtnContainer.setVisibility(View.VISIBLE);
            closeBtn.setOnClickListener(view-> alertLayout.setVisibility(View.GONE));
        }else{
            LinearLayout closeParent = (LinearLayout) closeBtn.getParent();
            closeParent.setVisibility(View.GONE);
        }

        return alertLayout;
    }


}