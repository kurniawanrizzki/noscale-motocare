package com.noscale.noscale_motocare.adapters;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.noscale.noscale_motocare.R;
import com.noscale.noscale_motocare.activities.MainActivity;
import com.noscale.noscale_motocare.models.Booking;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kurniawanrizzki on 21/01/18.
 */

public class BookingFrameAdapter extends RecyclerView.Adapter<BookingFrameAdapter.BookingViewHolder> {

    private List<Booking> bookingList;
    private MainActivity activity;

    public BookingFrameAdapter(MainActivity activity) {
        bookingList = new ArrayList<>();
        this.activity = activity;
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
        final Booking item = bookingList.get(position);

        if (item.getStatus().equals("done")) {
            holder.mStatusLayout.setBackgroundResource(R.drawable.bg_done);
            holder.deleteButtonLayout.setVisibility(View.GONE);
        } else if (item.getStatus().equals("canceled")) {
            holder.mStatusLayout.setBackgroundResource(R.drawable.bg_canceled);
            holder.deleteButtonLayout.setVisibility(View.GONE);
        } else {

            int hour = 8;

            if (!item.getSession().equals(activity.getString(R.string.date_first_session))) {
                hour = 13;
            }

            if (activity.getNotificationManager().isCancelable(item.getDate(),hour)) {
                holder.deleteButtonLayout.setVisibility(View.VISIBLE);
                holder.deleteButtonLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.getFragmentController().showAlertDialog(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    activity.getFragmentController().getMenuFragment().getBookingController().cancelBooking(item.getId());
                                    return;
                                }
                            }
                        },"Are you sure want to cancel your service at "+item.getGarage()+" ?", true);
                    }
                });
            } else {
                holder.deleteButtonLayout.setVisibility(View.GONE);
            }

            holder.mStatusLayout.setBackgroundResource(R.drawable.bg_pending);
        }

        holder.mBookingGarage.setText(item.getGarage());
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

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout mStatusLayout;
        private LinearLayout deleteButtonLayout;
        private TextView mStatusBooking;
        private TextView mBookingCode;
        private TextView mBookingGarage;
        private TextView mBookingService;
        private TextView mBookingDay;
        private TextView mBookingPrice;

        public BookingViewHolder(View itemView) {
            super(itemView);
            mStatusLayout = (LinearLayout) itemView.findViewById(R.id.status_layout);
            deleteButtonLayout = (LinearLayout) itemView.findViewById(R.id.delete_button_layout);
            mStatusBooking = (TextView) itemView.findViewById(R.id.status_booking);
            mBookingCode = (TextView) itemView.findViewById(R.id.booking_code);
            mBookingGarage = (TextView) itemView.findViewById(R.id.booking_garage);
            mBookingService = (TextView) itemView.findViewById(R.id.booking_service);
            mBookingDay = (TextView) itemView.findViewById(R.id.booking_day);
            mBookingPrice = (TextView) itemView.findViewById(R.id.booking_price);


        }
    }
}
