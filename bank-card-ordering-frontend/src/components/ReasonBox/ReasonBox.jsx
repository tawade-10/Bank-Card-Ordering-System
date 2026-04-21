import React, { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./ReasonBox.css";

export default function ReasonBox({ actionType, onSubmit, onCancel }) {
  const [reason, setReason] = useState("");

  const handleSubmit = () => {
    if (reason.trim().length === 0) {
      toast.error("Reason cannot be empty!", {
        position: "top-right",
      });
      return;
    }

    toast.success("Submitted successfully!", {
      position: "top-right",
    });

    setTimeout(() => {
      onSubmit(reason);
    }, 300);
  };

  return (
    <div className="reason-box">
      <ToastContainer />

      <textarea
        placeholder={`Enter reason for ${actionType.toLowerCase()}`}
        value={reason}
        onChange={(e) => setReason(e.target.value)}
      />

      <div className="reason-actions">
        <button className="btn-submit" onClick={handleSubmit}>
          Submit
        </button>

        <button className="btn-cancel" onClick={onCancel}>
          Cancel
        </button>
      </div>
    </div>
  );
}