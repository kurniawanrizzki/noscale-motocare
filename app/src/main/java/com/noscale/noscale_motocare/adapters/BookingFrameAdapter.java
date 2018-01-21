package com.noscale.noscale_motocare.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.controllers.BookingController;
import com.noscale.noscale_motocare.models.Booking;
import com.noscale.noscale_motocare.utils.Global;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class BookingFrameAdapter extends RecyclerView.Adapter<BookingFrameAdapter.BookingViewHolder> {

    private Context context;
    private BookingController controller;
    private List<Booking> bookingList;
    private String tag;

    public BookingFrameAdapter(Context context, BookingController controller, String tag) {
        this.context = context;
        this.controller = controller;
        this.tag = tag;
        bookingList = new ArrayList<>();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BookingFrameAdapter.BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_layout, parent, false);
        BookingViewHolder viewHolder = new BookingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        Booking item = bookingList.get(position);

        if (item.getStatus().equals("done")) {
            holder.mStatusLayout.setBackgroundResource(R.drawable.bg_accepted);
        } else {
            holder.mStatusLayout.setBackgroundResource(R.drawable.bg_pending);
        }

        holder.mStatusBooking.setText(item.getStatus());
        holder.mBookingCode.setText(item.getBookingCode());
        holder.mBookingService.setText(item.getServices());
        holder.mBookingDay.setText(item.getDate());
        holder.mBookingPrice.setText(formatRupiah.format(item.getPrice()));

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList (List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mStatusLayout;
        private TextView mStatusBooking;
        private TextView mBookingCode;
        private TextView mBookingService;
        private TextView mBookingDay;
        private TextView mBookingPrice;

        public BookingViewHolder(View itemView) {
            super(itemView);
            mStatusLayout = (LinearLayout) itemView.findViewById(R.id.status_layout);
            mStatusBooking = (TextView) itemView.findViewById(R.id.status_booking);
            mBookingCode = (TextView) itemView.findViewById(R.id.booking_code);
            mBookingService = (TextView) itemView.findViewById(R.id.booking_service);
            mBookingDay = (TextView) itemView.findViewById(R.id.booking_day);
            mBookingPrice = (TextView) itemView.findViewById(R.id.booking_price);
        }
    }
}
