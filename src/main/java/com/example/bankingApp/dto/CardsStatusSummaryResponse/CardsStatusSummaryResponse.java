package com.example.bankingApp.dto.CardsStatusSummaryResponse;

import com.example.bankingApp.entity.Enums.Status;

public class CardsStatusSummaryResponse {

    private long pending;
    private long approved;
    private long rejected;
    private long printed;
    private long dispatched;
    private long delivered;
    private long cancelled;

    public CardsStatusSummaryResponse(long pending, long approved, long rejected, long printed, long dispatched, long delivered, long cancelled) {
        this.pending = pending;
        this.approved = approved;
        this.rejected = rejected;
        this.printed = printed;
        this.dispatched = dispatched;
        this.delivered = delivered;
        this.cancelled = cancelled;
    }

    public CardsStatusSummaryResponse() {
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public long getApproved() {
        return approved;
    }

    public void setApproved(long approved) {
        this.approved = approved;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }

    public long getPrinted() {
        return printed;
    }

    public void setPrinted(long printed) {
        this.printed = printed;
    }

    public long getDispatched() {
        return dispatched;
    }

    public void setDispatched(long dispatched) {
        this.dispatched = dispatched;
    }

    public long getDelivered() {
        return delivered;
    }

    public void setDelivered(long delivered) {
        this.delivered = delivered;
    }

    public long getCancelled() {
        return cancelled;
    }

    public void setCancelled(long cancelled) {
        this.cancelled = cancelled;
    }
}
