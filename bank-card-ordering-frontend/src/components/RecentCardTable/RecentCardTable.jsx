import React from "react";
import "./RecentCardTable.css";

export default function RecentCardTable({ requests }) {

  // Convert YYYY-MM-DD → DD/MM/YYYY
  const formatDate = (date) => {
    if (!date) return "--";
    const [y, m, d] = date.split("-");
    return `${d}/${m}/${y}`;
  };

  // Combine LocalDate + LocalTime safely
  const formatDateTime = (date, time) => {
    if (!date) return "--";

    const formattedDate = formatDate(date);

    if (!time) return formattedDate;  // if time not present

    return `${formattedDate} ${time}`;
  };

  return (
    <div className="table-container">
      <table className="custom-table">
        <thead>
          <tr>
            <th>Request ID</th>
            <th>Card Type</th>
            <th>Status</th>
            <th>Date</th>
          </tr>
        </thead>

        <tbody>
          {requests.length > 0 ? (
            requests.map((req) => (
              <tr key={req.requestId}>
                <td>{req.requestId}</td>
                <td>{req.cardType}</td>
                <td>{req.status}</td>

                {/* FIXED DATE DISPLAY */}
                <td>
                  {formatDateTime(req.createdDate)}
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4" style={{ textAlign: "center" }}>
                No requests found.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}